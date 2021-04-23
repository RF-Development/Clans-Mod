package club.mineplex.clans.clansmod.keybinds;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.enhanced_mounts.EnhancedMounts;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import club.mineplex.clans.utils.UtilClient;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeybindMount extends ModKeybind {

    public KeybindMount() {
        super(new KeyBinding("clansmod.key.mount.desc", Keyboard.KEY_M, CLANS_MOD_CATEGORY));
    }

    @Override
    public void doPress() {

        ClientData data = ClansMod.getInstance().getClientData();
        if (!ClansMod.getInstance().getMinecraft().thePlayer.isSneaking() && data.isMineplex && data.mineplexServerType == ServerType.CLANS)
            EnhancedMounts.getModuleInstance().spawnMount();
        else
            UtilClient.playSound("note.bass", 2F, 0.5F);
    }
}
