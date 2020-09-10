
/* Drop Tables */

DROP TABLE IF EXISTS account_authority;
DROP TABLE IF EXISTS account;




/* Create Tables */

-- User table
CREATE TABLE account
(
	id bigserial NOT NULL UNIQUE,
	username varchar(64) NOT NULL UNIQUE,
	password varchar(64) NOT NULL UNIQUE,
	email varchar(64) NOT NULL UNIQUE,
	first_name varchar(32),
	last_name varchar(32),
	active_status boolean NOT NULL,
	deleted_status boolean NOT NULL,
	immutable_status boolean NOT NULL,
	created_user bigint NOT NULL,
	created_date timestamp with time zone NOT NULL,
	updated_user bigint NOT NULL,
	updated_date timestamp with time zone NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- Authorities for users
CREATE TABLE account_authority
(
	id bigserial NOT NULL UNIQUE,
	account_id bigint NOT NULL UNIQUE,
	role_name varchar(64) NOT NULL UNIQUE,
	active_status boolean NOT NULL,
	created_user bigint NOT NULL,
	created_date timestamp with time zone NOT NULL,
	updated_user bigint NOT NULL,
	updated_date timestamp with time zone NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE account
	ADD FOREIGN KEY (created_user)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account
	ADD FOREIGN KEY (updated_user)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_authority
	ADD FOREIGN KEY (account_id)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_authority
	ADD FOREIGN KEY (created_user)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_authority
	ADD FOREIGN KEY (updated_user)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE account IS 'User table';
COMMENT ON TABLE account_authority IS 'Authorities for users';



