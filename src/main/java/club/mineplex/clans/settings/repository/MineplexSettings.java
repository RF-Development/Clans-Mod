package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

public class MineplexSettings extends SettingsCategory {

    private final GuiSettingMode redundantMessageFilter;

    public MineplexSettings() {
        super("Mineplex");

        this.redundantMessageFilter = new GuiSettingMode("Message Filter", this, Status.ENABLED, Status.DISABLED);

        this.settings.add(this.redundantMessageFilter);
    }

    public boolean getRedundantMessageFilter() {
        return Status.valueOf(redundantMessageFilter.getModes().get(redundantMessageFilter.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

}
