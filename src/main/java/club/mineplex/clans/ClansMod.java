package club.mineplex.clans;

import club.mineplex.clans.clansmod.ModInitializer;
import club.mineplex.clans.clansmod.keybind.KeyBindingManager;
import club.mineplex.clans.gui.GuiEvents;
import club.mineplex.clans.listeners.ListenerServerConnection;
import club.mineplex.clans.modules.ModModule;
import club.mineplex.clans.modules.drop_prevention.DropPrevention;
import club.mineplex.clans.modules.enhanced_mounts.EnhancedMounts;
import club.mineplex.clans.modules.message_filter.MessageFilter;
import club.mineplex.clans.modules.mineplex_server.MineplexServerHandler;
import club.mineplex.clans.modules.slot_lock.SlotLock;
import club.mineplex.clans.modules.status_indicators.IndicatorManager;
import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilReference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.*;

@Mod(modid = UtilReference.MODID, version = UtilReference.VERSION, name = UtilReference.MODNAME)
public class ClansMod {

    @Mod.Instance(value = UtilReference.MODID)
    private static ClansMod instance;
    private final Map<Class<? extends ModModule>, ModModule> modules = new HashMap<>();
    private final DiscordIntegration discord = new DiscordIntegration();
    private ClientData clientData;
    private Configuration configuration;

    public static ClansMod getInstance() {
        return instance;
    }

    private void registerEvents(final Object... events) {
        for (final Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }

    private void registerModules(final ModModule... modules) {
        for (final ModModule module : modules) {
            this.modules.put(module.getClass(), module);
            MinecraftForge.EVENT_BUS.register(module);
        }
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        KeyBindingManager.getInstance().setup();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        clientData = new ClientData();
        UtilClient.checkModVersion(clientData);

        System.out.println("----------------");
        System.out.println();
        System.out.println(UtilReference.MODNAME + ": Made by ReyBot");
        System.out.println();
        System.out.println("----------------");

        registerEvents(
                this,
                new GuiEvents(),
                new ListenerServerConnection(),
                new IndicatorManager()
        );

        ModInitializer.getInstance().init();

        // Enable modules
        registerModules(
                new MineplexServerHandler(),
                new DropPrevention(),
                new SlotLock(),
                new MessageFilter(),
                new EnhancedMounts()
        );

        discord.start();
    }

    public List<ModModule> getEnabledModules() {
        return new ArrayList<>(modules.values());
    }

    public <T extends ModModule> Optional<T> getModule(final Class<T> moduleClass) {
        return Optional.ofNullable(moduleClass.cast(modules.get(moduleClass)));
    }

    public <T extends ModModule> T getModuleThrow(final Class<T> moduleClass) {
        return getModule(moduleClass)
                .orElseThrow(() -> new RuntimeException("Invalid module class: " + moduleClass.getName()));
    }

    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public ClientData getClientData() {
        return clientData;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
