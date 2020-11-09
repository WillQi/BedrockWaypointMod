package io.willqi.github.bedrockwaypointmod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.willqi.github.bedrockwaypointmod.internal.WaypointConfig;
import io.willqi.github.bedrockwaypointmod.internal.WaypointRepository;
import io.willqi.github.bedrockwaypointmod.internal.WaypointWindow;
import io.willqi.github.bedrockwaypointmod.ui.Text;
import io.willqi.github.bedrockwaypointmod.ui.UIObject;
import io.willqi.github.bedrockwaypointmod.utils.Vector3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WaypointLauncher {

    private final WaypointWindow window = new WaypointWindow();
    private final WaypointRepository repository = new WaypointRepository();
    private WaypointConfig config;

    public WaypointLauncher () throws IOException {
        setupDataFolder();
        setupConfigAndWaypoints();
    }

    public WaypointRepository getRepository () {
        return repository;
    }

    public WaypointWindow getWindow () {
        return window;
    }

    public WaypointConfig getConfig () {
        return config;
    }

    public void start () {

        // extract from config

        final int cBoxLx = getConfig().getCoordinatesForBox().getTopLeft().getX();
        final int cBoxLy = getConfig().getCoordinatesForBox().getTopLeft().getY();
        final int cBoxRx = getConfig().getCoordinatesForBox().getBottomRight().getX();
        final int cBoxRy = getConfig().getCoordinatesForBox().getBottomRight().getY();

        final int wListX = getConfig().getCoordinatesForWaypointsDisplay().getX();
        final int wListY = getConfig().getCoordinatesForWaypointsDisplay().getY();

        final int maxWaypointsDisplayed = getConfig().getMaxWaypoints();
        final float fontSize = getConfig().getFontSize();

        new Thread(() -> {

            final List<UIObject> lineObjects = new ArrayList<>();

            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                    break;
                }

                final String[] text = getWindow().readTextAt(cBoxLx, cBoxLy, cBoxRx, cBoxRy).split(" ");

                if (text.length >= 3) {
                    int x, y, z;
                    try {
                        x = Integer.valueOf(text[0]);
                        y = Integer.valueOf(text[1]);
                        z = Integer.valueOf(text[2]);
                    } catch (NumberFormatException exception) {
                        // Failed to parse coordinates. Re-parse it.
                        continue;
                    }

                    // Clear old UI objects
                    Iterator<UIObject> lineObjIterator = lineObjects.iterator();
                    while (lineObjIterator.hasNext()) {
                        final UIObject obj = lineObjIterator.next();
                        getWindow().removeUIObject(obj);
                        lineObjIterator.remove();
                    }

                    final Vector3 currentPos = new Vector3(x, y, z);

                    final List<Waypoint> waypoints = getRepository().findClosestWaypoints(currentPos, maxWaypointsDisplayed);
                    for (int i = 0; i < waypoints.size(); i++) {
                        Waypoint waypoint = waypoints.get(i);
                        final Text textObj = new Text(
                                String.format(
                                        "%s (%s %s %s) [%s blocks]",
                                        waypoint.getLabel(),
                                        waypoint.getLocation().getX(),
                                        waypoint.getLocation().getY(),
                                        waypoint.getLocation().getZ(),
                                        currentPos.distanceTo(waypoint.getLocation())
                                ),
                                wListX * (i + 1), wListY * (i + 1),
                                fontSize
                        );
                        lineObjects.add(textObj);
                        getWindow().addUIObject(textObj);
                    }

                    getWindow().requestWindowUpdate();
                }
            }
        }).run();

        // TODO: Another thread to show at start of program execution the current config boundaries. Fades away.
        // TODO: change elements array to be thread safe.

    }

    private void setupConfigAndWaypoints () throws IOException {

        final String jarDirectoryPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        // Default waypoints txt file.
        final File waypointsFile = new File(jarDirectoryPath, "waypoints.txt");
        if (!waypointsFile.exists()) {
            final InputStream waypointsStream = getClass().getResourceAsStream("/waypoints.txt");
            Files.copy(waypointsStream, Paths.get(waypointsFile.getAbsolutePath()));
        }

        // Default config file.
        final File configFile = new File(jarDirectoryPath, "config.yml");
        if (!configFile.exists()) {
            final InputStream configStream = getClass().getResourceAsStream("/config.yml");
            Files.copy(configStream, Paths.get(waypointsFile.getAbsolutePath()));
        }
        ObjectMapper configMapper = new ObjectMapper(new YAMLFactory());
        config = configMapper.readValue(configFile, WaypointConfig.class);

    }

    /**
     * Extract resources
     */
    private void setupDataFolder () throws IOException {

        final String jarDirectoryPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        final File dataFolder = new File(jarDirectoryPath, "data");
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                throw new IOException("Could not mkdir to create data folder");
            }
        }

        // WaypointWindow files
        final File tessDataFolder = new File(dataFolder.getAbsolutePath(), "tessdata");
        if (!tessDataFolder.exists()) {
            if (!tessDataFolder.mkdir()) {
                throw new IOException("Could not mkdir to create tessdata folder.");
            }
        }
        final File tesseractDataFile = new File(tessDataFolder.getAbsolutePath(), "eng.traineddata");
        if (!tesseractDataFile.exists()) {
            final InputStream trainedDataStream = getClass().getResourceAsStream("/tessdata/eng.traineddata");
            Files.copy(trainedDataStream, Paths.get(tesseractDataFile.getAbsolutePath()));
        }


    }

    public static void main (final String[] args) throws IOException {

        final WaypointLauncher launcher = new WaypointLauncher();
        launcher.start();

    }

}