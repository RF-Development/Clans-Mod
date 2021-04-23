package club.mineplex.clans.cache;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ModCache {

    public ModCache() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateCache();
            }
        }, 0, 30L * 60L * 1000L);
    }

    public abstract <T> Object get();

    public abstract void updateCache();

}
