package club.mineplex.clans.clansmod;

import net.minecraft.launchwrapper.Launch;

public class ModInitializer {

    private static ModInitializer instance;
    private boolean isObfuscated = false;

    public static ModInitializer getInstance() {
        if (instance == null) {
            instance = new ModInitializer();
        }
        return instance;
    }

    public void init() {

        isObfuscated = !((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"));

    }

    public boolean isObfuscated() {
        return isObfuscated;
    }

}
