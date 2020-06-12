package com.epam.lab.dao;

import com.epam.lab.exception.InvalidJsonFieldNameException;
import com.epam.lab.model.News;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.move;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Repository
public class FileReaderDaoImpl implements FileReaderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderDaoImpl.class);

    @Value("${error.folder}")
    private String errorFolderName;
    
    private final Gson gson;

    @Autowired
    public FileReaderDaoImpl(final Gson gsonValue) {
        gson = gsonValue;
    }

    @Override
    public List<News> readFile(final Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8)
                    .stream()
                    .map(str -> gson.fromJson(str, News.class))
                    .filter(news -> {
                        if (news.getTitle().isEmpty()) {
                            final String message =
                                    String.format("Error during parsing JSON from a file : %s", path.getFileName());
                            throw new InvalidJsonFieldNameException(message);
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public void moveFile(final Path path) {
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
