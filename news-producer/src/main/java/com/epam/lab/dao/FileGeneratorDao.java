package com.epam.lab.dao;

import java.nio.file.Path;
import java.util.List;
import java.util.TimerTask;

public interface FileGeneratorDao extends Dao {

    List<TimerTask> createTimerTasks(Path root, int fileCount);

    void runTasks(List<TimerTask> tasks, long testTime, long periodTime);
}
