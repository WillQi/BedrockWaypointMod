package io.willqi.github.bedrockwaypointmod.ui;

import java.awt.*;

public class Text implements UIObject {

    private final int x;
    private final int y;
    private final float fontSize;
    private final String message;
    private final Color color;

    public Text (final String message, final int x, final int y, final float fontSize) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.message = message;
        this.color = Color.WHITE;
    }

    public Text(final String message, final Color color, final int x, final int y, final float fontSize) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.message = message;
        this.color = color;
    }

    @Override
    public void render(final Graphics graphics) {

        graphics.setFont(graphics.getFont().deriveFont(fontSize));
        graphics.setColor(color);
        graphics.drawString(message, x, y);

    }
}
