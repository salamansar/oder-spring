CREATE SEQUENCE seq_user_id START WITH 1;

CREATE TABLE oder_user (
	id BIGINT PRIMARY KEY,
	login VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	last_name VARCHAR(255),
	first_name VARCHAR(255),
	middle_name VARCHAR(255)
);

CREATE SEQUENCE seq_income_id START WITH 1;

CREATE TABLE income (
	id BIGINT PRIMARY KEY,
	id_user BIGINT NOT NULL,
	income_date TIMESTAMP NOT NULL,
	amount NUMERIC(19,4) NOT NULL,
	description VARCHAR(255),
	document_number INTEGER,
	FOREIGN KEY (id_user) REFERENCES oder_user(id)
);

CREATE SEQUENCE seq_fixed_payment_id START WITH 1;

CREATE TABLE fixed_payment (
	id BIGINT PRIMARY KEY,
	id_category INTEGER,
	payment_year INTEGER NOT NULL,
	payment_value NUMERIC(19,4) NOT NULL,
	UNIQUE (id_category, payment_year)
)

