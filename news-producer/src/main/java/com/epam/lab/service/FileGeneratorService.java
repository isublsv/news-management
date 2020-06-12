package com.epam.lab.service;

import java.util.List;
import java.util.TimerTask;

public interface FileGeneratorService extends Service {

    List<TimerTask> createTasks(String root, final int fileCount);

    void runTasks(List<TimerTask> tasks, final long testTime, final double periodTime);
}
