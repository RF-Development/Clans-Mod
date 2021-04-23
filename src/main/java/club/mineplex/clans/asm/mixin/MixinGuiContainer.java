package club.mineplex.clans.asm.mixin;

import club.mineplex.clans.clansmod.KeyBindingManager;
import club.mineplex.clans.clansmod.keybinds.KeybindSlotLock;
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

    @Shadow private Slot theSlot;

    @Shadow protected abstract Slot getSlotAtPosition(int x, int y);

    @Shadow public Container inventorySlots;

    @SideOnly (Side.CLIENT)
    @Inject (at = @At("HEAD"), method = "keyTyped", cancellable = true)
    private void keyTyped(char typedChar, int keyCode, CallbackInfo callback) {

        // Stops people from dropping VALUABLE ITEMS
        if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode() && this.theSlot != null) {
            if (!DropPrevention.getModuleInstance().handleDrop(this.theSlot.getStack())) callback.cancel();
            else if (!SlotLock.getModuleInstance().isSlotInteractable(this.theSlot)) callback.cancel();
        }

        // Stops people from dropping stuff in LOCKED SLOTS
        if (keyCode == KeyBindingManager.getKeybind(KeybindSlotLock.class).getKeyBinding().getKeyCode()) {
            if (this.theSlot != null && this.theSlot.inventory instanceof InventoryPlayer) SlotLock.getModuleInstance().lockUnlockSlot(this.theSlot);
        }

    }

    @Inject (at = @At("HEAD"), method = "mouseClicked", cancellable = true)
    private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo callback) {
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);

        // Handles mount module
        if (this.inventorySlots instanceof ContainerChest) {
            EnhancedMounts.getModuleInstance().onGuiClick((ContainerChest) this.inventorySlots, slot);
        }

        // Stops people from pressing buttons in LOCKED SLOTS
        if (!SlotLock.getModuleInstance().isSlotInteractable(slot)) callback.cancel();
    }

    @Inject (at = @At("RETURN"), method = "drawSlot", cancellable = true)
    private void drawSlot(Slot slot, CallbackInfo callback) {

        // Draws red overlay on LOCKED SLOTS
        if (!SlotLock.getModuleInstance().isSlotInteractable(slot) && SlotLock.getModuleInstance().isEnabled()) {
            Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition +16, new Color(255, 0, 0, 50).getRGB());
        }

    }

    @Inject (at = @At("HEAD"), method = "handleMouseClick", cancellable = true)
    private void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo callback) {
        if (!SlotLock.getModuleInstance().isSlotInteractable(slotIn) && slotIn.inventory instanceof InventoryPlayer) callback.cancel();

        if (clickType == 2) { // Stops people from dragging items into LOCKED SLOTS

            Slot slot = UtilMixins.getSlotInHotbar(clickedButton);
            if (!SlotLock.getModuleInstance().isSlotInteractable(slot)) {
                callback.cancel();
            }

        }

    }

    @Inject(at = @At("HEAD"), method = "mouseClickMove", cancellable = true)
    private void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo callback) {

        // Stops people from clicking into LOCKED SLOTS
        if (this.getSlotAtPosition(mouseX, mouseY) != null && !SlotLock.getModuleInstance().isSlotInteractable(this.getSlotAtPosition(mouseX, mouseY))
                && this.getSlotAtPosition(mouseX, mouseY).inventory instanceof InventoryPlayer) {
            callback.cancel();
        }

    }

}
