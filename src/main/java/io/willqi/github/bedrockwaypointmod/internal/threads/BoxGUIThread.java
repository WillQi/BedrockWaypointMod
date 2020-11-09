package io.willqi.github.bedrockwaypointmod.internal.threads;

import io.willqi.github.bedrockwaypointmod.WaypointLauncher;
import io.willqi.github.bedrockwaypointmod.ui.Rectangle;

import java.awt.*;

public class BoxGUIThread implements Runnable {

    private final WaypointLauncher launcher;

    public BoxGUIThread (WaypointLauncher launcher) {
        this.launcher = launcher;
    }

    @Override
    public void run() {

        Color color = new Color(0, 255, 255, 100);
        final Rectangle coordinateRect = new Rectangle(
                launcher.getConfig().getCoordinatesForBox().getTopLeft().getX(),
                launcher.getConfig().getCoordinatesForBox().getTopLeft().getY(),
                launcher.getConfig().getCoordinatesForBox().getBottomRight().getX(),
                launcher.getConfig().getCoordinatesForBox().getBottomRight().getY(),
                color
        );
        final Rectangle waypointRect = new Rectangle(
                launcher.getConfig().getCoordinatesForWaypointsDisplay().getX(),
                launcher.getConfig().getCoordinatesForWaypointsDisplay().getY(),
                launcher.getConfig().getCoordinatesForWaypointsDisplay().getX() + (int)(launcher.getConfig().getMaxWaypoints() * launcher.getConfig().getFontSize() / 2),
                launcher.getConfig().getCoordinatesForWaypointsDisplay().getY() + (int)(launcher.getConfig().getMaxWaypoints() * launcher.getConfig().getFontSize()),
                color
        );

        launcher.getWindow().addUIObject(coordinateRect);
        launcher.getWindow().addUIObject(waypointRect);

        try {
            Thread.sleep(3000); // Not guarenteed, but it's fine for our use case.
        } catch (InterruptedException exception) {}

        launcher.getWindow().removeUIObject(coordinateRect);
        launcher.getWindow().removeUIObject(waypointRect);
    }

}
