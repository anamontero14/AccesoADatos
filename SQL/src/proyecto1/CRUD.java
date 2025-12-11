package proyecto1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD {

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
				sentenciaSQL = "CREATE TABLE " + nombreTabla + crearTablaGames();
			} else if (nombreTabla.equalsIgnoreCase("Compras")) {

				// si la tabla esa existe
				if (existenciaTabla("Player") && existenciaTabla("Games")) {
					// Paso 3. Definimos la sentencia de crear una nueva base de datos
					sentenciaSQL = "CREATE TABLE " + nombreTabla + crearTablaCompras();
				}
			}

			// Paso 4. Ejecutar la sentencia
			stmt.execute(sentenciaSQL);

			// si llega aquí significa que las tablas se pudieron crear
			exito = true;

		} catch (SQLException se) {
			System.err.println("Debe crear primero Player y Games.");
			// Gestionamos los posibles errores que puedan surgir durante la ejecucion de la
			// insercion
			se.printStackTrace();
		}

		return exito;

	}

	/**
	 * Método que sirve para comprobar si la operación que se ha realizado ha sido
	 * exitosa o no
	 * 
	 * @param nombreTablaInsertar - El nombre de la tabla en donde se van a insertar
	 *                            los datos
	 * @param valores             - Un array con los valores que valdrán los camposs
	 * @return True si se ha podido ejecutar la sentencia y False si no se pudo
	 *         ejecutar
	 */
	public static boolean insert(String nombreTabla, String valores[]) {
		// variable que gestionará si las tablas se crearon o no correctamente
		boolean exito = false;

		Connection conexion = null;
		Statement stmt = null;
		// variable que almacenará la sentencia sql
		String sentenciaSQL = "";

		// si en primer momento existe la tabla en la que se planea insertar
		if (existenciaTabla(nombreTabla)) {
			try {
				conexion = Conexion.obtenerConexion();
				stmt = conexion.createStatement();

				// se ejecutan todas las sentencias sql
				sentenciaSQL = insertarValores(nombreTabla, valores);
				stmt.execute(sentenciaSQL);

				exito = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return exito;
	}

	/**
	 * Método que te permite mostrar datos de una tabla
	 * 
	 * @param nombreTabla - El nombre de la tabla de la que mostrar datos
	 * @throws SQLException
	 */
	public static void mostrar(String nombreTabla, String campoFiltrar) {
		// variable que almacena la sentencia sql
		String querySelect = "";

		Connection conexion = null;
		Statement stmt = null;

		// si la tabla existe
		if (existenciaTabla(nombreTabla)) {
			// la query es igual a la sentencia sql más el nombre de la tabla una vez que
			// sabemos que la tabla existe
			querySelect = "SELECT * FROM " + nombreTabla;
			// si quiere filtrar por campo
			if (!campoFiltrar.equalsIgnoreCase("")) {

				// se añade el where a la sentencia
				querySelect += " WHERE " + campoFiltrar;
			}

			try {
				// obtiene la conexión a la base de datos
				conexion = Conexion.obtenerConexion();
				// crea un objeto Statement para ejecutar consultas SQL
				stmt = conexion.createStatement();

				// ejecuta la consulta y almacena los resultados en un ResultSet
				ResultSet rs = stmt.executeQuery(querySelect);

				// obtiene los metadatos del ResultSet para conocer información sobre las
				// columnas
				ResultSetMetaData metaData = rs.getMetaData();
				// obtiene el número total de columnas de la tabla
				int numColumnas = metaData.getColumnCount();

				// recorre cada fila del resultado
				while (rs.next()) {
					// variable para construir la línea completa con todos los valores
					String linea = "";
					// recorre cada columna de la fila actual
					for (int i = 1; i <= numColumnas; i++) {
						// añade lo que contenga cada columna a la línea
						linea += rs.getString(i) + " ";
					}
					// imprime la línea
					System.out.println(linea);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Método el cuál actualiza el campo o campos de una tabla con transacción
	 * 
	 * @param nombreTabla  - Nombre de la tabla en donde está el campo a actualizar
	 * @param nombreCampo  - El nombre del campo que tiene que actualizarse
	 * @param datoNuevo    - El nuevo dato que tendrá el campo
	 * @param campoFiltrar - El campo por el que se realizará un filtrado
	 * @param datoFiltrar  - El dato que tendrá que tener el campo de filtrado para
	 *                     poder actualizar los campos
	 * @param conexion     - La conexión para gestionar la transacción
	 * @return El número de filas afectadas por el update
	 */
	public static int update(String nombreTabla, String nombreCampo, String datoNuevo, String campoFiltrar,
			String datoFiltrar, Connection conexion) {
		int numFilasAfectadas = -1;
		Statement stmt = null;
		String sentenciaSQL = "";

		if (existenciaTabla(nombreTabla)) {
			try {
				stmt = conexion.createStatement();

				// se añade la sentencia a la variable
				sentenciaSQL = "UPDATE " + nombreTabla + " SET " + nombreCampo + " = ";

				// comprueba si el campo necesita comillas según su tipo
				if (campoNecesitaComillas(nombreTabla, nombreCampo)) {
					sentenciaSQL += "'" + datoNuevo + "'";
				} else {
					sentenciaSQL += datoNuevo;
				}

				// si el campo por el que filtrar no es una cadena vacía
				if (!campoFiltrar.equalsIgnoreCase("")) {
					sentenciaSQL += " WHERE " + campoFiltrar + " = ";

					// comprueba si el campo de filtrado necesita comillas
					if (campoNecesitaComillas(nombreTabla, campoFiltrar)) {
						sentenciaSQL += "'" + datoFiltrar + "'";
					} else {
						sentenciaSQL += datoFiltrar;
					}
				}

				// se almacenan el numero de filas afectadas
				numFilasAfectadas = stmt.executeUpdate(sentenciaSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return numFilasAfectadas;
	}

	/**
	 * Método que elimina registros de una tabla según un filtro
	 * 
	 * @param nombreTabla  - Nombre de la tabla de donde borrar
	 * @param campoFiltrar - Campo por el que filtrar
	 * @param datoFiltrar  - Valor del campo a filtrar
	 * @param conexion     - Conexión para gestionar transacción
	 * @return Número de filas eliminadas
	 */
	public static int delete(String nombreTabla, String campoFiltrar, String datoFiltrar, Connection conexion) {
		// varibale que almacenará el número de filas que han sido afectadas, está
		// inicializada a -1 primero para controlar el caso en el que se actualicen 0
		// filas pero que no sea un error
		int numFilasAfectadas = -1;
		// se crea el objeto de statement
		Statement stmt = null;
		// variable que almacena la sentencia sql
		String sentenciaSQL = "";

		// si la tabla que se quiere eliminar existe
		if (existenciaTabla(nombreTabla)) {
			// se abre un try catch para gestionar las excepciones
			try {
				// el statement se iguala a la conexion
				stmt = conexion.createStatement();

				// se agrega la sentencia a la tabla
				sentenciaSQL = "DELETE FROM " + nombreTabla;

				// en el caso de que quiera eliminar un dato específico de una tabla se
				// comprueba que el filtrado por el campo no esté vacío
				if (!campoFiltrar.equalsIgnoreCase("")) {
					// si no está vacío significa que quiere hacer un filtrado entonces se le agrega
					// a la sentencia sql
					sentenciaSQL += " WHERE " + campoFiltrar + " = ";

					// comprueba si el campo necesita comillas según su tipo
					if (campoNecesitaComillas(nombreTabla, campoFiltrar)) {
						// le agrega las comillas
						sentenciaSQL += "'" + datoFiltrar + "'";
					} else {
						// y si no las necesita agrega directamente el campo a filtrar
						sentenciaSQL += datoFiltrar;
					}
				}

				// finalmente la variable almacena el resultado de la query
				numFilasAfectadas = stmt.executeUpdate(sentenciaSQL);

			} catch (SQLException e) {
				System.err.println("No se eliminó");
				e.printStackTrace();
			}
		}

		// y se devuelve el número de líneas
		return numFilasAfectadas;
	}

	/**
	 * Método que elimina todas las tablas de la BBDD
	 * 
	 * @return true si se eliminaron correctamente y false si no se pudieron
	 *         eliminar
	 */
	public static boolean eliminarTablas() {
		boolean exito = false;
		Connection conexion = null;
		Statement stmt = null;
		String sentenciaSQL = "";

		try {
			conexion = Conexion.obtenerConexion();
			stmt = conexion.createStatement();

			// se van eliminando las tablas empezando por la que tiene claves foraneas
			sentenciaSQL = "DROP TABLE IF EXISTS Compras";
			stmt.execute(sentenciaSQL);
			sentenciaSQL = "DROP TABLE IF EXISTS Games";
			stmt.execute(sentenciaSQL);
			sentenciaSQL = "DROP TABLE IF EXISTS Player";
			stmt.execute(sentenciaSQL);
			// si todo fue bien hasta aquí siginifica que exito puede ser asignado a true
			exito = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// se devuelve el resultado
		return exito;
	}

	/**
	 * Método que elimina una tabla específica
	 * 
	 * @param nombreTabla - Nombre de la tabla a eliminar
	 * @return true si se eliminaron correctamente y false si no se pudieron
	 *         eliminar
	 */
	public static boolean eliminarTablaSeparada(String nombreTabla) {
		boolean exito = false;
		Connection conexion = null;
		Statement stmt = null;
		String sentenciaSQL = "";

		// si la tabla que se introduce por parámetros existe
		if (existenciaTabla(nombreTabla)) {
			try {
				conexion = Conexion.obtenerConexion();
				stmt = conexion.createStatement();

				// se iguala la variable a la sentencia sql
				sentenciaSQL = "DROP TABLE " + nombreTabla;
				// se ejecuta
				stmt.execute(sentenciaSQL);

				// si llega hasta aquí significa que todo fue bien
				exito = true;

			} catch (SQLException e) {
				System.err.println("Error: No se puede eliminar la tabla " + nombreTabla
						+ " porque tiene relaciones con otras tablas.");
			}
		}

		return exito;
	}

	/***
	 * Método que se encarga de comprobar el tipo del campo de una tabla especifico
	 * para poder realizar un filtrado
	 * 
	 * @param nombreTabla - Nombre de la tabla en la que está el campo
	 * @param nombreCampo - Nombre del campo del que se quiere saber el tipo
	 * @return el tipo del campo en forma de un numero entero
	 */
	public static int obtenerTipoCampo(String nombreTabla, String nombreCampo) {
		// almacena la sentencia select
		String querySelect = "SELECT * FROM " + nombreTabla + " LIMIT 1";
		// almacena el numero de columnas que tiene la tabla
		int numColumnas;
		// almacena el tipo del campo en forma de int
		int tipoColumna = -1;

		Connection conexion;
		try {
			conexion = Conexion.obtenerConexion();

			Statement stmt = conexion.createStatement();
			// se ejecuta la sentencia select
			ResultSet rs = stmt.executeQuery(querySelect);

			ResultSetMetaData metaData = rs.getMetaData();
			// se almacenan el numero de las columnas en la variable para poder recorrerla
			numColumnas = metaData.getColumnCount();

			// se recorren las columnas
			for (int i = 1; i <= numColumnas; i++) {
				// si el nombre de la columna es igual al nombre del campo que le llega por
				// parámetro de entrada
				if (metaData.getColumnName(i).equalsIgnoreCase(nombreCampo)) {
					// le asigna el numero de la columna a la variable como un entero
					tipoColumna = metaData.getColumnType(i);
				}
			}

			rs.close();
			stmt.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// se devuelve el tipo de la columna
		return tipoColumna;
	}

	/**
	 * Método que sirve para comprobar si una tabla existe o no
	 * 
	 * @param nombreTabla = El nombre de la tabla a comprobar
	 * @return true si la tabla existe y false si la tabla no existe
	 */
	private static boolean existenciaTabla(String nombreTabla) {
		boolean existe = false;
		String sql = "SELECT 1 FROM " + nombreTabla + " LIMIT 1";

		try (Connection conexion = Conexion.obtenerConexion(); java.sql.Statement stmt = conexion.createStatement()) {
			stmt.executeQuery(sql);
			existe = true;
		} catch (SQLException e) {
			// Si lanza SQLException, la tabla no existe
		}
		return existe;
	}

	/**
	 * Método que servirá para crear la tabla Player
	 * 
	 * @return
	 */
	private static String crearTablaPlayer() {
		return " (idPlayer INT PRIMARY KEY, nick VARCHAR(45), password VARCHAR(128), email VARCHAR(100));";
	}

	/**
	 * Método que servirá para crear la tabla Games
	 * 
	 * @return
	 */
	private static String crearTablaGames() {
		return "(idGame INT PRIMARY KEY, nombre VARCHAR(45), tiempoJugado TIME);";
	}

	/**
	 * Método que servirá para crear la tabla Games
	 * 
	 * @return
	 */
	private static String crearTablaCompras() {
		return "(idCompra INT PRIMARY KEY, idPlayer INT, idGame INT, cosa VARCHAR(25), precio DECIMAL(6,2), fechaCompra DATE, FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer), FOREIGN KEY (idGame) REFERENCES Games(idGame));";
	}

	/**
	 * Método estático que sirve para poder insertar datos en una tabla determinada,
	 * se recorre el array de cadenas que le llega y este se va añadiendo a la query
	 * (a la vez que el nombre de la tabla), no se comprueba la tabla que es.
	 * 
	 * @param nombreTablaInsertar - El nombre de la tabla en donde se van a insertar
	 *                            los datos
	 * @param valores             - Un array con los valores que valdrán los campos
	 * @return La query con la sentencia SQL
	 */
	private static String insertarValores(String nombreTablaInsertar, String[] valores) {
		// query que se ejecutara
		String querySQL = "INSERT INTO ";

		querySQL += nombreTablaInsertar + " VALUES (";

		// recorro el array
		for (int i = 0; i < valores.length; i++) {

			// si el valor NO es un número
			if (esNumero(valores[i])) {
				// si ES un número se añade a la sentencia directamente
				querySQL += valores[i];
				// si NO es un número se le ponen comillas
			} else {
				// se le colocan comillas y se añade a la sentencia
				querySQL += "'" + valores[i] + "'";
			}

			// si después del valor actual HAY otro valor más se coloca una coma
			if (i < valores.length - 1) {
				querySQL += ", ";
			}
		}

		// se cierra el paréntesis
		querySQL += ")";

		// se devuelve la query
		return querySQL;
	}

	/**
	 * Método auxiliar que sirve para determinar si una cadena de texto es o no un
	 * número
	 * 
	 * @param texto - Parámetro de entrada que es un string
	 * @return True si es un número y false si no lo es
	 */
	private static boolean esNumero(String texto) {
		if (texto == null || texto.isEmpty())
			return false;
		try {
			Double.parseDouble(texto); // o Integer.parseInt si solo enteros
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Método auxiliar que determina si un campo necesita comillas según su tipo
	 * 
	 * @param nombreTabla - Nombre de la tabla
	 * @param nombreCampo - Nombre del campo
	 * @return true si necesita comillas (texto/fecha), false si no (numérico)
	 */
	private static boolean campoNecesitaComillas(String nombreTabla, String nombreCampo) {

		// campo auxiliar que almacena el estado del campo de si necesita comillas o no
		boolean necesitaComillas = true;

		int tipoCampo = obtenerTipoCampo(nombreTabla, nombreCampo);

		// los tipos numéricos no necesitan comillas pero las fechas, el tiempo y las
		// cadenas sí
		if (tipoCampo == 3 || tipoCampo == 4 || tipoCampo == -5 || tipoCampo == 6 || tipoCampo == 7 || tipoCampo == 8) {
			// si es numérico no necesitará comillas
			necesitaComillas = false;
		}

		// devuelve el booleano
		return necesitaComillas;
	}

}
