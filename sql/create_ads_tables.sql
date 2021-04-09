SET TIMEZONE = 'America/Chicago';

DROP TABLE IF EXISTS pharmatech CASCADE;
DROP TABLE IF EXISTS nurse;
DROP TABLE IF EXISTS medication_order;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS drug;
DROP TABLE IF EXISTS token;

CREATE TABLE Employee (
    employee_id      SERIAL PRIMARY KEY,
    firstname       TEXT NOT NULL,
    lastname        TEXT NOT NULL
);

CREATE TABLE Account (
    account_id       SERIAL PRIMARY KEY,
    username         TEXT NOT NULL,
    password         TEXT NOT NULL,
    employee_id      SERIAL REFERENCES Employee(employee_id) ON DELETE CASCADE
);

CREATE TABLE PharmaTech (
    employee_id      SERIAL UNIQUE REFERENCES Employee(employee_id) ON DELETE CASCADE,
    account_id       SERIAL REFERENCES Account(account_id) ON DELETE CASCADE,
    PRIMARY KEY (employee_id)
);

CREATE TABLE Nurse (
    employee_id      SERIAL UNIQUE REFERENCES Employee(employee_id) ON DELETE CASCADE,
    account_id       SERIAL REFERENCES Account(account_id) ON DELETE CASCADE,
    PRIMARY KEY (employee_id)
);

CREATE TABLE Doctor (
    employee_id      SERIAL UNIQUE REFERENCES Employee(employee_id) ON DELETE CASCADE,
    account_id       SERIAL REFERENCES Account(account_id) ON DELETE CASCADE,
    PRIMARY KEY (employee_id)
);

CREATE TABLE Patient (
    patient_id       SERIAL PRIMARY KEY,
    firstname       TEXT NOT NULL,
    lastname        TEXT NOT NULL,
    phone_number     VARCHAR(10) NOT NULL,
    admit_date       TIMESTAMP NOT NULL DEFAULT NOW(),
    discharge_date   TIMESTAMP
);

CREATE TABLE Drug (
    drug_id          SERIAL PRIMARY KEY,
    drug_name        TEXT NOT NULL,
    concentration   TEXT NOT NULL
);

CREATE TABLE medication_order (
    order_id         SERIAL PRIMARY KEY,
    creation_date    TIMESTAMP NOT NULL DEFAULT NOW(),
    expiration_date  TIMESTAMP NOT NULL,
    quantity         INTEGER NOT NULL,
    patient_id       SERIAL REFERENCES Patient(patient_id),
    drug_id          SERIAL REFERENCES Drug(drug_id),
    doctor_id        SERIAL REFERENCES Doctor(employee_id)
);

CREATE TABLE Stock (
    quantity        INTEGER NOT NULL,
    threshold       INTEGER NOT NULL,
    drug_expiration  DATE NOT  NULL,
    drug_id          SERIAL PRIMARY KEY REFERENCES Drug
);

CREATE VIEW all_employees AS
(
SELECT employee.employee_id, employee_type, firstname, lastname, account_id, username
FROM (
         SELECT employeetypes.employee_id, employeetypes.account_id, employee_type, account.username
         FROM (
                  SELECT *, 'doctor' as employee_type
                  FROM doctor
                  UNION ALL
                  SELECT *, 'pharmatech' as employe_type
                  FROM pharmatech
                  UNION ALL
                  SELECT *, 'nurse' as employee_type
                  FROM nurse
              ) AS employeetypes
                  LEFT JOIN account ON employeetypes.account_id = account.account_id
     ) AS employee_and_account LEFT JOIN employee ON employee_and_account.employee_id = employee.employee_id
);

CREATE TABLE Token (
    account_id       SERIAL PRIMARY KEY REFERENCES Account(account_id),
    token            TEXT
)