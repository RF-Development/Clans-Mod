package club.mineplex.clans.gui.repository;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.settings.SettingsCategory;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.utils.UtilColor;
import club.mineplex.clans.utils.UtilReference;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class MainGUI extends ClansGUI {

    private final HashMap<GuiButton, Function<GuiButton, Boolean>> categoryFunctions = new HashMap<>();
    private final ArrayList<GuiButton> dynamicSettings = new ArrayList<>();

    private final int buttonSeparation = 5;
    private final int buttonHeight = 20;

    private final String title = UtilReference.MODNAME + " v" + UtilReference.VERSION;
    private float titleY;
    private float titleX;

    private int headerEnd;
    private int sidebarEnd;

    private SettingsCategory currentCategory = null;

    @Override
    public void initGui() {
        super.initGui();

        titleY = this.height / 18F;
        titleX = this.width / 2F - ClansMod.getInstance().getMinecraft().fontRendererObj.getStringWidth(title) / 2F;
        headerEnd = (int) titleY * 2 + ClansMod.getInstance().getMinecraft().fontRendererObj.FONT_HEIGHT;
        sidebarEnd = this.width / 5;

        final int buttonWidth = sidebarEnd - 25;
        final int xIndex = sidebarEnd / 2 - buttonWidth / 2;

        final int settingsSize = SettingsHandler.getSettings().size();
        int yIndex = (this.height / 2 + headerEnd / 2) - (settingsSize * (buttonHeight) + (settingsSize-1) * buttonSeparation) / 2;
        int id = 1;

        for (SettingsCategory category : SettingsHandler.getSettings()) {
            GuiButton button = new GuiButton(
                    id,
                    xIndex,
                    yIndex,
                    buttonWidth,
                    buttonHeight,
                    fontRendererObj.trimStringToWidth(category.getName(), buttonWidth)
            );

            this.buttonList.add(button);
            this.categoryFunctions.put(button, guiButton -> updateCategoryButtons(category));

            yIndex += buttonSeparation + buttonHeight;
            id++;
        }

        if (currentCategory != null) updateCategoryButtons(currentCategory);
    }

    private boolean updateCategoryButtons(SettingsCategory newCategory) {

        this.buttonList.removeAll(dynamicSettings);
        dynamicSettings.forEach(categoryFunctions::remove);
        dynamicSettings.clear();

        int id = 1000;
        int settingsSize = newCategory.getSettings().size();
        int buttonY = (this.height / 2 + headerEnd / 2) - (settingsSize * (buttonHeight) + (settingsSize-1) * buttonSeparation) / 2;
        int buttonWidth = newCategory.getSettings()
                .stream()
                .mapToInt(set -> fontRendererObj.getStringWidth(set.getLabel()) + 10)
                .max()
                .orElse(200);

        for (SettingsCategory.GuiSetting setting : newCategory.getSettings()) {
            GuiButton button = new GuiButton(
                    id,
                    this.width / 2 + sidebarEnd / 2 - buttonWidth / 2,
                    buttonY,
                    buttonWidth,
                    buttonHeight,
                    setting.getLabel()
            );

            this.buttonList.add(button);
            this.dynamicSettings.add(button);
            categoryFunctions.put(button, new Function<GuiButton, Boolean>() {
                @Override
                public Boolean apply(GuiButton guiButton) {
                    setting.doChange();
                    button.displayString = setting.getLabel();
                    return true;
                }
            });

            buttonY += buttonSeparation + buttonHeight;
            id++;
        }

        currentCategory = newCategory;
        return true;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (categoryFunctions.containsKey(button)) {
            categoryFunctions.get(button).apply(button);
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        String categoryTitle = currentCategory != null ? currentCategory.getName() + " Settings" : "";
        int color = UtilColor.getChromaColor();

        /* HEADER */
        drawRect(0, 0, this.width, headerEnd, new Color(255, 255, 255, 50).getRGB());
        fontRendererObj.drawStringWithShadow(title, titleX, titleY, color);

        /* SIDEBAR */
        drawRect(0, headerEnd, sidebarEnd, this.height, new Color(50, 50, 50, 150).getRGB());
        fontRendererObj.drawStringWithShadow(
                "Settings",
                sidebarEnd / 2F - fontRendererObj.getStringWidth("Settings") / 2F,
                headerEnd + (headerEnd / 4F) - fontRendererObj.FONT_HEIGHT / 2F,
                new Color(200, 200, 200, 150).getRGB()
        );

        /* SETTINGS PAGE */
        drawRect(sidebarEnd, headerEnd, this.width, headerEnd + (headerEnd / 2), new Color(10, 10, 10, 175).getRGB());
        drawRect(sidebarEnd, this.height - headerEnd + (headerEnd / 2), this.width, this.height, new Color(10, 10, 10, 175).getRGB());
        fontRendererObj.drawStringWithShadow(
                categoryTitle,
                (this.width - fontRendererObj.getStringWidth(categoryTitle) + sidebarEnd) / 2F,
                headerEnd + (headerEnd / 4F) - fontRendererObj.FONT_HEIGHT / 2F,
                new Color(150, 150, 150, 150).getRGB()
        );

        drawHorizontalLine(0, this.width, headerEnd, color);
        drawVerticalLine(sidebarEnd, headerEnd, this.height, color);

//        TEST GRID
//        drawHorizontalLine(0, this.width, this.height / 2 + headerEnd / 2, UtilColor.getChromaColor());
//        drawVerticalLine(this.width / 2 + sidebarEnd / 2, this.headerEnd, this.height, color);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}