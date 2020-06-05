package com.epam.lab.service;

import java.nio.file.Path;
import java.util.List;

public interface ScannerService extends Service {

    List<Path> findFiles(String root, final List<Path> paths);

    void scanFiles(List<Path> paths, int threadCount);
}
