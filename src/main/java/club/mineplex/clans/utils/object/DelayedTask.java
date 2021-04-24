package club.mineplex.clans.utils.object;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DelayedTask {
    private final Runnable run;
    private int counter;

    public DelayedTask(final Runnable run) {
        this(run, 2);
    }

    public DelayedTask(final Runnable run, final int ticks) {
        this.counter = ticks;
        this.run = run;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        if (this.counter <= 0) {
            MinecraftForge.EVENT_BUS.unregister(this);
            this.run.run();
        }
        this.counter--;
    }
}