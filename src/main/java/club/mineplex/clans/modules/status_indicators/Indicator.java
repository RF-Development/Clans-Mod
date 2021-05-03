package club.mineplex.clans.modules.status_indicators;

import club.mineplex.clans.modules.ModModule;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class Indicator extends ModModule {

    private final int horizontal;
    private final int vertical;
    private final ResourceLocation resourceLocation;
    private boolean isEnabled = false;

    protected Indicator(final String name,
                        final ResourceLocation resourceLocation,
                        final int horizontal,
                        final int vertical) {
        super(name);
        this.resourceLocation = resourceLocation;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public boolean isModuleUsable() {
        return isEnabled;
    }

    protected void setEnabled(final boolean enabled) {
        this.isEnabled = enabled;
    }

    protected abstract void handleMessage(IChatComponent message);

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (data.isMineplex()) {
            handleMessage(event.message);
        }
    }

}
