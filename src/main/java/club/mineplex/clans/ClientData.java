package club.mineplex.clans;

import club.mineplex.clans.chat_listeners.ChatListener;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ClientData {

    public static final String defaultMineplexServer = "Unknown Server";
    public static final String defaultMultiplayerServer = "Unknown Server";

    public ServerType mineplexServerType = ServerType.UNKNOWN;
    public String mineplexServer = defaultMineplexServer;
    public boolean isMineplex = false;

    public String multiplayerIP = defaultMultiplayerServer;
    public boolean onMultiplayer = false;

    public void handleServerDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.multiplayerIP = ClientData.defaultMultiplayerServer;
        this.mineplexServer = ClientData.defaultMineplexServer;
        this.onMultiplayer = false;
        this.isMineplex = false;
    }

    public void handleServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handleChecks();
            }
        }, 2000L);
    }

    public void handleChecks() {
        BlockingQueue<ChatListener> checks = new LinkedBlockingQueue<>(Arrays.asList(
        ));

        int checksMade = 1;
        while (!checks.isEmpty()) {

            try {
                ChatListener listener = checks.poll(2, TimeUnit.SECONDS);
                if (listener == null || !isMineplex && listener.isMineplexOnly()) continue;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        listener.startChecking();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                listener.stopAndHandle();
                            }
                        }, 2500L);

                    }
                }, checksMade * 1000L);

                checksMade++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
