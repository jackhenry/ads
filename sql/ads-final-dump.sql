--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1 (Debian 13.1-1.pgdg100+1)
-- Dumped by pg_dump version 13.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: ads; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE ads WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE ads OWNER TO postgres;

\connect ads

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    account_id integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    employee_id integer NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: account_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_account_id_seq OWNER TO postgres;

--
-- Name: account_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_account_id_seq OWNED BY public.account.account_id;


--
-- Name: account_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_employee_id_seq OWNER TO postgres;

--
-- Name: account_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_employee_id_seq OWNED BY public.account.employee_id;


--
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    employee_id integer NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- Name: nurse; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nurse (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);


ALTER TABLE public.nurse OWNER TO postgres;

--
-- Name: pharmatech; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pharmatech (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);


ALTER TABLE public.pharmatech OWNER TO postgres;

--
-- Name: all_employees; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.all_employees AS
 SELECT employee.employee_id,
    employee_and_account.employee_type,
    employee.firstname,
    employee.lastname,
    employee_and_account.account_id,
    employee_and_account.username
   FROM (( SELECT employeetypes.employee_id,
            employeetypes.account_id,
            employeetypes.employee_type,
            account.username
           FROM (( SELECT doctor.employee_id,
                    doctor.account_id,
                    'doctor'::text AS employee_type
                   FROM public.doctor
                UNION ALL
                 SELECT pharmatech.employee_id,
                    pharmatech.account_id,
                    'pharmatech'::text AS employe_type
                   FROM public.pharmatech
                UNION ALL
                 SELECT nurse.employee_id,
                    nurse.account_id,
                    'nurse'::text AS employee_type
                   FROM public.nurse) employeetypes
             LEFT JOIN public.account ON ((employeetypes.account_id = account.account_id)))) employee_and_account
     LEFT JOIN public.employee ON ((employee_and_account.employee_id = employee.employee_id)));


ALTER TABLE public.all_employees OWNER TO postgres;

--
-- Name: doctor_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.doctor_account_id_seq OWNER TO postgres;

--
-- Name: doctor_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_account_id_seq OWNED BY public.doctor.account_id;


--
-- Name: doctor_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.doctor_employee_id_seq OWNER TO postgres;

--
-- Name: doctor_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_employee_id_seq OWNED BY public.doctor.employee_id;


--
-- Name: drug; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.drug (
    drug_id integer NOT NULL,
    drug_name text NOT NULL,
    concentration text NOT NULL
);


ALTER TABLE public.drug OWNER TO postgres;

--
-- Name: drug_drug_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.drug_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.drug_drug_id_seq OWNER TO postgres;

--
-- Name: drug_drug_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.drug_drug_id_seq OWNED BY public.drug.drug_id;


--
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employee_employee_id_seq OWNER TO postgres;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employee_employee_id_seq OWNED BY public.employee.employee_id;


--
-- Name: medication_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medication_order (
    order_id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now() NOT NULL,
    expiration_date timestamp without time zone NOT NULL,
    quantity integer NOT NULL,
    patient_id integer NOT NULL,
    drug_id integer NOT NULL,
    doctor_id integer NOT NULL
);


ALTER TABLE public.medication_order OWNER TO postgres;

--
-- Name: medication_order_doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medication_order_doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medication_order_doctor_id_seq OWNER TO postgres;

--
-- Name: medication_order_doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medication_order_doctor_id_seq OWNED BY public.medication_order.doctor_id;


--
-- Name: medication_order_drug_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medication_order_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medication_order_drug_id_seq OWNER TO postgres;

--
-- Name: medication_order_drug_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medication_order_drug_id_seq OWNED BY public.medication_order.drug_id;


--
-- Name: medication_order_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medication_order_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medication_order_order_id_seq OWNER TO postgres;

--
-- Name: medication_order_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medication_order_order_id_seq OWNED BY public.medication_order.order_id;


--
-- Name: medication_order_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medication_order_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medication_order_patient_id_seq OWNER TO postgres;

--
-- Name: medication_order_patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medication_order_patient_id_seq OWNED BY public.medication_order.patient_id;


--
-- Name: nurse_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.nurse_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.nurse_account_id_seq OWNER TO postgres;

--
-- Name: nurse_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.nurse_account_id_seq OWNED BY public.nurse.account_id;


