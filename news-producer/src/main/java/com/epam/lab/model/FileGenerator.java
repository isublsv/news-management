package com.epam.lab.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.Files.createFile;
import static java.nio.file.Files.write;

public class FileGenerator extends TimerTask {

    private static final Logger LOGGER = LogManager.getLogger(FileGenerator.class);

    private static final int MIN_JSON_NUMBER = 3;
    private static final int MAX_JSON_NUMBER = 10;

    private final JsonProducer producer;
    private final Path path;
    private final int filesCount;

    public FileGenerator(final Path pathValue,
            final int filesCountValue,
            final JsonProducer newsJsonProducerValue) {
        this.producer = newsJsonProducerValue;
        this.path = pathValue;
        this.filesCount = filesCountValue;
    }

    @Override
    public void run() {
        List<Integer> indexes = getInvalidFileIndexes();
        final String startMessage = String.format("The task %s started ", Thread.currentThread().getName());
        LOGGER.debug(startMessage);
        for (int i = 0; i < filesCount; i++) {
            List<String> jsons = generateJsonList();
            if (indexes.contains(i)) {
                jsons.add(generateInvalidJson(indexes.indexOf(i)));
            }
            createJsonFile(jsons);
        }
        final String endMessage = String.format("The task %s stopped ", Thread.currentThread().getName());
        LOGGER.debug(endMessage);
    }

    private void createJsonFile(final List<String> jsons) {
        try {
            final Path pathToFile = Paths.get(
                    path.toAbsolutePath().toString(),  UUID.randomUUID().toString() + ".txt");
            createFile(pathToFile);
            write(pathToFile, jsons, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    private List<Integer> getInvalidFileIndexes() {
        List<Integer> integers = new ArrayList<>(filesCount);
        for (int i = 0; i < filesCount; i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        return integers.subList(0, 200);
    }

    private String generateInvalidJson(final int index) {
        String invalidNewsJson;
        switch (isBetween(index)) {
            case ZERO_FIFTY:
                invalidNewsJson = producer.generateInvalidNewsJson();
                break;
            case FIFTY_HUNDRED:
                invalidNewsJson = producer.generateFakeNewsJson();
                break;
            case HUNDRED_FIFTY:
                invalidNewsJson = producer.generateInvalidFormatJson();
                break;
            default:
                invalidNewsJson = producer.generateInvalidDbConstraintsNewsJson();
                break;
        }
        return invalidNewsJson;
    }
    
    private Range isBetween(final int index) {
        if (index >= 0 && index < 50) {
            return Range.ZERO_FIFTY;
        } else if (index >= 50 && index < 100) {
            return Range.FIFTY_HUNDRED;
        } else if (index >= 100 && index < 150) {
            return Range.HUNDRED_FIFTY;
        } else {
            return Range.REST;
        }
    }

    private List<String> generateJsonList() {
        int jsonNumber = ThreadLocalRandom.current().nextInt(MIN_JSON_NUMBER, MAX_JSON_NUMBER);
        return IntStream.range(0, jsonNumber)
                        .mapToObj(i -> producer.generateValidNewsJson())
                        .collect(Collectors.toList());
    }
    
    private enum Range {
        ZERO_FIFTY,
        FIFTY_HUNDRED,
        HUNDRED_FIFTY,
        REST
    }
}
