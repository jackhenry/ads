PGDMP                         y           ads    13.1 (Debian 13.1-1.pgdg100+1)    13.2 s               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    20084    ads    DATABASE     W   CREATE DATABASE ads WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';
    DROP DATABASE ads;
                postgres    false            ?            1259    20085    account    TABLE     ?   CREATE TABLE public.account (
    account_id integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    employee_id integer NOT NULL
);
    DROP TABLE public.account;
       public         heap    postgres    false            ?            1259    20091    account_account_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.account_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.account_account_id_seq;
       public          postgres    false    200                       0    0    account_account_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.account_account_id_seq OWNED BY public.account.account_id;
          public          postgres    false    201            ?            1259    20093    account_employee_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.account_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.account_employee_id_seq;
       public          postgres    false    200                       0    0    account_employee_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.account_employee_id_seq OWNED BY public.account.employee_id;
          public          postgres    false    202            ?            1259    20095    doctor    TABLE     b   CREATE TABLE public.doctor (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);
    DROP TABLE public.doctor;
       public         heap    postgres    false            ?            1259    20098    employee    TABLE     |   CREATE TABLE public.employee (
    employee_id integer NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL
);
    DROP TABLE public.employee;
       public         heap    postgres    false            ?            1259    20104    nurse    TABLE     a   CREATE TABLE public.nurse (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);
    DROP TABLE public.nurse;
       public         heap    postgres    false            ?            1259    20107 
   pharmatech    TABLE     f   CREATE TABLE public.pharmatech (
    employee_id integer NOT NULL,
    account_id integer NOT NULL
);
    DROP TABLE public.pharmatech;
       public         heap    postgres    false            ?            1259    20110    all_employees    VIEW     ?  CREATE VIEW public.all_employees AS
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
     DROP VIEW public.all_employees;
       public          postgres    false    206    204    203    200    200    204    203    204    205    205    206            ?            1259    20115    doctor_account_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.doctor_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.doctor_account_id_seq;
       public          postgres    false    203                       0    0    doctor_account_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.doctor_account_id_seq OWNED BY public.doctor.account_id;
          public          postgres    false    208            ?            1259    20117    doctor_employee_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.doctor_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.doctor_employee_id_seq;
       public          postgres    false    203                       0    0    doctor_employee_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.doctor_employee_id_seq OWNED BY public.doctor.employee_id;
          public          postgres    false    209            ?            1259    20119    drug    TABLE     y   CREATE TABLE public.drug (
    drug_id integer NOT NULL,
    drug_name text NOT NULL,
    concentration text NOT NULL
);
    DROP TABLE public.drug;
       public         heap    postgres    false            ?            1259    20125    drug_drug_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.drug_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.drug_drug_id_seq;
       public          postgres    false    210                       0    0    drug_drug_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.drug_drug_id_seq OWNED BY public.drug.drug_id;
          public          postgres    false    211            ?            1259    20127    employee_employee_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.employee_employee_id_seq;
       public          postgres    false    204                       0    0    employee_employee_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.employee_employee_id_seq OWNED BY public.employee.employee_id;
          public          postgres    false    212            ?            1259    20129    medication_order    TABLE     F  CREATE TABLE public.medication_order (
    order_id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now() NOT NULL,
    expiration_date timestamp without time zone NOT NULL,
    quantity integer NOT NULL,
    patient_id integer NOT NULL,
    drug_id integer NOT NULL,
    doctor_id integer NOT NULL
);
 $   DROP TABLE public.medication_order;
       public         heap    postgres    false            ?            1259    20133    medication_order_doctor_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.medication_order_doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.medication_order_doctor_id_seq;
       public          postgres    false    213                       0    0    medication_order_doctor_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.medication_order_doctor_id_seq OWNED BY public.medication_order.doctor_id;
          public          postgres    false    214            ?            1259    20135    medication_order_drug_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.medication_order_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.medication_order_drug_id_seq;
       public          postgres    false    213                       0    0    medication_order_drug_id_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.medication_order_drug_id_seq OWNED BY public.medication_order.drug_id;
          public          postgres    false    215            ?            1259    20137    medication_order_order_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.medication_order_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.medication_order_order_id_seq;
       public          postgres    false    213                       0    0    medication_order_order_id_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.medication_order_order_id_seq OWNED BY public.medication_order.order_id;
          public          postgres    false    216            ?            1259    20139    medication_order_patient_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.medication_order_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.medication_order_patient_id_seq;
       public          postgres    false    213                        0    0    medication_order_patient_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.medication_order_patient_id_seq OWNED BY public.medication_order.patient_id;
          public          postgres    false    217            ?            1259    20141    nurse_account_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.nurse_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.nurse_account_id_seq;
       public          postgres    false    205            !           0    0    nurse_account_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.nurse_account_id_seq OWNED BY public.nurse.account_id;
          public          postgres    false    218            ?            1259    20143    nurse_employee_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.nurse_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.nurse_employee_id_seq;
       public          postgres    false    205            "           0    0    nurse_employee_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.nurse_employee_id_seq OWNED BY public.nurse.employee_id;
          public          postgres    false    219            ?            1259    20145    patient    TABLE       CREATE TABLE public.patient (
    patient_id integer NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL,
    phone_number character varying(10) NOT NULL,
    admit_date timestamp without time zone DEFAULT now() NOT NULL,
    discharge_date timestamp without time zone
);
    DROP TABLE public.patient;
       public         heap    postgres    false            ?            1259    20152    patient_patient_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.patient_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.patient_patient_id_seq;
       public          postgres    false    220            #           0    0    patient_patient_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.patient_patient_id_seq OWNED BY public.patient.patient_id;
          public          postgres    false    221            ?            1259    20154    pharmatech_account_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.pharmatech_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.pharmatech_account_id_seq;
       public          postgres    false    206            $           0    0    pharmatech_account_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.pharmatech_account_id_seq OWNED BY public.pharmatech.account_id;
          public          postgres    false    222            ?            1259    20156    pharmatech_employee_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.pharmatech_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.pharmatech_employee_id_seq;
       public          postgres    false    206            %           0    0    pharmatech_employee_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.pharmatech_employee_id_seq OWNED BY public.pharmatech.employee_id;
          public          postgres    false    223            ?            1259    20158    stock    TABLE     ?   CREATE TABLE public.stock (
    quantity integer NOT NULL,
    threshold integer NOT NULL,
    drug_expiration date NOT NULL,
    drug_id integer NOT NULL
);
    DROP TABLE public.stock;
       public         heap    postgres    false            ?            1259    20161    stock_drug_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.stock_drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.stock_drug_id_seq;
       public          postgres    false    224            &           0    0    stock_drug_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.stock_drug_id_seq OWNED BY public.stock.drug_id;
          public          postgres    false    225            ?            1259    20163    token    TABLE     O   CREATE TABLE public.token (
    account_id integer NOT NULL,
    token text
);
    DROP TABLE public.token;
       public         heap    postgres    false            ?            1259    20169    token_account_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.token_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.token_account_id_seq;
       public          postgres    false    226            '           0    0    token_account_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.token_account_id_seq OWNED BY public.token.account_id;
          public          postgres    false    227            @           2604    20171    account account_id    DEFAULT     x   ALTER TABLE ONLY public.account ALTER COLUMN account_id SET DEFAULT nextval('public.account_account_id_seq'::regclass);
 A   ALTER TABLE public.account ALTER COLUMN account_id DROP DEFAULT;
       public          postgres    false    201    200            A           2604    20172    account employee_id    DEFAULT     z   ALTER TABLE ONLY public.account ALTER COLUMN employee_id SET DEFAULT nextval('public.account_employee_id_seq'::regclass);
 B   ALTER TABLE public.account ALTER COLUMN employee_id DROP DEFAULT;
       public          postgres    false    202    200            B           2604    20173    doctor employee_id    DEFAULT     x   ALTER TABLE ONLY public.doctor ALTER COLUMN employee_id SET DEFAULT nextval('public.doctor_employee_id_seq'::regclass);
 A   ALTER TABLE public.doctor ALTER COLUMN employee_id DROP DEFAULT;
       public          postgres    false    209    203            C           2604    20174    doctor account_id    DEFAULT     v   ALTER TABLE ONLY public.doctor ALTER COLUMN account_id SET DEFAULT nextval('public.doctor_account_id_seq'::regclass);
 @   ALTER TABLE public.doctor ALTER COLUMN account_id DROP DEFAULT;
       public          postgres    false    208    203            I           2604    20175    drug drug_id    DEFAULT     l   ALTER TABLE ONLY public.drug ALTER COLUMN drug_id SET DEFAULT nextval('public.drug_drug_id_seq'::regclass);
 ;   ALTER TABLE public.drug ALTER COLUMN drug_id DROP DEFAULT;
       public          postgres    false    211    210            D           2604    20176    employee employee_id    DEFAULT     |   ALTER TABLE ONLY public.employee ALTER COLUMN employee_id SET DEFAULT nextval('public.employee_employee_id_seq'::regclass);
 C   ALTER TABLE public.employee ALTER COLUMN employee_id DROP DEFAULT;
       public          postgres    false    212    204            K           2604    20177    medication_order order_id    DEFAULT     ?   ALTER TABLE ONLY public.medication_order ALTER COLUMN order_id SET DEFAULT nextval('public.medication_order_order_id_seq'::regclass);
 H   ALTER TABLE public.medication_order ALTER COLUMN order_id DROP DEFAULT;
       public          postgres    false    216    213            L           2604    20178    medication_order patient_id    DEFAULT     ?   ALTER TABLE ONLY public.medication_order ALTER COLUMN patient_id SET DEFAULT nextval('public.medication_order_patient_id_seq'::regclass);
 J   ALTER TABLE public.medication_order ALTER COLUMN patient_id DROP DEFAULT;
       public          postgres    false    217    213            M           2604    20179    medication_order drug_id    DEFAULT     ?   ALTER TABLE ONLY public.medication_order ALTER COLUMN drug_id SET DEFAULT nextval('public.medication_order_drug_id_seq'::regclass);
 G   ALTER TABLE public.medication_order ALTER COLUMN drug_id DROP DEFAULT;
       public          postgres    false    215    213            N           2604    20180    medication_order doctor_id    DEFAULT     ?   ALTER TABLE ONLY public.medication_order ALTER COLUMN doctor_id SET DEFAULT nextval('public.medication_order_doctor_id_seq'::regclass);
 I   ALTER TABLE public.medication_order ALTER COLUMN doctor_id DROP DEFAULT;
       public          postgres    false    214    213            E           2604    20181    nurse employee_id    DEFAULT     v   ALTER TABLE ONLY public.nurse ALTER COLUMN employee_id SET DEFAULT nextval('public.nurse_employee_id_seq'::regclass);
 @   ALTER TABLE public.nurse ALTER COLUMN employee_id DROP DEFAULT;
       public          postgres    false    219    205            F           2604    20182    nurse account_id    DEFAULT     t   ALTER TABLE ONLY public.nurse ALTER COLUMN account_id SET DEFAULT nextval('public.nurse_account_id_seq'::regclass);
 ?   ALTER TABLE public.nurse ALTER COLUMN account_id DROP DEFAULT;
       public          postgres    false    218    205            P           2604    20183    patient patient_id    DEFAULT     x   ALTER TABLE ONLY public.patient ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_patient_id_seq'::regclass);
 A   ALTER TABLE public.patient ALTER COLUMN patient_id DROP DEFAULT;
       public          postgres    false    221    220            G           2604    20184    pharmatech employee_id    DEFAULT     ?   ALTER TABLE ONLY public.pharmatech ALTER COLUMN employee_id SET DEFAULT nextval('public.pharmatech_employee_id_seq'::regclass);
 E   ALTER TABLE public.pharmatech ALTER COLUMN employee_id DROP DEFAULT;
       public          postgres    false    223    206            H           2604    20185    pharmatech account_id    DEFAULT     ~   ALTER TABLE ONLY public.pharmatech ALTER COLUMN account_id SET DEFAULT nextval('public.pharmatech_account_id_seq'::regclass);
 D   ALTER TABLE public.pharmatech ALTER COLUMN account_id DROP DEFAULT;
       public          postgres    false    222    206            Q           2604    20186    stock drug_id    DEFAULT     n   ALTER TABLE ONLY public.stock ALTER COLUMN drug_id SET DEFAULT nextval('public.stock_drug_id_seq'::regclass);
 <   ALTER TABLE public.stock ALTER COLUMN drug_id DROP DEFAULT;
       public          postgres    false    225    224            R           2604    20187    token account_id    DEFAULT     t   ALTER TABLE ONLY public.token ALTER COLUMN account_id SET DEFAULT nextval('public.token_account_id_seq'::regclass);
 ?   ALTER TABLE public.token ALTER COLUMN account_id DROP DEFAULT;
       public          postgres    false    227    226            ?          0    20085    account 
   TABLE DATA           N   COPY public.account (account_id, username, password, employee_id) FROM stdin;
    public          postgres    false    200   )?       ?          0    20095    doctor 
   TABLE DATA           9   COPY public.doctor (employee_id, account_id) FROM stdin;
    public          postgres    false    203   ?       ?          0    20119    drug 
   TABLE DATA           A   COPY public.drug (drug_id, drug_name, concentration) FROM stdin;
    public          postgres    false    210   B?       ?          0    20098    employee 
   TABLE DATA           D   COPY public.employee (employee_id, firstname, lastname) FROM stdin;
    public          postgres    false    204   4?                 0    20129    medication_order 
   TABLE DATA           ~   COPY public.medication_order (order_id, creation_date, expiration_date, quantity, patient_id, drug_id, doctor_id) FROM stdin;
    public          postgres    false    213   ?       ?          0    20104    nurse 
   TABLE DATA           8   COPY public.nurse (employee_id, account_id) FROM stdin;
    public          postgres    false    205   ??       	          0    20145    patient 
   TABLE DATA           l   COPY public.patient (patient_id, firstname, lastname, phone_number, admit_date, discharge_date) FROM stdin;
    public          postgres    false    220   ??       ?          0    20107 
   pharmatech 
   TABLE DATA           =   COPY public.pharmatech (employee_id, account_id) FROM stdin;
    public          postgres    false    206   E?                 0    20158    stock 
   TABLE DATA           N   COPY public.stock (quantity, threshold, drug_expiration, drug_id) FROM stdin;
    public          postgres    false    224   p?                 0    20163    token 
   TABLE DATA           2   COPY public.token (account_id, token) FROM stdin;
    public          postgres    false    226   ??       (           0    0    account_account_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.account_account_id_seq', 14, true);
          public          postgres    false    201            )           0    0    account_employee_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.account_employee_id_seq', 1, false);
          public          postgres    false    202            *           0    0    doctor_account_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.doctor_account_id_seq', 1, false);
          public          postgres    false    208            +           0    0    doctor_employee_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.doctor_employee_id_seq', 1, false);
          public          postgres    false    209            ,           0    0    drug_drug_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.drug_drug_id_seq', 13, true);
          public          postgres    false    211            -           0    0    employee_employee_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.employee_employee_id_seq', 15, true);
          public          postgres    false    212            .           0    0    medication_order_doctor_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.medication_order_doctor_id_seq', 1, false);
          public          postgres    false    214            /           0    0    medication_order_drug_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.medication_order_drug_id_seq', 1, false);
          public          postgres    false    215            0           0    0    medication_order_order_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.medication_order_order_id_seq', 9, true);
          public          postgres    false    216            1           0    0    medication_order_patient_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.medication_order_patient_id_seq', 1, false);
          public          postgres    false    217            2           0    0    nurse_account_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.nurse_account_id_seq', 1, false);
          public          postgres    false    218            3           0    0    nurse_employee_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.nurse_employee_id_seq', 1, false);
          public          postgres    false    219            4           0    0    patient_patient_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.patient_patient_id_seq', 13, true);
          public          postgres    false    221            5           0    0    pharmatech_account_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.pharmatech_account_id_seq', 1, false);
          public          postgres    false    222            6           0    0    pharmatech_employee_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.pharmatech_employee_id_seq', 1, false);
          public          postgres    false    223            7           0    0    stock_drug_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.stock_drug_id_seq', 1, false);
          public          postgres    false    225            8           0    0    token_account_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.token_account_id_seq', 1, false);
          public          postgres    false    227            T           2606    20189    account account_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_id);
 >   ALTER TABLE ONLY public.account DROP CONSTRAINT account_pkey;
       public            postgres    false    200            V           2606    20191    doctor doctor_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (employee_id);
 <   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
       public            postgres    false    203            ^           2606    20193    drug drug_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.drug
    ADD CONSTRAINT drug_pkey PRIMARY KEY (drug_id);
 8   ALTER TABLE ONLY public.drug DROP CONSTRAINT drug_pkey;
       public            postgres    false    210            X           2606    20195    employee employee_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (employee_id);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public            postgres    false    204            `           2606    20197 &   medication_order medication_order_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_pkey PRIMARY KEY (order_id);
 P   ALTER TABLE ONLY public.medication_order DROP CONSTRAINT medication_order_pkey;
       public            postgres    false    213            Z           2606    20199    nurse nurse_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_pkey PRIMARY KEY (employee_id);
 :   ALTER TABLE ONLY public.nurse DROP CONSTRAINT nurse_pkey;
       public            postgres    false    205            b           2606    20201    patient patient_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);
 >   ALTER TABLE ONLY public.patient DROP CONSTRAINT patient_pkey;
       public            postgres    false    220            \           2606    20203    pharmatech pharmatech_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_pkey PRIMARY KEY (employee_id);
 D   ALTER TABLE ONLY public.pharmatech DROP CONSTRAINT pharmatech_pkey;
       public            postgres    false    206            d           2606    20205    stock stock_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (drug_id);
 :   ALTER TABLE ONLY public.stock DROP CONSTRAINT stock_pkey;
       public            postgres    false    224            f           2606    20207    token token_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (account_id);
 :   ALTER TABLE ONLY public.token DROP CONSTRAINT token_pkey;
       public            postgres    false    226            g           2606    20208     account account_employee_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;
 J   ALTER TABLE ONLY public.account DROP CONSTRAINT account_employee_id_fkey;
       public          postgres    false    204    2904    200            h           2606    20213    doctor doctor_account_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_account_id_fkey;
       public          postgres    false    200    2900    203            i           2606    20218    doctor doctor_employee_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;
 H   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_employee_id_fkey;
       public          postgres    false    204    2904    203            n           2606    20223 0   medication_order medication_order_doctor_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(employee_id);
 Z   ALTER TABLE ONLY public.medication_order DROP CONSTRAINT medication_order_doctor_id_fkey;
       public          postgres    false    213    2902    203            o           2606    20228 .   medication_order medication_order_drug_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_drug_id_fkey FOREIGN KEY (drug_id) REFERENCES public.drug(drug_id);
 X   ALTER TABLE ONLY public.medication_order DROP CONSTRAINT medication_order_drug_id_fkey;
       public          postgres    false    210    213    2910            p           2606    20233 1   medication_order medication_order_patient_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.medication_order
    ADD CONSTRAINT medication_order_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient(patient_id);
 [   ALTER TABLE ONLY public.medication_order DROP CONSTRAINT medication_order_patient_id_fkey;
       public          postgres    false    220    2914    213            j           2606    20238    nurse nurse_account_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;
 E   ALTER TABLE ONLY public.nurse DROP CONSTRAINT nurse_account_id_fkey;
       public          postgres    false    205    2900    200            k           2606    20243    nurse nurse_employee_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;
 F   ALTER TABLE ONLY public.nurse DROP CONSTRAINT nurse_employee_id_fkey;
       public          postgres    false    2904    205    204            l           2606    20248 %   pharmatech pharmatech_account_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;
 O   ALTER TABLE ONLY public.pharmatech DROP CONSTRAINT pharmatech_account_id_fkey;
       public          postgres    false    2900    200    206            m           2606    20253 &   pharmatech pharmatech_employee_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.pharmatech
    ADD CONSTRAINT pharmatech_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(employee_id) ON DELETE CASCADE;
 P   ALTER TABLE ONLY public.pharmatech DROP CONSTRAINT pharmatech_employee_id_fkey;
       public          postgres    false    2904    206    204            q           2606    20258    stock stock_drug_id_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_drug_id_fkey FOREIGN KEY (drug_id) REFERENCES public.drug(drug_id);
 B   ALTER TABLE ONLY public.stock DROP CONSTRAINT stock_drug_id_fkey;
       public          postgres    false    210    2910    224            r           2606    20263    token token_account_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id);
 E   ALTER TABLE ONLY public.token DROP CONSTRAINT token_account_id_fkey;
       public          postgres    false    200    2900    226            ?   ?   x?E?Kn?0D?????Ir?n؈rԨ?@?Nr?rSd??Ð???\???&? E?R?)??z??Ƀ1ӌ?˒?l?*p??<}????????~A,B~?,?	?@??\?պ?f?'1??rɭ	ڲ?R?j1?q??&eÚ,Y????֪I????-߻-??xD>V{?׮o?a O???&j??x?&??????M˛?Lc?=???ADh?a?      ?   *   x??I  ?wFE¹^???n?Dair?)]?Ux??5?T??      ?   ?   x?m?AN?0E??)zH??eU?f$??b?????4U??IO?3?Y??????|???%???ha@M?@????0?ֈ]%?}??A"\UֱK????)????1⎋m\?b0?B???.?y?~??b?ŰQ{5fs
?uh???ο?#?4n??@M????鵇??f??&%a(??h?]s???*??V??^n?U??2d?z????=~??fX"?͞??m??a?wB?ǾgH      ?   ?   x???j?0E?w>?Ty??4$N??B7k"O?0?????????8?q?bk?KN?@?? ??Me?DK?&??J??V????8Y?569?*?e?s:Z?A??ʕ?7??????#?BD????|?	??*Vg????y|?YL???xј?(??[`?D??3cu?9?-?c???%2??$%9??SR?[?5^/??????????Q         ^   x???[
?@?o]E???<????udT_1? ?j??H????E????;??T???Y4?æH?~?6t??"?7?`???sG??ñ2?	?c6?      ?      x?3?4?24?44?24?44?????? !?      	   ?  x?m??n?0???)?&f??et??ڋ??Y.(?߾?????O????|?????4 102?Pv???٨?8????oi8?ݟoz^?8\ޫ?-a#?!6,?]???E~N?%? ???{}?$?Rh\l,??"????T?n??#ܔ|<??X?Ucw͝7???a??>?#?/i?N#??j9??\m??!?
#??s???|8v%??-??r??3??+|??9?v?#?Z?(?R???5td?g??@Bh???Ri????T?D?
??786XT{?;Y1??!O}??y?w?	?k.E?o9?(???w?u??yr???܁	a??װv????Bwy?i??y?.m-]??$:k|???z??]??0???c#d88/k?f????S??      ?      x?3?4???4?24?44?????? ?|         t   x?M???0??ޓЧ??_G?;???"v?.?|???0B?q?{a@Z??a?
v??f??CB/??g??ο?Z??S˺ǔ???E?y?[?j?鉷!7S?}?#?*X????ao$?         0   x?3???0Mwv,s.5p?/p-????K?u???vr??????? ?      