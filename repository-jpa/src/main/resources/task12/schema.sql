DROP DATABASE IF EXISTS news_db;

CREATE DATABASE news_db WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

\connect news_db

CREATE SCHEMA news AUTHORIZATION postgres;

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

CREATE TABLE news.comment (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    full_text character varying(500) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE news."user" (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(120) NOT NULL,
    CONSTRAINT uniq_login_name UNIQUE (login),
	PRIMARY KEY (id)
);

CREATE TABLE news.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT uniq_news_id UNIQUE (news_id),
	CONSTRAINT fk_news_id FOREIGN KEY (news_id)
		REFERENCES news.news (id) MATCH SIMPLE
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	CONSTRAINT fk_author_id FOREIGN KEY (author_id)
        REFERENCES news.author (id) MATCH SIMPLE
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);

CREATE TABLE news.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT uniq_news_id_tag_id UNIQUE (news_id, tag_id),
	CONSTRAINT fk_news_id FOREIGN KEY (news_id)
		REFERENCES news.news (id) MATCH SIMPLE
        ON UPDATE CASCADE 
        ON DELETE CASCADE ,
	CONSTRAINT fk_tag_id FOREIGN KEY (tag_id)
		REFERENCES news.tag (id) MATCH SIMPLE
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);

CREATE TABLE news.news_comment (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL,
	comment_id bigint NOT NULL,
	CONSTRAINT uniq_comment_id UNIQUE (comment_id),
	CONSTRAINT fk_news_id FOREIGN KEY (news_id)
		REFERENCES news.news (id) MATCH SIMPLE
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	CONSTRAINT fk_author_id FOREIGN KEY (author_id)
        REFERENCES news.author (id) MATCH SIMPLE
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
	CONSTRAINT fk_comment_id FOREIGN KEY (comment_id)
        REFERENCES news.comment (id) MATCH SIMPLE
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);

CREATE TABLE news.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL,
	CONSTRAINT fk_user_id FOREIGN KEY (user_id)
		REFERENCES news."user" (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION
);

CREATE INDEX idx_author_name_surname ON news.author (name) INCLUDE (surname);

CREATE INDEX idx_news_title ON news.news (title);


