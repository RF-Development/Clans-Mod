package club.mineplex.clans.clansmod;

import club.mineplex.clans.clansmod.keybinds.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.HashSet;

public class KeyBindingManager {

    private static final HashSet<ModKeybind> keybinds = new HashSet<>();

    static {
        keybinds.add(new KeybindHUD());
        keybinds.add(new KeybindSlotLock());
        keybinds.add(new KeybindClanHome());
        keybinds.add(new KeybindClanStuck());
        keybinds.add(new KeybindMount());
    }

    public static void setup() {
        keybinds.forEach(key -> ClientRegistry.registerKeyBinding(key.getKeyBinding()));
    }

    public static HashSet<ModKeybind> getKeybinds() {
        return keybinds;
    }

    public static ModKeybind getKeybind(Class<? extends ModKeybind> keybindingClass) {
        for (ModKeybind keybinding : keybinds) if (keybinding.getClass() == keybindingClass) return keybinding;
        throw new RuntimeException("Keybinding not found: " + keybindingClass.getName());
    }

}
