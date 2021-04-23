package club.mineplex.clans.modules.mineplex_server;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.ModModule;
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

public class MineplexServerHandler extends ModModule {

    private static final Pattern switchedServerRegex = Pattern.compile(".*> .* sent from ([A-z]+-[\\d]+) to ([A-z]+-[\\d]+)\\.");
    private static final Pattern mineplexServerRegex = Pattern.compile("[A-z]+-[\\d]+");

    private boolean running = false;

    public MineplexServerHandler() {
        super("Mineplex Server Checker");

        this.start();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();

        if (!message.matches(switchedServerRegex.pattern())) return;

        Matcher matcher = switchedServerRegex.matcher(message);
        if (!matcher.find()) return;

        update(matcher.group(2));
    }

    public void start() {
        if (this.running) return;

        this.running = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!running) this.cancel();

                IChatComponent component = getTabHeader();
                String server = ClientData.defaultMineplexServer;

                try {

                    /* TABLIST */
                    if (component != null)  {
                        server = component.getUnformattedText().substring(component.getUnformattedText().lastIndexOf(" ") + 3);
                    } else { /* SCOREBOARD */
                        IChatComponent sb = getScoreboard();
                        if (sb != null) server = sb.getUnformattedText();
                    }

                } catch (StringIndexOutOfBoundsException e) {
                    /* SCOREBOARD */
                    IChatComponent sb = getScoreboard();
                    if (sb != null) server = sb.getUnformattedText();
                }

                getScoreboard();
                update(server);

            }
        }, 0, 1000L);
    }

    private void update(String server) {
        data.mineplexServer = server;
        String compare = server.toLowerCase();

        ServerType serverType;
        if (compare.startsWith("lobby-")) serverType = ServerType.LOBBY;
        else if (compare.startsWith("clanshub-")) serverType = ServerType.CLANSHUB;
        else if (compare.startsWith("clans-")) serverType  = ServerType.CLANS;
        else if (compare.startsWith("staff-")) serverType = ServerType.STAFF;
        else serverType = ServerType.GAME;

        if (server.equalsIgnoreCase(ClientData.defaultMineplexServer)) serverType = ServerType.UNKNOWN;
        data.mineplexServerType = serverType;
    }

    private IChatComponent getTabHeader() {
        try {
            if (ClansMod.getInstance().getMinecraft().ingameGUI == null) return null;
            if (!ClansMod.getInstance().getClientData().isMineplex) return null;

            Field header = ClansMod.getInstance().getMinecraft().ingameGUI.getTabList().getClass().getDeclaredField("header");
            header.setAccessible(true);
            return (IChatComponent) header.get(ClansMod.getInstance().getMinecraft().ingameGUI.getTabList());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    private IChatComponent getScoreboard() {

        try {
            if (!ClansMod.getInstance().getClientData().isMineplex) return null;

            Scoreboard sb = ClansMod.getInstance().getMinecraft().theWorld.getScoreboard();
            if (sb == null) return null;

            ScoreObjective obj = sb.getObjectiveInDisplaySlot(1);
            Collection<Score> scores = sb.getSortedScores(obj);
            List<String> names = new ArrayList<>();

            scores.forEach((s) ->  {
                String name = "";
                ScorePlayerTeam team = sb.getPlayersTeam(s.getPlayerName());
                if (team != null) name += team.getColorPrefix();
                name += s.getPlayerName();
                if (team != null) name += team.getColorSuffix();
                names.add(name);
            });

            for (String name  : names) {
                Matcher matcher = mineplexServerRegex.matcher(name);

                if (matcher.find()) {
                    return new ChatComponentText(matcher.group());
                }

            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

}
