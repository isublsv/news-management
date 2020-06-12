package com.epam.lab.controller;

import com.epam.lab.service.FileGeneratorService;

import java.util.List;
import java.util.TimerTask;

public class FileGeneratorController {

    private final FileGeneratorService fileGeneratorService;

    public FileGeneratorController(final FileGeneratorService fileGeneratorServiceValue) {
        fileGeneratorService = fileGeneratorServiceValue;
    }
    
    public List<TimerTask> createTasks(final String root, final int fileCount) {
        if (!root.isEmpty()) {
            return fileGeneratorService.createTasks(root, fileCount);
        }
        throw new IllegalArgumentException("The root value cannot be empty! " + root);
    }
    
    public void runTasks(final List<TimerTask> tasks, final long testTime, final double periodTime) {
        if (!tasks.isEmpty() && testTime > 0 && periodTime > 0) {
            fileGeneratorService.runTasks(tasks, testTime, periodTime);
        }
    }
}
