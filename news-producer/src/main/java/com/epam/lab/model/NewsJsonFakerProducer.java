package com.epam.lab.model;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public final class NewsJsonFakerProducer implements JsonProducer {

    private static final int TITLE_NUMBER_OF_LETTERS = 30;
    private static final int SHORT_TEXT_NUMBER_OF_LETTERS = 80;
    private static final int FULL_TEXT_NUMBER_OF_LETTER = 500;

    private final Faker faker = new Faker();
    private final Gson gsonBuilder = new GsonBuilder().create();

    public String generateValidNewsJson() {
        News news = getRandomNews();

        Author author = getRandomAuthor();
        news.setAuthor(author);

        return gsonBuilder.toJson(news);
    }

    public String generateInvalidFormatJson() {
        return "{" + getRandomText(TITLE_NUMBER_OF_LETTERS) + "}";
    }

    public String generateFakeNewsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("title_value", getRandomText(TITLE_NUMBER_OF_LETTERS));
        json.addProperty("short_txt", getRandomText(SHORT_TEXT_NUMBER_OF_LETTERS));
        json.addProperty("full_txt", getRandomText(FULL_TEXT_NUMBER_OF_LETTER));

        JsonObject author = new JsonObject();
        author.addProperty("name", getRandomName());
        author.addProperty("surname", getRandomSurname());
        json.add("news_author", author);

        return json.toString();
    }

    public String generateInvalidNewsJson() {
        News news = getRandomNews();
        return gsonBuilder.toJson(news);
    }

    public String generateInvalidDbConstraintsNewsJson() {
        News news = getRandomNews();

        Author author = getRandomAuthor();
        news.setAuthor(author);

        news.addTag(new Tag(1L, "car"));
        return gsonBuilder.toJson(news);
    }

    private News getRandomNews() {
        News news = new News();
        news.setTitle(getRandomText(TITLE_NUMBER_OF_LETTERS));
        news.setShortText(getRandomText(SHORT_TEXT_NUMBER_OF_LETTERS));
        news.setFullText(getRandomText(FULL_TEXT_NUMBER_OF_LETTER));
        return news;
    }

    private String getRandomText(final int numberOfLetters) {
        return faker.lorem().fixedString(numberOfLetters);
    }

    private Author getRandomAuthor() {
        Author author = new Author();
        author.setName(getRandomName());
        author.setSurname(getRandomSurname());
        return author;
    }

    private String getRandomName() {
        return faker.name().firstName();
    }

    private String getRandomSurname() {
        return faker.name().lastName();
    }
}

