package club.mineplex.clans.modules.enhanced_mounts;

import club.mineplex.clans.ClansMod;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnhancedMounts extends ModModule {

    private static EnhancedMounts instance;
    private static int selectedMount = -1;
    private static boolean queued = false;

    static {
        selectedMount = ClansMod.getInstance().getConfiguration().get(SettingsHandler.getSettings(ClansSettings.class).getConfigID(), "selected-mount", -1).getInt();
    }

    public EnhancedMounts() {
        super("Enhanced Mounts");
        instance = this;
    }

    public static EnhancedMounts getModuleInstance() {
        return instance;
    }

    public void spawnMount() {
        ClansMod.getInstance().getMinecraft().thePlayer.sendChatMessage("/mount");
        queued = true;
    }

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (!isMountsGui(event.gui)) return;
        if (!queued) return;

        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent.Pre event) {
        if (!isMountsGui(event.gui)) return;
        if (!queued || selectedMount == -1) return;

        ContainerChest container = (ContainerChest) ((GuiChest) event.gui).inventorySlots;
        new DelayedTask(() -> {
            ClansMod.getInstance().getMinecraft().playerController.windowClick(container.windowId, EnhancedMounts.selectedMount, 0, 0, Minecraft.getMinecraft().thePlayer);
        }, 0);

        new DelayedTask(() -> {
            ClansMod.getInstance().getMinecraft().thePlayer.closeScreen();
            queued = false;
        }, 0);
    }

    public void onGuiClick(ContainerChest container, Slot click) {
        if (!isMountsGui(container)) return;
        if (click == null) return;

        EnhancedMounts.selectedMount = click.getSlotIndex();
        ClansMod.getInstance().getConfiguration().load();
        ClansMod.getInstance().getConfiguration().getCategory(SettingsHandler.getSettings(ClansSettings.class).getConfigID()).get("selected-mount").set(selectedMount);
        ClansMod.getInstance().getConfiguration().save();
    }

    @Override
    public boolean isEnabled() {
        return SettingsHandler.getSettings(ClansSettings.class).getEnhancedMounts() && data.isMineplex && data.mineplexServerType == ServerType.CLANS;
    }

    public boolean isMountsGui(GuiScreen screen) {
        if (!isEnabled()) return false;
        if (!(screen instanceof GuiChest)) return false;
        if (!(((GuiChest) screen).inventorySlots instanceof ContainerChest)) return false;

        GuiChest chest = (GuiChest) screen;
        ContainerChest container = (ContainerChest) chest.inventorySlots;
        if (!container.getLowerChestInventory().hasCustomName()) return false;

        return container.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Manage Mounts");
    }

    public boolean isMountsGui(ContainerChest container) {
        if (!isEnabled()) return false;
        if (!container.getLowerChestInventory().hasCustomName()) return false;
        return container.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Manage Mounts");
    }

}
