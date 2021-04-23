package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.modules.drop_prevention.DropPrevention;
import club.mineplex.clans.modules.slot_lock.SlotLock;
import club.mineplex.clans.utils.UtilMixins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {

    @Shadow protected Minecraft mc;

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    private void dropOneItem(boolean dropAll, CallbackInfoReturnable<EntityItem> callback) {
        Slot heldSlot = UtilMixins.getSlotInHotbar(this.mc.thePlayer.inventory.currentItem);

        if (this.mc.thePlayer.getHeldItem() != heldSlot.getStack()) {
            callback.cancel();
            return;
        }

        // Drop Prevention
        boolean cancel = false;
        if (!DropPrevention.getModuleInstance().handleDrop(this.mc.thePlayer.getHeldItem())) cancel = true;
        else if (!SlotLock.getModuleInstance().isSlotInteractable(heldSlot)) cancel = true;

        if (cancel) {
            callback.setReturnValue(null);
            callback.cancel();
        }

    }

}