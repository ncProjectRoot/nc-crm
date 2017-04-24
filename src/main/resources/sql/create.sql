-- Table: nc_crm.user_role
-- DROP TABLE nc_crm.user_role CASCADE;
CREATE TABLE nccrmdb.user_role
(
  id   BIGSERIAL              NOT NULL,
  name CHARACTER VARYING(100) NOT NULL
);

-- Table: nc_crm."user"
-- DROP TABLE nc_crm."user" CASCADE;
CREATE TABLE nccrmdb."user"
(
  id          BIGSERIAL              NOT NULL,
  email       CHARACTER VARYING(255) NOT NULL,
  password    CHARACTER VARYING(255) NOT NULL,
  first_name  CHARACTER VARYING(255) NOT NULL,
  last_name   CHARACTER VARYING(255) NOT NULL,
  middle_name CHARACTER VARYING(255),
  enable      BOOLEAN                NOT NULL,
  role_id     BIGINT                 NOT NULL
);


ALTER TABLE nccrmdb.user_role
  ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);

ALTER TABLE nccrmdb."user"
  ADD CONSTRAINT users_pkey PRIMARY KEY (id),
  ADD CONSTRAINT user_email_key UNIQUE (email),
  ADD CONSTRAINT user_role_id_fkey FOREIGN KEY (role_id)
REFERENCES nccrmdb.user_role (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

INSERT INTO nccrmdb.user_role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO nccrmdb.user_role (id, name) VALUES (2, 'ROLE_CUSTOMER');
INSERT INTO nccrmdb.user_role (id, name) VALUES (3, 'ROLE_CSR');
INSERT INTO nccrmdb.user_role (id, name) VALUES (4, 'ROLE_PMG');


CREATE TABLE nccrmdb.persistent_logins
(
    username character varying(64) NOT NULL,
    series character varying(64) NOT NULL,
    token character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL,
    CONSTRAINT persistent_logins_pkey PRIMARY KEY (series)
);