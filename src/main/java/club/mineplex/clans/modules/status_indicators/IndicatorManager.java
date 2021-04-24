package club.mineplex.clans.modules.status_indicators;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class IndicatorManager {

    public static final int U_PIXELS = 32;
    public static final int V_PIXELS = 32;

    final ClientData data = ClansMod.getInstance().getClientData();
    final List<Indicator> registeredIndicators = new ArrayList<>();

    public IndicatorManager() {
        // registeredIndicators.add(new Indicator());
    }

    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Pre event) {

        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR && data.isMineplex()) {
            final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            final List<Indicator> enabledIndicators = new ArrayList<>();
            for (final Indicator indicator : registeredIndicators) {
                if (indicator.isEnabled()) {
                    enabledIndicators.add(indicator);
                }
            }

            final int separation = 8;
            final int y = 25;
            int xIndex = scaledResolution.getScaledWidth() / 2 - (enabledIndicators.size() * U_PIXELS / 2) - (enabledIndicators.size() - 1) * separation;

            for (final Indicator enabledIndicator : enabledIndicators) {
                ClansMod.getInstance().getMinecraft().getTextureManager().bindTexture(enabledIndicator.getResourceLocation());
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                final int u = enabledIndicator.getHorizontal();
                final int v = enabledIndicator.getVertical();
                Gui.drawModalRectWithCustomSizedTexture(xIndex, y, u, v, U_PIXELS, V_PIXELS, U_PIXELS, V_PIXELS);
                xIndex += separation + U_PIXELS;
            }

        }

    }

}
