GO
USE Juvenil
GO

/*
1- Realiza un procedimiento llamado listadocuatromasprestados que nos muestre
por pantalla un listado de los cuatro libros más prestados y los socios a 
los que han sido prestados con el siguiente formato:

NombreLibro1	NumPrestamosLibro1 	GeneroLibro1
	DNISocio1	FechaPrestamoalSocio1
	...
	DNISocion	FechaPrestamoalSocion

El procedimiento debe gestionar adecuadamente las siguientes excepciones:

La tabla Libros está vacía.
La tabla Socios está vacía.
Hay menos de cuatro libros que hayan sido prestados.
*/

CREATE OR ALTER PROCEDURE listadocuatromasprestados
AS
BEGIN
	
	--declaracion de variables que servirán para almacenar los datos
	DECLARE @numLibro varchar(10)
	DECLARE @nombreLibro varchar(20)
	DECLARE @generoLibro varchar(20)
	DECLARE @dni varchar(10)
	DECLARE @fechaPrestamoSocio date

	--el cursor iterará sobre los 4 libros más prestados empezando por el que se ha prestado más y acabando por el que se ha prestado menos de los 4
	DECLARE cursorLibros CURSOR FOR
	--coge el nombre, el numero del libro y el genero de la tabla de libro
	SELECT TOP 4 L.Nombre, L.RefLibro, L.genero FROM prestamos AS P 
	INNER JOIN libros AS L ON L.RefLibro = P.RefLibro_Libros
	GROUP BY L.RefLibro, L.Nombre, L.genero
	ORDER BY COUNT(P.RefLibro_Libros) DESC

	--se abre el cursor
	OPEN cursorLibros
	FETCH NEXT FROM cursorLibros INTO @nombreLibro, @numLibro, @generoLibro
	--se imprime el nombre de las categorías a mostrar
	PRINT 'Nombre del libro		' + 'Número del libro prestado		' + 'Género del libro'

	WHILE (@@FETCH_STATUS = 0)
	BEGIN

	--se declara un nuevo cursor que iterará sobre los socios que han tomado prestado el libro
	DECLARE cursorSocios CURSOR FOR
	SELECT DNI_Socios, FechaPrestamo FROM prestamos WHERE RefLibro_Libros = @numLibro

	--se abre el cursor del socio
	OPEN cursorSocios
	FETCH NEXT FROM cursorSocios INTO @dni, @fechaPrestamoSocio

	--muestro los datos que se han pedido
	PRINT CAST(@nombreLibro AS varchar(20)) + '					' + CAST(@numLibro AS varchar(20)) + '						' + CAST(@generoLibro AS varchar(20))
	
	--indico las siguientes categorías a mostrar
	PRINT 'DNI del socio			  ' + 'Fecha de préstamo'

		WHILE (@@FETCH_STATUS = 0)
		BEGIN
			
			--muestro los datos
			PRINT CAST(@dni AS varchar(20)) + '						' + CAST(@fechaPrestamoSocio AS varchar(20))
			
			--paso al siguiente socio
			FETCH NEXT FROM cursorSocios INTO @dni, @fechaPrestamoSocio

		END
		--paso al siguiente libro
		FETCH NEXT FROM cursorLibros INTO @nombreLibro, @numLibro, @generoLibro

		--cierro el cursor de los socios
		CLOSE cursorSocios
		DEALLOCATE cursorSocios
	END

	--cierro el cursor de los libros
	CLOSE cursorLibros
	DEALLOCATE cursorLibros
END

EXEC listadocuatromasprestados

SELECT * FROM prestamos
SELECT * FROM libros

SELECT
L.Nombre,
L.RefLibro,
COUNT(P.RefLibro_Libros) AS 'Número de veces que se ha prestado'
FROM prestamos AS P
INNER JOIN libros AS L ON L.RefLibro = P.RefLibro_Libros
GROUP BY L.RefLibro, L.Nombre
ORDER BY COUNT(P.RefLibro_Libros) DESC

SELECT
L.RefLibro
FROM prestamos AS P
INNER JOIN libros AS L ON L.RefLibro = P.RefLibro_Libros
GROUP BY L.RefLibro
ORDER BY COUNT(P.RefLibro_Libros) DESC


