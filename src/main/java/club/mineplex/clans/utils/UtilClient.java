package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.utils.object.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class UtilClient {
    private UtilClient() {
    }

    public static void openGuiScreen(final GuiScreen screen) {
        new DelayedTask(() -> ClansMod.getInstance().getMinecraft().displayGuiScreen(screen));
    }

    public static void playSound(final String sound, final float volume, final float pitch) {
        Minecraft.getMinecraft().thePlayer.playSound(sound, volume, pitch);
    }

}
