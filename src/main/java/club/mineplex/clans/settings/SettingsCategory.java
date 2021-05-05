package club.mineplex.clans.settings;

import club.mineplex.clans.modules.ModModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class SettingsCategory {

    private final List<GuiSetting> settings = new ArrayList<>();
    private final String name;

    protected SettingsCategory(final String name) {
        this.name = name;
    }

    protected final void addSettings(final GuiSetting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public String getName() {
        return name;
    }

    public String getConfigID() {
        return getName().toLowerCase().replaceAll(" ", "-");
    }

    public List<GuiSetting> getSettings() {
        return new ArrayList<>(settings);
    }

    public abstract static class GuiSetting {

        private final String name;
        private final SettingsCategory category;
        private ModModule assignedModule = null;

        GuiSetting(final String name, final SettingsCategory category) {
            this.name = name;
            this.category = category;
        }

        public Optional<ModModule> getAssignedModule() {
            return Optional.ofNullable(assignedModule);
        }

        public void setAssignedModule(final ModModule module) {
            assignedModule = module;
        }

        public String getName() {
            return name;
        }

        SettingsCategory getCategory() {
            return category;
        }

        public void doChange() {
            doClick();
            save();
        }

        String getConfigID() {
            return getName().toLowerCase().replaceAll(" ", "-");
        }

        protected abstract void doClick();

        public abstract String getLabel();

        public abstract void save();

        public abstract void load(Object defaultV);

        public abstract List<String> getDescription();

    }

}
