DROP DATABASE IF EXISTS news_db;

CREATE DATABASE news_db WITH ENCODING = 'UTF8';

\connect news_db

CREATE SCHEMA news;

CREATE TABLE news.author (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL
);

CREATE TABLE news.news (
    id bigint NOT NULL,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL
);

CREATE TABLE news.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL
);

CREATE TABLE news.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL
);

CREATE TABLE news.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL
);

CREATE TABLE news.tag (
    id bigint NOT NULL,
    name character varying(30) NOT NULL
);

CREATE TABLE news."user" (
    id bigint NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL
);

ALTER TABLE ONLY news.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);

ALTER TABLE ONLY news.news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);

ALTER TABLE ONLY news.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY news."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY news.news_author
    ADD CONSTRAINT news_author_author_id_fkey FOREIGN KEY (author_id) REFERENCES news.author(id);

ALTER TABLE ONLY news.news_author
    ADD CONSTRAINT news_author_news_id_fkey FOREIGN KEY (news_id) REFERENCES news.news(id);

ALTER TABLE ONLY news.news_tag
    ADD CONSTRAINT news_tag_news_id_fkey FOREIGN KEY (news_id) REFERENCES news.news(id);

ALTER TABLE ONLY news.news_tag
    ADD CONSTRAINT news_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES news.tag(id);

ALTER TABLE ONLY news.roles
    ADD CONSTRAINT roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES news."user"(id);



