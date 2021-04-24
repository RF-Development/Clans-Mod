package club.mineplex.clans.gui.repository;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.gui.GuiButtonCustom;
import club.mineplex.clans.settings.SettingsCategory;
import club.mineplex.clans.settings.SettingsHandler;
import club.mineplex.clans.utils.UtilClient;
import club.mineplex.clans.utils.UtilColor;
import club.mineplex.clans.utils.UtilReference;
import club.mineplex.clans.utils.UtilResource;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MainGUI extends ClansGUI {
    private static final int BUTTON_SEPARATION = 5;
    private static final int BUTTON_HEIGHT = 20;

    private static final String TITLE = UtilReference.MODNAME + " v" + UtilReference.VERSION;
    
    private final Map<GuiButton, Function<GuiButton, Boolean>> categoryFunctions = new HashMap<>();
    private final List<GuiButton> dynamicSettings = new ArrayList<>();

    private float titleY;
    private float titleX;

    private int headerEnd;
    private int sidebarEnd;

    private SettingsCategory currentCategory = null;

    @Override
    public void initGui() {
        super.initGui();

        titleY = this.height / 18F;
        titleX = this.width / 2F - ClansMod.getInstance().getMinecraft().fontRendererObj.getStringWidth(TITLE) / 2F;
        headerEnd = (int) titleY * 2 + ClansMod.getInstance().getMinecraft().fontRendererObj.FONT_HEIGHT;
        sidebarEnd = this.width / 5;

        final int buttonWidth = sidebarEnd - 25;
        final int xIndex = sidebarEnd / 2 - buttonWidth / 2;

        final int settingsSize = SettingsHandler.getInstance().getSettings().size();
        int yIndex = (this.height / 2 + headerEnd / 2) - (settingsSize * (BUTTON_HEIGHT) + (settingsSize - 1) * BUTTON_SEPARATION) / 2;
        int id = 1;

        for (final SettingsCategory category : SettingsHandler.getInstance().getSettings()) {
            final GuiButton button = new GuiButton(
                    id,
                    xIndex,
                    yIndex,
                    buttonWidth,
                    BUTTON_HEIGHT,
                    fontRendererObj.trimStringToWidth(category.getName(), buttonWidth)
            );

            this.buttonList.add(button);
            this.categoryFunctions.put(button, guiButton -> updateCategoryButtons(category));

            yIndex += BUTTON_SEPARATION + BUTTON_HEIGHT;
            id++;
        }

        if (currentCategory != null) updateCategoryButtons(currentCategory);

        ScaledResolution sr = new ScaledResolution(this.mc);
        int scale = sr.getScaledHeight() / 15;
        GuiButtonCustom discord = new GuiButtonCustom(
                1001,
                sidebarEnd / 2 - sidebarEnd / 4 - scale / 2,
                this.height - 10 - scale,
                scale,
                scale,
                UtilResource.getResource("textures/icons/discord.png"),
                "Discord"
        );
        GuiButtonCustom github = new GuiButtonCustom(
                1002,
                sidebarEnd / 2 + sidebarEnd / 4 - scale / 2,
                this.height - 10 - scale,
                scale,
                scale,
                UtilResource.getResource("textures/icons/github.png"),
                "GitHub"
        );

        discord.setAllImage(true);
        github.setAllImage(true);
        this.buttonList.add(discord);
        this.buttonList.add(github);
    }

    private boolean updateCategoryButtons(final SettingsCategory newCategory) {

        this.buttonList.removeAll(dynamicSettings);
        dynamicSettings.forEach(categoryFunctions::remove);
        dynamicSettings.clear();

        int id = 1000;
        final int settingsSize = newCategory.getSettings().size();
        int buttonY = (this.height / 2 + headerEnd / 2) - (settingsSize * (BUTTON_HEIGHT) + (settingsSize - 1) * BUTTON_SEPARATION) / 2;
        final int buttonWidth = newCategory.getSettings()
                .stream()
                .mapToInt(set -> fontRendererObj.getStringWidth(set.getLabel()) + 10)
                .max()
                .orElse(200);

        for (final SettingsCategory.GuiSetting setting : newCategory.getSettings()) {
            final GuiButton button = new GuiButton(
                    id,
                    this.width / 2 + sidebarEnd / 2 - buttonWidth / 2,
                    buttonY,
                    buttonWidth,
                    BUTTON_HEIGHT,
                    setting.getLabel()
            );

            this.buttonList.add(button);
            this.dynamicSettings.add(button);
            categoryFunctions.put(button, guiButton -> {
                button.displayString = setting.getLabel();

                if (!setting.getAssignedModule().isPresent() || !setting.getAssignedModule().get().isModuleBlocked()) {
                    setting.doChange();
                }

                return true;
            });

            buttonY += BUTTON_SEPARATION + BUTTON_HEIGHT;
            id++;
        }

        currentCategory = newCategory;
        return true;
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (categoryFunctions.containsKey(button)) {
            categoryFunctions.get(button).apply(button);
        }

        if (button.id == 1002) {
            UtilClient.openWebLink(UtilReference.GITHUB);
        }

        if (button.id == 1001) {
            UtilClient.openWebLink(UtilReference.SUPPORT_DISCORD);
        }

    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        final String categoryTitle = currentCategory != null ? currentCategory.getName() + " Settings" : "";
        final int color = UtilColor.getChromaColor();

        /* HEADER */
        drawRect(0, 0, this.width, headerEnd, new Color(255, 255, 255, 50).getRGB());
        fontRendererObj.drawStringWithShadow(TITLE, titleX, titleY, color);

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