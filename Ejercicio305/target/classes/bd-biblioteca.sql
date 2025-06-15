drop database if exists biblioteca;
create database biblioteca;

use biblioteca;

create table libro (
	idLibro int primary key AUTO_INCREMENT,
    codigo varchar(20),
    titulo varchar(200),
    autores varchar(300),
    año int
);

create table cliente (
	idCliente int primary key AUTO_INCREMENT,
    DNI char(9),
    nombre varchar(200),
    email varchar(200)
);

create table alquiler (
	idAlquiler int primary key AUTO_INCREMENT,
    idLibro int,
	idCliente int,
    fecha Date,
    alquilado bool,
    foreign key (idLibro) references libro(idLibro),
    foreign key (idCliente) references cliente(idCliente)
);

insert into libro(codigo, titulo, autores, año)
values 	('asdfg', 'Pride and Prejudice', 'Jane Austen',1870),
		('asrwqeg', 'Persuation', 'Jane Austen',1875),
		('asdfdsa', 'El Quijote', 'Miguel de Cervantes',1700);

insert into cliente(DNI, nombre, email)
values 	('11111111A', 'Ana', 'ana@gmail.com'),
		('22222222B', 'Pablo', 'pablo@gmail.com');

insert into alquiler(idLibro, idCliente, fecha, alquilado)
values 	(1,1,'2013-11-05', false),
		(2,1,'2018-02-05', false),
		(3,1,'2022-12-10', true),
		(2,2,'2022-11-05', true),
		(3,2,'2020-11-05', false);