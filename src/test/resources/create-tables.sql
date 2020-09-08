drop table if exists comment;
drop table if exists post;
drop table if exists user_roles;
drop table if exists "user";
drop table if exists role;

alter sequence if exists comment_id_sql restart with 1;
alter sequence if exists user_id_sql restart with 1;
alter sequence if exists post_id_sql restart with 1;

create table role (
    id              varchar(50) unique not null,
    description     varchar(2000) not null
);

create table "user" (
    id              serial primary key,
    name            varchar(50),
    password        varchar(500),
    enabled         boolean default true
);

create table user_roles (
    user_id         int references "user"(id) not null ,
    role_id         varchar(50) references role(id) not null
);

create table post (
     id              serial primary key,
     title           varchar(2000) not null,
     body            text,
     created         timestamp without time zone not null default now(),
     changed         timestamp without time zone not null default now(),
     author_id       int references "user"(id) not null
);

create table comment (
     id              serial primary key,
     post_id         int references post(id) not null ,
     parent_id       int references comment(id) default 0,
     author_id       int references "user"(id) not null,
     body            text not null,
     created         timestamp without time zone not null default now(),
     changed         timestamp without time zone not null default now(),
     depth           int not null default 0
);