package club.mineplex.clans.chat_listeners;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.ModModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ChatListener extends ModModule {

    protected final ClientData data = ClansMod.getInstance().getClientData();

    private final String[] commandsToExecute;
    private boolean checking = false, success = false;
    private final boolean mineplexOnly;

    public ChatListener(boolean mineplexOnly, String name, String... commandsToExecute) {
        super(name);

        this.mineplexOnly = mineplexOnly;
        this.commandsToExecute = commandsToExecute;
    }

    protected abstract boolean handle(ClientChatReceivedEvent e);

    public boolean isMineplexOnly() {
        return mineplexOnly;
    }

    @SideOnly(Side.CLIENT)
    public void startChecking() {
        if (this.checking) return;

        this.checking = true;
        this.success = false;

        for (String s : commandsToExecute) {
            if (ClansMod.getInstance().getMinecraft().thePlayer == null || s == null) continue;
            ClansMod.getInstance().getMinecraft().thePlayer.sendChatMessage(s);
        }
    }

    public void stopChecking() {
        this.checking = false;
    }

    public boolean stopAndHandle() {

        this.stopChecking();
        boolean toReturn = success;

        success = false;
        return toReturn;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if (!this.checking || this.success) return;

        success = handle(e);
    }

}
