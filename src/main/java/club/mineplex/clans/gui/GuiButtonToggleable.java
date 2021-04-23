package club.mineplex.clans.gui;

import club.mineplex.clans.utils.object.ObjectPair;

public class GuiButtonToggleable extends GuiButtonCustom {

    private long lastUpdated = -1;
    private boolean on;

    public GuiButtonToggleable(int buttonId, int x, int y) {
        super(buttonId, x, y, 40, 20, GuiButtonCustom.customGUITextures, "");

        on = true;
    }

    @Override
    protected ObjectPair<Integer, Integer> updatePositions(boolean hovered) {

        int u, v;

        if (!this.enabled)
        {
            u = 0;
            v = 40 + (hovered ? 20 : 0);
        }
        else
        {
            u = isEnabled() ? 0 : 40;
            v = 80;

            if (hovered)
            {
                v += 20;
            }

        }

        return new ObjectPair<>(u, v);
    }

    public void setLocked(boolean locked) {
        this.enabled = !locked;
        lastUpdated = System.currentTimeMillis();
    }

    public void setEnabled(boolean enabled) {
        if (lastUpdated != -1 && System.currentTimeMillis() - lastUpdated < 200) return;

        this.on = enabled;
        lastUpdated = System.currentTimeMillis();
    }

    public boolean isEnabled() {
        return on;
    }

}
