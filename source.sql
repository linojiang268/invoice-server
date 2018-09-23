-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
  id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  username character varying(32) NOT NULL, -- 用户名
  password character varying(64) NOT NULL, -- 密码
  role character varying(32) NOT NULL, -- 角色
  name character varying(32), -- 姓名
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT users_username_unique UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users
  OWNER TO postgres;
COMMENT ON COLUMN public.users.username IS '用户名';
COMMENT ON COLUMN public.users.password IS '密码';
COMMENT ON COLUMN public.users.role IS '角色';
COMMENT ON COLUMN public.users.name IS '姓名';

