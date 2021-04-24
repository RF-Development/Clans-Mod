package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.ClansMod;
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

    protected MixinSlotItemHandler(final IInventory p_i1824_1_,
                                   final int p_i1824_2_,
                                   final int p_i1824_3_,
                                   final int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Inject(at = @At("HEAD"), method = "canTakeStack", cancellable = true)
    private void canTakeStack(final EntityPlayer player, final CallbackInfoReturnable<Boolean> cir) {

        if (!ClansMod.getInstance().getModuleThrow(SlotLock.class).isSlotInteractable(this.getSlotIndex())) {
            cir.setReturnValue(false);
        }

    }

}
