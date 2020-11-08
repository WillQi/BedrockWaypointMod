package io.willqi.github.bedrockwaypointmod;

import io.willqi.github.bedrockwaypointmod.internal.WaypointRepository;
import io.willqi.github.bedrockwaypointmod.internal.WaypointWindow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WaypointLauncher {

    private final WaypointWindow window = new WaypointWindow();
    private final WaypointRepository repository = new WaypointRepository();

    public WaypointLauncher () throws IOException {
        setupDataFolder();
    }

    public static void main (final String[] args) throws IOException {

        final WaypointLauncher launcher = new WaypointLauncher();

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

        // WaypointWindowC files
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

}