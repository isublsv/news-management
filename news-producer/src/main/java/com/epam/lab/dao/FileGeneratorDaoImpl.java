package com.epam.lab.dao;

import com.epam.lab.model.FileGenerator;
import com.epam.lab.model.NewsJsonFakerProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileGeneratorDaoImpl implements FileGeneratorDao {

    private static final Logger LOGGER = LogManager.getLogger(FileGeneratorDaoImpl.class);
    
    @Override
    public List<TimerTask> createTimerTasks(final Path root, final int fileCount) {
        List<TimerTask> tasks = new ArrayList<>();
        try (final Stream<FileGenerator> stream = Files.walk(root.toAbsolutePath())
                                                       .filter(Files::isDirectory)
                                                       .map(path -> new FileGenerator(path, fileCount,
                                                                                      new NewsJsonFakerProducer()))){
            stream.collect(Collectors.toCollection(() -> tasks));
        } catch (IOException e) {
            final String message = String.format("Error during generating timer tasks! %s", e);
            LOGGER.info(message);
        }
        return tasks;
    }

    @Override
    public void runTasks(final List<TimerTask> tasks, final long testTime, final long periodTime) {
        long endTime = System.currentTimeMillis() + testTime;

        while (System.currentTimeMillis() < endTime) {
            List<Timer> timers = new ArrayList<>(tasks.size());
            for (TimerTask task : tasks) {
                Timer timer = new Timer();
                timer.schedule(task, 0, periodTime);
                timers.add(timer);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(testTime);
            } catch (InterruptedException e) {
                LOGGER.info(e);
                Thread.currentThread().interrupt();
            }
            timers.forEach(Timer::cancel);
        }
    }
}
