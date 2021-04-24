package club.mineplex.clans;

import club.mineplex.clans.modules.mineplex_server.ServerType;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ClientData {

    private static final String DEFAULT_MINEPLEX_SERVER = "Unknown Server";
    private static final String DEFAULT_MULTIPLAYER_SERVER = "Unknown Server";

    private ServerType mineplexServerType = ServerType.UNKNOWN;
    private String mineplexServer = DEFAULT_MINEPLEX_SERVER;
    private boolean isMineplex = false;

    private String multiplayerIP = DEFAULT_MULTIPLAYER_SERVER;
    private boolean onMultiplayer = false;

    public void handleServerDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.multiplayerIP = ClientData.DEFAULT_MULTIPLAYER_SERVER;
        this.mineplexServer = ClientData.DEFAULT_MINEPLEX_SERVER;
        this.onMultiplayer = false;
        this.isMineplex = false;
    }

    public static String getDefaultMineplexServer() {
        return DEFAULT_MINEPLEX_SERVER;
    }

    public static String getDefaultMultiplayerServer() {
        return DEFAULT_MULTIPLAYER_SERVER;
    }

    public ServerType getMineplexServerType() {
        return mineplexServerType;
    }

    public void setMineplexServerType(final ServerType mineplexServerType) {
        this.mineplexServerType = mineplexServerType;
    }

    public String getMineplexServer() {
        return mineplexServer;
    }

    public void setMineplexServer(final String mineplexServer) {
        this.mineplexServer = mineplexServer;
    }

    public boolean isMineplex() {
        return isMineplex;
    }

    public void setMineplex(final boolean mineplex) {
        isMineplex = mineplex;
    }

    public String getMultiplayerIP() {
        return multiplayerIP;
    }

    public void setMultiplayerIP(final String multiplayerIP) {
        this.multiplayerIP = multiplayerIP;
    }

    public boolean isOnMultiplayer() {
        return onMultiplayer;
    }

    public void setOnMultiplayer(final boolean onMultiplayer) {
        this.onMultiplayer = onMultiplayer;
    }
}
