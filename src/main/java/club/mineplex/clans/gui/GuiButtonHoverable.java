package club.mineplex.clans.gui;

import net.minecraft.client.gui.GuiButton;

import java.util.List;
import java.util.Optional;

public class GuiButtonHoverable extends GuiButton {

    private final List<String> description;

    public GuiButtonHoverable(final int buttonId, final int x, final int y, final String buttonText, final List<String> description) {
        this(buttonId, x, y, 200, 20, buttonText, description);
    }

    public GuiButtonHoverable(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final List<String> description) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.description = description;
    }

    public final Optional<List<String>> getDescription() {
        return Optional.ofNullable(description);
    }

}
