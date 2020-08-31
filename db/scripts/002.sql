delete from "comment";
delete from post;
delete from user_roles;
delete from "user";
delete from role;

insert into role values
    ('ROLE_ADMIN', 'Forum administrator. Can create and edit any posts'),
    ('ROLE_POSTER', 'Forum poster. Can create and edit his posts'),
    ('ROLE_COMMENTER', 'Forum commenter. Can create and edit his comments');

insert into "user" (name, password) values
    ('root', 'password'),
    ('ivanov', '123456'),
    ('sidorov', '1234');


insert into user_roles (user_id, role_id) values
    ((select id from "user" where name = 'root'), 'ROLE_ADMIN'),
    ((select id from "user" where name = 'ivanov'), 'ROLE_POSTER'),
    ((select id from "user" where name = 'ivanov'), 'ROLE_COMMENTER'),
    ((select id from "user" where name = 'sidorov'), 'ROLE_COMMENTER');

insert into post (title, body, author_id) values
    ('Post 1', 'text1 text1 text1 text1 text1 text1', (select id from "user" where name = 'root')),
    ('Post 2', 'text2 text2 text2 text2 text2 text2', (select id from "user" where name = 'root')),
    ('Post 3', 'text1 text1 text1 text1 text1 text1', (select id from "user" where name = 'ivanov'));

insert into comment (post_id, parent_id, author_id, body, depth) values ((select id from post where title = 'Post 1'), null, (select id from "user" where name = 'sidorov'), 'comment1 comment1 comment1', 0);
insert into comment (post_id, parent_id, author_id, body, depth) values ((select id from post where title = 'Post 1'), (select id from "comment" where body = 'comment1 comment1 comment1'), (select id from "user" where name = 'root'), 'comment2 comment2 comment2', 1);
insert into comment (post_id, parent_id, author_id, body, depth) values ((select id from post where title = 'Post 1'), (select id from "comment" where body = 'comment2 comment2 comment2'), (select id from "user" where name = 'ivanov'), 'comment3 comment3 comment3', 2);
insert into comment (post_id, parent_id, author_id, body, depth) values ((select id from post where title = 'Post 2'), null, (select id from "user" where name = 'ivanov'), 'comment4 comment4 comment4', 0);
insert into comment (post_id, parent_id, author_id, body, depth) values ((select id from post where title = 'Post 2'), (select id from "comment" where body = 'comment4 comment4 comment4'), (select id from "user" where name = 'root'), 'comment5 comment5 comment5', 1);

