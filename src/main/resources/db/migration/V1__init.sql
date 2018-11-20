-- PostgreSQL
----------------------------------
-- Create Sequences

-- SEQUENCE: agileboard.app_role_id_seq

-- DROP SEQUENCE agileboard.app_role_id_seq;

CREATE SEQUENCE agileboard.app_role_id_seq;

ALTER SEQUENCE agileboard.app_role_id_seq
    OWNER TO agileboard;

-- SEQUENCE: agileboard.app_user_id_seq

-- DROP SEQUENCE agileboard.app_user_id_seq;

CREATE SEQUENCE agileboard.app_user_id_seq;

ALTER SEQUENCE agileboard.app_user_id_seq
    OWNER TO agileboard;

-- SEQUENCE: agileboard.initiative_id_seq

-- DROP SEQUENCE agileboard.initiative_id_seq;

CREATE SEQUENCE agileboard.initiative_id_seq;

ALTER SEQUENCE agileboard.initiative_id_seq
    OWNER TO agileboard;

-- SEQUENCE: agileboard.project_id_seq

-- DROP SEQUENCE agileboard.project_id_seq;

CREATE SEQUENCE agileboard.project_id_seq;

ALTER SEQUENCE agileboard.project_id_seq
    OWNER TO agileboard;

-- SEQUENCE: agileboard.story_id_seq

-- DROP SEQUENCE agileboard.story_id_seq;

CREATE SEQUENCE agileboard.story_id_seq;

ALTER SEQUENCE agileboard.story_id_seq
    OWNER TO agileboard;


-- Create Tables
-- Table: agileboard.app_role

-- DROP TABLE agileboard.app_role;

CREATE TABLE agileboard.app_role
(
    id bigint NOT NULL DEFAULT nextval('agileboard.app_role_id_seq'::regclass),
    name character varying(60) COLLATE pg_catalog."default",
    CONSTRAINT app_role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pgeynh3x611j4la2wf4l7v4ux UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.app_role
    OWNER to agileboard;

-- Table: agileboard.app_user

-- DROP TABLE agileboard.app_user;

CREATE TABLE agileboard.app_user
(
    id bigint NOT NULL DEFAULT nextval('agileboard.app_user_id_seq'::regclass),
    email character varying(40) COLLATE pg_catalog."default",
    name character varying(40) COLLATE pg_catalog."default",
    password character varying(100) COLLATE pg_catalog."default",
    username character varying(15) COLLATE pg_catalog."default",
    project_id bigint,
    CONSTRAINT app_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk1j9d9a06i600gd43uu3km82jw UNIQUE (email),
    CONSTRAINT uk3k4cplvh82srueuttfkwnylq0 UNIQUE (username)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.app_user
    OWNER to agileboard;

-- Table: agileboard.initiative

-- DROP TABLE agileboard.initiative;

CREATE TABLE agileboard.initiative
(
    id bigint NOT NULL DEFAULT nextval('agileboard.initiative_id_seq'::regclass),
    details character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    owner_id bigint,
    CONSTRAINT initiative_pkey PRIMARY KEY (id),
    CONSTRAINT fkar3xbcqsc1c5ushhmkvvth841 FOREIGN KEY (owner_id)
        REFERENCES agileboard.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.initiative
    OWNER to agileboard;

-- Table: agileboard.project

-- DROP TABLE agileboard.project;

CREATE TABLE agileboard.project
(
    id bigint NOT NULL DEFAULT nextval('agileboard.project_id_seq'::regclass),
    created_on timestamp without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    created_by_id bigint,
    CONSTRAINT project_pkey PRIMARY KEY (id),
    CONSTRAINT fkm0gp6kcj0jsegmrif0ykecumb FOREIGN KEY (created_by_id)
        REFERENCES agileboard.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.project
    OWNER to agileboard;

ALTER TABLE agileboard.app_user ADD CONSTRAINT fkr8pct29vtlv3m6kda52xmc0oh FOREIGN KEY (project_id)
        REFERENCES agileboard.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

-- Table: agileboard.story

-- DROP TABLE agileboard.story;

CREATE TABLE agileboard.story
(
    id bigint NOT NULL DEFAULT nextval('agileboard.story_id_seq'::regclass),
    details text COLLATE pg_catalog."default",
    name character varying(250) COLLATE pg_catalog."default",
    points integer,
    status character varying(255) COLLATE pg_catalog."default",
    story_number character varying(255) COLLATE pg_catalog."default",
    owner_id bigint,
    project_id bigint NOT NULL,
    CONSTRAINT story_pkey PRIMARY KEY (id),
    CONSTRAINT uk_2dnnv9e9kc7hlhic8kl4kjb7y UNIQUE (story_number),
    CONSTRAINT fk2vejtb1bsy4tuw1kafwi1viw1 FOREIGN KEY (owner_id)
        REFERENCES agileboard.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmsqatheg9j5hb2vi429cmvgis FOREIGN KEY (project_id)
        REFERENCES agileboard.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.story
    OWNER to agileboard;

-- Table: agileboard.user_roles

-- DROP TABLE agileboard.user_roles;

CREATE TABLE agileboard.user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk6fql8djp64yp4q9b3qeyhr82b FOREIGN KEY (user_id)
        REFERENCES agileboard.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkn3br4qteai89bj7n137xmrngi FOREIGN KEY (role_id)
        REFERENCES agileboard.app_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE agileboard.user_roles
    OWNER to agileboard;
	

-----------------------------------------
-- Insert Roles

INSERT INTO agileboard.app_role(
	id, name)
	VALUES (1, 'ROLE_USER');
	
INSERT INTO agileboard.app_role(
	id, name)
	VALUES (2, 'ROLE_ADMIN');
	
