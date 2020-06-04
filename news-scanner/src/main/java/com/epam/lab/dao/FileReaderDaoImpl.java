package com.epam.lab.dao;

import com.epam.lab.model.News;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.readAllLines;

@Repository
public class FileReaderDaoImpl implements FileReaderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderDaoImpl.class);

    @Value("{error.folder}")
    private String errorFolderName;
    
    private final Gson gson;

    @Autowired
    public FileReaderDaoImpl(final Gson gsonValue) {
        gson = gsonValue;
    }

    @Override
    public List<News> readFile(final Path path) {
        if (exists(path) && isNotInErrorFolder(path) && isaFile(path)) {
            try {
                return readAllLines(path, StandardCharsets.UTF_8)
                        .stream()
                        .map(str -> gson.fromJson(str, News.class))
                        .collect(Collectors.toList());
            } catch (JsonSyntaxException e) {
                final String message = String.format("Error during parsing a file : %s", e);
                LOGGER.info(message);
            } catch (IOException e) {
                final String message = String.format("Error during reading a file : %s", e);
                LOGGER.info(message);
            }
        }
        return Collections.emptyList();
    }

    private boolean isaFile(final Path path) {
        return !isDirectory(path);
    }

    private boolean isNotInErrorFolder(final Path path) {
        return !path.toString().contains(errorFolderName);
    }
}