/*
Partiendo del siguiente script, crea la BD correspondiente a los alumnos matriculados en algunos de los módulos de 1º y 2º curso del CFS y sus correspondientes notas:

REM ******** TABLAS ALUMNOS, ASIGNATURAS, NOTAS: ***********

DROP TABLE ALUMNOS cascade constraints;

-- Crear base de datos
CREATE DATABASE INSTITUTO;
GO
USE INSTITUTO;
GO

-- Crear tabla ALUMNOS
CREATE TABLE ALUMNOS (
    DNI VARCHAR(10) NOT NULL PRIMARY KEY,
    APENOM VARCHAR(50),
    DIREC VARCHAR(50),
    POBLA VARCHAR(30),
    TELEF VARCHAR(15)
);
GO

-- Crear tabla ASIGNATURAS
CREATE TABLE ASIGNATURAS (
    COD INT NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR(50)
);
GO

-- Crear tabla NOTAS
CREATE TABLE NOTAS (
    DNI VARCHAR(10) NOT NULL,
    COD INT NOT NULL,
    NOTA INT,
    CONSTRAINT PK_NOTAS PRIMARY KEY (DNI, COD),
    CONSTRAINT FK_NOTAS_ALUMNOS FOREIGN KEY (DNI) REFERENCES ALUMNOS(DNI),
    CONSTRAINT FK_NOTAS_ASIGNATURAS FOREIGN KEY (COD) REFERENCES ASIGNATURAS(COD)
);
GO

-- Insertar asignaturas
INSERT INTO ASIGNATURAS (COD, NOMBRE) VALUES
(1,'Prog. Leng. Estr.'),
(2,'Sist. Informáticos'),
(3,'Análisis'),
(4,'FOL'),
(5,'RET'),
(6,'Entornos Gráficos'),
(7,'Aplic. Entornos 4ªGen');
GO

-- Insertar alumnos
INSERT INTO ALUMNOS (DNI, APENOM, DIREC, POBLA, TELEF) VALUES
('12344345','Alcalde García, Elena', 'C/Las Matas, 24','Madrid','917766545'),
('04448242','Cerrato Vela, Luis', 'C/Mina 28 - 3A', 'Madrid','916566545'),
('56882942','Díaz Fernández, María', 'C/Luis Vives 25', 'Móstoles','915577545');
GO

INSERT INTO ALUMNOS (DNI, APENOM, DIREC, POBLA, TELEF) VALUES
('78294561','Gómez Ruiz, Andrés','Av. de los Pinos, 12','Madrid','912345678'),
('65983247','Martínez López, Carla','C/Mayor, 7','Alcalá de Henares','918765432'),
('33427895','Santos Prieto, Javier','C/Toledo, 14','Getafe','916112233'),
('90871234','Fernández León, Laura','Av. Europa, 33','Leganés','917889900'),
('11234567','Ramírez Ortega, Pablo','C/Sol, 22','Madrid','915443322'),
('77889955','Moreno Díaz, Isabel','C/Gran Vía, 45','Madrid','917221144'),
('44556677','Navarro Sánchez, Diego','C/Real, 10','Fuenlabrada','919887766'),
('99887766','Torres Gil, Ana','Av. Andalucía, 55','Móstoles','918332211'),
('55667788','Ruiz Castillo, Sergio','C/Velázquez, 18','Madrid','917556644'),
('66778899','Vega Hernández, Lucía','C/Prado, 3','Alcorcón','916334455');

-- Insertar notas
INSERT INTO NOTAS (DNI, COD, NOTA) VALUES
('12344345', 1, 6),
('12344345', 2, 5),
('12344345', 3, 6),
('04448242', 4, 6),
('04448242', 5, 8),
('04448242', 6, 4),
('04448242', 7, 5),
('56882942', 5, 7),
('56882942', 6, 8),
('56882942', 7, 9);
GO

INSERT INTO NOTAS (DNI, COD, NOTA) VALUES
('78294561', 1, 7),
('78294561', 2, 6),
('78294561', 3, 8),
('78294561', 4, 7),

('65983247', 1, 9),
('65983247', 5, 8),
('65983247', 6, 7),

('33427895', 2, 5),
('33427895', 3, 6),
('33427895', 7, 7),

('90871234', 1, 8),
('90871234', 4, 9),
('90871234', 5, 8),

('11234567', 3, 6),
('11234567', 5, 5),
('11234567', 7, 6),

('77889955', 2, 8),
('77889955', 3, 9),
('77889955', 6, 8),

('44556677', 1, 5),
('44556677', 4, 6),
('44556677', 5, 7),

('99887766', 5, 8),
('99887766', 6, 9),
('99887766', 7, 8),

('55667788', 1, 7),
('55667788', 2, 8),
('55667788', 3, 9),

('66778899', 4, 10),
('66778899', 5, 9),
('66778899', 7, 8);



COMMIT;

Diseña un procedimiento al que pasemos como parámetro de entrada el nombre de uno de los módulos existentes en la BD y 
visualice el nombre de los alumnos que lo han cursado junto a su nota.
Al final del listado debe aparecer el nº de suspensos, aprobados, notables y sobresalientes.
Asimismo, deben aparecer al final los nombres y notas de los alumnos que tengan la nota más alta y la más baja.
Debes comprobar que las tablas tengan almacenada información y que exista el módulo cuyo nombre pasamos como parámetro al procedimiento.
*/

