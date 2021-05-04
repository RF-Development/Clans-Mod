package club.mineplex.clans.modules.drop_prevention;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilText;
import net.minecraft.item.ItemStack;

public class ModuleDropPrevention extends ModModule {

    public ModuleDropPrevention() {
        super("Drop Prevention",
                SettingsHandler.getInstance().getSettingThrow(ClansSettings.class).getLegendaryDropPrevention(),
                SettingsHandler.getInstance().getSettingThrow(ClansSettings.class).getValuableDropPrevention()
        );
    }

    public boolean handleDrop(final ItemStack itemStack) {
        if (isEnabled() && itemStack != null) {
            boolean cancelled = false;

            final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("record")) {
                if (!clansSettings.getLegendaryDropPrevention().getCurrentMode().equals(Status.ENABLED)) {
                    return true;
                }
                cancelled = true;
            }

            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("foot")) {
                if (!clansSettings.getValuableDropPrevention().getCurrentMode().equals(Status.ENABLED)) {
                    return true;
                }
                cancelled = true;
            }

            if (cancelled) {
                final String name = itemStack.hasDisplayName() ? itemStack.getDisplayName() : itemStack.getItem().getItemStackDisplayName(itemStack);

                UtilClient.playSound("note.bass", 2F, 0.5F);
                UtilText.sendPlayerMessageWithPrefix(getName(), String.format("You are not allowed to drop &e%s&r.", name));
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isModuleUsable() {
        final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
        return (clansSettings.getLegendaryDropPrevention().getCurrentMode().equals(Status.ENABLED)
                || clansSettings.getValuableDropPrevention().getCurrentMode().equals(Status.ENABLED))
                && data.isMineplex()
                && data.getMineplexServerType() == ServerType.CLANS;
    }

}
