package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

public class MineplexSettings extends SettingsCategory {

    private final GuiSettingMode redundantMessageFilter;

    public MineplexSettings() {
        super("Mineplex");

        this.redundantMessageFilter = new GuiSettingMode("Message Filter", this, Status.ENABLED, Status.DISABLED);

        addSettings(
                redundantMessageFilter
        );
    }

    public boolean getRedundantMessageFilter() {
        return redundantMessageFilter.getModes().get(redundantMessageFilter.getCurrentMode()).equals(Status.ENABLED);
    }

}
