-----------------#1-----------------
SELECT CONCAT(t.author_name, ' ', t.author_surname),
		COUNT(t.total_news) AS total_news_count,
		AVG(t.total_comments) AS avg_comment_count_per_news,
		MAX(t.count3) AS most_popular_tag
FROM (SELECT news.author.name AS author_name,
			news.author.surname AS author_surname,
			news.news.id AS total_news,
			COUNT(DISTINCT news.comment.id) AS total_comments,
			COUNT(DISTINCT news.tag.id) AS count3
	 FROM news.news 
	 LEFT JOIN news.news_author ON news.news.id=news.news_author.news_id 
	 LEFT JOIN news.author ON news.news_author.author_id=news.author.id 
	 LEFT JOIN news.news_comment ON news.news.id=news.news_comment.news_id 
	 LEFT JOIN news.comment ON news.news_comment.comment_id=news.comment.id 
	 LEFT JOIN news.news_tag ON news.news_tag.news_id=news.news.id 
	 LEFT JOIN news.tag ON news.tag.id=news.news_tag.tag_id
	 GROUP BY news.author.name, news.author.surname, news.news.id) AS t
GROUP BY t.author_name, t.author_surname;

-----------------#2-----------------
SELECT CONCAT(news.author.name, ' ', news.author.surname) AS author
FROM news.news
LEFT JOIN news.news_author ON news.news.id=news.news_author.news_id
LEFT JOIN news.author ON news.news_author.author_id=news.author.id
GROUP BY news.author.id
HAVING SUM(LENGTH(news.news.full_text)) > 30 AND AVG(LENGTH(news.news.full_text)) > 10;

-----------------#3-----------------
CREATE OR REPLACE FUNCTION news.news_tags (IN news_id_value bigint, IN delimiter_value character varying(10))
    RETURNS text AS $news_tags$
	DECLARE news_tags text;
BEGIN
	SELECT STRING_AGG(news.tag.name, delimiter_value) INTO news_tags
	FROM news.news
	  LEFT JOIN news.news_tag ON news.news.id = news.news_tag.news_id
	  LEFT JOIN news.tag ON news.news_tag.tag_id = news.tag.id
	WHERE news.news.id = news_id_value
	GROUP BY news.news.id;
	RETURN news_tags;
END;
$news_tags$ LANGUAGE plpgsql;

--Example--
SELECT news.news_tags(5, ', ');

-----------------#4-----------------
SELECT news.news.id, news.news.title, news.news_tags(news.news.id, ', ') AS news_tags
FROM news.news;

-----------------#5-----------------
CREATE VIEW news.half_random AS
SELECT news.author.id AS author_id, CONCAT(news.author.name, ' ', news.author.surname) AS competitor
	  FROM news.author
	  ORDER BY random()
	  LIMIT (SELECT COUNT(*)/ 2 FROM news.author);
	  
-----------------#6-----------------
--Table--
CREATE TABLE news.logging (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    insert_date date NOT NULL DEFAULT CURRENT_DATE,
	referenced_table_name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY (id)
);

--Trigger function--
CREATE OR REPLACE FUNCTION news.insert_trigger() RETURNS TRIGGER AS $body$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO news.logging (referenced_table_name, description) 
        VALUES (TG_TABLE_NAME::TEXT, row_to_json(NEW));
        RETURN NEW;
    ELSE
        RAISE WARNING '[news.insert_trigger] - Other action occurred: %, at %', TG_OP, now();
        RETURN NULL;
    END IF;
END;
$body$
LANGUAGE plpgsql SECURITY DEFINER;

--SQL query generation that add trigger function for each table--
SELECT
    'CREATE TRIGGER '
    || trigger_name
	|| '_trigger'
    || ' BEFORE INSERT ON '
	|| tab_name
	|| ' FOR EACH ROW EXECUTE PROCEDURE news.insert_trigger();' AS trigger_creation_query
FROM (
    SELECT
        table_name AS trigger_name,
		quote_ident(table_schema) || '.' || quote_ident(table_name) AS tab_name
    FROM
        information_schema.tables
    WHERE
        table_schema NOT IN ('pg_catalog', 'information_schema')
        AND table_schema NOT LIKE 'pg_toast%'
		AND table_name NOT LIKE '%logging'
) tablist;

-----------------#7-----------------
SELECT
	spcname AS tablespace_name,
    pg_size_pretty (
        pg_tablespace_size (T. oid)
    ) AS tablespace_size
FROM pg_tablespace AS t;

-----------------#8-----------------
SELECT
    relname AS table_name,
    pg_size_pretty (
        pg_total_relation_size (C .oid)
    ) AS total_size
FROM
    pg_class AS C
LEFT JOIN pg_namespace AS N ON N.oid = C .relnamespace
WHERE
    nspname NOT IN (
        'pg_catalog',
        'information_schema'
    )
AND C .relkind <> 'i'
AND nspname !~ '^pg_toast'
AND relname NOT LIKE '%seq'
ORDER BY
    pg_total_relation_size (C .oid) DESC;
