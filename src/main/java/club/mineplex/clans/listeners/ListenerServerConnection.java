package club.mineplex.clans.listeners;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.utils.UtilReference;
import club.mineplex.clans.utils.UtilText;
import io.netty.channel.local.LocalAddress;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class ListenerServerConnection {

    private final List<String> mineplexAddresses = Arrays.asList(
            "173.236.67.11",
            "173.236.67.12",
            "173.236.67.13",
            "173.236.67.14",
            "173.236.67.15",
            "173.236.67.16",
            "173.236.67.17",
            "173.236.67.18",
            "173.236.67.19",
            "173.236.67.20",
            "173.236.67.21",
            "173.236.67.22",
            "173.236.67.23",
            "173.236.67.24",
            "173.236.67.25",
            "173.236.67.26",
            "173.236.67.27",
            "173.236.67.28",
            "173.236.67.29",
            "173.236.67.30",
            "173.236.67.31",
            "173.236.67.32",
            "173.236.67.33",
            "173.236.67.34",
            "173.236.67.35",
            "173.236.67.36",
            "173.236.67.37",
            "173.236.67.38",
            "96.45.82.193",
            "96.45.82.3",
            "96.45.83.216",
            "96.45.83.38",
            "173.236.67.101",
            "173.236.67.102",
            "173.236.67.103",
            "107.6.151.174",
            "107.6.151.190",
            "107.6.151.206",
            "107.6.151.210",
            "107.6.151.22",
            "107.6.176.114",
            "107.6.176.122",
            "107.6.176.138",
            "107.6.176.14",
            "107.6.176.166",
            "107.6.176.194"
    );
    private final ClientData data = ClansMod.getInstance().getClientData();

    @SubscribeEvent
    public void onServer(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.manager.getRemoteAddress() instanceof LocalAddress) {
            return;
        }

        data.setOnMultiplayer(true);

        final InetSocketAddress address = (InetSocketAddress) event.manager.getRemoteAddress();
        String ip = address.getHostString().toLowerCase();
        if (ip.endsWith(".")) {
            ip = ip.substring(0, ip.length() - 1);
        }

        data.setMineplex(ip.endsWith("mineplex.com") || mineplexAddresses.contains(ip));
        data.setMultiplayerIP(ip);
        checkModVersion();
    }

    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        data.handleServerDisconnect(event);
    }

    private void checkModVersion() {
        if (!data.isLatestVersion()) {
            return;
        }

        UtilText.sendPlayerMessage("&cThere is a new &4&lClans Mod &r&cversion available!");
        UtilText.sendPlayerMessage("&cYour version: &7" + UtilReference.VERSION);
        UtilText.sendPlayerMessage("&cNewer version: &7" + data.getLatestVersion());
        UtilText.sendPlayerMessage("&cYou can download it at &4" + UtilReference.DOWNLOAD);
        UtilText.sendPlayerMessage("&cYou can view the source code at &4" + UtilReference.GITHUB);
    }

}
