package club.mineplex.clans.modules.slot_lock;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.utils.UtilClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.util.Arrays;
import java.util.stream.Stream;

public class SlotLock extends ModModule {

    private static SlotLock instance;
    private static int[] lockedSlots;

    static {
        ClansMod.getInstance().getConfiguration().load();
        lockedSlots = ClansMod.getInstance()
                .getConfiguration()
                .get(SettingsHandler.getSettings(ClansSettings.class).getConfigID(), "locked-slots", new int[0])
                .getIntList();
        ClansMod.getInstance().getConfiguration().save();
    }

    public SlotLock() {
        super("Slot Lock");
        instance = this;
    }

    public static SlotLock getModuleInstance() {
        return instance;
    }

    public int[] getLockedSlots() {
        return lockedSlots.clone();
    }

    public void lockUnlockSlot(int slotIndex) {
        if (!isEnabled()) return;

        boolean lock = Arrays.stream(lockedSlots).noneMatch(i -> i == slotIndex);

        if (lock)
            lockedSlots = Stream.concat(Arrays.stream(lockedSlots).boxed(), Stream.of(slotIndex)).mapToInt(Integer::intValue).toArray();
        else lockedSlots = Arrays.stream(lockedSlots).filter(i -> i != slotIndex).toArray();
        UtilClient.playSound("random.door_open", 2F, 2F);

        ClansMod.getInstance().getConfiguration().load();
        ClansMod.getInstance().getConfiguration().get(SettingsHandler.getSettings(ClansSettings.class).getConfigID(), "locked-slots", new int[0]).set(lockedSlots);
        ClansMod.getInstance().getConfiguration().save();
    }

    public void lockUnlockSlot(Slot slot) {
        lockUnlockSlot(slot.getSlotIndex());
    }

    public boolean isSlotInteractable(int slotIndex) {
        Slot slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(slotIndex);
        return isSlotInteractable(slot);
    }

    public boolean isSlotInteractable(Slot slot) {
        if (!isEnabled()) return true;
        if (slot == null || !(slot.inventory instanceof InventoryPlayer)) return true;
        return Arrays.stream(lockedSlots).noneMatch(i -> i == slot.getSlotIndex());
    }

    @Override
    public boolean isEnabled() {
        return SettingsHandler.getSettings(ClansSettings.class).getSlotLocks() && data.isMineplex && data.mineplexServerType == ServerType.CLANS;
    }

}
