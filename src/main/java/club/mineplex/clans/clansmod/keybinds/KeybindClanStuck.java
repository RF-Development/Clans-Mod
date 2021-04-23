package club.mineplex.clans.clansmod.keybinds;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.utils.UtilClient;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeybindClanStuck extends ModKeybind {

    public KeybindClanStuck() {
        super(new KeyBinding("clansmod.key.cstuck.desc", Keyboard.KEY_J, CLANS_MOD_CATEGORY));
    }

    @Override
    public void doPress() {

        ClientData data = ClansMod.getInstance().getClientData();
        if (!ClansMod.getInstance().getMinecraft().thePlayer.isSneaking() && data.isMineplex && data.mineplexServerType == ServerType.CLANS)
            ClansMod.getInstance().getMinecraft().thePlayer.sendChatMessage("/clan stuck");
        else
            UtilClient.playSound("note.bass", 2F, 0.5F);
    }
}
