package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.modules.drop_prevention.DropPrevention;
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

        addSettings(
                displayRichStatus,
                displayServer,
                displayMineplexServer
        );
    }

    public GuiSettingMode getDisplayRichStatus() {
        return displayRichStatus;
    }

    public GuiSettingMode getDisplayMineplexServer() {
        return displayMineplexServer;
    }

    public GuiSettingMode getDisplayServer() {
        return displayServer;
    }

}
