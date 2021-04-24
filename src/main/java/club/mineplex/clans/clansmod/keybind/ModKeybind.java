package club.mineplex.clans.clansmod.keybind;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.modules.mineplex_server.ServerType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;

public abstract class ModKeybind {
    private static final String CLANS_KEY_START = "clansmod.key.";
    private static final String CLANS_MOD_CATEGORY = "clansmod.key.category";

    private final KeyBinding keyBinding;

    protected ModKeybind(final String description, final int keyCode) {
        this(description, keyCode, CLANS_MOD_CATEGORY);
    }

    protected ModKeybind(final String description, final int keyCode, final String category) {
        this(new KeyBinding(CLANS_KEY_START + description, keyCode, category));
    }

    protected ModKeybind(final KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void onPress() {
    }

    protected boolean inClans() {
        final ClientData data = getMod().getClientData();
        return data.isMineplex() && data.getMineplexServerType() == ServerType.CLANS;
    }

    protected EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    protected ClansMod getMod() {
        return ClansMod.getInstance();
    }
}
