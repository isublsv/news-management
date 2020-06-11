package com.epam.lab.model;

import com.epam.lab.dao.FileReaderDao;
import com.epam.lab.dao.NewsDao;
import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.InvalidJsonFieldNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.move;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConsumer.class);
    
    @Value("${error.folder}")
    private String errorFolderName;
    
    private final Path path;
    private final FileReaderDao fileReaderDao;
    private final NewsDao newsDao;

    public FileConsumer(final Path pathValue,
            final FileReaderDao fileReaderDaoValue,
            final NewsDao newsDaoValue) {
        path = pathValue;
        fileReaderDao = fileReaderDaoValue;
        newsDao = newsDaoValue;
    }

    @Override
    public void run() {
        synchronized (path) {
            if (Files.exists(path)) {
                final String start = String.format("Scanner thread started %s for file %s",
                                                   Thread.currentThread().getName(), path.toAbsolutePath());
                LOGGER.debug(start);

                try {
                    final List<News> news = fileReaderDao.readFile(path);
                    if (!news.isEmpty()) {
                        newsDao.addNews(news);
                        delete(path);
                    } else {
                        moveFileToErrorFolder(path);
                    }
                } catch (InvalidJsonFieldNameException | EntityDuplicatedException e) {
                    final String message = String.format("Error during parsing or saving a file to database %s",
                                                         e.getMessage());
                    LOGGER.error(message);
                    moveFileToErrorFolder(path);
                } catch (IOException e) {
                    final String message = String.format("Error during reading a file %s, %s",
                                                         path.getFileName(), e.getMessage());
                    LOGGER.error(message);
                }

                final String finish = String.format("Scanner thread finished %s for file %s",
                                                    Thread.currentThread().getName(), path.toAbsolutePath());
                LOGGER.debug(finish);
            }
        }
    }

    private void moveFileToErrorFolder(final Path path) {
        final Path errorFolder = get(path.getParent().toString(), errorFolderName);
        final Path newFilePath = get(errorFolder.toString(), path.getFileName().toString());

        try {
            if (!exists(errorFolder)) {
                createDirectory(errorFolder);
            }
            move(path, newFilePath, ATOMIC_MOVE, REPLACE_EXISTING);
        } catch (IOException e) {
            final String message = String.format("Error during moving a file : %s", e);
            LOGGER.error(message);
        }
    }
}
