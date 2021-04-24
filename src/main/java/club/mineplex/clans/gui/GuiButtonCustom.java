package club.mineplex.clans.gui;

import club.mineplex.clans.utils.UtilResource;
import club.mineplex.clans.utils.object.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class GuiButtonCustom extends GuiButton {

    public static final ResourceLocation customGUITextures = UtilResource.getResource("textures/gui/gui.png");
    private final ResourceLocation texture;

    protected int textureWidth;
    protected int textureHeight;
    protected int horizontal;
    protected int vertical;
    protected boolean allImage = false;

    public GuiButtonCustom(final int buttonId,
                           final int x,
                           final int y,
                           final int widthIn,
                           final int heightIn,
                           final ResourceLocation resource,
                           final String alternateText) {
        super(buttonId, x, y, widthIn, heightIn, alternateText);

        textureHeight = heightIn;
        textureWidth = widthIn;
        horizontal = 0;
        vertical = 0;

        texture = resource;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {

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

                final boolean hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                final Pair<Integer, Integer> pos = updatePositions(hovered);

                this.drawTexturedModalRect(this.xPosition, this.yPosition, pos.getKey(), pos.getValue(), this.width, this.height);

            }
        }
    }

    protected Pair<Integer, Integer> updatePositions(final boolean hovered) {
        return new Pair<>(
                horizontal,
                hovered ? vertical + height : vertical
        );
    }

}
