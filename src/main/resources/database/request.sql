CREATE TABLE request (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	web_user bigint NOT NULL REFERENCES web_user(id) ON DELETE CASCADE,
	phone character varying (9) NOT NULL,
	dogs_at_home tinyint NOT NULL DEFAULT 0,
	cats_at_home tinyint NOT NULL DEFAULT 0,
	kids_at_home tinyint NOT NULL DEFAULT 0,
	adopt_or_host boolean NOT NULL,
	comment character varying (200),
	creation_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status boolean,
	reject_comment character varying(200)
);