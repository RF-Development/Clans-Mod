package club.mineplex.clans.clansmod.keybind.listeners;

import club.mineplex.clans.clansmod.keybind.KeyBindingManager;
import club.mineplex.clans.clansmod.keybind.ModKeybind;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ListenerKeybind {
    private final KeyBindingManager keyBindingManager;

    public ListenerKeybind(final KeyBindingManager keyBindingManager) {
        this.keyBindingManager = keyBindingManager;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(final InputEvent.KeyInputEvent event) {
        for (final ModKeybind modKeybind : keyBindingManager.getKeyBinds()) {
            if (modKeybind.getKeyBinding().isPressed()) {
                modKeybind.onPress();
            }
        }
    }

}
