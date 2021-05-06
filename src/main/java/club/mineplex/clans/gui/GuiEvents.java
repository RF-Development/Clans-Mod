package club.mineplex.clans.gui;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.gui.repository.VersionBlockGUI;
import club.mineplex.clans.utils.object.DelayedTask;
import net.minecraft.client.Minecraft;
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
                    20210506,
                    gui.width / 2 - 10,
                    gui.height / 4 + 48 + 72 + 12 + 20 + 6,
                    20,
                    20
            );

            button.allImage = false;
            event.buttonList.add(button);

            new DelayedTask(() -> {
                if (!ClansMod.getInstance().getClientData().hasLatestRequiredVersion()) {
                    Minecraft.getMinecraft().displayGuiScreen(new VersionBlockGUI());
                }
            }, 10);

        }
    }

}