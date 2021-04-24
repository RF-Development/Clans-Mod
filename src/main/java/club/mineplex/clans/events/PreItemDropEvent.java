package club.mineplex.clans.events;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PreItemDropEvent extends Event {
    private final boolean dropAll;
    private final Slot itemSlot;

    public PreItemDropEvent(final boolean dropAll, final Slot itemSlot) {
        this.dropAll = dropAll;
        this.itemSlot = itemSlot;
    }

    public boolean isDropAll() {
        return dropAll;
    }

    public Slot getItemSlot() {
        return itemSlot;
    }
}
