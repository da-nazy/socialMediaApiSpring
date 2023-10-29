drop database socialmediadb;
drop user socialmedia;
create user socialmedia with password 'password';
create database socialmediadb with template=template0 owner=socialmedia;
\connect socialmediadb;
alter default privileges grant all on tables to socialmedia;
alter default privileges grant all on sequences to socialmedia;

create table users(
user_id integer primary key not null,
user_name varchar(20) not null,
profile_picture varchar(20) not null,
followers text[] null,
followings text[] null,
email varchar(30) not null,
password text not null
);
create table posts(
post_id integer primary key not null,
user_id integer not null,
title varchar(20) not null,
content varchar(50) not null,
 creation_date bigint not null
);

alter table posts add constraint post_users_fk
foreign key (user_id) references users(user_id);

create table comments(
 comment_id integer primary key not null,
 post_id integer not null,
 user_id integer not null,
 content varchar(50) not null,
 creation_date bigint not null
);

alter table comments add constraint comments_post_fk
foreign key (post_id) references posts(post_id);
alter table comments add constraint comments_users_fk
foreign key (user_id) references users(user_id);

create sequence users_seq increment 1 start 1;
create sequence comments_seq increment 1 start 1;
create sequence posts_seq increment 1 start 1;