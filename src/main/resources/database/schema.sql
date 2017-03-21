DROP TABLE IF EXISTS security.user_role;
DROP TABLE IF EXISTS security.role;
DROP TABLE IF EXISTS security.web_user;
DROP TABLE IF EXISTS province;

DROP SCHEMA IF EXISTS security;

CREATE TABLE province (
	id serial NOT NULL PRIMARY KEY,
	name character varying(50) NOT NULL UNIQUE
);

CREATE SCHEMA security;

CREATE TABLE security.web_user (
	id serial NOT NULL PRIMARY KEY,
	name character varying(25) NOT NULL,
	surname character varying(80),
	nif character varying(9) UNIQUE,
	address character varying(200),
	province_id bigint NOT NULL REFERENCES province (id),
	mail character varying(150) NOT NULL UNIQUE,
	username character varying(20) NOT NULL UNIQUE,
	password character varying(32) NOT NULL
);

CREATE TABLE security.role (
	id serial NOT NULL PRIMARY KEY,
	name character varying(20) NOT NULL UNIQUE
);

CREATE TABLE security.user_role (
	id serial NOT NULL PRIMARY KEY,
	user_id integer NOT NULL REFERENCES security.web_user(id),
	role_id integer NOT NULL REFERENCES security.role(id),
	CONSTRAINT user_role_uq UNIQUE (user_id, role_id)
);