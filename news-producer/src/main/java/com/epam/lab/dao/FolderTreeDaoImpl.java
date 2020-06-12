package com.epam.lab.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;

public class FolderTreeDaoImpl implements FolderTreeDao {

    private static final Logger LOGGER = LogManager.getLogger(FolderTreeDaoImpl.class);

    @Override
    public void createFolderTree(final Path root, final int subfolderCount) {
        try {
            createDirectory(root);
            for (int i = 1; i <= subfolderCount; i++) {
                int randomDepth = ThreadLocalRandom.current().nextInt(0, subfolderCount / 3 + 1);
                Path subDir = get(root.toAbsolutePath().toString(), String.valueOf(i));
                createDirectory(subDir);
                createSubfolder(subDir, randomDepth);
            }
        } catch (IOException e) {
            final String message = String.format("Error during creating main subfolders: %s", e);
            LOGGER.info(message);
        }
    }

    @Override
    public void deleteFolderTree(final Path root) {
        try (final Stream<File> stream = walk(root)
                                              .sorted(Comparator.reverseOrder())
                                              .map(Path::toFile)) {
            stream.forEach(File::delete);
        } catch (IOException e) {
            final String message = String.format("Error during deleting folder tree: %s", e);
            LOGGER.info(message);
        }

    }

    private void createSubfolder(final Path parent, final int depth) {
        if (depth == 0) {
            return;
        }
        for (int i = 1; i <= depth; i++) {
            Path subDir = get(parent.toString(), String.valueOf(i));
            try {
                createDirectory(subDir);
                createSubfolder(subDir, depth - 1);
            } catch (IOException e) {
                final String message = String.format("Error during creating subfolder: %s", e);
                LOGGER.info(message);
            }
        }
    }
}
