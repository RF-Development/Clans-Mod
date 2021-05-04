package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

import java.util.Arrays;

public class DiscordSettings extends SettingsCategory {

    private final GuiSettingMode displayMineplexServer;
    private final GuiSettingMode displayRichStatus;
    private final GuiSettingMode displayServer;

    public DiscordSettings() {
        super("Discord");

        displayRichStatus = new GuiSettingMode("Discord Rich Status", this, Arrays.asList(
                "Display a rich status on Discord in",
                "your game activity panel."
        ), Status.ENABLED, Status.DISABLED);

        displayServer = new GuiSettingMode("Display Server", this, Arrays.asList(
                "If enabled, your Discord rich status",
                "will display the IP of the server you",
                "are playing on."
        ), Status.ENABLED, Status.DISABLED);

        displayMineplexServer = new GuiSettingMode("Display Mineplex Server", this, Arrays.asList(
                "If enabled, your Discord rich status",
                "will display the Mineplex lobby you are",
                "currently in. (Example: Clans-7)"
        ), Status.ENABLED, Status.DISABLED);

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
