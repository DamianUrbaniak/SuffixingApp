package com.epam.rd.autotasks.suffixing;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Logger;

public class SuffixingApp {

    private static final Logger log = Logger.getLogger(SuffixingApp.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            log.severe("Too few arguments provided.");
            return;
        }

        try (Reader reader = Files.newBufferedReader(Path.of(args[0]))) {
            Properties properties = new Properties();
            properties.load(reader);
            String mode = properties.getProperty("mode");
            if (!isValidMode(mode)) {
                log.severe(String.format("Mode is not recognized: %s", mode));
                return;
            }

            String suffix = properties.getProperty("suffix");

            if (suffix == null || suffix.isEmpty()) {
                log.severe("No suffix is configured");
                return;
            }

            String filesProp = properties.getProperty("files");
            if (filesProp == null || filesProp.isEmpty()) {
                log.warning("No files are configured to be copied/moved");
                return;
            }

            String[] files = filesProp.split(":");
            if ("copy".equalsIgnoreCase(mode)) {
                new SuffixFilesCopier(suffix).copy(files);
                return;
            }
            if ("move".equalsIgnoreCase(mode)) {
                new SuffixFilesMover(suffix).move(files);
            }
        } catch (IOException e) {
            log.severe(String.format("Error during properties loading, message: %s.", e.getMessage()));
        }
    }

    private static boolean isValidMode(String mode) {
        return "copy".equalsIgnoreCase(mode) || "move".equalsIgnoreCase(mode);
    }
}
