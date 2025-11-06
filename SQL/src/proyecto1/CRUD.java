package proyecto1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Método conectar el cuál establecerá una conexión entre el entorno y la BBDD
	 * 
	 * @return un booleano para determinar si la operación fue o no exitosa
	 */
	public static boolean conectar() {

		// variable que almacenará el estado de la conexión
		boolean exito = false;

		// se gestiona con un try catch el estado de la conexión, si este es correcto
		// devulve true, si no false
		try (Connection conexion = Conexion.obtenerConexion()) {
			exito = true;
		} catch (SQLException e) {
			exito = false;
		}

		// se devuelve la variable
		return exito;
	}

	/**
	 * Método para crear todas las tablas de la BBDD
	 * 
	 * @return Devolverá true si se han podido crear correctamente y false si ha
	 *         habido una excepción y por lo tanto no se pudieron crear
	 */
	public static boolean crearTablas() {

		// variable que gestionará si las tablas se crearon o no correctamente
		boolean exito = false;

		Connection conexion = null;
		Statement stmt = null;
		// variable que almacenará la sentencia sql
		String sentenciaSQL = "";

		try {
			conexion = Conexion.obtenerConexion();
			stmt = conexion.createStatement();

			// se ejecutan todas las sentencias sql
			sentenciaSQL = "CREATE TABLE Player" + crearTablaPlayer();
			stmt.execute(sentenciaSQL);
			sentenciaSQL = "CREATE TABLE Games" + crearTablaGames();
			stmt.execute(sentenciaSQL);
			sentenciaSQL = "CREATE TABLE Compras" + crearTablaCompras();
			stmt.execute(sentenciaSQL);

			exito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exito;

	}

	/**
	 * Método que sirve para crear las tablas una a una, se creará únicamente el
	 * nombre de la tabla que se le meta por parámetros
	 * 
	 * @param nombreTabla = El nombre de la tabla que se creará
	 */
	public static boolean crearTablasSeparadas(String nombreTabla) {

		// variable que gestionará si las tablas se crearon o no correctamente
		boolean exito = false;

		Connection conexion = null;
		Statement stmt = null;
		// variable que almacenará la sentencia sql
		String sentenciaSQL = "";

		try {
			// Paso 1.Previamente habremos realizado la conexión
			conexion = Conexion.obtenerConexion();
			// Paso 2. Creamos un nuevo objeto con la conexión
			stmt = conexion.createStatement();

			// si el parámetro es igual a la tabla player entonces esta se crea
			if (nombreTabla.equalsIgnoreCase("Player")) {
				// sentencia sql que se almacena en la variable
				sentenciaSQL = "CREATE TABLE " + nombreTabla + crearTablaPlayer();
			} else if (nombreTabla.equalsIgnoreCase("Games")) {
				// sentencia sql que se almacena en la variable
				sentenciaSQL = "CREATE TABLE " + nombreTabla + crearTablaPlayer();
			} else if (nombreTabla.equalsIgnoreCase("Compras")) {

				// si la tabla esa existe
				if (existenciaTabla("Player") && existenciaTabla("Games")) {
					// Paso 3. Definimos la sentencia de crear una nueva base de datos
					sentenciaSQL = "CREATE TABLE " + nombreTabla + crearTablaPlayer();
				}
			}

			// Paso 4. Ejecutar la sentencia
			stmt.execute(sentenciaSQL);

			// si llega aquí significa que las tablas se pudieron crear
			exito = true;

		} catch (SQLException se) {
			// Gestionamos los posibles errores que puedan surgir durante la ejecucion de la
			// insercion
			se.printStackTrace();
		}

		return exito;

	}

	/**
	 * Método que sirve para comprobar si una tabla existe o no
	 * 
	 * @param nombreTabla = El nombre de la tabla a comprobar
	 * @return true si la tabla existe y false si la tabla no existe
	 */
	private static boolean existenciaTabla(String nombreTabla) {

		// variable que almacenará si la tabla existe o no
		boolean existe = false;

		Connection conexion;
		try {
			conexion = Conexion.obtenerConexion();
			// Desde la conexión, obtienes el objeto DatabaseMetaData
			DatabaseMetaData metaData = conexion.getMetaData();
			ResultSet tablas = metaData.getTables(null, null, nombreTabla, new String[] { "TABLE" });

			// si la tabla que se le pasa por parámetros existe entonces devuelve true
			if (tablas.next()) {
				existe = true;
			}

			tablas.close();

		} catch (SQLException e) {
		}

		return existe;

	}

	/**
	 * Método que servirá para crear la tabla Player
	 * 
	 * @return
	 */
	private static String crearTablaPlayer() {
		return " (idPlayer INT PRIMARY KEY, Nick VARCHAR(45), password VARCHAR(128), email VARCHAR(100));";
	}

	/**
	 * Método que servirá para crear la tabla Games
	 * 
	 * @return
	 */
	private static String crearTablaGames() {
		return "(idGames INT PRIMARY KEY, Nombre VARCHAR(45), tiempoJugador TIME);";
	}

	/**
	 * Método que servirá para crear la tabla Games
	 * 
	 * @return
	 */
	private static String crearTablaCompras() {
		return "(idCompra INT PRIMARY KEY, idPlayer INT, idGames INT, Cosa VARCHAR(25), Precio DECIMAL(6,2), FechaCompra DATE, FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer), FOREIGN KEY (idGames) REFERENCES Games(idGames));";
	}
}
