package club.mineplex.clans.listeners;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import io.netty.channel.local.LocalAddress;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class ListenerServerConnection {

    private final ArrayList<String> mineplexAddresses = new ArrayList<String>();
    private final ClientData data = ClansMod.getInstance().getClientData();

    public ListenerServerConnection() {
        this.mineplexAddresses.add("173.236.67.11");
        this.mineplexAddresses.add("173.236.67.12");
        this.mineplexAddresses.add("173.236.67.13");
        this.mineplexAddresses.add("173.236.67.14");
        this.mineplexAddresses.add("173.236.67.15");
        this.mineplexAddresses.add("173.236.67.16");
        this.mineplexAddresses.add("173.236.67.17");
        this.mineplexAddresses.add("173.236.67.18");
        this.mineplexAddresses.add("173.236.67.19");
        this.mineplexAddresses.add("173.236.67.20");
        this.mineplexAddresses.add("173.236.67.21");
        this.mineplexAddresses.add("173.236.67.22");
        this.mineplexAddresses.add("173.236.67.23");
        this.mineplexAddresses.add("173.236.67.24");
        this.mineplexAddresses.add("173.236.67.25");
        this.mineplexAddresses.add("173.236.67.26");
        this.mineplexAddresses.add("173.236.67.27");
        this.mineplexAddresses.add("173.236.67.28");
        this.mineplexAddresses.add("173.236.67.29");
        this.mineplexAddresses.add("173.236.67.30");
        this.mineplexAddresses.add("173.236.67.31");
        this.mineplexAddresses.add("173.236.67.32");
        this.mineplexAddresses.add("173.236.67.33");
        this.mineplexAddresses.add("173.236.67.34");
        this.mineplexAddresses.add("173.236.67.35");
        this.mineplexAddresses.add("173.236.67.36");
        this.mineplexAddresses.add("173.236.67.37");
        this.mineplexAddresses.add("173.236.67.38");
        this.mineplexAddresses.add("96.45.82.193");
        this.mineplexAddresses.add("96.45.82.3");
        this.mineplexAddresses.add("96.45.83.216");
        this.mineplexAddresses.add("96.45.83.38");
        this.mineplexAddresses.add("173.236.67.101");
        this.mineplexAddresses.add("173.236.67.102");
        this.mineplexAddresses.add("173.236.67.103");
        this.mineplexAddresses.add("107.6.151.174");
        this.mineplexAddresses.add("107.6.151.190");
        this.mineplexAddresses.add("107.6.151.206");
        this.mineplexAddresses.add("107.6.151.210");
        this.mineplexAddresses.add("107.6.151.22");
        this.mineplexAddresses.add("107.6.176.114");
        this.mineplexAddresses.add("107.6.176.122");
        this.mineplexAddresses.add("107.6.176.138");
        this.mineplexAddresses.add("107.6.176.14");
        this.mineplexAddresses.add("107.6.176.166");
        this.mineplexAddresses.add("107.6.176.194");
    }

    @SubscribeEvent
    public void onServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if (e.manager.getRemoteAddress() instanceof LocalAddress) return;

        data.onMultiplayer = true;

        InetSocketAddress address = (InetSocketAddress) e.manager.getRemoteAddress();
        String IP = address.getHostString().toLowerCase();
        if (IP.endsWith(".")) IP = IP.substring(0, IP.length() - 1);

        data.isMineplex = IP.endsWith("mineplex.com") || mineplexAddresses.contains(IP);
        data.multiplayerIP = IP;
        data.handleServerConnect(e);
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        data.handleServerDisconnect(e);
    }

}
