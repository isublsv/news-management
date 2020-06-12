package com.epam.lab.controller;

import com.epam.lab.service.FolderTreeService;

public class FolderTreeController {
    
    private final FolderTreeService folderTreeService;

    public FolderTreeController(final FolderTreeService folderTreeServiceValue) {
        folderTreeService = folderTreeServiceValue;
    }
    
    public void createFolderTree(final String root, final int subfolderCount) {
        if (!root.isEmpty() && subfolderCount > 0) {
            folderTreeService.createFolderTree(root, subfolderCount);
        }
    }
    
    public void deleteFolderTree(final String root) {
        if (!root.isEmpty()) {
            folderTreeService.deleteFolderTree(root);
        }
    }
}
