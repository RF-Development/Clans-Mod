package club.mineplex.clans.clansmod.keybind;

import club.mineplex.clans.clansmod.keybind.keybinds.*;
import club.mineplex.clans.clansmod.keybind.listeners.ListenerKeybind;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.*;

public class KeyBindingManager {
    private static KeyBindingManager instance;
    private final Map<Class<? extends ModKeybind>, ModKeybind> keyBinds = new HashMap<>();

    public static KeyBindingManager getInstance() {
        if (instance == null) {
            instance = new KeyBindingManager();
        }
        return instance;
    }

    private KeyBindingManager() {
        registerKeyBindings(
                new KeybindHUD(),
                new KeybindSlotLock(),
                new KeybindClanHome(),
                new KeybindClanStuck(),
                new KeybindMount()
        );

        MinecraftForge.EVENT_BUS.register(
                new ListenerKeybind(this)
        );
    }

    private void registerKeyBindings(final ModKeybind... modKeybinds) {
        for (final ModKeybind modKeybind : modKeybinds) {
            keyBinds.put(modKeybind.getClass(), modKeybind);
        }
    }

    public void setup() {
        for (final ModKeybind key : getKeyBinds()) {
            ClientRegistry.registerKeyBinding(key.getKeyBinding());
        }
    }

    public List<ModKeybind> getKeyBinds() {
        return new ArrayList<>(keyBinds.values());
    }

    public Optional<ModKeybind> getKeyBind(final Class<? extends ModKeybind> keybindingClass) {
        return Optional.ofNullable(keyBinds.get(keybindingClass));
    }

    public ModKeybind getKeyBindThrow(final Class<? extends ModKeybind> keybindingClass) {
        return getKeyBind(keybindingClass)
                .orElseThrow(() -> new RuntimeException("Keybinding not found: " + keybindingClass.getName()));
    }
}
