package io.willqi.github.bedrockwaypointmod.ui;

import java.awt.*;

public class Rectangle implements UIObject {

    private final int xA;
    private final int yA;
    private final int xB;
    private final int yB;
    private Color color;

    public Rectangle (int xA, int yA, int xB, int yB, Color color) {
        this.xA = xA;
        this.yA = yA;
        this.xB = xB;
        this.yB = yB;
        this.color = color;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(xA, yA, xB - xA, yB - yA);
    }

}
