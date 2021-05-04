package club.mineplex.clans.gui;

import club.mineplex.clans.utils.UtilResource;
import club.mineplex.clans.utils.object.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class GuiButtonWithImage extends GuiButton {

    static final ResourceLocation customGUITextures = UtilResource.getResource("textures/gui/gui.png");
    private final ResourceLocation texture;

    int textureWidth;
    int textureHeight;
    int horizontal;
    int vertical;
    boolean allImage = false;

    public GuiButtonWithImage(final int buttonId,
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
        if (visible) {

            if (allImage) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                mc.getTextureManager().bindTexture(texture);
                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, width, height, textureWidth, textureHeight);

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            } else {

                mc.getTextureManager().bindTexture(texture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                final boolean hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
                final Pair<Integer, Integer> pos = updatePositions(hovered);

                drawTexturedModalRect(xPosition, yPosition, pos.getKey(), pos.getValue(), width, height);

            }
        }
    }

    protected Pair<Integer, Integer> updatePositions(final boolean hovered) {
        return new Pair<>(
                horizontal,
                hovered ? vertical + height : vertical
        );
    }

    public boolean isAllImage() {
        return allImage;
    }

    public void setAllImage(final boolean allImage) {
        this.allImage = allImage;
    }
}
