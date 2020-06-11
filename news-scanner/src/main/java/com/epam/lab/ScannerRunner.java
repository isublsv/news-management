package com.epam.lab;

import com.epam.lab.controller.ScannerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScannerRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerRunner.class);

    private final ScannerController controller;

    @Value("${root.folder}")
    private String rootFolder;

    @Value("${scan.delay}")
    private String scanDelay;

    @Autowired
    public ScannerRunner(final ScannerController controllerValue) {
        controller = controllerValue;
    }

    @Override
    public void run(final String... args) {
        LOGGER.info("Scanner started!");
        final long scanDelayMs = (long) (Double.parseDouble(scanDelay) * 1000);
        while (true) {
            List<Path> paths = controller.findFiles(rootFolder);
            controller.scanFiles(paths);
            try {
                TimeUnit.MILLISECONDS.sleep(scanDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                final String message = String.format("The scanner has been interrupted! %s", e);
                LOGGER.error(message);
            }
        }
    }
}
