package club.mineplex.clans.settings;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.enums.Status;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuiSettingMode extends SettingsCategory.GuiSetting {

    private final List<Status> modes = new ArrayList<>();
    private int currentMode = 0;

    public GuiSettingMode(final String label,
                          final SettingsCategory category,
                          final Status mode1,
                          final Status mode2,
                          final Status... modes) {
        super(label, category);

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

    public List<Status> getModes() {
        return new ArrayList<>(modes);
    }

    public int getCurrentMode() {
        return currentMode;
    }

    @Override
    protected void doClick() {
        currentMode = currentMode + 1 > this.modes.size() - 1 ? 0 : currentMode + 1;
    }

    @Override
    public String getLabel() {
        return this.getName() + ": " + modes.get(currentMode);
    }

    @Override
    public void save() {
        final Configuration configuration = ClansMod.getInstance().getConfiguration();
        configuration.getCategory(getCategory().getConfigID()).get(getConfigID()).set(modes.get(currentMode).getName());
        configuration.save();
    }

    @Override
    public void load(final Object defaultV) {
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
