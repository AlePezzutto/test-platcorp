CREATE DATABASE platcorp;
CREATE USER usr_platcorp WITH PASSWORD 'platcorp2019';
GRANT ALL PRIVILEGES ON DATABASE platcorp TO usr_platcorp;
\c platcorp
create table public.client
(
     client_id   SERIAL       NOT NULL
    ,client_name VARCHAR(100) NOT NULL
    ,client_age  SMALLINT NOT NULL
    ,CONSTRAINT client_pk PRIMARY KEY (client_id)
    ,CONSTRAINT client_uk01 UNIQUE (client_name)
);
ALTER TABLE public.client OWNER to usr_platcorp;
create table public.client_info
(
     client_id INTEGER      NOT NULL
    ,create_dt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,ip_addr   VARCHAR(15)  NOT NULL
    ,now_temp  NUMERIC(4,2) NOT NULL
    ,min_temp  NUMERIC(4,2) NOT NULL
    ,max_temp  NUMERIC(4,2) NOT NULL
    ,PRIMARY KEY (client_id)
    ,FOREIGN KEY (client_id) REFERENCES client (client_id)
);
ALTER TABLE public.client_info OWNER to usr_platcorp;
