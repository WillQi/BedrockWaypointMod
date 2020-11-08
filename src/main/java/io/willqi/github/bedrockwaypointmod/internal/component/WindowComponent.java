package io.willqi.github.bedrockwaypointmod.internal.component;

import io.willqi.github.bedrockwaypointmod.ui.UIObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WindowComponent extends JPanel {

    private static Font MINECRAFT_FONT;
    private final List<UIObject> objects = Collections.synchronizedList(new ArrayList<>());

    public WindowComponent () {
        try {
            MINECRAFT_FONT = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Minecraft-Regular.ttf"));
        } catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
            MINECRAFT_FONT = Font.getFont("Arial");
        }
    }

    public void requestUpdate () {
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        // Clear background
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, getWidth(), getWidth());
        g2d.setComposite(AlphaComposite.SrcOver);

        g.setFont(MINECRAFT_FONT);
        for (UIObject object : objects) {
            object.render(g);
        }
    }

    public synchronized void addUIObject (UIObject obj) {
        objects.add(obj);
    }

    public synchronized void removeUIObject (UIObject obj) {
        objects.remove(obj);
    }


}
