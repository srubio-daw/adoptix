DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS web_user;
DROP TABLE IF EXISTS province;

CREATE TABLE province (
	id tinyint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name character varying(50) NOT NULL UNIQUE
);

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

CREATE TABLE role (
	id tinyint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name character varying(20) NOT NULL UNIQUE
);

CREATE TABLE user_role (
	id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_id int NOT NULL REFERENCES web_user(id),
	role_id tinyint NOT NULL REFERENCES role(id),
	CONSTRAINT user_role_uq UNIQUE (user_id, role_id)
);