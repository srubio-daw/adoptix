CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE security.role (
	id serial NOT NULL PRIMARY KEY,
	name character varying(20) NOT NULL UNIQUE
);