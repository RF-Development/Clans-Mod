package club.mineplex.clans.modules.map_fix;

import club.mineplex.clans.modules.ModModule;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleMapFix extends ModModule {

    public ModuleMapFix() {
        super("Clans Map Fix");
    }

    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        if (!data.isMineplex()) {
            return;
        }
        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().clearLoadedMaps();
    }

}
