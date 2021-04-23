package club.mineplex.clans.settings;

import club.mineplex.clans.ClansMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiSettingMode extends SettingsCategory.GuiSetting {

    private final List<String> modes = new ArrayList<>();
    public int currentMode = 0;

    public GuiSettingMode(String label, SettingsCategory category, Object mode1, Object mode2, Object... modes) {
        super(label, category);

        this.modes.addAll(Arrays.asList(mode1.toString(), mode2.toString()));
        if (modes != null) Arrays.stream(modes).forEach(o -> this.modes.add(o.toString()));

        load(mode1);
    }

    public List<String> getModes() {
        return new ArrayList<>(modes);
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
        ClansMod.getInstance().getConfiguration().getCategory(getCategory().getConfigID()).get(getConfigID()).set(modes.get(currentMode));
        ClansMod.getInstance().getConfiguration().save();
    }

    @Override
    public void load(Object defaultV) {
        ClansMod.getInstance().getConfiguration().load();
        String found = ClansMod.getInstance().getConfiguration().get(getCategory().getConfigID(), getConfigID(), defaultV.toString()).getString();
        ClansMod.getInstance().getConfiguration().save();

        currentMode = modes.stream().filter(s -> s.equalsIgnoreCase(found)).findAny().map(modes::indexOf).orElse(0);
    }

}
