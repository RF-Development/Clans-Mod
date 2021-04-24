package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.clansmod.keybind.KeyBindingManager;
import club.mineplex.clans.clansmod.keybind.keybinds.KeybindSlotLock;
import club.mineplex.clans.modules.drop_prevention.DropPrevention;
import club.mineplex.clans.modules.enhanced_mounts.EnhancedMounts;
import club.mineplex.clans.modules.slot_lock.SlotLock;
import club.mineplex.clans.utils.UtilMixins;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {

    @Shadow
    public Container inventorySlots;
    @Shadow
    private Slot theSlot;

    @Shadow
    protected abstract Slot getSlotAtPosition(int x, int y);

    @SideOnly(Side.CLIENT)
    @Inject(at = @At("HEAD"), method = "keyTyped", cancellable = true)
    private void keyTyped(final char typedChar, final int keyCode, final CallbackInfo callback) {

        // Stops people from dropping VALUABLE ITEMS
        if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode() && this.theSlot != null) {
            final DropPrevention dropPrevention = ClansMod.getInstance().getModuleThrow(DropPrevention.class);
            final SlotLock slotLock = ClansMod.getInstance().getModuleThrow(SlotLock.class);
            if (!dropPrevention.handleDrop(this.theSlot.getStack()) || !slotLock.isSlotInteractable(theSlot)) {
                callback.cancel();
            }
        }

        // Stops people from dropping stuff in LOCKED SLOTS
        if (keyCode == KeyBindingManager.getInstance().getKeyBindThrow(KeybindSlotLock.class).getKeyBinding().getKeyCode()) {
            if (this.theSlot != null && this.theSlot.inventory instanceof InventoryPlayer) {
                ClansMod.getInstance().getModuleThrow(SlotLock.class).lockUnlockSlot(this.theSlot);
            }
        }

    }

    @Inject(at = @At("HEAD"), method = "mouseClicked", cancellable = true)
    private void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo callback) {
        final Slot slot = this.getSlotAtPosition(mouseX, mouseY);

        // Handles mount module
        if (this.inventorySlots instanceof ContainerChest) {
            ClansMod.getInstance().getModuleThrow(EnhancedMounts.class).onGuiClick((ContainerChest) this.inventorySlots, slot);
        }

        // Stops people from pressing buttons in LOCKED SLOTS
        if (!ClansMod.getInstance().getModuleThrow(SlotLock.class).isSlotInteractable(slot)) {
            callback.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "drawSlot", cancellable = true)
    private void drawSlot(final Slot slot, final CallbackInfo callback) {

        // Draws red overlay on LOCKED SLOTS
        if (!ClansMod.getInstance().getModuleThrow(SlotLock.class).isSlotInteractable(slot)) {
            Gui.drawRect(
                    slot.xDisplayPosition,
                    slot.yDisplayPosition,
                    slot.xDisplayPosition + 16,
                    slot.yDisplayPosition + 16,
                    new Color(255, 0, 0, 50).getRGB()
            );
        }

    }

    @Inject(at = @At("HEAD"), method = "handleMouseClick", cancellable = true)
    private void handleMouseClick(final Slot slotIn,
                                  final int slotId,
                                  final int clickedButton,
                                  final int clickType,
                                  final CallbackInfo callback) {
        final SlotLock slotLock = ClansMod.getInstance().getModuleThrow(SlotLock.class);
        if (!slotLock.isSlotInteractable(slotIn) && slotIn.inventory instanceof InventoryPlayer) {
            callback.cancel();
        }

        // Stops people from dragging items into LOCKED SLOTS
        if (clickType == 2) {
            final Slot slot = UtilMixins.getSlotInHotbar(clickedButton);
            if (!slotLock.isSlotInteractable(slot)) {
                callback.cancel();
            }

        }

    }

    @Inject(at = @At("HEAD"), method = "mouseClickMove", cancellable = true)
    private void mouseClickMove(final int mouseX,
                                final int mouseY,
                                final int clickedMouseButton,
                                final long timeSinceLastClick,
                                final CallbackInfo callback) {

        // Stops people from clicking into LOCKED SLOTS
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        if (slot != null
                && !ClansMod.getInstance().getModuleThrow(SlotLock.class).isSlotInteractable(slot)
                && slot.inventory instanceof InventoryPlayer) {
            callback.cancel();
        }

    }

}
