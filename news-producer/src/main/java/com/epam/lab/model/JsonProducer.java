package com.epam.lab.model;

public interface JsonProducer {

    String generateValidNewsJson();
    String generateInvalidFormatJson();
    String generateFakeNewsJson();
    String generateInvalidNewsJson();
    String generateInvalidDbConstraintsNewsJson();
}
