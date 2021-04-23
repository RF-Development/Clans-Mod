package club.mineplex.clans.asm;

import club.mineplex.clans.utils.UtilReference;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@Name (UtilReference.MODNAME)
@MCVersion ("1.8.9")
public class FMLLoadingPlugin implements IFMLLoadingPlugin {

    public FMLLoadingPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("clansmod.mixins.json");
    }

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> map) {}

    public String getAccessTransformerClass() {
        return null;
    }

}
