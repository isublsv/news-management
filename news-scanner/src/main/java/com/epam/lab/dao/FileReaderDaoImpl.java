package com.epam.lab.dao;

import com.epam.lab.exception.InvalidJsonFieldNameException;
import com.epam.lab.model.News;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileReaderDaoImpl implements FileReaderDao {

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
}
