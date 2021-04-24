package club.mineplex.clans.cache;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class ModCache<T> {

    protected ModCache() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateCache();
            }
        }, 0, TimeUnit.MINUTES.toMillis(30));
    }

    public abstract T get();

    public abstract void updateCache();

}
