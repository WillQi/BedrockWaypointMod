package io.willqi.github.bedrockwaypointmod;

import io.willqi.github.bedrockwaypointmod.utils.Vector3;

public class Waypoint {

    private String label;

    private Vector3 location;

    public Waypoint (String label, Vector3 location) {
        this.label = label;
        this.location = location;
    }

    public Vector3 getLocation () {
        return location;
    }

    public String getLabel () {
        return label;
    }

}
