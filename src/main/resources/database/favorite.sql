CREATE TABLE favorite (
	user_id bigint NOT NULL,
	pet_id bigint NOT NULL,
	CONSTRAINT favorite_pk PRIMARY KEY (user_id, pet_id)
);