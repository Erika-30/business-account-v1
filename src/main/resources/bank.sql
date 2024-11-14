-- Create the database
-- CREATE DATABASE bank;

-- Connect to the database
\c bankdb;

-- Create the schema
CREATE SCHEMA bank_sch;

-- Create the customers table within the schema
CREATE TABLE bank_sch.customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(255) CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Create the Bank Accounts table within the schema
CREATE TABLE bank_sch.account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE DEFAULT 'ACCOUNT-' || nextval('bank_sch.account_id_seq'),
    balance DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    account_type VARCHAR(20) NOT NULL CHECK (account_type IN ('SAVINGS', 'CURRENT')),
    customer_id INTEGER NOT NULL,
    CONSTRAINT fk_customer
        FOREIGN KEY(customer_id) 
        REFERENCES bank_sch.customer(id)
);