package club.mineplex.clans.settings.repository;

import club.mineplex.clans.enums.Status;
import club.mineplex.clans.settings.GuiSettingMode;
import club.mineplex.clans.settings.SettingsCategory;
import net.minecraft.client.resources.I18n;

import java.util.Arrays;

public class ClansSettings extends SettingsCategory {

    private final GuiSettingMode legendaryDropPrevention;
    private final GuiSettingMode valuableDropPrevention;
    private final GuiSettingMode slotLocks;
    private final GuiSettingMode enhancedMounts;

    public ClansSettings() {
        super("Clans");

        legendaryDropPrevention = new GuiSettingMode("Legendary Drop Prevention", this, Arrays.asList(
                "Stop dropping your legendary items.",
                "The list of legendary items includes:",
                "",
                "● Windblade",
                "● Hyper Axe",
                "● Magnetic Maul",
                "● Giant's Broadsword",
                "● Meridian Scepter",
                "● Alligator's Tooth",
                "● Scythe of the Fallen Lord",
                "● Knight's Greatlance",
                "● Runed Pickaxe"
        ), Status.ENABLED, Status.DISABLED);


        valuableDropPrevention = new GuiSettingMode("Valuable Item Drop Prevention", this, Arrays.asList(
                "Stop dropping your valuable items.",
                "The list of valuable items includes:",
                "",
                "● Gold Token"
        ), Status.ENABLED, Status.DISABLED);


        slotLocks = new GuiSettingMode("Inventory Slot Locks", this, Arrays.asList(
                "Prevent yourself from interacting with certain",
                "slots in your inventory.",
                "",
                "You can now lock and unlock slots by hovering",
                "over them in your inventory and pressing the",
                String.format("'%s' keybind found in your game controls.", I18n.format("clansmod.key.slotlock.desc")),
                "",
                "You cannot drop an item in a locked slot and you",
                "cannot move it in your inventory."
        ), Status.ENABLED, Status.DISABLED);


        enhancedMounts = new GuiSettingMode("Enhanced Mounts", this, Arrays.asList(
                "Spawn your most recent mount by pressing",
                String.format("the '%s' keybind found", I18n.format("clansmod.key.mount.desc")),
                "in your control settings.",
                "",
                "Your most recent mount is determined by",
                "the last one you clicked in the /mount GUI"
        ), Status.ENABLED, Status.DISABLED);


        addSettings(
                valuableDropPrevention,
                legendaryDropPrevention,
                slotLocks,
                enhancedMounts
        );
    }

    public GuiSettingMode getLegendaryDropPrevention() {
        return legendaryDropPrevention;
    }

    public GuiSettingMode getValuableDropPrevention() {
        return valuableDropPrevention;
    }

    public GuiSettingMode getSlotLocks() {
        return slotLocks;
    }

    public GuiSettingMode getEnhancedMounts() {
        return enhancedMounts;
    }

}
