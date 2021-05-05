package club.mineplex.clans.modules;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.utils.UtilClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;

public abstract class ModModule {

    protected final ClientData data = ClansMod.getInstance().getClientData();
    private final boolean isBlocked;
    private final String name;

    public ModModule(final String name, GuiSettingMode... assignedSettings) {
        this.name = name;
        this.isBlocked = !UtilClient.isModFeatureAllowed(this.name.replaceAll(" ", "-").trim().toLowerCase());

        for (GuiSettingMode assignedSetting : assignedSettings) {
            assignedSetting.setAssignedModule(this);
        }
    }

    public final String getName() {
        return this.name;
    }

    protected boolean isModuleUsable() {
        return true;
    }

    public boolean isEnabled() {
        return this.isModuleUsable() && !this.isModuleBlocked();
    }

    public final boolean isModuleBlocked() {
        return this.isBlocked;
    }

    protected final ClansMod getMod() {
        return ClansMod.getInstance();
    }

    protected final <T extends SettingsCategory> T getSettingThrow(final Class<T> categoryClass) {
        return SettingsHandler.getInstance().getSettingThrow(categoryClass);
    }

    protected final Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    protected final EntityPlayerSP getPlayer() {
        return this.getMinecraft().thePlayer;
    }

    protected final PlayerControllerMP getPlayerController() {
        return this.getMinecraft().playerController;
    }
}
