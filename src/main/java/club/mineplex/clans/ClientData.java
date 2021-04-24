package club.mineplex.clans;

import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.utils.UtilReference;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ClientData {

    private static final String DEFAULT_MINEPLEX_SERVER = "Unknown Server";
    private static final String DEFAULT_MULTIPLAYER_SERVER = "Unknown Server";

    private ServerType mineplexServerType = ServerType.UNKNOWN;
    private String mineplexServer = DEFAULT_MINEPLEX_SERVER;
    private boolean isMineplex = false;

    private String multiplayerIP = DEFAULT_MULTIPLAYER_SERVER;
    private boolean onMultiplayer = false;

    private String latestRequiredVersion = UtilReference.VERSION;
    private String latestVersion = UtilReference.VERSION;
    private boolean hasLatestRequired = true;

    public static String getDefaultMineplexServer() {
        return DEFAULT_MINEPLEX_SERVER;
    }

    public static String getDefaultMultiplayerServer() {
        return DEFAULT_MULTIPLAYER_SERVER;
    }

    public void handleServerDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        multiplayerIP = ClientData.DEFAULT_MULTIPLAYER_SERVER;
        mineplexServer = ClientData.DEFAULT_MINEPLEX_SERVER;
        onMultiplayer = false;
        isMineplex = false;
    }

    public ServerType getMineplexServerType() {
        return mineplexServerType;
    }

    public void setMineplexServerType(final ServerType mineplexServerType) {
        this.mineplexServerType = mineplexServerType;
    }

    String getMineplexServer() {
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

    String getMultiplayerIP() {
        return multiplayerIP;
    }

    public void setMultiplayerIP(final String multiplayerIP) {
        this.multiplayerIP = multiplayerIP;
    }

    boolean isOnMultiplayer() {
        return onMultiplayer;
    }

    public void setOnMultiplayer(final boolean onMultiplayer) {
        this.onMultiplayer = onMultiplayer;
    }

    public String getLatestRequiredVersion() {
        return latestRequiredVersion;
    }

    public void setLatestRequiredVersion(final String latestRequiredVersion) {
        this.latestRequiredVersion = latestRequiredVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public boolean isLatestVersion() {
        return UtilReference.VERSION.equals(latestVersion);
    }

    public void setLatestVersion(final String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public boolean hasLatestRequiredVersion() {
        return hasLatestRequired;
    }

    public void setHasLatestRequiredVersion(final boolean hasLatestRequired) {
        this.hasLatestRequired = hasLatestRequired;
    }

}
