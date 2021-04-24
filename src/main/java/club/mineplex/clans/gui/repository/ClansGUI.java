package club.mineplex.clans.gui.repository;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class ClansGUI extends GuiScreen {

    protected GuiScreen previousScreen;
    private boolean useBackButton = true;

    public ClansGUI(final GuiScreen previous) {
        this.previousScreen = previous;
    }

    public ClansGUI() {
        this.useBackButton = false;
    }

    @Override
    public void initGui() {

        if (useBackButton) {
            final int buttonWidth = 200;
            this.buttonList.add(
                    new GuiButton(
                            999,
                            24 / 3,
                            this.height - 24,
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

                this.mc.displayGuiScreen(previousScreen);

            } else {

                try {
                    this.mc.thePlayer.closeScreen();
                } catch (final NullPointerException e) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }

            }
        }
    }
}
