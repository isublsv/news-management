package com.epam.lab.model;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public final class NewsJsonFakerProducer implements JsonProducer {

    private static final int FULL_TEXT_SENTENCE_COUNT = 5;

    private final Faker faker = new Faker();
    private final Gson gsonBuilder = new GsonBuilder().create();

    public String generateValidNewsJson() {
        News news = getRandomNews();

        Author author = getRandomAuthor();
        news.setAuthor(author);

        return gsonBuilder.toJson(news);
    }

    public String generateInvalidFormatJson() {
        return "{" + faker.lorem().characters(30) + "}";
    }

    public String generateFakeNewsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("title_value", getRandomTitle());
        json.addProperty("short_txt", getRandomShortText());
        json.addProperty("full_txt", getRandomFullText());

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
        news.setTitle(getRandomTitle());
        news.setShortText(getRandomShortText());
        news.setFullText(getRandomFullText());
        return news;
    }

    private String getRandomTitle() {
        return faker.lorem().fixedString(30);
    }

    private String getRandomShortText() {
        return faker.lorem().word();
    }

    private String getRandomFullText() {
        return faker.lorem().sentence(FULL_TEXT_SENTENCE_COUNT);
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

