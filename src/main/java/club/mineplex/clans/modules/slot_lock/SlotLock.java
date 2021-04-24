package club.mineplex.clans.modules.slot_lock;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.enums.Status;
import club.mineplex.clans.events.PreItemDropEvent;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.UtilClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.stream.Stream;

public class SlotLock extends ModModule {
    private int[] lockedSlots;

    public SlotLock() {
        super("Slot Lock", SettingsHandler.getInstance().getSettingThrow(ClansSettings.class).getSlotLocks());

        final ClansSettings clansSettings = SettingsHandler.getInstance().getSettingThrow(ClansSettings.class);
        final Configuration configuration = ClansMod.getInstance().getConfiguration();

        configuration.load();
        lockedSlots = configuration
                .get(clansSettings.getConfigID(), "locked-slots", new int[0])
                .getIntList();
        configuration.save();
    }

    public int[] getLockedSlots() {
        return lockedSlots.clone();
    }

    @SubscribeEvent
    public void onPreItemDrop(final PreItemDropEvent event) {
        if (!isSlotInteractable(event.getItemSlot())) {
            event.setCanceled(true);
        }
    }

    public void lockUnlockSlot(final int slotIndex) {
        if (!isEnabled()) {
            return;
        }

        final boolean lock = Arrays.stream(lockedSlots).noneMatch(i -> i == slotIndex);
        if (lock) {
            lockedSlots = Stream.concat(
                    Arrays.stream(lockedSlots).boxed(),
                    Stream.of(slotIndex)
            )
                    .mapToInt(Integer::intValue)
                    .toArray();
        } else {
            lockedSlots = Arrays.stream(lockedSlots).filter(i -> i != slotIndex).toArray();
        }
        UtilClient.playSound("random.door_open", 2F, 2F);

        final ClansSettings clansSettings = SettingsHandler.getInstance().getSettingThrow(ClansSettings.class);
        final Configuration configuration = ClansMod.getInstance().getConfiguration();
        configuration.load();
        configuration.get(clansSettings.getConfigID(), "locked-slots", new int[0]).set(lockedSlots);
        configuration.save();
    }

    public void lockUnlockSlot(final Slot slot) {
        lockUnlockSlot(slot.getSlotIndex());
    }

    public boolean isSlotInteractable(final int slotIndex) {
        final Slot slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(slotIndex);
        return isSlotInteractable(slot);
    }

    public boolean isSlotInteractable(final Slot slot) {
        if (!isEnabled()) {
            return true;
        }
        if (slot == null || !(slot.inventory instanceof InventoryPlayer)) {
            return true;
        }
        return Arrays.stream(lockedSlots).noneMatch(i -> i == slot.getSlotIndex());
    }

    @Override
    public boolean isModuleUsable() {
        final ClansSettings clansSettings = SettingsHandler.getInstance().getSettingThrow(ClansSettings.class);
        return clansSettings.getSlotLocks().getCurrentMode().equals(Status.ENABLED)
                && data.isMineplex()
                && data.getMineplexServerType() == ServerType.CLANS;
    }

}
