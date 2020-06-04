package com.epam.lab.dao;

import java.nio.file.Path;

public interface FolderTreeDao extends Dao {

    void createFolderTree(Path root, int subfolderCount);

    void deleteFolderTree(Path root);
}
