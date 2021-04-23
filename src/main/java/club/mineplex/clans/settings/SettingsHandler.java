package club.mineplex.clans.settings;

import club.mineplex.clans.settings.repository.ClansSettings;
import club.mineplex.clans.settings.repository.DiscordSettings;
import club.mineplex.clans.settings.repository.MineplexSettings;

import java.util.ArrayList;

public class SettingsHandler {

    private static final ArrayList<SettingsCategory> settings = new ArrayList<>();

    private static final DiscordSettings discordSettings;
    private static final ClansSettings clansSettings;
    private static final MineplexSettings mineplexSettings;

    static {
        discordSettings = new DiscordSettings();
        clansSettings = new ClansSettings();
        mineplexSettings = new MineplexSettings();

        settings.add(discordSettings);
        settings.add(clansSettings);
        settings.add(mineplexSettings);
    }

    public static ArrayList<SettingsCategory> getSettings() {
        return new ArrayList<>(settings);
    }

    public static <T> T getSettings(Class<T> categoryClass) {
        for (SettingsCategory setting : getSettings()) if (setting.getClass() == categoryClass) return categoryClass.cast(setting);
        throw new RuntimeException("Invalid settings class: " + categoryClass.getName());
    }

}
