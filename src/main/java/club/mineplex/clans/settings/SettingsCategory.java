package club.mineplex.clans.settings;

import java.util.ArrayList;
import java.util.List;

public abstract class SettingsCategory {

    protected final List<GuiSetting> settings = new ArrayList<>();
    private final String name;

    public SettingsCategory(String name) {
        this.name = name;
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

    public static abstract class GuiSetting {

        private final String name;
        private final SettingsCategory category;

        public GuiSetting(String name, SettingsCategory category) {
            this.name = name;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public SettingsCategory getCategory() {
            return category;
        }

        public void doChange() {
            doClick();
            save();
        }

        protected String getConfigID() {
            return getName().toLowerCase().replaceAll(" ", "-");
        }

        protected abstract void doClick();

        public abstract String getLabel();

        public abstract void save();

        public abstract void load(Object defaultV);

    }

}
