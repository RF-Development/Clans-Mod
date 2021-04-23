package club.mineplex.clans.clansmod.keybinds;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.gui.repository.MainGUI;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class KeybindHUD extends ModKeybind {

    @SideOnly(Side.CLIENT)
    public KeybindHUD() {
        super(new KeyBinding("clansmod.key.hud.desc", Keyboard.KEY_RSHIFT, CLANS_MOD_CATEGORY));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void doPress() {
        ClansMod.getInstance().getMinecraft().displayGuiScreen(new MainGUI());
    }

}
