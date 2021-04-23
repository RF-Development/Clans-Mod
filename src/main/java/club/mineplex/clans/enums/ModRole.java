package club.mineplex.clans.enums;

import club.mineplex.clans.utils.UtilResource;
import net.minecraft.util.ResourceLocation;

public enum ModRole {

    CLANS_INSIGHTS("Clans Insights", UtilResource.getResource("textures/icons/tablist/ci.png")),
    CLANS_MANAGEMENT("Clans Management", UtilResource.getResource("textures/icons/tablist/cs.png")),
    CLANS_MANAGEMENT_ASSISTANT("Clans Management Assistant", UtilResource.getResource("textures/icons/tablist/cs.png")),
    MOD_DEV("Developer", UtilResource.getResource("textures/icons/tablist/developer.png")),
    IDEAS("Ideas", UtilResource.getResource("textures/icons/tablist/ideas.png"));

    private final ResourceLocation resourceLocation;
    private final String name;

    ModRole(String name, ResourceLocation resource) {
        this.name = name;
        this.resourceLocation = resource;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return resourceLocation;
    }

    public static ModRole of(String name) {
        for (ModRole value : ModRole.values()) if (value.getName().equalsIgnoreCase(name)) return value;
        return null;
    }

}
