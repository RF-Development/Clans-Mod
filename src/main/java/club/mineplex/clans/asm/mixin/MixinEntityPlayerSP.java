package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.events.PreItemDropEvent;
import club.mineplex.clans.modules.drop_prevention.ModuleDropPrevention;
import club.mineplex.clans.utils.UtilMixins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {

    @Shadow
    protected Minecraft mc;

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    private void dropOneItem(final boolean dropAll, final CallbackInfoReturnable<EntityItem> callback) {
        final Slot heldSlot = UtilMixins.getSlotInHotbar(mc.thePlayer.inventory.currentItem);

        // Updates the rendered selected slot and the itemstack object being held
        Minecraft.getMinecraft().playerController.updateController();

        if (mc.thePlayer.getHeldItem() != heldSlot.getStack()) {
            callback.cancel();
            return;
        }

        final PreItemDropEvent preItemDropEvent = new PreItemDropEvent(dropAll, heldSlot);
        MinecraftForge.EVENT_BUS.post(preItemDropEvent);
        if (preItemDropEvent.isCanceled()) {
            callback.setReturnValue(null);
        }

        // Drop Prevention
        final ModuleDropPrevention dropPrevention = ClansMod.getInstance().getModuleThrow(ModuleDropPrevention.class);
        if (!dropPrevention.handleDrop(mc.thePlayer.getHeldItem())) {
            callback.setReturnValue(null);
        }

    }

}