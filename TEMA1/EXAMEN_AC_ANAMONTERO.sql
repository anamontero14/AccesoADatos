/*

CREATE DATABASE cursores2526NombreAlumno

GO
USE cursores2526NombreAlumno
GO

CREATE TABLE EMPLEADOS (
 IdEmpleado VARCHAR(10) PRIMARY KEY,
 Nombre VARCHAR(30) NOT NULL,
 Ciudad VARCHAR(20),
 PuntosFidelidad INT DEFAULT 0
);
CREATE TABLE PRODUCTOS (
 CodProducto VARCHAR(10)PRIMARY KEY,
 Nombre VARCHAR(30) NOT NULL,
 Categoria VARCHAR(20),
 Precio DECIMAL(8,2),
 Stock INT
);
CREATE TABLE VENTAS (
 IdEmpleado VARCHAR(10) ,
 CodProducto VARCHAR(10),
 FechaVenta DATE NOT NULL,
 Cantidad INT DEFAULT 1,
 primary key (idEmpleado, CodProducto, fechaVenta),
 FOREIGN KEY (idEmpleado) REFERENCES EMPLEADOS(idEmpleado),
 FOREIGN KEY (CodProducto) REFERENCES PRODUCTOS(CodProducto)
);
INSERT INTO EMPLEADOS VALUES
('C01', 'Laura Pérez', 'Madrid', 150),
('C02', 'Juan Gómez', 'Barcelona', 80),
('C03', 'Ana López', 'Sevilla', 120),
('C04', 'Pedro Ruiz', 'Valencia', 60),
('C05', 'Marta Díaz', 'Bilbao', 40);
INSERT INTO PRODUCTOS VALUES
('P01', 'Camiseta Roja', 'Ropa', 19.99, 50),
('P02', 'Pantalón Jeans', 'Ropa', 39.99, 30),
('P03', 'Zapatillas Running', 'Calzado', 59.99, 20),
('P04', 'Sudadera Negra', 'Ropa', 29.99, 25),
('P05', 'Mochila Deportiva', 'Accesorios', 24.99, 15);
INSERT INTO VENTAS VALUES
('C01', 'P01', '2024-10-01', 2),
('C02', 'P01', '2024-10-02', 1),
('C03', 'P02', '2024-10-03', 1),
('C04', 'P02', '2024-10-04', 3),
('C05', 'P03', '2024-10-05', 1),
('C01', 'P03', '2024-10-06', 2),
('C02', 'P03', '2024-10-07', 1),
('C03', 'P04', '2024-10-08', 1),
('C01', 'P05', '2024-10-09', 1);

DROP DATABASE cursores2526NombreAlumno
*/

/*
Crea un procedimiento almacenado llamado
ListadoTresMasVendidos
que muestre por pantalla un listado con los tres productos más vendidos, junto con los empleados
que los vendieron y la fecha de cada venta, con el siguiente formato:
NombreProducto1 NumVentasProducto1 CategoriaProducto1
 IdEmpleado1 NombreEmpleado1 FechaVenta1
 IdEmpleado2 NombreEmpleado2 FechaVenta2
 ...
 IdEmpleadon NombreEmpleadon FechaVentan
NombreProducto2 NumVentasProducto2 CategoriaProducto2
 IdEmpleado1 NombreEmpleado1 FechaVenta1
 IdEmpleado2 NombreEmpleado2 FechaVenta2
 ...
 IdEmpleadon NombreEmpleadon FechaVentan
NombreProducto3 NumVentasProducto3 CategoriaProducto3
 IdEmpleado1 NombreEmpleado1 FechaVenta1
 IdEmpleado2 NombreEmpleado2 FechaVenta2
 ...
 IdEmpleadon NombreEmpleado3 FechaVentan

 El procedimiento debe manejar las siguientes excepciones:
a) La tabla Productos está vacía.
b) La tabla Empleados está vacía.
c) Hay menos de tres ventas realizadas (debe mostrar un mensaje adecuado)
*/

SELECT * FROM PRODUCTOS
SELECT * FROM EMPLEADOS
SELECT * FROM VENTAS

