package io.willqi.github.bedrockwaypointmod.internal;

import io.willqi.github.bedrockwaypointmod.Waypoint;
import io.willqi.github.bedrockwaypointmod.utils.Vector3;
import io.willqi.github.bedrockwaypointmod.utils.WaypointComparator;

import java.util.ArrayList;
import java.util.List;

public class WaypointRepository {

    private final List<Waypoint> waypoints = new ArrayList<>();

    public void addWaypoint (final Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public List<Waypoint> findClosestWaypoints (final Vector3 origin, final int amount) {
        waypoints.sort(new WaypointComparator(origin));
        return waypoints.subList(0, Math.min(amount, waypoints.size()));
    }


}
