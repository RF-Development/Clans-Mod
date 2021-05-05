package club.mineplex.clans.settings;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.enums.Status;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GuiSettingMode extends SettingsCategory.GuiSetting {

    private final List<Status> modes = new ArrayList<>();
    private final List<String> description;

    private int currentMode = 0;

    public GuiSettingMode(final String label,
                          final SettingsCategory category,
                          final Status mode1,
                          final Status mode2,
                          final Status... modes) {
        this(label, category, null, mode1, mode2, modes);
    }

    public GuiSettingMode(final String label,
                          final SettingsCategory category,
                          final List<String> description,
                          final Status mode1,
                          final Status mode2,
                          final Status... modes) {
        super(label, category);

        this.description = description;
        Collections.addAll(
                this.modes,
                mode1,
                mode2
        );
        if (modes != null) {
            this.modes.addAll(Arrays.asList(modes));
        }

        load(mode1);
    }

    @Override
    public final List<String> getDescription() {
        return description;
    }

    public final List<Status> getModes() {
        return new ArrayList<>(modes);
    }

    public final Status getCurrentMode() {
        return modes.get(currentMode);
    }

    @Override
    protected final void doClick() {
        currentMode = currentMode + 1 > modes.size() - 1 ? 0 : currentMode + 1;
    }

    @Override
    public final String getLabel() {
        String label = modes.get(currentMode).toString();
        if (getAssignedModule().isPresent() && getAssignedModule().get().isModuleBlocked()) {
            label = EnumChatFormatting.RED + "Blocked";
        }

        return getName() + ": " + label;
    }

    @Override
    public final void save() {
        final Configuration configuration = ClansMod.getInstance().getConfiguration();
        configuration.getCategory(getCategory().getConfigID()).get(getConfigID()).set(modes.get(currentMode).getName());
        configuration.save();
    }

    @Override
    public final void load(final Object defaultV) {

        ClansMod.getInstance().getConfiguration().load();
        final String found = ClansMod.getInstance().getConfiguration().get(getCategory().getConfigID(), getConfigID(), defaultV.toString()).getString();
        ClansMod.getInstance().getConfiguration().save();

        currentMode = modes.stream()
                .filter(mode -> mode.getName().equalsIgnoreCase(found))
                .findAny()
                .map(modes::indexOf)
                .orElse(0);
    }

}