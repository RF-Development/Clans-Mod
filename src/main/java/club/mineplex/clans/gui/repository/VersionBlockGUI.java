package club.mineplex.clans.gui.repository;

import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilReference;
import net.minecraft.client.gui.GuiButton;

public class VersionBlockGUI extends ClansGUI {

    @Override
    public void initGui() {
        super.initGui();

        final String buttonTitle = "Click here to get the download!";
        final int buttonWidth = fontRendererObj.getStringWidth(buttonTitle) + 15;
        buttonList.add(new GuiButton(
                999,
                width / 2 - buttonWidth / 2,
                height / 2 + 20,
                buttonWidth,
                20,
                buttonTitle
        ));
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 999) {
            UtilClient.openWebLink(clientData.getModDownloadUrl());
        }
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        // IGNORE
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();

        final String title = UtilReference.MODNAME + " cannot initialize!";
        fontRendererObj.drawStringWithShadow(
                title,
                (float) width / 2 - (fontRendererObj.getStringWidth(title) / 2F),
                height / 2F - 20,
                0xFFFFFF)
        ;

        final String currentVersion = UtilReference.VERSION;
        final String requiredVersion = clientData.getLatestRequiredVersion();
        final String update = String.format(
                "You must update the mod to %s. [Currently on: %s]",
                requiredVersion,
                currentVersion
        );

        fontRendererObj.drawStringWithShadow(
                update,
                (float) width / 2 - (fontRendererObj.getStringWidth(update) / 2F),
                height / 2F,
                0xFFFFFF
        );

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
