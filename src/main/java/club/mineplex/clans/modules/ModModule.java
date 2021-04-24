package club.mineplex.clans.modules;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.settings.SettingsCategory;
import club.mineplex.clans.settings.SettingsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;

public class ModModule {

    protected final ClientData data = ClansMod.getInstance().getClientData();
    private final String name;

    public ModModule(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return true;
    }

    protected ClansMod getMod() {
        return ClansMod.getInstance();
    }

    protected <T extends SettingsCategory> T getSettingThrow(final Class<T> categoryClass) {
        return SettingsHandler.getInstance().getSettingThrow(categoryClass);
    }

    protected Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    protected EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }

    protected PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }
}
