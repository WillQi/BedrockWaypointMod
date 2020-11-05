package io.willqi.github.bedrockwaypointmod.utils;

public class Vector3 {

    private final int x;
    private final int y;
    private final int z;

    public Vector3 (final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public int getZ () {
        return z;
    }

}
