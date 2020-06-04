package com.epam.lab.configuration;

import org.springframework.beans.factory.annotation.Value;

public class ConfigurationManager {

    @Value("${root.folder}")
    private String rootFolder;

    @Value("${subfolders.count}")
    private String subFolderCount;

    @Value("${test.time}")
    private String testTime;

    @Value("${period.time}")
    private String periodTime;

    @Value("${files.count}")
    private String filesCount;

    public String getRootFolder() {
        return rootFolder;
    }

    public int getSubFolderCount() {
        return Integer.parseInt(subFolderCount);
    }

    public long getTestTime() {
        return Long.parseLong(testTime);
    }

    public double getPeriodTime() {
        return Double.parseDouble(periodTime);
    }

    public int getFilesCount() {
        return Integer.parseInt(filesCount);
    }

}
