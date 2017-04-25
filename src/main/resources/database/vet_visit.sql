CREATE TABLE vet_visit (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	pet bigint NOT NULL REFERENCES pet(id),
	description character varying (200) NOT NULL,
	visit_date date NOT NULL,
	cost numeric (7,2)
);