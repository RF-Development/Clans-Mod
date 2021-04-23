package club.mineplex.clans.modules.drop_prevention;

import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilText;
import net.minecraft.item.ItemStack;

public class DropPrevention extends ModModule {

    private static DropPrevention instance;

    public DropPrevention() {
        super("Drop Prevention");
        instance = this;
    }

    public static DropPrevention getModuleInstance() {
        return instance;
    }

    public boolean handleDrop(ItemStack itemStack) {

        if (isEnabled() && itemStack != null) {
            boolean cancelled = false;

            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("record")) {
                if (!SettingsHandler.getSettings(ClansSettings.class).getLegendaryDropPrevention()) return true;
                cancelled = true;
            }


            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("foot")) {
                if (!SettingsHandler.getSettings(ClansSettings.class).getValuableDropPrevention()) return true;
                cancelled = true;
            }

            if (cancelled) {
                String name = itemStack.hasDisplayName() ? itemStack.getDisplayName() : itemStack.getItem().getItemStackDisplayName(itemStack);

                UtilClient.playSound("note.bass", 2F, 0.5F);
                UtilText.sendPlayerMessageWithPrefix(this.getName(), String.format("You are not allowed to drop &e%s&r.", name));
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isEnabled() {
        return (SettingsHandler.getSettings(ClansSettings.class).getLegendaryDropPrevention() || SettingsHandler.getSettings(ClansSettings.class).getValuableDropPrevention())
                && data.isMineplex && data.mineplexServerType == ServerType.CLANS;
    }

}
