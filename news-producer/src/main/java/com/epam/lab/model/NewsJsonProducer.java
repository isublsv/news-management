package com.epam.lab.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.abstraction.MockUnitString;
import net.andreinc.mockneat.unit.text.Formatter;

import static net.andreinc.mockneat.types.enums.MarkovChainType.KAFKA;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.text.Words.words;
import static net.andreinc.mockneat.unit.types.Ints.ints;

public final class NewsJsonProducer implements JsonProducer {

    private static final String TITLE_FORMAT = "#{noun} #{id}";
    private static final String NOUN = "noun";
    private static final String ID = "id";

    private static final int SHORT_TEXT_LOWER_BOUND = 10;
    private static final int SHORT_TEXT_UPPER_BOUND = 100;
    private static final int FULL_TEXT_LOWER_BOUND = 100;
    private static final int FULL_TEXT_UPPER_BOUND = 1000;

    private static final MockNeat mockNeat = MockNeat.threadLocal();
    private static final Gson gsonBuilder = new GsonBuilder().create();

    public String generateValidNewsJson() {
        return mockNeat.filler(News::new)
                       //.setter(News::setId, mockNeat.longSeq().start(100).increment(2).max(10000))
                       .setter(News::setTitle, getTitleFormat())
                       .setter(News::setShortText, getTextUnit(SHORT_TEXT_LOWER_BOUND, SHORT_TEXT_UPPER_BOUND))
                       .setter(News::setFullText, getTextUnit(FULL_TEXT_LOWER_BOUND, FULL_TEXT_UPPER_BOUND))
                       .setter(News::setAuthor, getAuthorUnit())
                       .map(gsonBuilder::toJson)
                       .val();
    }

    public String generateInvalidFormatJson() {
        return mockNeat.fmt("{#{str}}")
                       .param("str", strings().size(20))
                       .get();
    }

    public String generateFakeNewsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("title_value", getTitleFormat().get());
        json.addProperty("short_txt", getTextUnit(SHORT_TEXT_LOWER_BOUND, SHORT_TEXT_UPPER_BOUND).get());
        json.addProperty("full_txt", getTextUnit(FULL_TEXT_LOWER_BOUND, FULL_TEXT_UPPER_BOUND).get());

        JsonObject author = new JsonObject();
        author.addProperty("name", mockNeat.names().first().get());
        author.addProperty("surname", mockNeat.names().last().get());
        json.add("news_author", author);

        return json.toString();
    }

    public String generateInvalidNewsJson() {
        return mockNeat.filler(News::new)
                       .setter(News::setTitle, getTitleFormat())
                       .setter(News::setShortText, getTextUnit(SHORT_TEXT_LOWER_BOUND, SHORT_TEXT_UPPER_BOUND))
                       .setter(News::setFullText, getTextUnit(FULL_TEXT_LOWER_BOUND, FULL_TEXT_UPPER_BOUND))
                       .map(gsonBuilder::toJson)
                       .val();
    }

    public String generateInvalidDbConstraintsNewsJson() {
        return mockNeat.filler(News::new)
                       .setter(News::setTitle, getTitleFormat())
                       .setter(News::setShortText, getTextUnit(SHORT_TEXT_LOWER_BOUND, SHORT_TEXT_UPPER_BOUND))
                       .setter(News::setFullText, getTextUnit(FULL_TEXT_LOWER_BOUND, FULL_TEXT_UPPER_BOUND))
                       .setter(News::setAuthor, getAuthorUnit())
                       .setter(News::addTag, mockNeat.constructor(Tag.class).params(1L, "car"))
                       .map(gsonBuilder::toJson)
                       .val();
    }

    private Formatter getTitleFormat() {
        return mockNeat.fmt(TITLE_FORMAT)
                       .param(NOUN, words().nouns())
                       .param(ID, ints().bound(1000));
    }

    private MockUnitString getTextUnit(final int lowerBound, final int upperBound) {
        return mockNeat.markovs()
                       .size(ints().range(lowerBound, upperBound).get())
                       .type(KAFKA);
    }

    private MockUnit<Author> getAuthorUnit() {
        return mockNeat.filler(Author::new)
                       .setter(Author::setName, mockNeat.names().first())
                       .setter(Author::setSurname, mockNeat.names().last());
    }
}
