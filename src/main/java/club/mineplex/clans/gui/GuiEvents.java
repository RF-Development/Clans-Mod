package club.mineplex.clans.gui;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEvents {

    @SubscribeEvent
    public void guiEvent(final GuiScreenEvent.InitGuiEvent.Post event) {
        final GuiScreen gui = event.gui;
        if (gui instanceof GuiMainMenu) {
            final GuiButtonMain button = new GuiButtonMain(
                    20,
                    gui.width / 2 + 104,
                    gui.height / 4 + 48 + 72 + 12,
                    20,
                    20
            );
            button.allImage = false;

            event.buttonList.add(button);
        }
    }

}