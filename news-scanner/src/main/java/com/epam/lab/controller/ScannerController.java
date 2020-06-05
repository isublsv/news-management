package com.epam.lab.controller;

import com.epam.lab.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.List;

@Controller
public class ScannerController {

    private final ScannerService scannerService;

    @Autowired
    public ScannerController(final ScannerService scannerServiceValue) {
        scannerService = scannerServiceValue;
    }

    public List<Path> findFiles(final String root, final List<Path> paths) {
        if (!root.isEmpty()) {
            return scannerService.findFiles(root, paths);
        }
        return paths;
    }

    public void scanFiles(final List<Path> paths, final int threadCount) {
        if (!paths.isEmpty() && threadCount > 0) {
            scannerService.scanFiles(paths, threadCount);
        }
    }
}
