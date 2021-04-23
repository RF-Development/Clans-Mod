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

    public static final int uPixels = 32, vPixels = 32;

    final ClientData data = ClansMod.getInstance().getClientData();
    final List<Indicator> registeredIndicators = new ArrayList<>();

    public IndicatorManager() {
        // registeredIndicators.add(new Indicator());
    }

    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {

        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR && data.isMineplex) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            List<Indicator> enabledIndicators = new ArrayList<>();
            registeredIndicators.forEach(indicator -> {
                if (indicator.isEnabled()) enabledIndicators.add(indicator);
            });

            int separation = 8;
            int y = 25, xIndex = sr.getScaledWidth() / 2 - (enabledIndicators.size() * uPixels / 2) - (enabledIndicators.size() - 1) * separation;

            for (Indicator enabledIndicator : enabledIndicators) {
                ClansMod.getInstance().getMinecraft().getTextureManager().bindTexture(enabledIndicator.getResourceLocation());
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                int u = enabledIndicator.u, v = enabledIndicator.v;
                Gui.drawModalRectWithCustomSizedTexture(xIndex, y, u, v, uPixels, vPixels, uPixels, vPixels);
                xIndex += separation + uPixels;
            }

        }

    }

}
