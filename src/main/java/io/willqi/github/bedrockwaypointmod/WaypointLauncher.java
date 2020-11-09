package io.willqi.github.bedrockwaypointmod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.willqi.github.bedrockwaypointmod.internal.WaypointConfig;
import io.willqi.github.bedrockwaypointmod.internal.WaypointRepository;
import io.willqi.github.bedrockwaypointmod.internal.WaypointWindow;
import io.willqi.github.bedrockwaypointmod.internal.threads.PrimaryModThread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        new Thread(new PrimaryModThread(this)::run).run();
        // TODO: Another thread to show at start of program execution the current config boundaries. Fades away.
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