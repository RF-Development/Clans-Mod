package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

public class DiscordSettings extends SettingsCategory {

    private final GuiSettingMode displayMineplexServer;
    private final GuiSettingMode displayRichStatus;
    private final GuiSettingMode displayServer;

    public DiscordSettings() {
        super("Discord");

        displayRichStatus = new GuiSettingMode("Discord Rich Status", this, Status.ENABLED, Status.DISABLED);
        displayServer = new GuiSettingMode("Display Server", this, Status.ENABLED, Status.DISABLED);
        displayMineplexServer = new GuiSettingMode("Display Mineplex Server", this, Status.ENABLED, Status.DISABLED);

        this.settings.add(displayRichStatus);
        this.settings.add(displayServer);
        this.settings.add(displayMineplexServer);
    }

    public boolean getDisplayRichStatus() {
        return Status.valueOf(displayRichStatus.getModes().get(displayRichStatus.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

    public boolean getDisplayMineplexServer() {
        return Status.valueOf(displayMineplexServer.getModes().get(displayMineplexServer.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

    public boolean getDisplayServer() {
        return Status.valueOf(displayServer.getModes().get(displayServer.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

}
