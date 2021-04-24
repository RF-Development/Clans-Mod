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

        addSettings(
                this.valuableDropPrevention,
                this.legendaryDropPrevention,
                this.slotLocks,
                this.enhancedMounts
        );
    }

    public boolean getLegendaryDropPrevention() {
        return legendaryDropPrevention.getModes().get(legendaryDropPrevention.getCurrentMode()).equals(Status.ENABLED);
    }

    public boolean getValuableDropPrevention() {
        return valuableDropPrevention.getModes().get(valuableDropPrevention.getCurrentMode()).equals(Status.ENABLED);
    }

    public boolean getSlotLocks() {
        return slotLocks.getModes().get(slotLocks.getCurrentMode()).equals(Status.ENABLED);
    }

    public boolean getEnhancedMounts() {
        return enhancedMounts.getModes().get(enhancedMounts.getCurrentMode()).equals(Status.ENABLED);
    }

}
