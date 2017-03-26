CREATE TABLE web_user (
	id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name character varying(25) NOT NULL,
	surname character varying(80),
	nif character varying(9) UNIQUE,
	address character varying(200),
	province_id tinyint NOT NULL REFERENCES province (id),
	mail character varying(150) NOT NULL UNIQUE,
	username character varying(20) NOT NULL UNIQUE,
	password character varying(32) NOT NULL
);