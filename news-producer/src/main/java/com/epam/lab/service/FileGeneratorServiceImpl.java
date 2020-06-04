package com.epam.lab.service;

import com.epam.lab.dao.FileGeneratorDao;

import java.nio.file.Path;
import java.util.List;
import java.util.TimerTask;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;

public class FileGeneratorServiceImpl implements FileGeneratorService {

    private final FileGeneratorDao fileGeneratorDao;

    public FileGeneratorServiceImpl(final FileGeneratorDao fileGeneratorDaoValue) {
        fileGeneratorDao = fileGeneratorDaoValue;
    }

    @Override
    public List<TimerTask> createTasks(final String root, final int fileCount) {
        final Path path = get(root);
        if (exists(path)) {
            return fileGeneratorDao.createTimerTasks(path, fileCount);
        }
        throw new IllegalArgumentException("Provided path not exist! " + root);
    }

    @Override
    public void runTasks(final List<TimerTask> tasks, final long testTime, final double periodTime) {
        long testTimeMs = testTime * 1000;
        long periodTimeMs = (long) (periodTime * 1000);
        
        fileGeneratorDao.runTasks(tasks, testTimeMs, periodTimeMs);
    }
}
