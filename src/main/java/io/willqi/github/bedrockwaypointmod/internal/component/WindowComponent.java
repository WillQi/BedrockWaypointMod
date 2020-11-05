package io.willqi.github.bedrockwaypointmod.internal.component;

import io.willqi.github.bedrockwaypointmod.ui.UIObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindowComponent extends JPanel implements ActionListener {

    private static Font MINECRAFT_FONT;
    private final List<UIObject> objects = new ArrayList<>();

    public WindowComponent () {
        final Timer timer = new Timer(100, this);
        try {
            MINECRAFT_FONT = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Minecraft-Regular.ttf"));
        } catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
            MINECRAFT_FONT = Font.getFont("Arial");
        }
        timer.start();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
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

    public void addUIObject (UIObject obj) {
        objects.add(obj);
    }

    public void removeUIObject (UIObject obj) {
        objects.remove(obj);
    }


}
