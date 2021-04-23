package club.mineplex.clans;

import club.mineplex.clans.asm.FMLLoadingPlugin;
import club.mineplex.clans.clansmod.KeyBindingManager;
import club.mineplex.clans.clansmod.ModInitializer;
import club.mineplex.clans.gui.GuiEvents;
import club.mineplex.clans.listeners.ListenerKeybind;
import club.mineplex.clans.listeners.ListenerServerConnection;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.drop_prevention.DropPrevention;
import club.mineplex.clans.modules.enhanced_mounts.EnhancedMounts;
import club.mineplex.clans.modules.message_filter.MessageFilter;
import club.mineplex.clans.modules.mineplex_server.MineplexServerHandler;
import club.mineplex.clans.modules.slot_lock.SlotLock;
import club.mineplex.clans.modules.status_indicators.IndicatorManager;
import club.mineplex.clans.utils.UtilReference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;

@Mod(modid = UtilReference.MODID, version = UtilReference.VERSION, name = UtilReference.MODNAME)
public class ClansMod {

    final ArrayList<ModModule> enabledModules = new ArrayList<>();
    final DiscordIntegration discord = new DiscordIntegration();

    private static ClansMod instance;

    private Minecraft mc = Minecraft.getMinecraft();
    private ClientData clientData;
    private Configuration configuration;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        KeyBindingManager.setup();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;
        clientData = new ClientData();

        discord.start();

        System.out.println("----------------");
        System.out.println();
        System.out.println(UtilReference.MODNAME + ": Made by ReyBot");
        System.out.println();
        System.out.println("----------------");

        new FMLLoadingPlugin();

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(new GuiEvents());
        MinecraftForge.EVENT_BUS.register(new ListenerServerConnection());
        MinecraftForge.EVENT_BUS.register(new IndicatorManager());
        MinecraftForge.EVENT_BUS.register(new ListenerKeybind());

        ModInitializer.getInstance().init();
        enableModules();
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void postInit(FMLPostInitializationEvent event) {
        this.mc = Minecraft.getMinecraft();
    }

    private void enableModules() {
        enabledModules.addAll(Arrays.asList(
                new MineplexServerHandler(),
                new DropPrevention(),
                new SlotLock(),
                new MessageFilter(),
                new EnhancedMounts()
        ));
    }

    public ArrayList<ModModule> getEnabledModules() {
        return enabledModules;
    }

    public static ClansMod getInstance() {
        return instance;
    }

    public Minecraft getMinecraft() {
        return mc;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
