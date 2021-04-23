package club.mineplex.clans.asm.mixin;

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
    private static void canAddItemToSlot(Slot p_canAddItemToSlot_0_, ItemStack p_canAddItemToSlot_1_, boolean p_canAddItemToSlot_2_, CallbackInfoReturnable<Boolean> cir) {
        if (!SlotLock.getModuleInstance().isSlotInteractable(p_canAddItemToSlot_0_)) {
            cir.setReturnValue(false);
        }
    }
}
