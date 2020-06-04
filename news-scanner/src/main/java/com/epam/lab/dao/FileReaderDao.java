package com.epam.lab.dao;

import com.epam.lab.model.News;

import java.nio.file.Path;
import java.util.List;

public interface FileReaderDao extends Dao {

    List<News> readFile(Path path);
}
