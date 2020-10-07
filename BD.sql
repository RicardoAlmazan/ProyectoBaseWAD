drop database if exists 3cm9;
create database 3cm9;
use 3cm9;

create table Evento (
	idEvento int not null primary key auto_increment,
    nombreEvento varchar(50) not null,
    sede varchar(50) not null,
    fechaInicio date,
    fechaTermino date
);