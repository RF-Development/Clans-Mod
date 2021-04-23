package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.modules.slot_lock.SlotLock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotItemHandler.class)
public abstract class MixinSlotItemHandler extends Slot {

    public MixinSlotItemHandler(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Inject(at = @At("HEAD"), method = "canTakeStack", cancellable = true)
    private void canTakeStack(EntityPlayer p_canTakeStack_1_, CallbackInfoReturnable<Boolean> cir) {

        if (!SlotLock.getModuleInstance().isSlotInteractable(this.getSlotIndex())) {
            cir.setReturnValue(false);
        }

    }

}
