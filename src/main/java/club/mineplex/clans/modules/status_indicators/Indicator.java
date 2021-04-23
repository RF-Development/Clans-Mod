package club.mineplex.clans.modules.status_indicators;

import club.mineplex.clans.modules.ModModule;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class Indicator extends ModModule {

    private final String name;
    private final ResourceLocation resourceLocation;
    public final int u, v;

    private boolean isEnabled = false;

    public Indicator(String name, ResourceLocation resourceLocation, int u, int v) {
        super(name);
        this.name = name;
        this.resourceLocation = resourceLocation;
        this.u = u;
        this.v = v;
    }

    @Override
    public String getName() {
        return name;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    protected void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    protected abstract void handleMessage(IChatComponent message);

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!data.isMineplex) return;

        handleMessage(event.message);
    }

}
