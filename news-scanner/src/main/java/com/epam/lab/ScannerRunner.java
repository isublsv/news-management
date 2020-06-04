package com.epam.lab;

import com.epam.lab.controller.ScannerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScannerRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerRunner.class);

    private final ScannerController controller;

    @Value("${root.folder}")
    private String rootFolder;

    @Value("${scan.delay}")
    private String scanDelay;

    @Value("${thread.count}")
    private String threadCount;

    @Autowired
    public ScannerRunner(final ScannerController controllerValue) {
        controller = controllerValue;
    }

    @Override
    public void run(final String... args) {
        LOGGER.info("Scanner started!");
        controller.scanFolder(rootFolder, Integer.parseInt(threadCount), Double.parseDouble(scanDelay));
    }
}
