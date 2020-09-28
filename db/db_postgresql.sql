
/* Drop Tables */

DROP TABLE IF EXISTS account_role;
DROP TABLE IF EXISTS check_out;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS book_item;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;




/* Create Tables */

-- User table
CREATE TABLE account
(
	account_id bigserial NOT NULL UNIQUE,
	username text NOT NULL UNIQUE,
	password text NOT NULL,
	email text NOT NULL UNIQUE,
	first_name text,
	last_name text,
	-- Total number of books checked out
	books_checked_out int DEFAULT 0,
	active_status boolean NOT NULL,
	deleted_status boolean NOT NULL,
	immutable_status boolean NOT NULL,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	update_date timestamp with time zone NOT NULL,
	PRIMARY KEY (account_id)
) WITHOUT OIDS;


CREATE TABLE account_role
(
	account_id bigint NOT NULL,
	role_id bigint NOT NULL
) WITHOUT OIDS;


CREATE TABLE author
(
	-- Author ID
	author_id bigserial NOT NULL UNIQUE,
	first_name text NOT NULL,
	last_name text NOT NULL,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	update_date timestamp with time zone NOT NULL,
	PRIMARY KEY (author_id)
) WITHOUT OIDS;


CREATE TABLE book
(
	-- Author ID
	book_id bigserial NOT NULL UNIQUE,
	title text NOT NULL,
	subject text,
	publisher text,
	-- Language of the book
	lang text,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	update_date timestamp with time zone NOT NULL,
	-- Author ID
	book_author_id bigint NOT NULL,
	PRIMARY KEY (book_id)
) WITHOUT OIDS;


CREATE TABLE book_item
(
	book_item_id bigserial NOT NULL UNIQUE,
	-- ISBN - ebook, paperback, hardcover edition of the same book will have differen ISBN
	isbn text NOT NULL,
	-- Paperback, hardcover and etc.
	format text NOT NULL,
	-- Whether available, reservee, loaned or lost
	status text NOT NULL,
	rack_number int,
	-- Description of physical location of the book
	location_identifier text NOT NULL,
	purchase_date timestamp with time zone,
	price decimal,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	update_date timestamp with time zone NOT NULL,
	-- Book table foreign key
	book_item_book_id bigint NOT NULL,
	PRIMARY KEY (book_item_id)
) WITHOUT OIDS;


-- Table for keeping borrowed book details
CREATE TABLE check_out
(
	check_out_id bigserial NOT NULL UNIQUE,
	-- Date when the book is borrowed
	-- 
	check_out_date timestamp with time zone NOT NULL,
	-- Date for the book to be returned
	due_date timestamp with time zone NOT NULL,
	-- Actual return date of the book
	return_date timestamp with time zone,
	-- Amount to be paid if a book return date is overdue
	fine numeric DEFAULT 0.00,
	book_item_id bigint NOT NULL,
	borrower_id bigint NOT NULL,
	PRIMARY KEY (check_out_id)
) WITHOUT OIDS;


CREATE TABLE reservation
(
	reservation_id bigserial NOT NULL UNIQUE,
	status text,
	request_date timestamp with time zone NOT NULL,
	book_item_id bigint NOT NULL UNIQUE,
	reserver_id bigint NOT NULL UNIQUE,
	pick_up_date timestamp with time zone,
	PRIMARY KEY (reservation_id)
) WITHOUT OIDS;


-- Authorities for users
CREATE TABLE role
(
	role_id bigserial NOT NULL UNIQUE,
	role_name text NOT NULL UNIQUE,
	active_status boolean NOT NULL,
	creator bigint NOT NULL,
	creation_date timestamp with time zone NOT NULL,
	update_date timestamp with time zone NOT NULL,
	updater bigint NOT NULL,
	PRIMARY KEY (role_id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE account
	ADD FOREIGN KEY (creator)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account
	ADD FOREIGN KEY (updater)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_role
	ADD FOREIGN KEY (account_id)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE author
	ADD FOREIGN KEY (creator)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE author
	ADD FOREIGN KEY (updater)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book
	ADD FOREIGN KEY (creator)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book
	ADD FOREIGN KEY (updater)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book_item
	ADD FOREIGN KEY (updater)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book_item
	ADD FOREIGN KEY (creator)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE check_out
	ADD FOREIGN KEY (borrower_id)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservation
	ADD FOREIGN KEY (reserver_id)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE role
	ADD FOREIGN KEY (creator)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE role
	ADD FOREIGN KEY (updater)
	REFERENCES account (account_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book
	ADD FOREIGN KEY (book_author_id)
	REFERENCES author (author_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book_item
	ADD FOREIGN KEY (book_item_book_id)
	REFERENCES book (book_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE check_out
	ADD FOREIGN KEY (book_item_id)
	REFERENCES book_item (book_item_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservation
	ADD FOREIGN KEY (book_item_id)
	REFERENCES book_item (book_item_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE account_role
	ADD FOREIGN KEY (role_id)
	REFERENCES role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE account IS 'User table';
COMMENT ON COLUMN account.books_checked_out IS 'Total number of books checked out';
COMMENT ON COLUMN author.author_id IS 'Author ID';
COMMENT ON COLUMN book.book_id IS 'Author ID';
COMMENT ON COLUMN book.lang IS 'Language of the book';
COMMENT ON COLUMN book.book_author_id IS 'Author ID';
COMMENT ON COLUMN book_item.isbn IS 'ISBN - ebook, paperback, hardcover edition of the same book will have differen ISBN';
COMMENT ON COLUMN book_item.format IS 'Paperback, hardcover and etc.';
COMMENT ON COLUMN book_item.status IS 'Whether available, reservee, loaned or lost';
COMMENT ON COLUMN book_item.location_identifier IS 'Description of physical location of the book';
COMMENT ON COLUMN book_item.book_item_book_id IS 'Book table foreign key';
COMMENT ON TABLE check_out IS 'Table for keeping borrowed book details';
COMMENT ON COLUMN check_out.check_out_date IS 'Date when the book is borrowed
';
COMMENT ON COLUMN check_out.due_date IS 'Date for the book to be returned';
COMMENT ON COLUMN check_out.return_date IS 'Actual return date of the book';
COMMENT ON COLUMN check_out.fine IS 'Amount to be paid if a book return date is overdue';
COMMENT ON TABLE role IS 'Authorities for users';



