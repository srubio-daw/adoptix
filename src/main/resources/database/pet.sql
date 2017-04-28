CREATE TABLE pet (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet_type tinyint NOT NULL,
	name character varying (20) NOT NULL,
	breed character varying (50) NOT NULL,
	age smallint NOT NULL,
	province tinyint NOT NULL REFERENCES province(id),
	association int NOT NULL REFERENCES web_user(id),
	for_adoption boolean NOT NULL,
	for_host boolean NOT NULL,
	adopter int REFERENCES web_user(id),
	host int REFERENCES web_user(id),
	description character varying (200),
	dogs_affinity boolean NOT NULL DEFAULT true,
	cats_affinity boolean NOT NULL DEFAULT true,
	kids_affinity boolean NOT NULL DEFAULT true
);