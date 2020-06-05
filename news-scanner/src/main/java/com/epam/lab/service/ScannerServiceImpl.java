package com.epam.lab.service;

import com.epam.lab.dao.FileReaderDao;
import com.epam.lab.dao.NewsDao;
import com.epam.lab.model.FileConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class ScannerServiceImpl implements ScannerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerServiceImpl.class);
    private static final String USER_DIR = "user.dir";

    @Value("{error.folder}")
    private String errorFolderName;

    private final FileReaderDao fileReaderDao;
    private final NewsDao newsDao;

    @Autowired
    public ScannerServiceImpl(final FileReaderDao fileReaderDaoValue, final NewsDao newsDaoValue) {
        fileReaderDao = fileReaderDaoValue;
        newsDao = newsDaoValue;
    }

    @Override
    public List<Path> findFiles(final String root, final List<Path> paths) {
        Path projectDir = get(System.getProperty(USER_DIR));
        Path parent = get(projectDir.toAbsolutePath().toString(), root);

        if (Files.exists(parent)) {
            try (final Stream<Path> pathStream = Files.find(parent, Integer.MAX_VALUE, 
                                                            (path, basicFileAttributesValue) ->
                                  !Files.isDirectory(path) && !path.toString().contains(errorFolderName))) {
                pathStream.collect(Collectors.toCollection(() -> paths));
            } catch (IOException e) {
                final String message = String.format("Error during scanning files %s", e);
                LOGGER.error(message);
            }
        }

        final String message = String.format("Files found: %d", paths.size());
        LOGGER.debug(message);
        return paths;

    }

    @Override
    public void scanFiles(final List<Path> paths, final int threadCount) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(threadCount);
        paths.forEach(path -> {
            service.schedule(new FileConsumer(path, fileReaderDao, newsDao), 0, MILLISECONDS);
            paths.remove(path);
        });
    }
}