CREATE OR ALTER PROCEDURE ListadoTresMasVendidos
AS
BEGIN
	
	--VARIABLES QUE SE USARÁN PARA EL PRIMER CURSOR
	--variable que almacenará el nombre del producto
	DECLARE @nombreProducto varchar(20)
	--variable que almacenará el numero de ventas por producto
	DECLARE @numVentasProducto int
	--variable que almacena la categoría del producto
	DECLARE @categoriaProducto varchar(20)

	--VARIABLES QUE SE USARÁN PARA EL SEGUNDO CURSOR
	--almacenará el id del empleado
	DECLARE @idEmpleado varchar(20)
	--almacenará el nombre del empleado
	DECLARE @nombreEmpleado varchar(20)
	--almacenará la fecha de la venta
	DECLARE @fechaVenta date

	--creo el cursor que iterará sobre la tabla de ventas
	DECLARE cursorVentas CURSOR FOR
	SELECT TOP 3 P.Nombre, COUNT(V.CodProducto) AS 'Cantidad de unidades vendidas', P.Categoria FROM VENTAS AS V 
	INNER JOIN PRODUCTOS AS P ON V.CodProducto = P.CodProducto
	GROUP BY P.Nombre, P.Categoria
	ORDER BY COUNT(V.CodProducto) DESC

	--si al contar los registros de la tabla productos el resultado es 0
	--significa que estará vacía
	IF ((SELECT COUNT(*) FROM PRODUCTOS) = 0)
		BEGIN

			PRINT 'La tabla "PRODUCTOS" está vacía'

		END
	--ejecuta el mismo control sobre la tala empleados para asegurarse de que no está vacía
	ELSE IF((SELECT COUNT(*) FROM EMPLEADOS) = 0)
		BEGIN
			
			PRINT 'La tabla "EMPLEADOS" está vacía'

		END
	ELSE
	--LA TABLA PRODUCTOS NO ESTÁ VACÍA
		BEGIN
			
			IF ((SELECT COUNT(*) FROM VENTAS) < 3)
			BEGIN
			
				PRINT 'AVISO: Hay menos de 3 productos vendidos'

			END
		
			--abro el cursor
			OPEN cursorVentas
			--primera iteracion
			FETCH cursorVentas INTO @nombreProducto, @numVentasProducto, @categoriaProducto
	
			--CURSOR EMPLEADOS Y VENTAS
			WHILE (@@FETCH_STATUS = 0)
			BEGIN
		
				PRINT @nombreProducto + '		' + CAST(@numVentasProducto AS VARCHAR(20)) + '			' + @categoriaProducto

				--CREO EL SIGUIENTE CURSOR
				DECLARE cursorEmpleados CURSOR FOR
				SELECT V.IdEmpleado, E.Nombre, V.FechaVenta FROM EMPLEADOS AS E
				INNER JOIN VENTAS AS V ON E.IdEmpleado = V.IdEmpleado
				WHERE V.CodProducto = (SELECT CodProducto FROM PRODUCTOS WHERE Nombre = @nombreProducto)

				--abro el cursor que iterará sobre los empleados que han ido vendiendo los productos
				OPEN cursorEmpleados
				--hago la primera iteración
				FETCH cursorEmpleados INTO @idEmpleado, @nombreEmpleado, @fechaVenta

				--empiezo a recorrer
				WHILE(@@FETCH_STATUS = 0)
				BEGIN
					--printeo el resultado de la iteracion
					PRINT @idEmpleado + '					' + @nombreEmpleado + '	' + CAST(@fechaVenta AS VARCHAR(20))

					--salto al siguiente registro
					FETCH cursorEmpleados INTO @idEmpleado, @nombreEmpleado, @fechaVenta

				END

				--cierro el cursor
				CLOSE cursorEmpleados
				DEALLOCATE cursorEmpleados

				FETCH cursorVentas INTO @nombreProducto, @numVentasProducto, @categoriaProducto

			END

			--cierro el cursor
			CLOSE cursorVentas
			DEALLOCATE cursorVentas
		END

END

EXEC ListadoTresMasVendidos

SELECT * FROM PRODUCTOS
SELECT * FROM EMPLEADOS
SELECT * FROM VENTAS

/*
BEGIN TRANSACTION

DELETE FROM VENTAS

ROLLBACK*/