--
-- Name: nurse_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.nurse_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.nurse_employee_id_seq OWNER TO postgres;

--
-- Name: nurse_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.nurse_employee_id_seq OWNED BY public.nurse.employee_id;


--
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient (
    patient_id integer NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL,
    phone_number character varying(10) NOT NULL,
    admit_date timestamp without time zone DEFAULT now() NOT NULL,
    discharge_date timestamp without time zone
);


ALTER TABLE public.patient OWNER TO postgres;

--
-- Name: patient_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.patient_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.patient_patient_id_seq OWNER TO postgres;

--
-- Name: patient_patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.patient_patient_id_seq OWNED BY public.patient.patient_id;


--
-- Name: pharmatech_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pharmatech_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pharmatech_account_id_seq OWNER TO postgres;

--
-- Name: pharmatech_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pharmatech_account_id_seq OWNED BY public.pharmatech.account_id;


--
-- Name: pharmatech_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pharmatech_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pharmatech_employee_id_seq OWNER TO postgres;

--
-- Name: pharmatech_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pharmatech_employee_id_seq OWNED BY public.pharmatech.employee_id;


--
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    quantity integer NOT NULL,
    threshold integer NOT NULL,
    drug_expiration date NOT NULL,
    drug_id integer NOT NULL
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- Name: stock_drug_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stock_drug_id_seq OWNER TO postgres;

--
-- Name: stock_drug_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_drug_id_seq OWNED BY public.stock.drug_id;


--
-- Name: token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.token (
    account_id integer NOT NULL,
    token text
);


ALTER TABLE public.token OWNER TO postgres;

--
-- Name: token_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.token_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.token_account_id_seq OWNER TO postgres;

--
-- Name: token_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.token_account_id_seq OWNED BY public.token.account_id;


--
-- Name: account account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN account_id SET DEFAULT nextval('public.account_account_id_seq'::regclass);


--
-- Name: account employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN employee_id SET DEFAULT nextval('public.account_employee_id_seq'::regclass);


--
-- Name: doctor employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN employee_id SET DEFAULT nextval('public.doctor_employee_id_seq'::regclass);


--
-- Name: doctor account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN account_id SET DEFAULT nextval('public.doctor_account_id_seq'::regclass);


--
-- Name: drug drug_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drug ALTER COLUMN drug_id SET DEFAULT nextval('public.drug_drug_id_seq'::regclass);


--
-- Name: employee employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee ALTER COLUMN employee_id SET DEFAULT nextval('public.employee_employee_id_seq'::regclass);


--
-- Name: medication_order order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order ALTER COLUMN order_id SET DEFAULT nextval('public.medication_order_order_id_seq'::regclass);


--
-- Name: medication_order patient_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order ALTER COLUMN patient_id SET DEFAULT nextval('public.medication_order_patient_id_seq'::regclass);


--
-- Name: medication_order drug_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order ALTER COLUMN drug_id SET DEFAULT nextval('public.medication_order_drug_id_seq'::regclass);


--
-- Name: medication_order doctor_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order ALTER COLUMN doctor_id SET DEFAULT nextval('public.medication_order_doctor_id_seq'::regclass);


--
-- Name: nurse employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse ALTER COLUMN employee_id SET DEFAULT nextval('public.nurse_employee_id_seq'::regclass);


--
-- Name: nurse account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse ALTER COLUMN account_id SET DEFAULT nextval('public.nurse_account_id_seq'::regclass);


--
-- Name: patient patient_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_patient_id_seq'::regclass);


--
-- Name: pharmatech employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmatech ALTER COLUMN employee_id SET DEFAULT nextval('public.pharmatech_employee_id_seq'::regclass);


--
-- Name: pharmatech account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmatech ALTER COLUMN account_id SET DEFAULT nextval('public.pharmatech_account_id_seq'::regclass);


--
-- Name: stock drug_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock ALTER COLUMN drug_id SET DEFAULT nextval('public.stock_drug_id_seq'::regclass);


