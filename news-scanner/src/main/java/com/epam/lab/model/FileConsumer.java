package com.epam.lab.model;

import com.epam.lab.dao.FileReaderDao;
import com.epam.lab.dao.NewsDao;
import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.InvalidJsonFieldNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.delete;

public class FileConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConsumer.class);
    
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
            final String start = String.format("Scanner thread started %s for file %s",
                                               Thread.currentThread().getName(), path.toAbsolutePath());
            LOGGER.debug(start);

            try {
                final List<News> news = fileReaderDao.readFile(path);
                if (!news.isEmpty()) {
                    newsDao.addNews(news);
                    delete(path);
                } else {
                    fileReaderDao.moveFile(path);
                }
            } catch (InvalidJsonFieldNameException | EntityDuplicatedException e) {
                final String message = String.format("Error during parsing or saving a file to database %s",
                                                     e.getMessage());
                LOGGER.error(message);
                fileReaderDao.moveFile(path);
            } catch (IOException e) {
                final String message = String.format("Error during reading a file %s", e.getMessage());
                LOGGER.error(message);
            }

            final String finish = String.format("Scanner thread finished %s for file %s",
                                                Thread.currentThread().getName(), path.toAbsolutePath());
            LOGGER.debug(finish);
        }
    }
}
