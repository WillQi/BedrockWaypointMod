package io.willqi.github.bedrockwaypointmod.internal;

import io.willqi.github.bedrockwaypointmod.internal.component.WindowComponent;
import io.willqi.github.bedrockwaypointmod.ui.UIObject;

import java.awt.*;

public class WaypointWindow {

    private WindowComponent component;

    public WaypointWindow () {
        setupWindow();
    }

    /**
     * Add UI object to the screen.
     * @param obj Object you want to add.
     */
    public void addUIObject (UIObject obj) {
        component.addUIObject(obj);
    }

    /**
     * Remove UI object from the screen.
     * @param obj Object you want to remove.
     */
    public void removeUIObject (UIObject obj) {
        component.removeUIObject(obj);
    }

    /**
     * Extract text from the screen
     * @param xA The left x coordinate.
     * @param yA The top y coordinate.
     * @param xB The right x coordinate.
     * @param yB The bottom y coordinate.
     * @return The text that was extracted.
     */
    private String readTextAt (final int xA, final int yA, final int xB, final int yB) {
        return null;
    }

    /**
     * Used to initialize the window we can draw on.
     */
    private void setupWindow () {
        final Window window = new Window(null);
        component = new WindowComponent();
        window.add(component);
        window.pack();
        window.setBounds(window.getGraphicsConfiguration().getBounds());
        window.setBackground(new Color(0, true));
        window.setAlwaysOnTop(true);
        window.setVisible(true);
    }
}
