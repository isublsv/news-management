package com.epam.lab.service;

import com.epam.lab.model.FileConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;

@Service
public class ScannerServiceImpl implements ScannerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerServiceImpl.class);
    private static final String USER_DIR = "user.dir";

    @Value("${error.folder}")
    private String errorFolderName;
    
    private final ExecutorService service;

    @Autowired
    public ScannerServiceImpl(final ExecutorService serviceValue) {
        service = serviceValue;
    }

    @Lookup
    protected FileConsumer getFileConsumer(final Path path) {
        return null;
    }

    @Override
    public List<Path> findFiles(final String root) {
        Path projectDir = get(System.getProperty(USER_DIR));
        Path parent = get(projectDir.toAbsolutePath().toString(), root);
        List<Path> paths = new CopyOnWriteArrayList<>();
        if (Files.exists(parent)) {
            try (final Stream<Path> pathStream = Files.find(parent, Integer.MAX_VALUE, 
                                                            (path, basicFileAttributesValue) ->
                                  !Files.isDirectory(path) && !path.toString().contains(errorFolderName))) {
                pathStream.collect(Collectors.toCollection(() -> paths));
            } catch (IOException e) {
                final String message = String.format("Error during scanning files %s", e);
                LOGGER.error(message);
            }
        }

        final String message = String.format("Files found: %d", paths.size());
        LOGGER.debug(message);
        return paths;

    }

    @Override
    public void scanFiles(final List<Path> paths) {
        paths.forEach(path -> service.execute(getFileConsumer(path)));
    }
}
