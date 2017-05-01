CREATE TABLE user_role (
	id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
	user_id int NOT NULL REFERENCES web_user(id) ON DELETE CASCADE,
	role_id tinyint NOT NULL REFERENCES role(id) ON DELETE CASCADE,
	CONSTRAINT user_role_uq UNIQUE (user_id, role_id)
);