GO
USE INSTITUTO
GO

CREATE OR ALTER PROCEDURE VisualizarAlumnos
@nombreModulo varchar(50)
AS
BEGIN

	--variable en la que se irá almacenando el nombre del alumno
	DECLARE @nombreAlum varchar(50)
	--variable en la que se irá almacenando la nota del alumno
	DECLARE @notaAlum int
	--almacena el numero de suspensos
	DECLARE @suspensos int = 0
	--almacena el numero de aprobados
	DECLARE @aprobados int = 0
	--almacena el numero de notables
	DECLARE @notables int = 0
	--almacena el numero de sobresalientes
	DECLARE @sobresalientes int = 0
	--almacena la nota mas alta
	DECLARE @notaMasAlta int = 0
	--almacena el alumno con la nota mas alta
	DECLARE @nombreAlumnoNotaAlta varchar(20)

	/*Declaro un cursor el cual irá iterando sobre los alumnos que estén en el
	módulo asignatura que se indique por parámetros, devolviendo siempre el nombre del
	alumno y su nota (siempre y cuando concuerde con el nombre del modulo)*/
	DECLARE cursorAlumnos CURSOR FOR
	SELECT A.APENOM, N.NOTA FROM ALUMNOS AS A 
	INNER JOIN NOTAS AS N ON N.DNI = A.DNI INNER JOIN ASIGNATURAS AS ASI ON N.COD = ASI.COD
	WHERE ASI.NOMBRE = @nombreModulo

	/*Se abre el cursor y en cada iteración el nombre y la nota se guardarán
	en las variables especificadas*/
	OPEN cursorAlumnos
	FETCH NEXT FROM cursorAlumnos INTO @nombreAlum, @notaAlum

	WHILE (@@FETCH_STATUS = 0)
	BEGIN

		PRINT 'Nombre del alumno: ' + @nombreAlum + '| Nota del alumno: ' + CAST(@notaAlum AS varchar(20))

		--si es un suspenso
		IF (@notaAlum < 5)
		BEGIN
			--se suma un suspenso
			SET @suspensos += 1
		END
		--si es un aprobado
		ELSE IF (@notaAlum = 5 OR @notaAlum = 6)
		BEGIN
			--se suma un aprobado
			SET @aprobados += 1
		END
		--si es un notable
		ELSE IF (@notaAlum = 7 OR @notaAlum = 8)
		BEGIN
			--se suma un notable
			SET @notables += 1
		END
		--si es un sobresaliente
		ELSE IF (@notaAlum > 8)
		BEGIN
			--se suma un sobresaliente
			SET @sobresalientes += 1
			--el sobresaliente será la nota mas alta
			SET @notaMasAlta = @notaAlum
			SET @nombreAlumnoNotaAlta = @nombreAlum

		END

		FETCH NEXT FROM cursorAlumnos INTO @nombreAlum, @notaAlum

	END

	CLOSE cursorAlumnos
	DEALLOCATE cursorAlumnos

	PRINT 'Nº de suspensos: ' + CAST(@suspensos AS VARCHAR(20))
	PRINT 'Nº de aprobados: ' + CAST(@aprobados AS VARCHAR(20))
	PRINT 'Nº de notables: ' + CAST(@notables AS VARCHAR(20))
	PRINT 'Nº de sobresalientes: ' + CAST(@sobresalientes AS VARCHAR(20))
	PRINT 'Alumno con la nota más alta: ' + @nombreAlumnoNotaAlta + ' con un ' + CAST(@notaMasAlta AS VARCHAR(20))
END

EXEC VisualizarAlumnos 'Aplic. Entornos 4ªGen'

SELECT * FROM ALUMNOS
SELECT * FROM ASIGNATURAS
SELECT * FROM NOTAS

