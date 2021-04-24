package club.mineplex.clans.settings;

import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.settings.repository.DiscordSettings;
import club.mineplex.clans.settings.repository.MineplexSettings;

import java.util.*;

public class SettingsHandler {
    private static SettingsHandler instance;

    public static SettingsHandler getInstance() {
        if (instance == null) {
            instance = new SettingsHandler();
        }
        return instance;
    }

    private final Map<Class<? extends SettingsCategory>, SettingsCategory> settings = new HashMap<>();

    private SettingsHandler() {
        registerSettingCategories(
                new DiscordSettings(),
                new ClansSettings(),
                new MineplexSettings()
        );
    }

    private void registerSettingCategories(final SettingsCategory... categories) {
        for (final SettingsCategory category : categories) {
            settings.put(category.getClass(), category);
        }
    }

    public List<SettingsCategory> getSettings() {
        return new ArrayList<>(settings.values());
    }

    public <T extends SettingsCategory> Optional<T> getSetting(final Class<T> categoryClass) {
        return (Optional<T>) Optional.ofNullable(settings.get(categoryClass));
    }

    public <T extends SettingsCategory> T getSettingThrow(final Class<T> categoryClass) {
        return getSetting(categoryClass)
                .orElseThrow(() -> new RuntimeException("Invalid settings class: " + categoryClass.getName()));
    }
}
