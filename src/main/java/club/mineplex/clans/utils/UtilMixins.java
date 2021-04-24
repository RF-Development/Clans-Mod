package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import net.minecraft.inventory.Slot;

public class UtilMixins {
    private UtilMixins() {
    }

    public static Slot getSlotInHotbar(final int slot) {
        int slot2 = ClansMod.getInstance().getMinecraft().thePlayer.inventoryContainer.inventorySlots.size() + slot;
        slot2 -= 9;

        return ClansMod.getInstance().getMinecraft().thePlayer.inventoryContainer.getSlot(slot2);
    }

}
