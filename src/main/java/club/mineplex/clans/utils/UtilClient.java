package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.utils.object.ConnectionBuilder;
import club.mineplex.clans.utils.object.DelayedTask;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.net.URI;

public class UtilClient {
    private UtilClient() {
    }

    public static void openWebLink(final String url) {
        try {
            final Class<?> oclass = Class.forName("java.awt.Desktop");
            final Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(url)});
        } catch (final Exception e) {
            System.err.println("Couldn\\'t open link");
            e.printStackTrace();
        }
    }

    public static void openGuiScreen(final GuiScreen screen) {
        new DelayedTask(() -> ClansMod.getInstance().getMinecraft().displayGuiScreen(screen));
    }

    public static void playSound(final String sound, final float volume, final float pitch) {
        Minecraft.getMinecraft().thePlayer.playSound(sound, volume, pitch);
    }

    public static void checkModVersion(final ClientData clientData) {

        try {
            final ConnectionBuilder builder = new ConnectionBuilder("http://api.mineplex.club/clansmod/version");
            builder.header("Content-Type", "application/json");
            builder.send();
            builder.skipRedirects();

            final JsonObject obj = new Gson().fromJson(builder.getResponseString(), JsonObject.class);
            final String latestVersion = obj.get("lastVersion").getAsString();
            final String downloadUrl = obj.get("downloadUrl").getAsString();

            clientData.setLatestRequiredVersion(latestVersion);
            clientData.setHasLatestRequiredVersion(isGreaterVersion(latestVersion, UtilReference.VERSION));
            clientData.setModDownloadUrl(downloadUrl);

        } catch (final JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public static boolean isModFeatureAllowed(final String moduleId) {
        final ConnectionBuilder builder = new ConnectionBuilder("http://api.mineplex.club/clansmod/features");
        builder.send();
        builder.skipRedirects();

        try {
            final Gson gson = new Gson();
            final JsonArray array = gson.fromJson(builder.getResponseString(), JsonArray.class);

            for (final JsonElement jsonElement : array) {
                final JsonObject object = jsonElement.getAsJsonObject();

                if (object.get("name").getAsString().equals(moduleId)) {
                    return object.get("enabled").getAsBoolean();
                }

            }

        } catch (final JsonSyntaxException e) {
            e.printStackTrace();
            return true;
        }

        return true;
    }

    private static boolean isGreaterVersion(final String lower, final String higher) {
        final String[] arrayLower = lower.split("\\.");
        final String[] arrayHigher = higher.split("\\.");

        for (int i = 0; i < Math.max(arrayHigher.length, arrayLower.length); i++) {
            final int indexRequired = i < arrayLower.length ? Integer.parseInt(arrayLower[i]) : 0;
            final int indexCurrent = i < arrayHigher.length ? Integer.parseInt(arrayHigher[i]) : 0;

            if (indexCurrent >= indexRequired) {
                continue;
            }

            return false;
        }

        return true;
    }

}