/*
A partir de las tablas creadas con el siguiente script:

-- Crear la base de datos
CREATE DATABASE Tienda;
GO

-- Usar la base de datos creada
USE Tienda;
GO

-- Crear tabla de productos
CREATE TABLE productos
(
    CodProducto     VARCHAR(10) NOT NULL PRIMARY KEY,
    Nombre          VARCHAR(50) NOT NULL,
    LineaProducto   VARCHAR(20),
    PrecioUnitario  INT,
    Stock           INT
);
GO

-- Crear tabla de ventas
CREATE TABLE ventas
(
    CodVenta         VARCHAR(10) NOT NULL PRIMARY KEY,
    CodProducto      VARCHAR(10) NOT NULL,
    FechaVenta       DATE,
    UnidadesVendidas INT,
    CONSTRAINT FK_Ventas_Productos 
        FOREIGN KEY (CodProducto) REFERENCES productos(CodProducto)
);
GO

-- Insertar productos
INSERT INTO productos (CodProducto, Nombre, LineaProducto, PrecioUnitario, Stock) VALUES
('1','Procesador P133', 'Proc',15000,20),
('2','Placa base VX',   'PB', 18000,15),
('3','Simm EDO 16Mb',   'Memo', 7000,30),
('4','Disco SCSI 4Gb',  'Disc',38000, 5),
('5','Procesador K6-2', 'Proc',18500,10),
('6','Disco IDE 2.5Gb', 'Disc',20000,25),
('7','Procesador MMX',  'Proc',15000, 5),
('8','Placa Base Atlas','PB', 12000, 3),
('9','DIMM SDRAM 32Mb', 'Memo',17000,12);
GO

-- Insertar ventas
INSERT INTO ventas (CodVenta, CodProducto, FechaVenta, UnidadesVendidas) VALUES
('V1', '2', '1997-09-22',2),
('V2', '4', '1997-09-22',1),
('V3', '6', '1997-09-23',3),
('V4', '5', '1997-09-26',5),
('V5', '9', '1997-09-28',3),
('V6', '4', '1997-09-28',1),
('V7', '6', '1997-10-02',2),
('V8', '6', '1997-10-02',1),
('V9', '2', '1997-10-04',4),
('V10','9', '1997-10-04',4),
('V11','6', '1997-10-05',2),
('V12','7', '1997-10-07',1),
('V13','4', '1997-10-10',3),
('V14','4', '1997-10-16',2),
('V15','3', '1997-10-18',3),
('V16','4', '1997-10-18',5),
('V17','6', '1997-10-22',2),
('V18','6', '1997-11-02',2),
('V19','2', '1997-11-04',3),
('V20','9', '1997-12-04',3);
GO

DROP DATABASE Tienda

*/
/*
a) Realiza un procedimiento que actualice la columna 
Stock de la tabla Productos a partir de los registros de la tabla Ventas.

El citado procedimiento debe informarnos por pantalla si alguna 
de las tablas está vacía o si el stock de un producto pasa a 
ser negativo, en cuyo caso se parará la actualización de ese producto.

a.1) Suponemos que se han realizado una serie de Ventas (todos 
los registros añadidos en la tabla Ventas), así debemos realizar 
un procedimiento para actualizar la tabla Productos con las ventas 
realizadas que están en la tabla Ventas.
*/

GO
USE Tienda
GO

SELECT * FROM productos
SELECT * FROM ventas

CREATE OR ALTER PROCEDURE ActualizarStock
AS
BEGIN

	--variable para almacenar el codigo del producto
	DECLARE @codigoProducto int
	--unidades vendidas
	DECLARE @unidadesVendidas int
	--stock actual
	DECLARE @stock int
	--variable para almacenar el numero de productos que hay que eliminar del stock
	DECLARE @stockActualizado int = 0

	--crear un cursor
	DECLARE cursorProductos CURSOR FOR
	SELECT P.CodProducto ,V.UnidadesVendidas, P.Stock FROM productos AS P
	INNER JOIN ventas AS V ON V.CodProducto = P.CodProducto
	--abro el cursor
	OPEN cursorProductos
	FETCH NEXT FROM cursorProductos INTO @codigoProducto, @unidadesVendidas, @stock

	WHILE (@@FETCH_STATUS = 0)
	BEGIN

		--actualizo el stock y lo guardo en la variable
		SET @stockActualizado = @stock - @unidadesVendidas

		--printeo para comprobar
		PRINT 'Stock actual: ' + CAST(@stock AS varchar(20))
		PRINT 'Stock actualizado: ' + CAST(@stockActualizado AS varchar(20)) + '	' + CAST(@stock AS varchar(20)) + ' - ' + CAST(@unidadesVendidas AS varchar(20))

		--actualizo la tabla en si
		UPDATE productos SET Stock = @stockActualizado WHERE @codigoProducto = CodProducto

		--siguiente producrto
		FETCH NEXT FROM cursorProductos INTO @codigoProducto, @unidadesVendidas, @stock

		--vuelvo a ponerlo a 0
		SET @stockActualizado = 0

	END

	CLOSE cursorProductos
	DEALLOCATE cursorProductos

END

