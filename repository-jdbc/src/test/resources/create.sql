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