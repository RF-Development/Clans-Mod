package club.mineplex.clans.clansmod.keybind.keybinds;

import club.mineplex.clans.clansmod.keybind.ModKeybind;
import club.mineplex.clans.gui.repository.MainGUI;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class KeybindHUD extends ModKeybind {

    @SideOnly(Side.CLIENT)
    public KeybindHUD() {
        super("hud.desc", Keyboard.KEY_RSHIFT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onPress() {
        getMod().getMinecraft().displayGuiScreen(new MainGUI());
    }

}
