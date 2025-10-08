GO
USE SCOTT
GO
/*Ejercicio 1

Haz una función llamada DevolverCodDept que reciba el nombre de un departamento y devuelva su código.*/

CREATE or ALTER FUNCTION dbo.fn_DevolverCodDept 
(@NombreDep varchar(20))
RETURNS int
AS
BEGIN
	DECLARE @codigo int

	SET @codigo = (SELECT DEPTNO FROM DEPT WHERE DNAME = @NombreDep)

	RETURN @codigo
END

SELECT * FROM DEPT

SELECT dbo.fn_DevolverCodDept ('ACCOUNTING') as 'Código departamento'

/*Ejercicio 2

Realiza un procedimiento llamado HallarNumEmp que recibiendo 
un nombre de departamento, muestre en pantalla el número de empleados de 
dicho departamento. Puedes utilizar la función creada en el ejercicio 1.

Si el departamento no tiene empleados deberá mostrar un mensaje informando 
de ello. Si el departamento no existe se tratará la excepción correspondiente.*/

CREATE OR ALTER PROCEDURE HallarNumEmp 
@nombreDep varchar(20)
AS
BEGIN
	
	DECLARE @numDep int
	DECLARE @numEmp int

	--coge el numero de departamento
	SET @numDep = (SELECT dbo.fn_DevolverCodDept (@nombreDep))

	--coge el numero de empleados
	SET @numEmp = (SELECT COUNT(EMPNO) FROM EMP WHERE DEPTNO = @numDep)

	IF (@numDep IS NULL)
	BEGIN
		PRINT 'El departamento no existe'
	END
	ELSE
	BEGIN	
		IF (@numEmp = 0)
		BEGIN
			PRINT 'El departamento especificado no consta de empleados'
		END
		ELSE
		BEGIN
			SELECT COUNT(EMPNO) AS 'Nº de empleados' FROM EMP WHERE DEPTNO = @numDep
		END
	END

END

EXECUTE HallarNumEmp 'ACCOUNTING'

SELECT * FROM DEPT
SELECT * FROM EMP

/*Ejercicio 3

Realiza una función llamada CalcularCosteSalarial que reciba un 
nombre de departamento y devuelva la suma de los salarios y comisiones 
de los empleados de dicho departamento. Trata las excepciones que consideres necesarias.*/

CREATE OR ALTER FUNCTION CalcularCosteSalarial
(@nombreDept varchar(20))
RETURNS TABLE 
AS
RETURN (
	SELECT SUM(SAL) AS 'Suma salarios', ISNULL(SUM(COMM), 0) AS 'Suma comisiones' FROM EMP AS E
	INNER JOIN DEPT AS D ON E.DEPTNO = D.DEPTNO
	WHERE @nombreDept = D.DNAME
) 

select * from EMP
SELECT * FROM DEPT

SELECT [Suma salarios] FROM dbo.CalcularCosteSalarial ('ACCOUNTING')

/*Ejercicio 4 Cr

Realiza un procedimiento MostrarCostesSalariales que muestre los nombres de 
todos los departamentos y el coste salarial de cada uno de ellos. Puedes usar 
la función del ejercicio 3.*/

CREATE OR ALTER PROCEDURE MostrarCostesSalariales
AS
BEGIN
    DECLARE @nombreDept varchar(20)
    DECLARE @costeSalarial decimal(10,2)
    
    DECLARE dept_cursor CURSOR FOR
    SELECT DNAME FROM DEPT
    
    OPEN dept_cursor
    FETCH NEXT FROM dept_cursor INTO @nombreDept
    
    WHILE (@@FETCH_STATUS = 0)
    BEGIN
        SELECT @costeSalarial = (SELECT [Suma salarios] FROM dbo.CalcularCosteSalarial(@nombreDept))
        
        PRINT 'Departamento: ' + @nombreDept + ' - Coste Salarial: ' + CAST(@costeSalarial AS varchar(20))
        
        FETCH NEXT FROM dept_cursor INTO @nombreDept
    END
    
    CLOSE dept_cursor
    DEALLOCATE dept_cursor
END

EXECUTE MostrarCostesSalariales

/*Ejercicio 5

Realiza un procedimiento MostrarAbreviaturas que muestre las tres primeras letras del nombre de cada empleado.*/

CREATE OR ALTER PROCEDURE MostrarAbreviaturas
AS
BEGIN

	SELECT LEFT(ENAME, 3) AS 'Iniciales de empleado' FROM EMP

END

SELECT * FROM EMP

EXECUTE MostrarAbreviaturas

/*Ejercicio 6 cr 

Realiza un procedimiento MostrarMasAntiguos que muestre el nombre del 
empleado más antiguo de cada departamento junto con el nombre del departamento. 
Trata las excepciones que consideres necesarias*/

CREATE OR ALTER PROCEDURE MostrarMasAntiguos
AS
BEGIN
	
	--declaro las dos variables que voy a necesitar
	--para ir recorriendo los departamentos
	DECLARE @nombreDept varchar(40)
	--para ir cogiendo el nombre del empleado
	DECLARE @nombreEmp varchar(20)

	--declaración del cursor
	DECLARE cursor4 CURSOR FOR
	--que va a recorrer los nombres de los departamentos
	SELECT DNAME FROM DEPT

	--se abre el cursor
	OPEN cursor4 
	--en la siguiente iteración vuelca la información en el nombre
	FETCH NEXT FROM cursor4 INTO @nombreDept

	WHILE (@@FETCH_STATUS = 0)
	BEGIN

		--escojo el nombre del empleado correspondiente
		SET @nombreEmp = (SELECT TOP 1 E.ENAME FROM EMP AS E INNER JOIN DEPT AS D ON E.DEPTNO = D.DEPTNO WHERE D.DNAME = @nombreDept ORDER BY E.HIREDATE ASC)

		PRINT 'Nombre de empleado más antiguo: ' + @nombreEmp + ' | Nombre del departamento: ' + @nombreDept

		--paso a la siguiente iteracion
		FETCH NEXT FROM cursor4 INTO @nombreDept

	END

	--cierro el cursor
	CLOSE cursor4
	DEALLOCATE cursor4

