package club.mineplex.clans.modules.message_filter;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.MineplexSettings;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleMessageFilter extends ModModule {
    private static final String[] CLANS_FILTER_MESSAGE_STARTS = {
            "Clans> You cannot harm",
            "Clan Search> Too many players matched. Try a more specific search",
            "Clan Search> No clans matched. Try a more specific search",
            "Clan Search> No players matched. Try a more specific search"
    };

    private static final String[] FILTER_MESSAGE_STARTS = {
            "-=[GWEN Anticheat Bulletin]=-",
            "Over the last week, ",
            "were detected as cheaters and",
            "were removed from the network!",
            "This does not include the number",
            "of accounts in the next banwave."
    };


    public ModuleMessageFilter() {
        super("Message Filter", SettingsHandler.getInstance().getSettingThrow(MineplexSettings.class).getRedundantMessageFilter());
    }

    private boolean isBlockedMessage(final IChatComponent message, final String[] messagesToFiler) {
        for (final String filteredStart : messagesToFiler) {
            if (message.getUnformattedText().startsWith(filteredStart)) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onMessageReceived(final ClientChatReceivedEvent event) {
        if (!isEnabled() || !data.isMineplex()) {
            return;
        }

        // Clans
        if (data.getMineplexServerType() == ServerType.CLANS && isBlockedMessage(event.message, CLANS_FILTER_MESSAGE_STARTS)) {
            event.setCanceled(true);
            return;
        }

        // Global
        if (isBlockedMessage(event.message, FILTER_MESSAGE_STARTS)) {
            event.setCanceled(true);
            return;
        }
    }

    @Override
    public boolean isModuleUsable() {
        return getSettingThrow(MineplexSettings.class).getRedundantMessageFilter().getCurrentMode().equals(Status.ENABLED);
    }

}
