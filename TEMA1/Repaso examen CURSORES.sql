GO
USE Juvenil
GO

/*
1- Realiza un procedimiento llamado listadocuatromasprestados que nos muestre
por pantalla un listado de los cuatro libros m�s prestados y los socios a 
los que han sido prestados con el siguiente formato:

NombreLibro1	NumPrestamosLibro1 	GeneroLibro1
	DNISocio1	FechaPrestamoalSocio1
	...
	DNISocion	FechaPrestamoalSocion

El procedimiento debe gestionar adecuadamente las siguientes excepciones:

La tabla Libros est� vac�a.
La tabla Socios est� vac�a.
Hay menos de cuatro libros que hayan sido prestados.
*/

SELECT * FROM libros
SELECT * FROM prestamos

CREATE OR ALTER PROCEDURE listadocuatromasprestados
AS
BEGIN

	--almacenar� el nombre del libro seleccionado
	DECLARE @nombreLibro varchar(20)
	--numero de veces que el libro ha sido prestado
	DECLARE @numVecesLibroPrestado int
	--genero del libro que se presta
	DECLARE @generoLibroPrestado varchar(20)
	--dni del socio
	DECLARE @dniSocio varchar(10)
	--fecha en la que se prest� el libro al socio
	DECLARE @fechaPrestamo date

	--me creo un cursor el cual recorrer� los libros prestados
	DECLARE cursorLibros CURSOR FOR
	/*El cursor devolver� en cada iteraci�n el nombre del libro prestado,
	el numero de veces que se ha prestado el cual se sabe debido a contar
	el n�mero de veces que aparece la referencia del libro en la tabla y
	tambi�n el g�nero del libro*/
	SELECT L.Nombre, COUNT(P.RefLibro_Libros), L.genero FROM prestamos AS P
	INNER JOIN libros AS L ON L.RefLibro = P.RefLibro_Libros
	GROUP BY L.Nombre, L.genero

	--abro el cursor
	OPEN cursorLibros
	--hago la primera iteraci�n
	FETCH cursorLibros INTO @nombreLibro, @numVecesLibroPrestado, @generoLibroPrestado

	--empiezo con el cursor
	WHILE (@@FETCH_STATUS = 0)
	BEGIN
		--creo otro cursor para que este itere sobre los socios
		DECLARE cursorSocios CURSOR FOR
		/*Este cursor selecciona el dni del socio que ha tomado prestado
		dicho libro y tambi�n la fecha en la que se prest� el libro*/
		SELECT S.DNI, P.FechaPrestamo FROM socios AS S
		INNER JOIN prestamos AS P ON P.DNI_Socios = S.DNI
		/*y con la condicion de que la referencia del libro que se est� prestando
		tiene que ser igual a la referencia que salga de agarrar el nombre del libro
		e igualarlo al nombre del libro actual*/
		WHERE P.RefLibro_Libros = (SELECT RefLibro FROM libros WHERE Nombre = @nombreLibro)
	
		--abro el cursor de los socios
		OPEN cursorSocios
		--agarro la primera iteracion
		FETCH cursorSocios INTO @dniSocio, @fechaPrestamo

		--printeo el nombre del libro, el numero de veces que ha sido prestado y su genero
		PRINT 'Nombre del libro			N�mero de veces que se ha prestado			G�nero del libro'
		PRINT @nombreLibro + '						' + CAST(@numVecesLibroPrestado AS varchar(20)) + '					' + @generoLibroPrestado

		PRINT 'DNI del socio			Fecha del pr�stamo'

		--hago otro bucle while para recorrer los socios
		WHILE (@@FETCH_STATUS = 0)
		BEGIN

			PRINT @dniSocio + '			' + CAST(@fechaPrestamo AS varchar(20))

			--salto a la siguiente iteraci�n (al siguiente socio)
			FETCH cursorSocios INTO @dniSocio, @fechaPrestamo

		END

		--siguiente iteraci�n del cursor para los libros prestados
		FETCH cursorLibros INTO @nombreLibro, @numVecesLibroPrestado, @generoLibroPrestado

		--cierro el cursor de los socios
		CLOSE cursorSocios
		DEALLOCATE cursorSocios

	END

	--cierro el cursor de los libros prestados
	CLOSE cursorLibros
	DEALLOCATE cursorLibros

END

EXEC listadocuatromasprestados
