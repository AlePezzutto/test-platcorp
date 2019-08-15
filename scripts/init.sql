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
create table public.permission
(
     id          BIGSERIAL    NOT NULL
    ,description VARCHAR(255) DEFAULT NULL
    ,PRIMARY KEY (id)
);
ALTER TABLE public.permission OWNER to usr_platcorp;
create table public.users
(
     id        BIGSERIAL     NOT NULL
    ,user_name VARCHAR(255)  DEFAULT NULL
    ,full_name VARCHAR(255)  DEFAULT NULL
    ,password  VARCHAR(255)  DEFAULT NULL
    ,account_non_expired     BOOLEAN DEFAULT NULL
    ,account_non_locked      BOOLEAN DEFAULT NULL
    ,credentials_non_expired BOOLEAN DEFAULT NULL
    ,enabled                 BOOLEAN DEFAULT NULL
    ,PRIMARY KEY (id)
    ,UNIQUE (user_name)
);
ALTER TABLE public.users OWNER to usr_platcorp;
create table public.user_permission
(
     id_user        BIGINT NOT NULL
    ,id_permission  BIGINT NOT NULL
    ,PRIMARY KEY (id_user, id_permission)
    ,FOREIGN KEY (id_user) REFERENCES users (id)
    ,FOREIGN KEY (id_permission) REFERENCES permission (id)
);
ALTER TABLE public.user_permission OWNER to usr_platcorp;
insert into public.permission (description) VALUES ('ADMIN');
insert into public.permission (description) VALUES ('MANAGER');
insert into public.permission (description) VALUES ('COMMON_USER');
insert into public.users (
    user_name, 
    full_name, 
    password, 
    account_non_expired, 
    account_non_locked, 
    credentials_non_expired,
    enabled
) values (
    'poirot', 
    'Hercule Poirot', 
    '$2a$16$9qr2tv0HmXbHBsx.TZFjfux742wCZM32a8Wi6iBqwIqaizlHPuxHS', 
    TRUE, 
    TRUE, 
    TRUE, 
    TRUE
);
insert into public.users (
    user_name, 
    full_name, 
    password, 
    account_non_expired, 
    account_non_locked, 
    credentials_non_expired,
    enabled
) values (
    'sherlock', 
    'Sherlock Holmes', 
    '$2a$16$h4yDQCYTy62R6xrtFDWONeMH3Lim4WQuU/aj8hxW.dJJoeyvtEkhK', 
    TRUE, 
    TRUE, 
    TRUE, 
    TRUE
);
insert into public.user_permission (id_user, id_permission) values (1, 1);
insert into public.user_permission (id_user, id_permission) values (1, 2);
insert into public.user_permission (id_user, id_permission) values (2, 1);
