package club.mineplex.clans.gui;

import club.mineplex.clans.utils.UtilResource;
import club.mineplex.clans.utils.object.ObjectPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class GuiButtonCustom extends GuiButton {

    public static final ResourceLocation customGUITextures = UtilResource.getResource("textures/gui/gui.png");
    private final ResourceLocation texture;

    protected int textureWidth, textureHeight;
    protected int u, v;
    protected boolean allImage = false;

    public GuiButtonCustom(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation resource, String alternateText) {
        super(buttonId, x, y, widthIn, heightIn, alternateText);

        textureHeight = heightIn;
        textureWidth = widthIn;
        u = 0;
        v = 0;

        texture = resource;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {

            if (allImage) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                mc.getTextureManager().bindTexture(texture);
                Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, width, height, textureWidth, textureHeight);

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            } else {

                mc.getTextureManager().bindTexture(texture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                boolean hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                ObjectPair<Integer, Integer> pos = updatePositions(hovered);

                this.drawTexturedModalRect(this.xPosition, this.yPosition, pos.getKey(), pos.getValue(), this.width, this.height);

            }
        }
    }

    protected ObjectPair<Integer, Integer> updatePositions(boolean hovered) {
        return new ObjectPair<>(
                u,
                hovered ? v + height : v
        );
    }

}
