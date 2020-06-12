package com.epam.lab.controller;

import com.epam.lab.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Controller
public class ScannerController {

    private final ScannerService scannerService;

    @Autowired
    public ScannerController(final ScannerService scannerServiceValue) {
        scannerService = scannerServiceValue;
    }

    public List<Path> findFiles(final String root) {
        if (!root.isEmpty()) {
            return scannerService.findFiles(root);
        }
        return Collections.emptyList();
    }

    public void scanFiles(final List<Path> paths) {
        if (!paths.isEmpty()) {
            scannerService.scanFiles(paths);
        }
    }
}
