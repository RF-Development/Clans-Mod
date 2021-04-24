package club.mineplex.clans.gui;

import club.mineplex.clans.utils.object.Pair;

public class GuiButtonToggleable extends GuiButtonCustom {

    private long lastUpdated = -1;
    private boolean on;

    public GuiButtonToggleable(final int buttonId, final int x, final int y) {
        super(buttonId, x, y, 40, 20, GuiButtonCustom.customGUITextures, "");

        on = true;
    }

    @Override
    protected Pair<Integer, Integer> updatePositions(final boolean hovered) {

        final int u;
        int v;

        if (!this.enabled) {
            u = 0;
            v = 40 + (hovered ? 20 : 0);
        } else {
            u = isEnabled() ? 0 : 40;
            v = 80;

            if (hovered) {
                v += 20;
            }

        }

        return new Pair<>(u, v);
    }

    public void setLocked(final boolean locked) {
        this.enabled = !locked;
        lastUpdated = System.currentTimeMillis();
    }

    public boolean isEnabled() {
        return on;
    }

    public void setEnabled(final boolean enabled) {
        if (lastUpdated != -1 && System.currentTimeMillis() - lastUpdated < 200) {
            return;
        }

        this.on = enabled;
        lastUpdated = System.currentTimeMillis();
    }

}
