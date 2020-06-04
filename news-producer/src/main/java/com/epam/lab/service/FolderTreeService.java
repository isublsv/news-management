package com.epam.lab.service;

public interface FolderTreeService extends Service {

    void createFolderTree(String root, final int subfolderCount);

    void deleteFolderTree(String root);
}
