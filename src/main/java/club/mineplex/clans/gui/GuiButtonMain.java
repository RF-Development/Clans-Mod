package club.mineplex.clans.gui;

import club.mineplex.clans.gui.repository.MainGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiButtonMain extends GuiButtonCustom {

    public GuiButtonMain(final int buttonId, final int x, final int y, final int widthIn, final int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, GuiButtonCustom.customGUITextures, "");

        textureHeight = heightIn;
        textureWidth = widthIn;
        horizontal = 0;
        vertical = 0;
    }

    @SubscribeEvent
    public void onClick(final GuiScreenEvent.ActionPerformedEvent event) {
        if (event.gui instanceof GuiMainMenu && (event.button.id == this.id)) {
            Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
        }
    }

}