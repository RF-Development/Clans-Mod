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

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {
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

        if (SettingsHandler.getSettings(DiscordSettings.class).getDisplayRichStatus()) {

            if (!this.running) start();

            ClientData data = ClansMod.getInstance().getClientData();
            String server;

            if (data.onMultiplayer)  {

                if (!SettingsHandler.getSettings(DiscordSettings.class).getDisplayServer()) {
                    server = "Multiplayer";
                } else {
                    if (data.isMineplex && SettingsHandler.getSettings(DiscordSettings.class).getDisplayMineplexServer())
                        server = "Mineplex " + (data.mineplexServerType.equals(ServerType.STAFF) ? "Private Server" : data.mineplexServer);
                    else
                        server = data.multiplayerIP;
                }

            } else {
                server = "Main Menu";
            }

            DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(server);
            b.setDetails(data.onMultiplayer ? "In-Game" : "");
            b.setBigImage("mporange", "");
            b.setStartTimestamps(created);

            DiscordRPC.discordUpdatePresence(b.build());

        } else if (this.running) {

            this.shutdown();

        }

    }

}
