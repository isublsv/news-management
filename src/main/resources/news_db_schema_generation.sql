DROP ROLE news_user;

CREATE ROLE news_user WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'root';

DROP DATABASE news_db;

CREATE DATABASE news_db WITH ENCODING = 'UTF8';

ALTER DATABASE news_db OWNER TO news_user;

\connect news_db

CREATE SCHEMA public;

ALTER SCHEMA public OWNER TO news_user;

CREATE TABLE public.author (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL
);

ALTER TABLE public.author OWNER TO news_user;

CREATE TABLE public.news (
    id bigint NOT NULL,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL
);

ALTER TABLE public.news OWNER TO news_user;

CREATE TABLE public.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL
);

ALTER TABLE public.news_author OWNER TO news_user;

CREATE TABLE public.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL
);

ALTER TABLE public.news_tag OWNER TO news_user;

CREATE TABLE public.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL
);

ALTER TABLE public.roles OWNER TO news_user;

CREATE TABLE public.tag (
    id bigint NOT NULL,
    name character varying(30) NOT NULL
);

ALTER TABLE public.tag OWNER TO news_user;


CREATE TABLE public."user" (
    id bigint NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL
);

ALTER TABLE public."user" OWNER TO news_user;

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.author(id);

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id);

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id);

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);

REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO news_user;
GRANT ALL ON SCHEMA public TO PUBLIC;


