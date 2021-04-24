package club.mineplex.clans.clansmod.keybind.keybinds;

import club.mineplex.clans.clansmod.keybind.ModKeybind;
import club.mineplex.clans.utils.UtilClient;
import org.lwjgl.input.Keyboard;

public class KeybindClanStuck extends ModKeybind {

    public KeybindClanStuck() {
        super("cstuck.desc", Keyboard.KEY_J);
    }

    @Override
    public void onPress() {
        if (!getPlayer().isSneaking() && inClans()) {
            getPlayer().sendChatMessage("/clan stuck");
        } else {
            UtilClient.playSound("note.bass", 2F, 0.5F);
        }
    }
}
