package com.epam.lab.controller;

import com.epam.lab.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.Set;

@Controller
public class ScannerController {

    private final ScannerService scannerService;

    @Autowired
    public ScannerController(final ScannerService scannerServiceValue) {
        scannerService = scannerServiceValue;
    }

    public void findFiles(final String root, final Set<Path> paths) {
        if (!root.isEmpty()) {
            scannerService.findFiles(root, paths);
        }
    }

    public void scanFiles(final Set<Path> paths) {
        if (!paths.isEmpty()) {
            scannerService.scanFiles(paths);
        }
    }
}
