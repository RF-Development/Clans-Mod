package club.mineplex.clans.modules.enhanced_mounts;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.enums.Status;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.object.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnhancedMounts extends ModModule {
    private int selectedMount = -1;
    private boolean queued = false;

    public EnhancedMounts() {
        super("Enhanced Mounts", SettingsHandler.getInstance().getSettingThrow(ClansSettings.class).getEnhancedMounts());

        final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
        selectedMount = ClansMod.getInstance().getConfiguration().get(clansSettings.getConfigID(), "selected-mount", -1).getInt();
    }

    public void spawnMount() {
        ClansMod.getInstance().getMinecraft().thePlayer.sendChatMessage("/mount");
        queued = true;
    }

    @SubscribeEvent
    public void onGuiOpen(final GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (isMountsGui(event.gui) && queued) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(final GuiScreenEvent.InitGuiEvent.Pre event) {
        if (!isMountsGui(event.gui) || !queued || selectedMount == -1) {
            return;
        }

        final ContainerChest container = (ContainerChest) ((GuiChest) event.gui).inventorySlots;
        new DelayedTask(() ->
                getPlayerController().windowClick(
                        container.windowId,
                        selectedMount,
                        0,
                        0,
                        Minecraft.getMinecraft().thePlayer
                ),
                0
        );

        new DelayedTask(() -> {
            getPlayer().closeScreen();
            queued = false;
        }, 0);
    }

    public void onGuiClick(final ContainerChest container, final Slot click) {
        if (!isMountsGui(container) || click == null) {
            return;
        }

        selectedMount = click.getSlotIndex();

        final ClansSettings clansSettings = getSettingThrow(ClansSettings.class);
        final Configuration configuration = ClansMod.getInstance().getConfiguration();
        configuration.load();
        configuration.getCategory(clansSettings.getConfigID()).get("selected-mount").set(selectedMount);
        configuration.save();
    }

    @Override
    public boolean isModuleUsable() {
        return getSettingThrow(ClansSettings.class).getEnhancedMounts().getCurrentMode().equals(Status.ENABLED)
                && data.isMineplex()
                && data.getMineplexServerType() == ServerType.CLANS;
    }

    public boolean isMountsGui(final GuiScreen screen) {
        if (!isEnabled()) {
            return false;
        }
        if (!(screen instanceof GuiChest)) {
            return false;
        }
        if (!(((GuiChest) screen).inventorySlots instanceof ContainerChest)) {
            return false;
        }

        final GuiChest chest = (GuiChest) screen;
        final ContainerChest container = (ContainerChest) chest.inventorySlots;
        if (!container.getLowerChestInventory().hasCustomName()) {
            return false;
        }

        return container.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Manage Mounts");
    }

    public boolean isMountsGui(final ContainerChest container) {
        if (!isEnabled()) return false;
        if (!container.getLowerChestInventory().hasCustomName()) return false;
        return container.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Manage Mounts");
    }

}
