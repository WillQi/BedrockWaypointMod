package io.willqi.github.bedrockwaypointmod.internal;

import io.willqi.github.bedrockwaypointmod.internal.component.WindowComponent;
import io.willqi.github.bedrockwaypointmod.ui.UIObject;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class WaypointWindow {

    private WindowComponent component;
    private Tesseract tesseract;

    public WaypointWindow () {
        setupWindow();
        setupTesseract();
    }

    public void requestWindowUpdate () {
        component.requestUpdate();
    }

    /**
     * Add UI object to the screen.
     * @param obj Object you want to add.
     */
    public void addUIObject (UIObject obj) {
        component.addUIObject(obj);
    }

    /**
     * Remove UI object from the screen.
     * @param obj Object you want to remove.
     */
    public void removeUIObject (UIObject obj) {
        component.removeUIObject(obj);
    }

    /**
     * Extract text from the screen
     * @param xA The left x coordinate.
     * @param yA The top y coordinate.
     * @param xB The right x coordinate.
     * @param yB The bottom y coordinate.
     * @return The text that was extracted if any.
     */
    public String readTextAt (final int xA, final int yA, final int xB, final int yB) {

        Rectangle screenImage = new Rectangle(xA, yA, xB - xA, yB - yA);
        try {
            BufferedImage image = new Robot().createScreenCapture(screenImage);
            return tesseract.doOCR(image);
        } catch (AWTException | TesseractException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    /**
     * Used to initialize the window we can draw on.
     */
    private void setupWindow () {
        final Window window = new Window(null);
        component = new WindowComponent();
        window.add(component);
        window.pack();
        window.setBounds(window.getGraphicsConfiguration().getBounds());
        window.setBackground(new Color(0, true));
        window.setAlwaysOnTop(true);
        window.setVisible(true);
    }

    /**
     * Used to initialize the tessearct API so that we can read text from the screen.
     */
    private void setupTesseract () {

        final String jarDirectoryPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        final String tessDataPath = Paths.get(Paths.get(jarDirectoryPath, "data").toString(), "tessdata").toString();
        tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", "70");
        tesseract.setDatapath(tessDataPath);

    }
}
