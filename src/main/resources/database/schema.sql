DROP TABLE IF EXISTS vet_visit;
DROP TABLE IF EXISTS vet_test;
DROP TABLE IF EXISTS vaccine;
DROP TABLE IF EXISTS pet;
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

CREATE TABLE pet (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet_type tinyint NOT NULL,
	name character varying (20) NOT NULL,
	breed character varying (50) NOT NULL,
	age smallint NOT NULL,
	province tinyint NOT NULL REFERENCES province(id),
	association int NOT NULL REFERENCES web_user(id),
	adopted boolean NOT NULL,
	for_adoption boolean NOT NULL,
	for_host boolean NOT NULL,
	adopter int REFERENCES web_user(id),
	host int REFERENCES web_user(id),
	comment character varying (200),
	dogs_affinity boolean NOT NULL DEFAULT true,
	cats_affinity boolean NOT NULL DEFAULT true,
	kids_affinity boolean NOT NULL DEFAULT true
);

CREATE TABLE vaccine (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	name character varying (50) NOT NULL,
	description character varying (200),
	applied_on date NOT NULL
);

CREATE TABLE vet_test (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	name character varying (50) NOT NULL,
	description character varying (200) NOT NULL,
	applied_on date NOT NULL
);

CREATE TABLE vet_visit (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	description character varying (200) NOT NULL,
	visit_date date NOT NULL,
	cost numeric (7,2)
);