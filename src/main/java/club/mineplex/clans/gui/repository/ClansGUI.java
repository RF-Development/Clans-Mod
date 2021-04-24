package club.mineplex.clans.gui.repository;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.ClientData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class ClansGUI extends GuiScreen {

    protected final ClientData clientData = ClansMod.getInstance().getClientData();
    private final GuiScreen previousScreen;
    private final boolean useBackButton;

    public ClansGUI(final GuiScreen previous) {
        previousScreen = previous;
        useBackButton = true;
    }

    ClansGUI() {
        previousScreen = null;
        useBackButton = false;
    }

    @Override
    public void initGui() {

        if (useBackButton) {
            final int buttonWidth = 200;
            buttonList.add(
                    new GuiButton(
                            999,
                            24 / 3,
                            height - 24,
                            buttonWidth / 3,
                            20,
                            "Back"
                    )
            );
        }

        super.initGui();
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 9999) {
            if (previousScreen != null) {

                mc.displayGuiScreen(previousScreen);

            } else {

                try {
                    mc.thePlayer.closeScreen();
                } catch (final NullPointerException e) {
                    mc.displayGuiScreen(new GuiMainMenu());
                }

            }
        }
    }
}
