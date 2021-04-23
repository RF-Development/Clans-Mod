package club.mineplex.clans.clansmod.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class KeybindSlotLock extends ModKeybind {

    @SideOnly(Side.CLIENT)
    public KeybindSlotLock() {
        super(new KeyBinding("clansmod.key.slotlock.desc", Keyboard.KEY_L, ModKeybind.CLANS_MOD_CATEGORY));
    }

}
