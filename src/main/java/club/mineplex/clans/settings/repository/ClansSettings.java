package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;

public class ClansSettings extends SettingsCategory {

    private final GuiSettingMode legendaryDropPrevention;
    private final GuiSettingMode valuableDropPrevention;
    private final GuiSettingMode slotLocks;
    private final GuiSettingMode enhancedMounts;

    public ClansSettings() {
        super("Clans");

        this.legendaryDropPrevention = new GuiSettingMode("Legendary Drop Prevention", this, Status.ENABLED, Status.DISABLED);
        this.valuableDropPrevention = new GuiSettingMode("Valuable Item Drop Prevention", this, Status.ENABLED, Status.DISABLED);
        this.slotLocks = new GuiSettingMode("Inventory Slot Locks", this, Status.ENABLED, Status.DISABLED);
        this.enhancedMounts = new GuiSettingMode("Enhanced Mounts", this, Status.ENABLED, Status.DISABLED);

        this.settings.add(this.valuableDropPrevention);
        this.settings.add(this.legendaryDropPrevention);
        this.settings.add(this.slotLocks);
        this.settings.add(this.enhancedMounts);
    }

    public boolean getLegendaryDropPrevention() {
        return Status.valueOf(legendaryDropPrevention.getModes().get(legendaryDropPrevention.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

    public boolean getValuableDropPrevention() {
        return Status.valueOf(valuableDropPrevention.getModes().get(valuableDropPrevention.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

    public boolean getSlotLocks() {
        return Status.valueOf(slotLocks.getModes().get(slotLocks.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

    public boolean getEnhancedMounts() {
        return Status.valueOf(enhancedMounts.getModes().get(enhancedMounts.currentMode).toUpperCase()).equals(Status.ENABLED);
    }

}
