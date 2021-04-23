package club.mineplex.clans.clansmod.keybinds;

import net.minecraft.client.settings.KeyBinding;

public abstract class ModKeybind {

    public static final String CLANS_MOD_CATEGORY = "clansmod.key.category";

    private final KeyBinding keyBinding;

    public ModKeybind(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void doPress() { }

}
