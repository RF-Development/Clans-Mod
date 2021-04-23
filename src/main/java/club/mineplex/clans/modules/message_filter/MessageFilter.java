package club.mineplex.clans.modules.message_filter;

import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.MineplexSettings;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageFilter extends ModModule {

    public MessageFilter() {
        super("Message Filter");
    }

    @SubscribeEvent
    public void onMessageReceived(ClientChatReceivedEvent event) {
        if (!isEnabled() || !data.isMineplex) return;
        boolean cancel = false;

        // Clans
        if (data.mineplexServerType == ServerType.CLANS) {
            if (event.message.getUnformattedText().startsWith("Clans> You cannot harm")) cancel = true;
            if (event.message.getUnformattedText().startsWith("Clan Search> Too many players matched. Try a more specific search")) cancel = true;
            if (event.message.getUnformattedText().startsWith("Clan Search> No clans matched. Try a more specific search")) cancel = true;
            if (event.message.getUnformattedText().startsWith("Clan Search> No players matched. Try a more specific search")) cancel = true;
        }

        // GWEN
        if (event.message.getUnformattedText().startsWith("-=[GWEN Anticheat Bulletin]=-")) cancel = true;
        if (event.message.getUnformattedText().startsWith("Over the last week, ")) cancel = true;
        if (event.message.getUnformattedText().startsWith("were detected as cheaters and")) cancel = true;
        if (event.message.getUnformattedText().startsWith("were removed from the network!")) cancel = true;
        if (event.message.getUnformattedText().startsWith("This does not include the number")) cancel = true;
        if (event.message.getUnformattedText().startsWith("of accounts in the next banwave.")) cancel = true;

        if (cancel) event.setCanceled(true);
    }

    @Override
    public boolean isEnabled() {
        return SettingsHandler.getSettings(MineplexSettings.class).getRedundantMessageFilter();
    }

}
