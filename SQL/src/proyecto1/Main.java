package proyecto1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private static Scanner leer = new Scanner(System.in);

	public static void main(String[] args) {

		// array de cadenas que almacenan los valores que se mandarán para insertar en
		// la query
		String valoresInsertar[] = null;

		// variable que almacena el nombre de la tabla que quiere crear
		String nombreTabla = "";
		// variable que almacena el campo por el que quiere buscar el usuario
		String campoTabla = "";
		// variable que almacena el valor que el usuario quiere que tenga un campo de la
		// tabla
		String valorCampoTabla = "";
		// variable que almacenará el filtrado que un usuario le quiere dar a una tabla
		// al listarla
		String filtradoTablaMostrar = "";
		// variable que almacena el tipo de cada campo
		int tipoCampo = -1;
		// almacena la respuesta a una pregunta
		String respuesta = "";
		// variable que almacena cómo filtrar el dato
		String filtrado = "";
		// variable que almacena el campo a filtrar
		String campoFiltrar = "";
		// almacena el nuevo valor de un campo
		String campoNuevoValor = "";
		// almacena el numero de filas afectadas
		int numFilasAfectadas = -1;

		// variable para almacenar la opción que se escoge del menú
		int opcion = 0;
		// variable que almacena opciones internas del bucle
		int opcionInterna = 0;

		do {
			// muestra el menú de opciones que tiene el usuario
			menu();
			// le pide que introduzca una opción
			System.out.print("Selecciona una opción: ");
			opcion = leer.nextInt();
			leer.nextLine();

			// recoge la opcion y se mete en el switch
			switch (opcion) {
			case 1 -> {
				do {
					System.out.println("CREAR TABLAS");
					System.out.println("¿Quieres crear TODAS LAS TABLAS o LAS TABLAS POR SEPARADO?");
					System.out.println("	1. Crear todas las tablas");
					System.out.println("	2. Crear las tablas por separado");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
					// se repetirá este bucle mientras la opción siga siendo 0 (si el usuario pulsa
					// enter) o si la opción es inválida
				} while (opcionInterna == 0 && opcionInterna >= 2 && opcionInterna <= 1);

				// dependiendo de la opción que se haya escogido
				switch (opcionInterna) {
				// si quiere crear todas las tablas
				case 1: {
					System.out.println("CREAR TODAS LAS TABLAS");
					// se llama al método del crud
					if (CRUD.crearTablas()) {
						// si se pudieron crear manda un mensaje indicándolo
						System.out.println("	Las tablas se crearon correctamente");
					} else {
						System.err.println("	Las tablas no se pudieron crear");
					}
				}
				// si quiere crear las tablas por separado
				case 2: {
					System.out.println("CREAR LAS TABLAS POR SEPARADO");
					// se repetirá este bucle
					do {
						System.out.print("Introduca el nombre de la tabla que quiere crear: ");
						nombreTabla = leer.nextLine();
						// mientras el usuario no introduzca un nombre apropiado
					} while (nombreTabla != "");

					// se llama al método del crud
					if (CRUD.crearTablasSeparadas(nombreTabla)) {
						System.out.println("	Las tablas se crearon correctamente");
					} else {
						System.err.println("	Las tablas no se pudieron crear");
					}
				}
				}
			}
			// insertar datos en una tabla
			case 2 -> {
				System.out.println("INSERTAR DATOS EN TABLAS");

				// seguirá preguntando por el nombre de la tabla hasta que deje de ser cadena
				// vacía
				do {
					System.out.print(
							"Introduce el nombre de la tabla donde quieres introducir nuevos datos (Compras/Games/Player): ");
					nombreTabla = leer.nextLine();
				} while (nombreTabla.equalsIgnoreCase(""));

				// si el nombre de la tabla es player
				if (nombreTabla.equalsIgnoreCase("Player")) {
					// se iguala el array a los campos que se introduzcan en la funcion player
					valoresInsertar = valoresCamposPlayer();
				} else if (nombreTabla.equalsIgnoreCase("Games")) {
					// se iguala el array a los campos que se introduzcan en la funcion games
					valoresInsertar = valoresCamposGames();
				} else if (nombreTabla.equalsIgnoreCase("Compras")) {
					// se iguala el array a los campos que se introduzcan en la funcion compras
					valoresInsertar = valoresCamposCompras();
				}

				// si la ejecucion del insert es correcta
				if (CRUD.insert(nombreTabla, valoresInsertar)) {
					System.out.println("	Se insertaron los campos en la tabla " + nombreTabla);
				} else {
					System.err.println("	No se pudieron insertar los campos en la tabla " + nombreTabla);
				}

			}
			case 3 -> {
				System.out.println("LISTAR TABLAS");

				// se vuelve a inicializar la variable a cadena vacia
				filtradoTablaMostrar = "";

				// se le pregunta lo que quiere hacer
				do {
					System.out.println("¿Quiere mostrar toda la tabla o solo un dato específico de esta?: ");
					System.out.println("	1. Mostrar toda la tabla");
					System.out.println("	2. Mostrar un campo específico");
					System.out.print("Introduzca su elección: ");
					opcionInterna = leer.nextInt();
					// se limpia el buffer
					leer.nextLine();
				} while (opcionInterna != 1 && opcionInterna != 2);

				System.out.print("Introduzca la tabla que quiere ver (Player/Games/Compras): ");
				nombreTabla = leer.nextLine();

				// si quiere mostrar una campo en específico
				if (opcionInterna == 2) {
					System.out.println("CAMPOS DE LA TABLA " + nombreTabla);
					// se muestran todos los campos de la tabla
					mostrarCamposTabla(nombreTabla);
					System.out.print("Introduzca el campo por el que filtrar: ");
					campoTabla = leer.nextLine();
					System.out.print("Introduzca el valor que quiere que tenga " + campoTabla + " :");
					valorCampoTabla = leer.nextLine();
					// se almacena el tipo del campo (que es un int) en una variable
					tipoCampo = CRUD.obtenerTipoCampo(nombreTabla, campoTabla);

					// si el tipo del campo es entre 4 y 8 significa que es numérico
					if (tipoCampo == 3 || tipoCampo == 4) {
						// se añade la sentencia a la variable
						filtradoTablaMostrar = campoTabla + " = " + valorCampoTabla;
					} else if (tipoCampo == 12) {
						// se añade la sentencia a la variable
						filtradoTablaMostrar = campoTabla + " LIKE '%" + valorCampoTabla + "%'";
					} else if (tipoCampo == 91 || tipoCampo == 92) {
						// repetirá la pregunta mientras que el usuario no introduzca nada
						do {
							System.out.print("¿Quiere buscar una fecha menor o mayor a la introducida?");
							filtrado = leer.nextLine();
						} while (filtrado.equalsIgnoreCase("") || filtrado.equalsIgnoreCase("mayor")
								|| filtrado.equalsIgnoreCase("menor"));

						if (filtrado.equalsIgnoreCase("menor")) {
							// se añade la sentencia a la variable
							filtradoTablaMostrar = campoTabla + " < " + valorCampoTabla;
						} else if (filtrado.equalsIgnoreCase("mayor")) {
							// se añade la sentencia a la variable
							filtradoTablaMostrar = campoTabla + " > " + valorCampoTabla;
						}
					}
				}

				// se llama a la función del CRUD para poder mostrar una tabla y si es necesario
				// mostrar una tabla filtrada por un campo especifico
				CRUD.mostrar(nombreTabla, filtradoTablaMostrar);
			}
			case 4 -> {
				System.out.println("ACTUALIZAR UN CAMPOS DE UNA TABLA");
				System.out.print("Introduzca la tabla de la que quiere actualizar el campo (Player/Games/Compras): ");
				nombreTabla = leer.nextLine();

				// se muestran todos los campos de la tabla escogida
				mostrarCamposTabla(nombreTabla);
				System.out.print("Introduzca el campo de la tabla que quiere actualizar: ");
				campoTabla = leer.nextLine();

				System.out.print("Introduzca el nuevo valor del campo:");
				campoNuevoValor = leer.nextLine();

				// se le pregunta si quiere hacer un filtrado tantas veces hasta que seleccione
				// una opción correcta
				do {
					System.out.print("¿Desea hacer un filtrado? (S/N): ");
					respuesta = leer.nextLine();
				} while (respuesta.equalsIgnoreCase("")
						|| (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")));

				// si quiere hacer un filtrado
				if (respuesta.equalsIgnoreCase("S")) {
					// se vuelven a mostrar los campos de la tabla
					mostrarCamposTabla(nombreTabla);
					System.out.print("Introduzca el campo por el que quiere filtrar: ");
					campoFiltrar = leer.nextLine();
					System.out.print("Introduzca el valor que desea que tenga el campo por el que quiere filtrar: ");
					filtrado = leer.nextLine();
				}

				// se instancia la conexión para poder hacer uso del rollback
				Connection conexionTransaccion = null;

				try {
					conexionTransaccion = Conexion.obtenerConexion();
					// se desactiva el commit automático para poder hacer un rollback en caso de no
					// querer que la operación se ejecute
					conexionTransaccion.setAutoCommit(false);

					// se almacenan el número de filas afectadas
					numFilasAfectadas = CRUD.update(nombreTabla, campoTabla, campoNuevoValor, campoFiltrar, filtrado,
							conexionTransaccion);

					// si el numero de filas afectadas es mayor que 0
					if (numFilasAfectadas > 0) {
						// indica el número de filas que se actualizaron
						System.out.println("	Se actualizaron " + numFilasAfectadas + " filas");
					} else if (numFilasAfectadas == 0) {
						// si el número de filas actualizadas es 0 indica que no se ha actualizado
						// ninguna fila
						System.out.println("	No se actualizó ninguna fila");
						conexionTransaccion.rollback();
					} else {
						// indica que ocurrió un error si la sentencia devuelve un número negativo
						System.err.println("	No se pudo ejecutar la sentencia correctamente");
						conexionTransaccion.rollback();
					}

					CRUD.mostrar(nombreTabla, "");

					// preguntar si confirmar cambios
					do {
						System.out.print("	¿Desea confirmar los cambios? (S/N): ");
						respuesta = leer.nextLine();
						// se le seguirá preguntando mientras el valor que almacene respuesta sea
						// diferente a s/S, n/N e igual a cadena vacía
					} while (respuesta.equalsIgnoreCase("")
							|| (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")));

					// si desea confirmar los cambio
					if (respuesta.equalsIgnoreCase("S")) {
						// se hace un commit de la transacción
						conexionTransaccion.commit();
						// se confirman los cambios
						System.out.println("	Cambios confirmados correctamente.");
					} else {
						// si no, se hace un rollback
						conexionTransaccion.rollback();
						System.out.println("	Cambios revertidos.");
					}

					// se vuelve a poner a true ya que si no las demás operaciones no se podrían
					// ejecutar
					conexionTransaccion.setAutoCommit(true);
					// se cierra la conexion de la transaccion
					conexionTransaccion.close();

				} catch (SQLException e) {
					if (conexionTransaccion != null) {
						try {
							conexionTransaccion.rollback();
							System.err.println("Error en la transacción. Cambios revertidos.");
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
					e.printStackTrace();
				}

			}
			// si la respuesta es 5 / para borrar datos de las tablas
			case 5 -> {
				System.out.println("BORRAR DATOS DE TABLAS");

				// se le pregunta al usuario qué tipo de borrado quiere hacer
				do {
					System.out.println("¿Quieres borrar TODOS LOS DATOS o DATOS ESPECÍFICOS?");
					System.out.println("	1. Borrar todos los datos de una tabla");
					System.out.println("	2. Borrar datos específicos");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					// se limpia el buffer
					leer.nextLine();
					// se le sigue preguntando mientras la opción no sea ni 1 ni 2
				} while (opcionInterna != 1 && opcionInterna != 2);

				// se le pregunta por la tabla de la que quiere borrar datos
				System.out.print("Introduzca la tabla de la que quiere borrar datos (Player/Games/Compras): ");
				nombreTabla = leer.nextLine();

				// si quiere borrar datos específicos
				if (opcionInterna == 2) {
					// se muestra los NOMBRES de los campos de la tabla especificada
					mostrarCamposTabla(nombreTabla);
					System.out.print("Introduzca el campo por el que filtrar: ");
					campoFiltrar = leer.nextLine();
					System.out.print("Introduzca el valor del campo: ");
					filtrado = leer.nextLine();
				} else {
					// se vuelven a inicializar las variables
					campoFiltrar = "";
					filtrado = "";
				}

				// mostrar datos antes de borrar
				System.out.println("	Datos actuales de la tabla:");
				CRUD.mostrar(nombreTabla, "");

				// iniciar transacción en caso de que se quiera deshacer esta operación
				Connection conexionTransaccion = null;

				try {
					conexionTransaccion = Conexion.obtenerConexion();
					// se inicializan los commit automaticos otra vez a false
					conexionTransaccion.setAutoCommit(false);
					// se almacenan las filas a las que ha afectado el método delete del crud
					numFilasAfectadas = CRUD.delete(nombreTabla, campoFiltrar, filtrado, conexionTransaccion);

					// si las filas afectadas son mayores que 0
					if (numFilasAfectadas > 0) {
						// indica el número de filas que han sido afectadas
						System.out.println("	Se eliminaron " + numFilasAfectadas + " filas");
						System.out.println();
						// muestra otra vez los datos después de borrar
						System.out.println("	Datos después del borrado:");
						CRUD.mostrar(nombreTabla, "");
					} else if (numFilasAfectadas == 0) {
						System.out.println("	No se eliminó ninguna fila");
						conexionTransaccion.rollback();
					} else {
						System.err.println("	No se pudo ejecutar la sentencia correctamente");
						conexionTransaccion.rollback();
					}

					// se le pregunta al usuario si quiere confirmar la acción del borrado pero
					// seguirá repitiendo la pregunta mientras que la variable respuesta sea una
					// cadena vacía y no sea ni s/S ni n/N
					do {
						System.out.print("	¿Desea confirmar el borrado? (S/N): ");
						respuesta = leer.nextLine();
					} while (respuesta.equalsIgnoreCase("")
							|| (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")));

					// si quiere quedarse con los datos después del borrado
					if (respuesta.equalsIgnoreCase("S")) {
						// se hace un commit y se informa
						conexionTransaccion.commit();
						System.out.println("	Borrado confirmado correctamente.");
					} else {
						// si no, se hace un rollback
						conexionTransaccion.rollback();
						System.out.println("	Borrado cancelado.");
					}

					// el auto commit vuelve a ser true para que las demás operaciones si se puedan
					// ejecutar
					conexionTransaccion.setAutoCommit(true);
					conexionTransaccion.close();

				} catch (SQLException e) {
					if (conexionTransaccion != null) {
						try {
							conexionTransaccion.rollback();
							System.err.println("Error en la transacción. Cambios revertidos.");
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
					e.printStackTrace();
				}
			}
			case 6 -> {
				System.out.println("ELIMINAR TABLAS");

				// vuelve a mostrar unas opciones y a preguntarle al usuario mientras que la
				// opción no sea ninguna de las indicadas
				do {
					System.out.println("¿Quieres eliminar TODAS LAS TABLAS o UNA TABLA ESPECÍFICA?");
					System.out.println("	1. Eliminar todas las tablas");
					System.out.println("	2. Eliminar una tabla específica");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
				} while (opcionInterna != 1 && opcionInterna != 2);

				if (opcionInterna == 1) {
					System.out.println("ELIMINAR TODAS LAS TABLAS");

					// se le pregunta al usuario si está seguro de lo que está haciendo
					do {
						System.out.print("¿Está seguro de que quiere eliminar TODAS las tablas? (S/N): ");
						respuesta = leer.nextLine();
					} while (respuesta.equalsIgnoreCase("")
							|| (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")));

					// si dice que sí entonces elimina TODAS las tablas
					if (respuesta.equalsIgnoreCase("S")) {
						// si la operación devuelve un true
						if (CRUD.eliminarTablas()) {
							System.out.println("	Todas las tablas se eliminaron correctamente");
						} else {
							System.err.println("	No se pudieron eliminar las tablas");
						}
						// si dice que no
					} else {
						System.out.println("Operación cancelada.");
					}
					// si sólo quiere eliminar una tabla específica
				} else {
					System.out.println("ELIMINAR UNA TABLA ESPECÍFICA");
					System.out.println("IMPORTANTE: Se tienen que eliminar primero las tablas con referencias a otras");
					System.out.println("Orden recomendado: Compras -> Games -> Player");

					// se le pregunta por el nombre de la tabla a eliminar
					System.out.print("Introduzca el nombre de la tabla que quiere eliminar: ");
					nombreTabla = leer.nextLine();

					// se le pregunta igual que antes si está seguro de eliminar la tabla
					// especificada hasta que escoja una opción válida
					do {
						System.out.print("¿Está seguro de que quiere eliminar la tabla " + nombreTabla + "? (S/N): ");
						respuesta = leer.nextLine();
					} while (respuesta.equalsIgnoreCase("")
							|| (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")));

					// si responde que sí
					if (respuesta.equalsIgnoreCase("S")) {
						// se elimina dicha tabla si existe
						if (CRUD.eliminarTablaSeparada(nombreTabla)) {
							System.out.println("	La tabla " + nombreTabla + " se eliminó correctamente");
							// si no existe le indica que no se puede eliminar
						} else {
							System.err.println("	No se pudo eliminar la tabla " + nombreTabla);
						}
						// si reponde que no se cancela la operación
					} else {
						System.out.println("	Operación cancelada.");
					}
				}

			}
			case 7 -> {
				System.out.println("Saliendo del programa...");
			}
			}

			// se vuelve a inicializar el nombre de la tabla
			nombreTabla = "";
			// se le sigue preguntando al usuario hasta que meta una opción fuera del rango
		} while (opcion >= 1 && opcion <= 6);

		System.out.println();
		System.out.println("FIN DEL PROGRAMA");

		leer.close();
	}

	// función para menú
	private static void menu() {
		System.out.println("ESCOJA UNA OPCIÓN:");
		System.out.println("	1. Crear tablas");
		System.out.println("	2. Insertar datos en tablas");
		System.out.println("	3. Listar datos de tablas");
		System.out.println("	4. Modificar datos de tablas");
		System.out.println("	5. Borrar datos de tablas");
		System.out.println("	6. Borrar tablas");
		System.out.println("	7. Salir");
	}

	// funcion para poder introducir los datos de la tabla Player que devuelve un
	// array de string con todos los valores de la tabla
	private static String[] valoresCamposPlayer() {

		String valores[] = new String[4];

		// campos para introducir los valores
		String idPlayer;
		String nick = "";
		String password = "";
		String email = "";

		// introduce los valores de cada uno
		System.out.print("Introduzca el ID del jugador (nº entero): ");
		idPlayer = leer.nextLine();
		System.out.print("Introduzca el nick del jugador: ");
		nick = leer.nextLine();
		System.out.print("Introduzca la contraseña del jugador: ");
		password = leer.nextLine();
		System.out.print("Introduzca el email del jugador: ");
		email = leer.nextLine();

		// se insertan las variables en un array
		valores[0] = idPlayer;
		valores[1] = nick;
		valores[2] = password;
		valores[3] = email;

		return valores;
	}

	// funcion para poder introducir los datos de la tabla Games
	private static String[] valoresCamposGames() {
		String valores[] = new String[3];

		// campos para introducir los valores
		String idGame = "";
		String nombre = "";
		String tiempoJugado = "";

		// introduce los valores de cada uno
		System.out.print("Introduzca el ID del juego (nº entero): ");
		idGame = leer.nextLine();
		System.out.print("Introduzca el nombre del juego: ");
		nombre = leer.nextLine();
		System.out.print("Introduzca la tiempo jugado del juego (HH:MM:SS): ");
		tiempoJugado = leer.nextLine();

		// se insertan las variables en un array
		valores[0] = idGame;
		valores[1] = nombre;
		valores[2] = tiempoJugado;

		return valores;
	}

	// funcion para poder introducir los datos de la tabla Compras
	private static String[] valoresCamposCompras() {
		String valores[] = new String[6];

		// campos para introducir los valores
		String idCompra = "";
		String idPlayer = "";
		String idGame = "";
		String cosa = "";
		String precio = "";
		String fechaCompra = "";

		// introduce los valores para cada campo
		System.out.print("Introduce el ID de la compra (nº entero): ");
		idCompra = leer.nextLine();
		System.out.print("Introduce el ID del jugador (nº entero): ");
		idPlayer = leer.nextLine();
		System.out.print("Introduce el ID del juego (nº entero): ");
		idGame = leer.nextLine();
		System.out.print("Introduce la cosa: ");
		cosa = leer.nextLine();
		System.out.print("Introduce el importe de la compra (0.0): ");
		precio = leer.nextLine();
		System.out.print("Introduce la fecha de la compra (YYYY-MM-DD): ");
		fechaCompra = leer.nextLine();

		valores[0] = idCompra;
		valores[1] = idPlayer;
		valores[2] = idGame;
		valores[3] = cosa;
		valores[4] = precio;
		valores[5] = fechaCompra;

		return valores;
	}

	// funcion para poder mostrar todos los campos de una tabla
	private static void mostrarCamposTabla(String nombreTabla) {
		if (nombreTabla.equalsIgnoreCase("Player")) {
			System.out.println("idPlayer");
			System.out.println("nick");
			System.out.println("password");
			System.out.println("email");
		} else if (nombreTabla.equalsIgnoreCase("Games")) {
			System.out.println("idGame");
			System.out.println("nombre");
			System.out.println("tiempoJugado");
		} else if (nombreTabla.equalsIgnoreCase("Compras")) {
			System.out.println("idCompra");
			System.out.println("idPlayer");
			System.out.println("idGame");
			System.out.println("cosa");
			System.out.println("precio");
			System.out.println("fechaCompra");
		}
	}
}