--
-- Name: token account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token ALTER COLUMN account_id SET DEFAULT nextval('public.token_account_id_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account (account_id, username, password, employee_id) VALUES (1, 'jackerickson', 'junkmail1', 1);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (2, 'rachel', 'junkmail1', 2);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (3, 'freddurst', 'junkmail1', 3);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (4, 'falcon', 'falcon', 5);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (5, 'alexandre', 'arackstraw1', 6);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (6, 'peggi', 'prodear2', 7);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (7, 'saxon', 'sfullerton', 8);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (8, 'alard', 'amerrin5', 9);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (9, 'adelina', 'aserston6', 10);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (11, 'philippa', 'pguillfordd', 12);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (12, 'geralt', 'ofrivia', 13);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (13, 'yennefer', 'ofvengerburg', 14);
INSERT INTO public.account (account_id, username, password, employee_id) VALUES (14, 'ivorykose', 'ivorykose123', 15);


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.doctor (employee_id, account_id) VALUES (1, 1);
INSERT INTO public.doctor (employee_id, account_id) VALUES (2, 2);
INSERT INTO public.doctor (employee_id, account_id) VALUES (5, 4);
INSERT INTO public.doctor (employee_id, account_id) VALUES (7, 6);
INSERT INTO public.doctor (employee_id, account_id) VALUES (9, 8);
INSERT INTO public.doctor (employee_id, account_id) VALUES (10, 9);
INSERT INTO public.doctor (employee_id, account_id) VALUES (13, 12);


--
-- Data for Name: drug; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (1, 'Viagra', '1000mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (2, 'Cialis', '1200mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (3, 'Lisinopril', '50mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (4, 'Amoxicillin', '500mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (5, 'Vitamin D', '50000IU');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (6, 'Cetirizine hydrochloride', '10mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (7, 'Azithromycin', '250mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (8, 'Amlodipine besylate', '10mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (9, 'Albuterol sulfate HFA', '90mcg/act');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (10, 'Cyclobenzaprine hydrochloride', '10mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (11, 'Cephalexin', '500mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (12, 'Hydrochlorothiazide', '25mg');
INSERT INTO public.drug (drug_id, drug_name, concentration) VALUES (13, 'Levothyroxine sodium', '1000mg');


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (1, 'Jack', 'Erickson');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (2, 'Rachel', 'Grushan');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (3, 'Fred', 'Durst');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (4, 'Amaleta', 'Huburt');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (5, 'Donella', 'Falconbridge');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (6, 'Alexandre', 'Rackstraw');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (7, 'Peggi', 'Rodear');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (8, 'Saxon', 'Fullerton');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (9, 'Alard', 'Merrin');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (10, 'Adelina', 'Searston');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (12, 'Philippa', 'Eilhart');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (13, 'Geralt', 'Rivia');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (14, 'Yennefer', 'Vengerburg');
INSERT INTO public.employee (employee_id, firstname, lastname) VALUES (15, 'Ivory', 'Kose');


--
-- Data for Name: medication_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (3, '2021-04-09 19:00:00', '2021-04-23 19:00:00', 6, 2, 5, 1);
INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (4, '2021-04-09 19:00:00', '2021-04-23 19:00:00', 10, 7, 3, 10);
INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (6, '2021-04-09 19:00:00', '2021-04-23 19:00:00', 5000, 10, 9, 10);
INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (7, '2021-04-09 19:00:00', '2021-04-23 19:00:00', 10, 11, 9, 10);
INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (8, '2021-04-09 19:00:00', '2021-04-12 19:00:00', 50, 13, 10, 7);
INSERT INTO public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) VALUES (9, '2021-04-09 19:00:00', '2021-04-23 19:00:00', 10, 7, 4, 1);


--
-- Data for Name: nurse; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.nurse (employee_id, account_id) VALUES (6, 5);
INSERT INTO public.nurse (employee_id, account_id) VALUES (14, 13);
INSERT INTO public.nurse (employee_id, account_id) VALUES (15, 14);


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (1, 'Ted', 'Bundy', '6517797012', '2021-04-08 20:07:02.940459', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (2, 'Malone', 'Malonesky', '6514500000', '2021-04-09 10:48:12.243033', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (4, 'Jone', 'Darwin', '4291236692', '2021-04-10 17:58:30.724433', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (6, 'Kara-lynn', 'Grimoldby', '2412998745', '2021-04-10 17:58:56.732337', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (7, 'Shaun', 'Swanson', '8829327392', '2021-04-10 17:59:16.437817', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (8, 'Thurston', 'Amerighi', '9032947604', '2021-04-10 17:59:35.973627', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (9, 'Curtice', 'Warby', '4917455146', '2021-04-10 17:59:51.862182', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (10, 'Sampson', 'Schubart', '7448942148', '2021-04-10 18:00:06.776654', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (11, 'Elisha', 'Gulberg', '9217418100', '2021-04-10 18:00:21.980459', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (12, 'Hillie', 'Stoke', '4414108612', '2021-04-10 18:00:35.362791', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (13, 'Kilian', 'Hussy', '8376605830', '2021-04-10 18:00:53.678196', NULL);
INSERT INTO public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) VALUES (3, 'Joe', 'Mauer', '9315077589', '2021-04-09 16:08:41.275644', NULL);


--
-- Data for Name: pharmatech; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pharmatech (employee_id, account_id) VALUES (3, 3);
INSERT INTO public.pharmatech (employee_id, account_id) VALUES (8, 7);
INSERT INTO public.pharmatech (employee_id, account_id) VALUES (12, 11);


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (1500, 1000, '2022-06-21', 2);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (5000, 2000, '2020-06-11', 3);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (500, 200, '2021-08-11', 4);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (6000, 2000, '2023-09-26', 5);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (2000, 550, '2022-07-21', 6);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (700, 200, '2022-12-31', 7);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (500, 200, '2022-09-17', 9);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (1111, 122, '2024-03-03', 10);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (4921, 500, '2021-04-07', 1);
INSERT INTO public.stock (quantity, threshold, drug_expiration, drug_id) VALUES (484, 200, '2021-09-26', 8);


--
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.token (account_id, token) VALUES (1, 'Jx5gCAvFSuU0FyWpEr_yoTNaMG8xkBBn');


--
-- Name: account_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_account_id_seq', 14, true);


--
-- Name: account_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_employee_id_seq', 1, false);


--
-- Name: doctor_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_account_id_seq', 1, false);


--
-- Name: doctor_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_employee_id_seq', 1, false);


--
-- Name: drug_drug_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.drug_drug_id_seq', 13, true);


--
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employee_employee_id_seq', 15, true);


--
-- Name: medication_order_doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medication_order_doctor_id_seq', 1, false);


--
-- Name: medication_order_drug_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medication_order_drug_id_seq', 1, false);


--
-- Name: medication_order_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medication_order_order_id_seq', 9, true);


--
-- Name: medication_order_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medication_order_patient_id_seq', 1, false);


--
-- Name: nurse_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nurse_account_id_seq', 1, false);


--
-- Name: nurse_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nurse_employee_id_seq', 1, false);


--
-- Name: patient_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patient_patient_id_seq', 13, true);


--
-- Name: pharmatech_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pharmatech_account_id_seq', 1, false);


--
-- Name: pharmatech_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pharmatech_employee_id_seq', 1, false);


--
-- Name: stock_drug_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_drug_id_seq', 1, false);


--
-- Name: token_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.token_account_id_seq', 1, false);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_id);


--
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (employee_id);


--
-- Name: drug drug_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drug
    ADD CONSTRAINT drug_pkey PRIMARY KEY (drug_id);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (employee_id);


--
-- Name: medication_order medication_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_pkey PRIMARY KEY (order_id);


--
-- Name: nurse nurse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_pkey PRIMARY KEY (employee_id);


--
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);


--
-- Name: pharmatech pharmatech_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_pkey PRIMARY KEY (employee_id);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (drug_id);


--
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (account_id);


--
-- Name: account account_employee_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;


--
-- Name: doctor doctor_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;


--
-- Name: doctor doctor_employee_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;


--
-- Name: medication_order medication_order_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(employee_id);


--
-- Name: medication_order medication_order_drug_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_drug_id_fkey FOREIGN KEY (drug_id) REFERENCES public.drug(drug_id);


--
-- Name: medication_order medication_order_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient(patient_id);


--
-- Name: nurse nurse_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;


--
-- Name: nurse nurse_employee_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;


--
-- Name: pharmatech pharmatech_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;


--
-- Name: pharmatech pharmatech_employee_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;


--
-- Name: stock stock_drug_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_drug_id_fkey FOREIGN KEY (drug_id) REFERENCES public.drug(drug_id);


--
-- Name: token token_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id);


--
-- PostgreSQL database dump complete
--

