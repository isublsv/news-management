package com.epam.lab.service;

import com.epam.lab.dao.FolderTreeDao;

import java.io.File;
import java.nio.file.Path;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;

public class FolderTreeServiceImpl implements FolderTreeService {

    private static final String USER_DIR = "user.dir";
    private final FolderTreeDao folderTreeDao;

    public FolderTreeServiceImpl(final FolderTreeDao folderTreeDaoValue) {
        folderTreeDao = folderTreeDaoValue;
    }

    @Override
    public void createFolderTree(final String root, final int subfolderCount) {
        Path projectDir = get(System.getProperty(USER_DIR));
        Path parent = get(projectDir.toAbsolutePath().toString(), File.separator, root);
        folderTreeDao.createFolderTree(parent, subfolderCount);
    }

    @Override
    public void deleteFolderTree(final String root) {
        final Path path = get(root);
        if (exists(path)) {
            folderTreeDao.deleteFolderTree(path);
        }
    }
}
