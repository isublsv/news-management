package com.epam.lab;

import com.epam.lab.configuration.ConfigurationManager;
import com.epam.lab.configuration.ProducerConfiguration;
import com.epam.lab.controller.FileGeneratorController;
import com.epam.lab.controller.FolderTreeController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.TimerTask;

public class ProducerRunner {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ProducerConfiguration.class);

        final ConfigurationManager manager = context.getBean(ConfigurationManager.class);

        final FolderTreeController folderTreeController = context.getBean(FolderTreeController.class);
        folderTreeController.deleteFolderTree(manager.getRootFolder());
        folderTreeController.createFolderTree(manager.getRootFolder(), manager.getSubFolderCount());

        final FileGeneratorController fileGeneratorController = context.getBean(FileGeneratorController.class);
        final List<TimerTask> tasks = fileGeneratorController.createTasks(manager.getRootFolder(),
                                                                          manager.getFilesCount());
        fileGeneratorController.runTasks(tasks, manager.getTestTime(), manager.getPeriodTime());
    }
}
