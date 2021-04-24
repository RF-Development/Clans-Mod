package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import club.mineplex.clans.utils.object.ConnectionBuilder;
import club.mineplex.clans.utils.object.DelayedTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

public class UtilClient {
    private UtilClient() {
    }

    public static void openWebLink(String url) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(url)});
        } catch (Exception e) {
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
        final String latestVersion = obj.get("latest-version").getAsString();
        final String latestRequired = obj.get("latest-required").getAsString();
        final String[] arrayRequired = latestRequired.split("\\.");
        final String[] arrayCurrent = UtilReference.VERSION.split("\\.");

        clientData.setLatestRequiredVersion(latestRequired);
        clientData.setLatestVersion(latestVersion);

            for (int i = 0; i < Math.max(arrayCurrent.length, arrayRequired.length); i++) {
                final int indexRequired = i < arrayRequired.length ? Integer.parseInt(arrayRequired[i]) : 0;
                final int indexCurrent = i < arrayCurrent.length ? Integer.parseInt(arrayCurrent[i]) : 0;

                if (indexCurrent >= indexRequired) {
                    continue;
                }

                clientData.setHasLatestRequiredVersion(false);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

}
