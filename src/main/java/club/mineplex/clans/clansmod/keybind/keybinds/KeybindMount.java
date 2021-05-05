package club.mineplex.clans.clansmod.keybind.keybinds;

import club.mineplex.clans.clansmod.keybind.ModKeybind;
import club.mineplex.clans.modules.enhanced_mounts.ModuleEnhancedMounts;
import club.mineplex.clans.utils.UtilClient;
import org.lwjgl.input.Keyboard;

public class KeybindMount extends ModKeybind {

    public KeybindMount() {
        super("mount.desc", Keyboard.KEY_M);
    }

    @Override
    public void onPress() {
        if (!getPlayer().isSneaking() && inClans()) {
            getMod().getModuleThrow(ModuleEnhancedMounts.class).spawnMount();
        } else {
            UtilClient.playSound("note.bass", 2F, 0.5F);
        }
    }
}
