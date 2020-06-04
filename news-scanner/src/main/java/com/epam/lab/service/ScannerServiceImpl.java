package com.epam.lab.service;

import com.epam.lab.dao.FileReaderDao;
import com.epam.lab.dao.NewsDao;
import com.epam.lab.model.FileConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class ScannerServiceImpl implements ScannerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerServiceImpl.class);

    private final FileReaderDao fileReaderDao;
    private final NewsDao newsDao;

    @Autowired
    public ScannerServiceImpl(final FileReaderDao fileReaderDaoValue, final NewsDao newsDaoValue) {
        fileReaderDao = fileReaderDaoValue;
        newsDao = newsDaoValue;
    }

    @Override
    public void scanFolder(final String root, final int threadCount, final double scanDelay) {
        Path projectDir = get(System.getProperty("user.dir"));
        Path parent = get(projectDir.toAbsolutePath().toString(), root);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(threadCount);
        long scanDelayMs = (long) (scanDelay * 1000);

        try (final Stream<Path> stream = Files.walk(parent)) {
            final Stream<? extends ScheduledFuture<?>> scheduledFutureStream = stream
                    .map(path -> service.scheduleAtFixedRate(
                            new FileConsumer(path, fileReaderDao, newsDao), 0, scanDelayMs, MILLISECONDS));
            scheduledFutureStream.forEach(value -> value.cancel(true));
        } catch (IOException e) {
            final String message = String.format("Error during scanning files %s", e);
            LOGGER.info(message);
        }
        service.shutdown();
    }
}
