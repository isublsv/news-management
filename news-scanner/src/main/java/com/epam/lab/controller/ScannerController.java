package com.epam.lab.controller;

import com.epam.lab.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ScannerController {

    private final ScannerService scannerService;

    @Autowired
    public ScannerController(final ScannerService scannerServiceValue) {
        scannerService = scannerServiceValue;
    }

    public void scanFolder(final String root, final int threadCount, final double scanDelay) {
        if (!root.isEmpty() && threadCount > 0 && scanDelay > 0) {
            scannerService.scanFolder(root, threadCount, scanDelay);
        }
    }
}
