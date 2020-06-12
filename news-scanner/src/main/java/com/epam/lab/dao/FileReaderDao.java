package com.epam.lab.dao;

import com.epam.lab.model.News;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileReaderDao extends Dao {

    List<News> readFile(Path path) throws IOException;
    
    void moveFile(Path path);
}
