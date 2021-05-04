package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

import java.util.Arrays;

public class MineplexSettings extends SettingsCategory {

    private final GuiSettingMode redundantMessageFilter;

    public MineplexSettings() {
        super("Mineplex");

        redundantMessageFilter = new GuiSettingMode("Message Filter", this, Arrays.asList(
                "Your game chat will not be filled with",
                "redundant and annoying messages in",
                "Mineplex.",
                "",
                "This message list includes:",
                "● 'You cannot harm' message",
                "● Redundant 'no matches found' search message",
                "● Redundant 'too many matches found' search message",
                "● GWEN Bulletin",
                "● Clans map bug message when switching servers"
        ), Status.ENABLED, Status.DISABLED);

        addSettings(
                redundantMessageFilter
        );
    }

    public GuiSettingMode getRedundantMessageFilter() {
        return redundantMessageFilter;
    }

}
