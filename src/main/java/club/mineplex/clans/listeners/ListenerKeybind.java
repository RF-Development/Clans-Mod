package club.mineplex.clans.listeners;

import club.mineplex.clans.clansmod.KeyBindingManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ListenerKeybind {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(InputEvent.KeyInputEvent event) {
        KeyBindingManager.getKeybinds().forEach((keyBinding) -> {
            if (keyBinding.getKeyBinding().isPressed()) keyBinding.doPress();
        });
    }

}
