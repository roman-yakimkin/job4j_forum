delete from "comment";
delete from post;
delete from user_roles;
delete from "user";
delete from role;

insert into role values
('ROLE_ADMIN', 'Forum administrator. Can create and edit any posts'),
('ROLE_POSTER', 'Forum poster. Can create and edit his posts'),
('ROLE_COMMENTER', 'Forum commenter. Can create and edit his comments');

insert into "user" (id, name, password) values
(1, 'root', '$2a$10$qrq4ciDIf479sS57iferPeLVOaoe6t8MYKcujnnYklJRbye7BVQ7C'),
(2, 'ivanov', '$2a$10$JzWHg6rnYKnXE3wF9N/VCu70n7JLEPpe7sgGBLUYzxWtuMm9.rxzO'),
(3, 'sidorov', '$2a$10$tOOPZPWs6xzJ6grmlp/3GOHgX.xczOALasnft1RpNLac11JwEPLWO');


insert into user_roles (user_id, role_id) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_POSTER'),
(2, 'ROLE_COMMENTER'),
(3, 'ROLE_COMMENTER');

insert into post (id, title, body, author_id) values
(1, 'Post 1', 'text1 text1 text1 text1 text1 text1', 1),
(2, 'Post 2', 'text2 text2 text2 text2 text2 text2', 1),
(3, 'Post 3', 'text1 text1 text1 text1 text1 text1', 2);

insert into comment (id, post_id, parent_id, author_id, body, depth) values (1, 1, null, (select id from "user" where name = 'sidorov'), 'comment1 comment1 comment1', 0);
insert into comment (id, post_id, parent_id, author_id, body, depth) values (2, 1, 1, 1, 'comment2 comment2 comment2', 1);
insert into comment (id, post_id, parent_id, author_id, body, depth) values (3, 1, 2, 2, 'comment3 comment3 comment3', 2);
insert into comment (id, post_id, parent_id, author_id, body, depth) values (4, 2, null, 2, 'comment4 comment4 comment4', 0);
insert into comment (id, post_id, parent_id, author_id, body, depth) values (5, 2, 4, 1, 'comment5 comment5 comment5', 1);
