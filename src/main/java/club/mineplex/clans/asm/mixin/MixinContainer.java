package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.modules.slot_lock.SlotLock;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Container.class)
public class MixinContainer {

    // Stops people from double clicking items and dragging items out of hotbar slots
    @Inject(at = @At("HEAD"), method = "canAddItemToSlot", cancellable = true)
    private static void canAddItemToSlot(final Slot slot0,
                                         final ItemStack slot1,
                                         final boolean slot2,
                                         final CallbackInfoReturnable<Boolean> cir) {
        if (!ClansMod.getInstance().getModuleThrow(SlotLock.class).isSlotInteractable(slot0)) {
            cir.setReturnValue(false);
        }
    }
}
