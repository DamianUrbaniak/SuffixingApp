package com.epam.rd.autotasks.suffixing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class SuffixFilesCopier {

    private static final Logger log = Logger.getLogger(SuffixFilesCopier.class.getName());

    private final String suffix;
    private final SuffixAdder suffixAdder = new SuffixAdder();

    public SuffixFilesCopier(String suffix) {
        this.suffix = suffix;
    }

    public void copy(String[] files) {
        for (String source : files) {
            if (!Files.exists(Path.of(source))) {
                log.severe(String.format("No such file: %s", source));
            } else {
                try {
                    String target = suffixAdder.add(source, suffix);
                    log.info(String.format("%s -> %s", source, target));
                    Files.copy(Path.of(source), Path.of(target));
                } catch (IOException e) {
                    log.severe(String.format("Error during file: %s processing, message: %s.", source, e.getMessage()));
                }
            }
        }
    }
}
