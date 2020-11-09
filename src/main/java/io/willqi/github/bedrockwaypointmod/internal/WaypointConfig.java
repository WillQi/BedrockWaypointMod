package io.willqi.github.bedrockwaypointmod.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WaypointConfig {

    public static class WaypointCoordinateObject {
        @JsonProperty
        private Vector2 topLeft;

        @JsonProperty
        private Vector2 bottomRight;

        public Vector2 getTopLeft () {
            return topLeft;
        }

        public Vector2 getBottomRight () {
            return bottomRight;
        }

    }

    public static class Vector2 {
        @JsonProperty
        private int x;

        @JsonProperty
        private int y;

        public int getX () {
            return x;
        }

        public int getY () {
            return y;
        }
    }

    @JsonProperty
    private WaypointCoordinateObject coordinateBox;
    
    @JsonProperty
    private Vector2 waypointBox;

    @JsonProperty
    private int maxWaypoints;

    @JsonProperty
    private float fontSize;

    public WaypointCoordinateObject getCoordinatesForBox () {
        return coordinateBox;
    }

    public Vector2 getCoordinatesForWaypointsDisplay () {
        return waypointBox;
    }

    public int getMaxWaypoints () {
        return maxWaypoints;
    }

    public float getFontSize () {
        return fontSize;
    }
}
