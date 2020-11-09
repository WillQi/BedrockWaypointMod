package io.willqi.github.bedrockwaypointmod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.willqi.github.bedrockwaypointmod.internal.WaypointConfig;
import io.willqi.github.bedrockwaypointmod.internal.WaypointRepository;
import io.willqi.github.bedrockwaypointmod.internal.WaypointWindow;
import io.willqi.github.bedrockwaypointmod.internal.threads.BoxGUIThread;
import io.willqi.github.bedrockwaypointmod.internal.threads.PrimaryModThread;
import io.willqi.github.bedrockwaypointmod.utils.Vector3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WaypointLauncher {

    private final WaypointWindow window = new WaypointWindow();
    private final WaypointRepository repository = new WaypointRepository();
    private WaypointConfig config;

    public WaypointLauncher () throws IOException {
        setupDataFolder();
        setupConfigAndWaypointsFiles();
        extractWaypointsFromWaypointFile();
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
        new Thread(new PrimaryModThread(this)).start();
        new Thread(new BoxGUIThread(this)).start();
    }

    private void extractWaypointsFromWaypointFile () {

        final String jarDirectoryPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        final File waypointsFile = new File(jarDirectoryPath, "waypoints.txt");
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(waypointsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    // Parse the waypoint.

                    String[] parts = line.replaceAll(",", "").split(" ");
                    if (parts.length < 4) {
                        System.out.println("Invalid waypoint: " + line);
                        continue;
                    }

                    int x, y, z;
                    try {
                        x = Integer.parseInt(parts[0]);
                        y = Integer.parseInt(parts[1]);
                        z = Integer.parseInt(parts[2]);
                    } catch (NumberFormatException exception) {
                        // Invalid coordinates.
                        System.out.println("Invalid waypoint: " + line);
                        continue;
                    }

                    final StringBuilder nameBuilder = new StringBuilder(parts[3]);
                    for (int i = 4; i < parts.length; i++) {
                        nameBuilder.append(" ").append(parts[i]);
                    }
                    final String name = nameBuilder.toString();
                    getRepository().addWaypoint(new Waypoint(name, new Vector3(x, y, z)));
                    System.out.println(String.format("Succesfully added waypoint %s (%s, %s, %s)", name, x, y, z));
                }
            }
        } catch (FileNotFoundException exception) {
            // No waypoints file. Technically should never happen, but eh.
            System.out.println("waypoints.txt could not be found.");
        } catch (IOException exception) {
            // Failed to read waypoints.
            System.out.println("Failed to read waypoints.txt");
        }

    }

    private void setupConfigAndWaypointsFiles() throws IOException {

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