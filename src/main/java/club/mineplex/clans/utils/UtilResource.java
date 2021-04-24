package club.mineplex.clans.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class UtilResource {
    private UtilResource() {
    }

    public static void drawPng(final boolean blend,
                               final Minecraft mc,
                               final String resourcePath,
                               final int x,
                               final int y,
                               final float u,
                               final float v,
                               final int width,
                               final int height,
                               final float textureWidth,
                               final float textureHeight) {
        GlStateManager.pushMatrix();
        if (blend) GlStateManager.enableBlend();
        else GlStateManager.disableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        mc.getTextureManager().bindTexture(new ResourceLocation("clansmod", resourcePath));
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static ResourceLocation getResource(final String path) {
        return new ResourceLocation("clansmod", path);
    }

}