EXEC ActualizarStock

/*
a.2) Mediante Triggers: Tenemos la tabla Ventas y Productos, debemos 
actualizar la tabla Productos con las modificaciones o inserciones que 
se hagan en la tabla Ventas de la siguiente forma:
- Si se aumentan las unidades vendidas de una venta ya realizada (me 
pasarán el código de la venta, el código del producto y las unidades 
vendidas), se deberá actualizar el Stock de la tabla Productos.
- Si se realiza una devolución de una venta (me pasan el código de la 
venta, el código del producto y las unidades devueltas), se 
deberá actualizar el Stock de la tabla Productos. Hay que tener 
en cuenta que si se devuelven todas las unidades que habían sido 
vendidas, se deberá borrar esa venta de la tabla Ventas.
*/


select * from ventas

CREATE OR ALTER TRIGGER Prueba
    ON Ventas
    FOR UPDATE
    AS
    BEGIN
    SET NOCOUNT ON

	IF UPDATE(UnidadesVendidas)
	BEGIN

		UPDATE p
        SET p.Stock = p.Stock - (i.UnidadesVendidas - d.UnidadesVendidas)
        FROM Productos p
        INNER JOIN inserted i ON p.CodProducto = i.CodProducto
        INNER JOIN deleted d ON d.CodProducto = i.CodProducto

	END

END


/*b) Realiza un procedimiento que presente por pantalla un listado de las ventas con el siguiente formato:

Linea Producto: NombreLinea1
	
	Prod11		UnidadesTotales1	ImporteTotal1
 	Prod12		UnidadesTotales2	ImporteTotal2
	…
	Prod1n		UnidadesTotalesn	ImporteTotaln

Importe total NombreLinea1: ImporteLinea1

Linea Producto: NombreLinea2
	
	Prod21		UnidadesTotales1	ImporteTotal1
 	Prod22		UnidadesTotales2	ImporteTotal2
	…
	Prod2n		UnidadesTotalesn	ImporteTotaln

Importe total NombreLinea2: ImporteLinea2
.
.
.
Total Ventas: Importedetodaslaslineas
*/

SELECT * FROM ventas
SELECT * FROM productos

CREATE OR ALTER PROCEDURE listadoVentas
AS
BEGIN

	--variable que almacene la linea del producto
	DECLARE @lineaProducto varchar(20)
	--nombre del producto actual
	DECLARE @nombreProducto varchar(20)
	--unidades totales que se han vendido del producto actual
	DECLARE @unidadesTotales int
	--importe total del producto actual
	DECLARE @importeTotalProducto int
	--importe en total de todas las lineas
	DECLARE @importeTotalLineas int = 0

	--declaro el cursor que iterará en la tabla de ventas
	DECLARE cursorVentas CURSOR FOR
	/*seleccionará la línea de producto, su nombre, el número de unidades
	vendidas por producto y el importe de dichas unidades vendidas que será el precio
	por unidad de cada producto multiplicado por el numero total de unidades vendidas*/
	SELECT P.LineaProducto, P.Nombre, SUM(V.UnidadesVendidas) AS 'Nº unidades vendidas', (P.PrecioUnitario * SUM(V.UnidadesVendidas)) AS 'Importe total' FROM ventas AS V
	INNER JOIN productos AS P ON P.CodProducto = V.CodProducto
	GROUP BY P.LineaProducto, P.Nombre, P.PrecioUnitario

	--abro el cursor
	OPEN cursorVentas
	--hago la primera iteración
	FETCH cursorVentas INTO @lineaProducto, @nombreProducto, @unidadesTotales, @importeTotalProducto

	--empiezo a recorrer toda la tabla con el while
	WHILE (@@FETCH_STATUS = 0)
	BEGIN
		
		PRINT 'Línea producto: ' + @lineaProducto
		--casteo las variables que contienen numeros porque pueden llegar a dar errores
		PRINT 'Nombre del producto			Unidades totales			Importe total'
		PRINT @nombreProducto + '						' + CAST(@unidadesTotales AS varchar(20)) + '						' + CAST(@importeTotalProducto AS varchar(20))

		--voy sumandole a la variable el importe total por producto para calcular el importe total de todos los productos vendidos
		SET @importeTotalLineas += @importeTotalProducto

		--salto al siguiente producto
		FETCH cursorVentas INTO @lineaProducto, @nombreProducto, @unidadesTotales, @importeTotalProducto

	END

	PRINT 'Total ventas: ' + CAST(@importeTotalLineas AS varchar(20))

	--cierro el cursor
	CLOSE cursorVentas
	DEALLOCATE cursorVentas

END

EXEC listadoVentas