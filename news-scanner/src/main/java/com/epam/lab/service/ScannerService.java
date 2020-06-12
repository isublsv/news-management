package com.epam.lab.service;

import java.nio.file.Path;
import java.util.Set;

public interface ScannerService extends Service {

    Set<Path> findFiles(String root, final Set<Path> paths);

    void scanFiles(Set<Path> paths);
}
