package club.mineplex.clans.clansmod.keybind.keybinds;

import club.mineplex.clans.clansmod.keybind.ModKeybind;
import club.mineplex.clans.utils.UtilClient;
import org.lwjgl.input.Keyboard;

public class KeybindClanHome extends ModKeybind {
    public KeybindClanHome() {
        super("chome.desc", Keyboard.KEY_H);
    }

    @Override
    public void onPress() {
        if (!getPlayer().isSneaking() && inClans()) {
            getPlayer().sendChatMessage("/clan home");
        } else {
            UtilClient.playSound("note.bass", 2F, 0.5F);
        }
    }
}
