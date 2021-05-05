package club.mineplex.clans.modules.mineplex_server;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.ModModule;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleMineplexServerHandler extends ModModule {

    private static final Pattern SWITCHED_SERVER_REGEX = Pattern.compile(".*> .* sent from ([A-z]+-[\\d]+) to ([A-z]+-[\\d]+)\\.");
    private static final Pattern MINEPLEX_SERVER_REGEX = Pattern.compile("[A-z]+-[\\d]+");

    private boolean running = false;

    public ModuleMineplexServerHandler() {
        super("Mineplex Server Checker");

        start();
    }

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent e) {
        final String message = e.message.getUnformattedText();

        if (!message.matches(SWITCHED_SERVER_REGEX.pattern())) {
            return;
        }

        final Matcher matcher = SWITCHED_SERVER_REGEX.matcher(message);
        if (matcher.find()) {
            update(matcher.group(2));
        }
    }

    private void start() {
        if (running) {
            return;
        }

        running = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!running) {
                    cancel();
                }

                final Optional<IChatComponent> tabHeaderOpt = getTabHeader();
                String server = ClientData.getDefaultMineplexServer();

                try {

                    /* TABLIST */
                    if (tabHeaderOpt.isPresent()) {
                        final String tabHeaderText = tabHeaderOpt.get().getUnformattedText();
                        server = tabHeaderText.substring(tabHeaderText.lastIndexOf(" ") + 3);
                    } else { /* SCOREBOARD */
                        final Optional<IChatComponent> scoreboardOpt = getScoreboard();
                        if (scoreboardOpt.isPresent()) {
                            server = scoreboardOpt.get().getUnformattedText();
                        }
                    }

                } catch (final StringIndexOutOfBoundsException e) {
                    /* SCOREBOARD */
                    final Optional<IChatComponent> scoreboardOpt = getScoreboard();
                    if (scoreboardOpt.isPresent()) {
                        server = scoreboardOpt.get().getUnformattedText();
                    }
                }

                if (server.matches(MINEPLEX_SERVER_REGEX.pattern())) {
                    getScoreboard();
                    update(server);
                }

            }
        }, 0, 1000L);
    }

    private void update(final String server) {
        data.setMineplexServer(server);
        final String compare = server.toLowerCase();

        ServerType serverType;
        if (compare.startsWith("lobby-")) {
            serverType = ServerType.LOBBY;
        } else if (compare.startsWith("clanshub-")) {
            serverType = ServerType.CLANSHUB;
        } else if (compare.startsWith("clans-")) {
            serverType = ServerType.CLANS;
        } else if (compare.startsWith("staff-")) {
            serverType = ServerType.STAFF;
        } else {
            serverType = ServerType.GAME;
        }

        if (server.equalsIgnoreCase(ClientData.getDefaultMineplexServer())) {
            serverType = ServerType.UNKNOWN;
        }
        data.setMineplexServerType(serverType);
    }

    private Optional<IChatComponent> getTabHeader() {
        if (ClansMod.getInstance().getMinecraft().ingameGUI == null || !ClansMod.getInstance().getClientData().isMineplex()) {
            return Optional.empty();
        }

        try {
            final GuiPlayerTabOverlay tabList = ClansMod.getInstance().getMinecraft().ingameGUI.getTabList();
            final Field headerField = tabList.getClass().getDeclaredField("header");
            headerField.setAccessible(true);
            final IChatComponent header = (IChatComponent) headerField.get(tabList);
            return Optional.ofNullable(header);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<IChatComponent> getScoreboard() {

        try {
            if (!ClansMod.getInstance().getClientData().isMineplex()) {
                return Optional.empty();
            }

            final Scoreboard sb = ClansMod.getInstance().getMinecraft().theWorld.getScoreboard();
            if (sb == null) {
                return Optional.empty();
            }

            final ScoreObjective obj = sb.getObjectiveInDisplaySlot(1);
            final List<String> names = new ArrayList<>();

            for (final Score score : sb.getSortedScores(obj)) {
                String name = "";
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                if (team != null) {
                    name += team.getColorPrefix();
                }
                name += score.getPlayerName();
                if (team != null) {
                    name += team.getColorSuffix();
                }
                names.add(name);
            }

            for (final String name : names) {
                final Matcher matcher = MINEPLEX_SERVER_REGEX.matcher(name);

                if (matcher.find()) {
                    return Optional.of(
                            new ChatComponentText(matcher.group())
                    );
                }

            }

        } catch (final Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

}
