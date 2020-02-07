CREATE TABLE news.author (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE news.news (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE news.tag (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    CONSTRAINT uniq_tag_name UNIQUE (name),
	PRIMARY KEY (id)
);

CREATE TABLE news."user" (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE news.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT uniq_news_id UNIQUE (news_id),
	FOREIGN KEY (news_id)
		REFERENCES news.news (id)
		ON UPDATE CASCADE
        ON DELETE CASCADE,
	FOREIGN KEY (author_id)
        REFERENCES news.author (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE news.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT uniq_news_id_tag_id UNIQUE (news_id, tag_id),
	FOREIGN KEY (news_id)
		REFERENCES news.news(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	FOREIGN KEY (tag_id)
		REFERENCES news.tag(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE news.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL,
	FOREIGN KEY (user_id)
		REFERENCES news."user"(id)
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
);

CREATE VIEW news.full_view AS SELECT search_by.id AS news_id,
    search_by.title,
    search_by.short_text,
    search_by.full_text,
    search_by.creation_date AS date,
    search_by.tag_ids,
    search_by.tag_names,
    author.id AS author_id,
    author.name AS author_name,
    author.surname AS author_surname
   FROM ( SELECT news.id,
            news.title,
            news.short_text,
            news.full_text,
            news.creation_date,
            news.modification_date,
            array_agg(tag.id) AS tag_ids,
            array_agg(tag.name) AS tag_names
           FROM news.news
             LEFT JOIN news.news_tag ON news.id = news_tag.news_id
             LEFT JOIN news.tag ON tag.id = news_tag.tag_id
          GROUP BY news.id) search_by
     LEFT JOIN news.news_author ON search_by.id = news_author.news_id
     LEFT JOIN news.author ON news_author.author_id = author.id;
