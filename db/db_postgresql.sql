
/* Drop Tables */

DROP TABLE IF EXISTS account_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;




/* Create Tables */

-- User table
CREATE TABLE account
(
	id bigserial NOT NULL UNIQUE,
	username varchar(64) NOT NULL UNIQUE,
	password varchar(64) NOT NULL,
	email varchar(64) NOT NULL UNIQUE,
	first_name varchar(32),
	last_name varchar(32),
	active_status boolean NOT NULL,
	deleted_status boolean NOT NULL,
	immutable_status boolean NOT NULL,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	update_date timestamp with time zone NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE account_role
(
	account_id bigint NOT NULL,
	role_id bigint NOT NULL
) WITHOUT OIDS;


-- Authorities for users
CREATE TABLE role
(
	id bigserial NOT NULL UNIQUE,
	role_name varchar(64) NOT NULL UNIQUE,
	active_status boolean NOT NULL,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	update_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE account
	ADD FOREIGN KEY (updater)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account
	ADD FOREIGN KEY (creator)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_role
	ADD FOREIGN KEY (account_id)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE role
	ADD FOREIGN KEY (updater)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE role
	ADD FOREIGN KEY (creator)
	REFERENCES account (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_role
	ADD FOREIGN KEY (role_id)
	REFERENCES role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE account IS 'User table';
COMMENT ON TABLE role IS 'Authorities for users';



