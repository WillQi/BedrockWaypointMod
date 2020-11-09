package io.willqi.github.bedrockwaypointmod.internal.threads;

import io.willqi.github.bedrockwaypointmod.Waypoint;
import io.willqi.github.bedrockwaypointmod.WaypointLauncher;
import io.willqi.github.bedrockwaypointmod.ui.Text;
import io.willqi.github.bedrockwaypointmod.ui.UIObject;
import io.willqi.github.bedrockwaypointmod.utils.Vector3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimaryModThread implements Runnable {

    private final WaypointLauncher launcher;

    public PrimaryModThread (WaypointLauncher launcher) {
        this.launcher = launcher;
    }

    @Override
    public void run() {

        final int cBoxLx = launcher.getConfig().getCoordinatesForBox().getTopLeft().getX();
        final int cBoxLy = launcher.getConfig().getCoordinatesForBox().getTopLeft().getY();
        final int cBoxRx = launcher.getConfig().getCoordinatesForBox().getBottomRight().getX();
        final int cBoxRy = launcher.getConfig().getCoordinatesForBox().getBottomRight().getY();

        final int wListX = launcher.getConfig().getCoordinatesForWaypointsDisplay().getX();
        final int wListY = launcher.getConfig().getCoordinatesForWaypointsDisplay().getY();

        final int maxWaypointsDisplayed = launcher.getConfig().getMaxWaypoints();
        final float fontSize = launcher.getConfig().getFontSize();

        final List<UIObject> lineObjects = new ArrayList<>();

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                break;
            }

            final String[] text = launcher.getWindow().readTextAt(cBoxLx, cBoxLy, cBoxRx, cBoxRy).split(" ");

            if (text.length >= 3) {
                int x, y, z;
                try {
                    x = Integer.valueOf(text[0]);
                    y = Integer.valueOf(text[1]);
                    z = Integer.valueOf(text[2]);
                } catch (NumberFormatException exception) {
                    // Failed to parse coordinates. Re-parse it.
                    continue;
                }

                // Clear old UI objects
                Iterator<UIObject> lineObjIterator = lineObjects.iterator();
                while (lineObjIterator.hasNext()) {
                    final UIObject obj = lineObjIterator.next();
                    launcher.getWindow().removeUIObject(obj);
                    lineObjIterator.remove();
                }

                final Vector3 currentPos = new Vector3(x, y, z);

                final List<Waypoint> waypoints = launcher.getRepository().findClosestWaypoints(currentPos, maxWaypointsDisplayed);
                for (int i = 0; i < waypoints.size(); i++) {
                    Waypoint waypoint = waypoints.get(i);
                    final Text textObj = new Text(
                            String.format(
                                    "%s (%s %s %s) [%s blocks]",
                                    waypoint.getLabel(),
                                    waypoint.getLocation().getX(),
                                    waypoint.getLocation().getY(),
                                    waypoint.getLocation().getZ(),
                                    currentPos.distanceTo(waypoint.getLocation())
                            ),
                            wListX * (i + 1), wListY * (i + 1),
                            fontSize
                    );
                    lineObjects.add(textObj);
                    launcher.getWindow().addUIObject(textObj);
                }

                launcher.getWindow().requestWindowUpdate();
            }
        }

    }
}
