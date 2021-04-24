package club.mineplex.clans.modules.drop_prevention;

import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilText;
import net.minecraft.item.ItemStack;

public class DropPrevention extends ModModule {

    public DropPrevention() {
        super("Drop Prevention");
    }

    public boolean handleDrop(final ItemStack itemStack) {
        if (isEnabled() && itemStack != null) {
            boolean cancelled = false;

            final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("record")) {
                if (!clansSettings.getLegendaryDropPrevention()) return true;
                cancelled = true;
            }


            if (itemStack.getItem().getUnlocalizedName().toLowerCase().contains("foot")) {
                if (!clansSettings.getValuableDropPrevention()) return true;
                cancelled = true;
            }

            if (cancelled) {
                final String name = itemStack.hasDisplayName() ? itemStack.getDisplayName() : itemStack.getItem().getItemStackDisplayName(itemStack);

                UtilClient.playSound("note.bass", 2F, 0.5F);
                UtilText.sendPlayerMessageWithPrefix(this.getName(), String.format("You are not allowed to drop &e%s&r.", name));
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isEnabled() {
        final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
        return (clansSettings.getLegendaryDropPrevention() || clansSettings.getValuableDropPrevention())
                && data.isMineplex()
                && data.getMineplexServerType() == ServerType.CLANS;
    }

}
