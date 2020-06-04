package com.epam.lab.service;

public interface ScannerService extends Service {

    void scanFolder(String root, final int threadCount, final double scanDelay);
}
