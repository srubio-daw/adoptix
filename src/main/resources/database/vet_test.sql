CREATE TABLE vet_test (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	name character varying (50) NOT NULL,
	description character varying (200),
	applied_on date NOT NULL
);