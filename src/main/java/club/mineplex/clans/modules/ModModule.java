package club.mineplex.clans.modules;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import net.minecraftforge.common.MinecraftForge;

public class ModModule {

    protected final ClientData data = ClansMod.getInstance().getClientData();
    private final String name;

    public ModModule(String name) {
        this.name = name;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return true;
    }

}
