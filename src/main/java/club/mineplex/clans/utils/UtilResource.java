package club.mineplex.clans.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class UtilResource {

    public static void drawPng(boolean blend, Minecraft mc, String resourcePath, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        GlStateManager.pushMatrix();
        if (blend) GlStateManager.enableBlend();
        else GlStateManager.disableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        mc.getTextureManager().bindTexture(new ResourceLocation("clansmod", resourcePath));
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static ResourceLocation getResource(String path) {
        return new ResourceLocation("clansmod" , path);
    }

}
