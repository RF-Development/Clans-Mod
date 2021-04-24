package club.mineplex.clans;

import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.DiscordSettings;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.util.Timer;
import java.util.TimerTask;

public class DiscordIntegration {

    private boolean running = true;
    private long created = 0;

    private Timer updateTask = null;

    public void start() {
        this.created = System.currentTimeMillis();

        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {
            System.out.println("Loaded Discord Integration! [@" + discordUser.username + "#" + discordUser.discriminator + "]");
            update();
        }).build();

        running = true;
        DiscordRPC.discordInitialize("798391471773319209", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) DiscordRPC.discordRunCallbacks();
            }
        }.start();

        if (updateTask == null) {
            updateTask = new Timer();
            updateTask.schedule(new TimerTask() {
                @Override
                public void run() {
                    update();
                }
            }, 0, 1000L);
        }

    }

    public void shutdown() {
        this.running = false;
        DiscordRPC.discordShutdown();
        System.out.println("Disabled Discord Integration!");
    }

    private void update() {
        final DiscordSettings discordSettings = SettingsHandler.getInstance().getSettingThrow(DiscordSettings.class);
        if (discordSettings.getDisplayRichStatus()) {

            if (!this.running) start();

            final ClientData data = ClansMod.getInstance().getClientData();
            final String server;

            if (data.isOnMultiplayer()) {

                if (!discordSettings.getDisplayServer()) {
                    server = "Multiplayer";
                } else {
                    if (data.isMineplex() && discordSettings.getDisplayMineplexServer())
                        server = "Mineplex " + (data.getMineplexServerType().equals(ServerType.STAFF) ? "Private Server" : data.getMineplexServer());
                    else
                        server = data.getMultiplayerIP();
                }

            } else {
                server = "Main Menu";
            }

            final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(server);
            builder.setDetails(data.isOnMultiplayer() ? "In-Game" : "");
            builder.setBigImage("mporange", "");
            builder.setStartTimestamps(created);

            DiscordRPC.discordUpdatePresence(builder.build());

        } else if (this.running) {

            this.shutdown();

        }

    }

}
