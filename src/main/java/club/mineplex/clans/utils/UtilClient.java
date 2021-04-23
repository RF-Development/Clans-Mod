package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.utils.object.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.net.URI;
import java.net.URL;

public class UtilClient {

    public static void openWebLink(URL url) {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {url.toURI()});
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

    }

    public static void openGuiScreen(GuiScreen screen) {
        new DelayedTask(() -> ClansMod.getInstance().getMinecraft().displayGuiScreen(screen));
    }

    public static void playSound(String sound, float volume, float pitch) {
        Minecraft.getMinecraft().thePlayer.playSound(sound, volume, pitch);
    }

}
