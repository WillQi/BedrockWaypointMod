package io.willqi.github.bedrockwaypointmod.internal.repository;

import io.willqi.github.bedrockwaypointmod.Waypoint;
import io.willqi.github.bedrockwaypointmod.utils.Vector3;

public class WaypointComparator implements java.util.Comparator<Waypoint> {

    private final Vector3 origin;

    public WaypointComparator(final Vector3 origin) {
        this.origin = origin;
    }

    @Override
    public int compare(final Waypoint waypointA, final Waypoint waypointB) {

        final int waypointADistance = (int)Math.sqrt(
                Math.pow(waypointA.getLocation().getX() - origin.getX(), 2) +
                Math.pow(waypointA.getLocation().getY() - origin.getY(), 2) +
                Math.pow(waypointA.getLocation().getZ() - origin.getZ(), 2)
        );

        final int waypointBDistance = (int)Math.sqrt(
                Math.pow(waypointB.getLocation().getX() - origin.getX(), 2) +
                Math.pow(waypointB.getLocation().getY() - origin.getY(), 2) +
                Math.pow(waypointB.getLocation().getZ() - origin.getZ(), 2)
        );

        return waypointADistance - waypointBDistance;

    }
}
