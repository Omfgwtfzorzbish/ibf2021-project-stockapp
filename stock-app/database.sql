drop database if exists stockapp;
create database stockapp;
use stockapp;
create table user(
username char(20) not null,
password varchar(64) not null,
email varchar(50) not null,
primary key(username));

create table portfolio(
username char(20) not null,
ticker char(10) not null,
price float,
position int,
date_added date not null,
primary key(ticker),
	constraint fk_username
		foreign key (username)
			references user(username));


