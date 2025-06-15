drop database if exists facturacion;
create database facturacion;

use facturacion;

create table cliente(
	idCliente integer primary key AUTO_INCREMENT,
    nombre varchar(100),
    direccion varchar(250)
);

create table telefono(
	idTelefono integer primary key AUTO_INCREMENT,
    idCliente integer,
    descripcion varchar(100),
    numero varchar(20),
    foreign key (idCliente) references cliente(idCliente)
);

create table factura(
	idFactura integer primary key AUTO_INCREMENT,
    idCliente integer,
    descripcion varchar(500),
    precio double,
    fecha Date,
    foreign key (idCliente) references cliente(idCliente)
);


insert into cliente values (1, 'Leire', 'Rua Nova, 3');
insert into cliente values (2, 'Tomas', 'Rua Nova, 8');
insert into cliente values (3, 'Antonio', 'Preguntoiro, 1');

insert into telefono values (1, 1, 'fixo', '98111111');
insert into telefono values (2, 1, 'movil', '695111111');
insert into telefono values (3, 1, 'outro', '111111111');
insert into telefono values (4, 2, 'movil', '222222222');
insert into telefono values (5, 2, 'fixo', '981333333');
insert into telefono values (6, 3, 'movil', '695333333');

insert into factura values (1, 1, 'Software', 1000, '2021-11-05');
insert into factura values (2, 1, 'Curso', 2000,'2021-12-06');
insert into factura values (3, 2, 'Certificado', 200, '2021-12-15');
insert into factura values (4, 2, 'Material', 4000, '2022-01-05');
insert into factura values (5, 3, 'Curso', 2000, '2022-01-10');

