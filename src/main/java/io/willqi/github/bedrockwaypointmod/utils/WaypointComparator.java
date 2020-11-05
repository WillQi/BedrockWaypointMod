package io.willqi.github.bedrockwaypointmod.utils;

import io.willqi.github.bedrockwaypointmod.Waypoint;

import java.util.Comparator;

public class WaypointComparator implements Comparator<Waypoint> {

    private final Vector3 origin;

    public WaypointComparator (final Vector3 origin) {
        this.origin = origin;
    }

    @Override
    public int compare(final Waypoint waypointA, final Waypoint waypointB) {
        return 0;
    }
}
