package com.epam.lab.service;

import java.nio.file.Path;
import java.util.List;

public interface ScannerService extends Service {

    List<Path> findFiles(String root);

    void scanFiles(List<Path> paths);
}