END

/*SELECT E.ENAME, D.DNAME, E.HIREDATE FROM EMP AS E INNER JOIN DEPT AS D ON E.DEPTNO = D.DEPTNO ORDER BY E.HIREDATE ASC*/

EXECUTE MostrarMasAntiguos --bien

/*Ejercicio 7 CR 

Realiza un procedimiento MostrarJefes que reciba el nombre de un departamento 
y muestre los nombres de los empleados de ese departamento que son jefes de otros 
empleados.Trata las excepciones que consideres necesarias.*/

CREATE OR ALTER PROCEDURE MostrarJefes
@nombreDept varchar(20)
AS
BEGIN

	DECLARE @nombreEmp varchar(20)
	DECLARE @numEmpleado int

	DECLARE cursor5 CURSOR FOR
	SELECT EMPNO FROM EMP AS E
	INNER JOIN DEPT AS D ON E.DEPTNO = D.DEPTNO
	WHERE D.DNAME = @nombreDept

	OPEN cursor5
	FETCH NEXT FROM cursor5 INTO @numEmpleado

	WHILE (@@FETCH_STATUS = 0)
	BEGIN

		SET @nombreEmp = (SELECT DISTINCT TOP 1
							E.ENAME
							FROM EMP AS E
							INNER JOIN DEPT AS D
							ON E.DEPTNO = D.DEPTNO
							INNER JOIN EMP AS EM
							ON EM.MGR = E.EMPNO
							WHERE E.EMPNO = @numEmpleado)

		PRINT 'Nombre del empleado: ' + @nombreEmp + ' | Nombre del departamento: ' + @nombreDept

		FETCH NEXT FROM cursor5 INTO  @numEmpleado

	END

	CLOSE cursor5
	DEALLOCATE cursor5

END

EXECUTE MostrarJefes 'ACCOUNTING'

/*SELECT DISTINCT
E.ENAME,
E.MGR AS 'JEFE'
FROM EMP AS E
INNER JOIN DEPT AS D
ON E.DEPTNO = D.DEPTNO
INNER JOIN EMP AS EM
ON EM.MGR = E.EMPNO
WHERE D.DNAME = 'ACCOUNTING' --sustituir eso*/

SELECT * FROM EMP
SELECT * FROM DEPT

/*Ejercicio 8

Realiza un procedimiento MostrarMejoresVendedores que muestre los nombres de 
los dos vendedores con más comisiones. Trata las excepciones que consideres necesarias.*/

CREATE OR ALTER PROCEDURE MostrarMejoresVendedores
AS
BEGIN

	SELECT TOP 2
	ENAME,
	ISNULL(COMM, 0)
	FROM EMP
	ORDER BY COMM DESC

END

EXECUTE MostrarMejoresVendedores

/*Ejercicio 10
Realiza un procedimiento RecortarSueldos que recorte el sueldo un 20% a los empleados 
cuyo nombre empiece por la letra que recibe como parámetro.Trata las excepciones 
que consideres necesarias*/

CREATE OR ALTER PROCEDURE RecortarSueldos
@letra varchar(1)
AS
BEGIN
		
	--MOSTRAR ANTES DE LOS CAMPOS
	SELECT ENAME, SAL FROM EMP

	--ACTUALIZAR EL SALARIO
	UPDATE EMP SET SAL = (SAL - (SAL * 0.2)) WHERE LEFT(ENAME, 1) = UPPER(@letra)

	--MOSTRAR LOS CAMBIOS
	SELECT ENAME, SAL FROM EMP

END

SELECT * FROM EMP

EXECUTE RecortarSueldos 's'

/*Ejercicio 11 cr
 
Realiza un procedimiento BorrarBecarios que borre a los dos empleados más nuevos de 
cada departamento. Trata las excepciones que consideres necesarias.*/

CREATE OR ALTER PROCEDURE BorrarBecarios
AS
BEGIN
	
	DECLARE @numEmp int
	DECLARE @numDep int

	DECLARE cursorDep CURSOR FOR
	SELECT DEPTNO FROM DEPT

	OPEN cursorDep
	FETCH NEXT FROM cursorDep INTO @numDep

	WHILE (@@FETCH_STATUS = 0)
	BEGIN
		
		DECLARE cursorEmp CURSOR FOR
		SELECT TOP 2 EMPNO FROM EMP WHERE EMPNO = @numEmp ORDER BY HIREDATE DESC

		OPEN cursorEmp
		FETCH NEXT FROM cursorEmp INTO @numEmp

			WHILE (@@FETCH_STATUS = 0)
			BEGIN
				PRINT @numEmp

				FETCH NEXT FROM cursorEmp INTO @numEmp
			END	

		FETCH NEXT FROM cursorDep INTO @numDep

	END

	CLOSE cursorEmp
	DEALLOCATE cursorEmp

	CLOSE cursorDep
	DEALLOCATE cursorDep

	--DELETE FROM [nombre tabla] WHERE [condición]

END

EXECUTE BorrarBecarios

SELECT * FROM DEPT