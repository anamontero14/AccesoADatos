
CREATE DATABASE Juvenil
GO
USE Juvenil
GO

--TABLA CREADA
CREATE TABLE socios(
DNI varchar(10) NOT NULL,
Nombre varchar(20) NOT NULL,
Direccion varchar(20),
penalizaciones int DEFAULT 2,
CONSTRAINT PK_socios PRIMARY KEY (DNI)
)

--DROP TABLE socios

--TABLA CREADA
CREATE TABLE libros(
RefLibro varchar(10) NOT NULL,
Nombre varchar(30) NOT NULL,
autor varchar(20) NOT NULL,
genero varchar(10),
añoPublicacion int,
editorial varchar(10),
CONSTRAINT PK_libros PRIMARY KEY (refLibro)
)

--DROP TABLE libros

--TABLA CREADA
CREATE TABLE prestamos(
DNI_Socios varchar(10) NOT NULL,
RefLibro_Libros varchar(10) NOT NULL,
FechaPrestamo date NOT NULL,
duracion int DEFAULT 24,
CONSTRAINT PK_prestamos PRIMARY KEY (fechaPrestamo, dni_Socios, refLibro_Libros),
CONSTRAINT FK_prestamos_socios FOREIGN KEY (dni_Socios) REFERENCES socios (DNI),
CONSTRAINT FK_prestamos_libros FOREIGN KEY (refLibro_Libros) REFERENCES libros (refLibro)
)

--DROP TABLE prestamos

INSERT INTO socios VALUES ('111-A', 'David',   'Sevilla Este', 2);
INSERT INTO socios VALUES ('222-B', 'Mariano', 'Los Remedios', 3);

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('333-C', 'Raul',    'Triana'      );

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('444-D', 'Rocio',   'La Oliva'    );

INSERT INTO socios VALUES ('555-E', 'Marilo',  'Triana',       2);
INSERT INTO socios VALUES ('666-F', 'Benjamin','Montequinto',  5);

INSERT INTO socios (DNI, Nombre, Direccion)
VALUES ('777-G', 'Carlos',  'Los Remedios');

INSERT INTO socios VALUES ('888-H', 'Manolo',  'Montequinto',  2);


INSERT INTO libros
VALUES('E-1', 'El valor de educar', 'Savater',    'Ensayo', 1994, 'Alfaguara');
INSERT INTO libros
VALUES('N-1', 'El Quijote',         'Cervantes',  'Novela', 1602, 'Anagrama');
INSERT INTO libros
VALUES('E-2', 'La Republica',       'Plat�n',     'Ensayo', -230, 'Anagrama');
INSERT INTO libros
VALUES('N-2', 'Tombuctu',           'Auster',     'Novela', 1998, 'Planeta');
INSERT INTO libros
VALUES('N-3', 'Todos los nombres',  'Saramago',   'Novela', 1995, 'Planeta');
INSERT INTO libros
VALUES('E-3', 'Etica para Amador',  'Savater',    'Ensayo', 1991, 'Alfaguara');
INSERT INTO libros
VALUES('P-1', 'Rimas y Leyendas',   'Becquer',    'Poesia', 1837, 'Anagrama');
INSERT INTO libros
VALUES('P-2', 'Las flores del mal', 'Baudelaire', 'Poesia', 1853, 'Anagrama');
INSERT INTO libros
VALUES('P-3', 'El fulgor',          'Valente',    'Poesia', 1998, 'Alfaguara');
INSERT INTO libros
VALUES('N-4', 'Lolita',             'Nabokov',    'Novela', 1965, 'Planeta');
INSERT INTO libros
VALUES('C-1', 'En salvaje compa�ia','Rivas',      'Cuento', 2001, 'Alfaguara');


INSERT INTO prestamos VALUES('111-A','E-1', '17/12/00',24);
INSERT INTO prestamos VALUES('333-C','C-1', '15/12/01',48);
INSERT INTO prestamos VALUES('111-A','N-1', '17/12/01',24);
INSERT INTO prestamos VALUES('444-D','E-1', '17/12/01',48);
--INSERT INTO prestamos VALUES('111-A','C-2', '17/12/01',72);

INSERT INTO prestamos (DNI_Socios, RefLibro_Libros, FechaPrestamo) 
VALUES('777-G','N-1', '07/12/01');

INSERT INTO prestamos VALUES('111-A','N-1', '16/12/01',48);