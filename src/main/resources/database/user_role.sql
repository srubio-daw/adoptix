CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE security.user_role (
	id serial NOT NULL PRIMARY KEY,
	user_id integer NOT NULL REFERENCES security.web_user(id),
	role_id integer NOT NULL REFERENCES security.role(id),
	CONSTRAINT user_role_uq UNIQUE (user_id, role_id)
);