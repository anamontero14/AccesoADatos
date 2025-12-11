package proyecto1.main;

import proyecto1.entidades.Compras;
import proyecto1.entidades.Game;
import proyecto1.entidades.Player;
import proyecto1.util.HibernateUtil;
import proyecto1.crud.CRUD;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static Scanner leer = new Scanner(System.in);

	public static void main(String[] args) {

		int opcion = 0;
		int opcionInterna = 0;
		String nombreTabla = "";
		String respuesta = "";

		// Verificar conexión al inicio
		if (CRUD.conectar()) {
			System.out.println("Conexión exitosa con Hibernate");
			System.out.println("Las tablas se crearán automáticamente si no existen");
			System.out.println();
		} else {
			System.err.println("Error al conectar con Hibernate");
			return;
		}

		do {
			menu();
			System.out.print("Selecciona una opción: ");
			opcion = leer.nextInt();
			leer.nextLine();

			switch (opcion) {
			case 1 -> {
				System.out.println("INSERTAR DATOS EN TABLAS");
				do {
					System.out.println("¿En qué tabla quiere insertar datos?");
					System.out.println("    1. Player");
					System.out.println("    2. Game");
					System.out.println("    3. Compras");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
				} while (opcionInterna < 1 || opcionInterna > 3);

				switch (opcionInterna) {
				case 1: {
					insertarPlayer();
				}
				case 2: {
					insertarGame();
				}
				case 3: {
					insertarCompra();
				}
				}
			}
			case 2 -> {
				System.out.println("LISTAR DATOS DE TABLAS");
				do {
					System.out.println("¿Qué tabla quiere listar?");
					System.out.println("    1. Player");
					System.out.println("    2. Game");
					System.out.println("    3. Compras");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
				} while (opcionInterna < 1 || opcionInterna > 3);

				switch (opcionInterna) {
				case 1: {
					listarPlayers();
				}
				case 2: {
					listarGame();
				}
				case 3: {
					listarCompras();
				}
				}
			}
			case 3 -> {
				System.out.println("MODIFICAR DATOS DE TABLAS");
				do {
					System.out.println("¿Qué tabla quiere modificar?");
					System.out.println("    1. Player");
					System.out.println("    2. Game");
					System.out.println("    3. Compras");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
				} while (opcionInterna < 1 || opcionInterna > 3);

				switch (opcionInterna) {
				case 1: {
					modificarPlayer();
				}
				case 2: {
					modificarGame();
				}
				case 3: {
					modificarCompra();
				}
				}
			}
			case 4 -> {
				System.out.println("BORRAR DATOS DE TABLAS");
				do {
					System.out.println("¿De qué tabla quiere borrar datos?");
					System.out.println("    1. Player");
					System.out.println("    2. Game");
					System.out.println("    3. Compras");
					System.out.println("    4. Borrar TODOS los registros de una tabla");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					leer.nextLine();
				} while (opcionInterna < 1 || opcionInterna > 4);

				switch (opcionInterna) {
				case 1: {
					borrarPlayer();
				}
				case 2: {
					borrarGame();
				}
				case 3: {
					borrarCompra();
				}
				case 4: {
					borrarTodosRegistros();
				}
				}
			}
			case 5 -> {
				System.out.println("Saliendo del programa...");
			}
			default -> {
				System.err.println("Opción no válida");
			}
			}

			System.out.println();

		} while (opcion >= 1 && opcion <= 4);

		System.out.println("FIN DEL PROGRAMA");
		HibernateUtil.cerrarSessionFactory();
		leer.close();
	}

	/**
	 * Método que muestra el menú principal
	 */
	private static void menu() {
		System.out.println("ESCOJA UNA OPCIÓN:");
		System.out.println("    1. Insertar datos en tablas");
		System.out.println("    2. Listar datos de tablas");
		System.out.println("    3. Modificar datos de tablas");
		System.out.println("    4. Borrar datos de tablas");
		System.out.println("    5. Salir");
	}

	/**
	 * Método para insertar un nuevo Player
	 */
	private static void insertarPlayer() {
		String nick = "";
		String password = "";
		String email = "";

		System.out.println("INSERTAR NUEVO PLAYER");
		System.out.print("Introduzca el nick del jugador: ");
		nick = leer.nextLine();
		System.out.print("Introduzca la contraseña del jugador: ");
		password = leer.nextLine();
		System.out.print("Introduzca el email del jugador: ");
		email = leer.nextLine();

		Player player = new Player(nick, password, email);

		if (CRUD.insertarPlayer(player)) {
			System.out.println("    Player insertado correctamente con ID: " + player.getIdPlayer());
		} else {
			System.err.println("    No se pudo insertar el Player");
		}
	}

	/**
	 * Método para insertar un nuevo Game
	 */
	private static void insertarGame() {
		String nombre = "";
		String tiempoJugado = "";

		System.out.println("INSERTAR NUEVO GAME");
		System.out.print("Introduzca el nombre del juego: ");
		nombre = leer.nextLine();
		System.out.print("Introduzca el tiempo jugado (HH:MM:SS): ");
		tiempoJugado = leer.nextLine();

		Time tiempo = Time.valueOf(tiempoJugado);
		Game game = new Game(nombre, tiempo);

		if (CRUD.insertarGame(game)) {
			System.out.println("    Game insertado correctamente con ID: " + game.getIdGame());
		} else {
			System.err.println("    No se pudo insertar el Game");
		}
	}

	/**
	 * Método para insertar una nueva Compra
	 */
	private static void insertarCompra() {
		String nickPlayer = "";
		String nombreGame = "";
		String cosa = "";
		String precioStr = "";
		String fechaCompraStr = "";
		int idPlayerSeleccionado = 0;
		int idGameSeleccionado = 0;

		System.out.println("INSERTAR NUEVA COMPRA");

		// Buscar player por nick
		System.out.print("Introduzca el nick del jugador: ");
		nickPlayer = leer.nextLine();
		List<Player> playersEncontrados = CRUD.listarPlayersPorNick(nickPlayer);

		if (playersEncontrados.isEmpty()) {
			System.err.println("    No se encontró ningún jugador con ese nick");
			return;
		}

		if (playersEncontrados.size() == 1) {
			idPlayerSeleccionado = playersEncontrados.get(0).getIdPlayer();
			System.out.println("    Player seleccionado: " + playersEncontrados.get(0).getNick());
		} else {
			System.out.println("    Se encontraron varios jugadores:");
			for (int i = 0; i < playersEncontrados.size(); i++) {
				System.out.println("        " + (i + 1) + ". " + playersEncontrados.get(i));
			}
			System.out.print("    Seleccione el número del jugador: ");
			int seleccion = leer.nextInt();
			leer.nextLine();
			idPlayerSeleccionado = playersEncontrados.get(seleccion - 1).getIdPlayer();
		}

		// Buscar game por nombre
		System.out.print("Introduzca el nombre del juego: ");
		nombreGame = leer.nextLine();
		List<Game> gamesEncontrados = CRUD.listarGamePorNombre(nombreGame);

		if (gamesEncontrados.isEmpty()) {
			System.err.println("    No se encontró ningún juego con ese nombre");
			return;
		}

		if (gamesEncontrados.size() == 1) {
			idGameSeleccionado = gamesEncontrados.get(0).getIdGame();
			System.out.println("    Game seleccionado: " + gamesEncontrados.get(0).getNombre());
		} else {
			System.out.println("    Se encontraron varios juegos:");
			for (int i = 0; i < gamesEncontrados.size(); i++) {
				System.out.println("        " + (i + 1) + ". " + gamesEncontrados.get(i));
			}
			System.out.print("    Seleccione el número del juego: ");
			int seleccion = leer.nextInt();
			leer.nextLine();
			idGameSeleccionado = gamesEncontrados.get(seleccion - 1).getIdGame();
		}

		// Obtener los objetos completos
		Player player = CRUD.buscarPlayerPorId(idPlayerSeleccionado);
		Game game = CRUD.buscarGamePorId(idGameSeleccionado);

		System.out.print("Introduzca la cosa: ");
		cosa = leer.nextLine();
		System.out.print("Introduzca el precio: ");
		precioStr = leer.nextLine();
		System.out.print("Introduzca la fecha de compra (YYYY-MM-DD): ");
		fechaCompraStr = leer.nextLine();

		BigDecimal precio = new BigDecimal(precioStr);
		Date fechaCompra = Date.valueOf(fechaCompraStr);

		Compras compra = new Compras(player, game, cosa, precio, fechaCompra);

		if (CRUD.insertarCompras(compra)) {
			System.out.println("    Compra insertada correctamente con ID: " + compra.getIdCompra());
		} else {
			System.err.println("    No se pudo insertar la Compra");
		}
	}

	/**
	 * Método para listar Players
	 */
	private static void listarPlayers() {
		String respuesta = "";
		String nick = "";

		System.out.println("LISTAR PLAYERS");
		do {
			System.out.print("¿Quiere filtrar por nick? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		List<Player> players = null;

		if (respuesta.equalsIgnoreCase("S")) {
			System.out.print("Introduzca el nick a buscar: ");
			nick = leer.nextLine();
			players = CRUD.listarPlayersPorNick(nick);
		} else {
			players = CRUD.listarPlayers();
		}

		if (players.isEmpty()) {
			System.out.println("    No se encontraron registros");
		} else {
			System.out.println("    PLAYERS ENCONTRADOS:");
			for (Player p : players) {
				System.out.println("    " + p);
			}
		}
	}

	/**
	 * Método para listar Game
	 */
	private static void listarGame() {
		String respuesta = "";
		String nombre = "";

		System.out.println("LISTAR GAMES");
		do {
			System.out.print("¿Quiere filtrar por nombre? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		List<Game> games = null;

		if (respuesta.equalsIgnoreCase("S")) {
			System.out.print("Introduzca el nombre a buscar: ");
			nombre = leer.nextLine();
			games = CRUD.listarGamePorNombre(nombre);
		} else {
			games = CRUD.listarGame();
		}

		if (games.isEmpty()) {
			System.out.println("    No se encontraron registros");
		} else {
			System.out.println("    GAMES ENCONTRADOS:");
			for (Game g : games) {
				System.out.println("    " + g);
			}
		}
	}

	/**
	 * Método para listar Compras
	 */
	private static void listarCompras() {
		String respuesta = "";
		String cosa = "";

		System.out.println("LISTAR COMPRAS");
		do {
			System.out.print("¿Quiere filtrar por cosa? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		List<Compras> compras = null;

		if (respuesta.equalsIgnoreCase("S")) {
			System.out.print("Introduzca la cosa a buscar: ");
			cosa = leer.nextLine();
			compras = CRUD.listarComprasPorCosa(cosa);
		} else {
			compras = CRUD.listarCompras();
		}

		if (compras.isEmpty()) {
			System.out.println("    No se encontraron registros");
		} else {
			System.out.println("    COMPRAS ENCONTRADAS:");
			for (Compras c : compras) {
				System.out.println("    " + c);
			}
		}
	}

	/**
	 * Método para modificar un Player
	 */
	private static void modificarPlayer() {
		int id = 0;
		String respuesta = "";
		String nuevoValor = "";
		int campoModificar = 0;

		System.out.println("MODIFICAR PLAYER");
		System.out.print("Introduzca el ID del Player a modificar: ");
		id = leer.nextInt();
		leer.nextLine();

		Player player = CRUD.buscarPlayerPorId(id);

		if (player == null) {
			System.err.println("    No se encontró el Player con ID: " + id);
			return;
		}

		System.out.println("    Player encontrado: " + player);

		do {
			System.out.println("    ¿Qué campo quiere modificar?");
			System.out.println("        1. Nick");
			System.out.println("        2. Password");
			System.out.println("        3. Email");
			System.out.print("    Seleccione una opción: ");
			campoModificar = leer.nextInt();
			leer.nextLine();
		} while (campoModificar < 1 || campoModificar > 3);

		System.out.print("    Introduzca el nuevo valor: ");
		nuevoValor = leer.nextLine();

		switch (campoModificar) {
		case 1:
			player.setNick(nuevoValor);
		case 2:
			player.setPassword(nuevoValor);
		case 3:
			player.setEmail(nuevoValor);
		}

		System.out.println("    Datos después de la modificación: " + player);

		do {
			System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.actualizarPlayer(player)) {
				System.out.println("    Player actualizado correctamente");
			} else {
				System.err.println("    No se pudo actualizar el Player");
			}
		} else {
			System.out.println("    Cambios cancelados");
		}
	}

	/**
	 * Método para modificar un Game
	 */
	private static void modificarGame() {
		int id = 0;
		String respuesta = "";
		String nuevoValor = "";
		int campoModificar = 0;

		System.out.println("MODIFICAR GAME");
		System.out.print("Introduzca el ID del Game a modificar: ");
		id = leer.nextInt();
		leer.nextLine();

		Game game = CRUD.buscarGamePorId(id);

		if (game == null) {
			System.err.println("    No se encontró el Game con ID: " + id);
			return;
		}

		System.out.println("    Game encontrado: " + game);

		do {
			System.out.println("    ¿Qué campo quiere modificar?");
			System.out.println("        1. Nombre");
			System.out.println("        2. Tiempo jugado");
			System.out.print("    Seleccione una opción: ");
			campoModificar = leer.nextInt();
			leer.nextLine();
		} while (campoModificar < 1 || campoModificar > 2);

		System.out.print("    Introduzca el nuevo valor: ");
		nuevoValor = leer.nextLine();

		switch (campoModificar) {
		case 1:
			game.setNombre(nuevoValor);
		case 2:
			Time tiempo = Time.valueOf(nuevoValor);
			game.setTiempoJugado(tiempo);
		}

		System.out.println("    Datos después de la modificación: " + game);

		do {
			System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.actualizarGame(game)) {
				System.out.println("    Game actualizado correctamente");
			} else {
				System.err.println("    No se pudo actualizar el Game");
			}
		} else {
			System.out.println("    Cambios cancelados");
		}
	}

	/**
	 * Método para modificar una Compra
	 */
	private static void modificarCompra() {
		int id = 0;
		String respuesta = "";
		String nuevoValor = "";
		int campoModificar = 0;

		System.out.println("MODIFICAR COMPRA");
		System.out.print("Introduzca el ID de la Compra a modificar: ");
		id = leer.nextInt();
		leer.nextLine();

		Compras compra = CRUD.buscarCompraPorId(id);

		if (compra == null) {
			System.err.println("    No se encontró la Compra con ID: " + id);
			return;
		}

		System.out.println("    Compra encontrada: " + compra);

		do {
			System.out.println("    ¿Qué campo quiere modificar?");
			System.out.println("        1. Cosa");
			System.out.println("        2. Precio");
			System.out.println("        3. Fecha de compra");
			System.out.print("    Seleccione una opción: ");
			campoModificar = leer.nextInt();
			leer.nextLine();
		} while (campoModificar < 1 || campoModificar > 3);

		System.out.print("    Introduzca el nuevo valor: ");
		nuevoValor = leer.nextLine();

		switch (campoModificar) {
		case 1:
			compra.setCosa(nuevoValor);
		case 2:
			BigDecimal precio = new BigDecimal(nuevoValor);
			compra.setPrecio(precio);
		case 3:
			Date fecha = Date.valueOf(nuevoValor);
			compra.setFechaCompra(fecha);
		}

		System.out.println("    Datos después de la modificación: " + compra);

		do {
			System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.actualizarCompra(compra)) {
				System.out.println("    Compra actualizada correctamente");
			} else {
				System.err.println("    No se pudo actualizar la Compra");
			}
		} else {
			System.out.println("    Cambios cancelados");
		}
	}

	/**
	 * Método para borrar un Player
	 */
	private static void borrarPlayer() {
		int id = 0;
		String respuesta = "";

		System.out.println("BORRAR PLAYER");
		System.out.print("Introduzca el ID del Player a borrar: ");
		id = leer.nextInt();
		leer.nextLine();

		Player player = CRUD.buscarPlayerPorId(id);

		if (player == null) {
			System.err.println("    No se encontró el Player con ID: " + id);
			return;
		}

		System.out.println("    Player a borrar: " + player);

		do {
			System.out.print("    ¿Está seguro de que quiere borrar este Player? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.eliminarPlayer(id)) {
				System.out.println("    Player borrado correctamente");
			} else {
				System.err.println("    No se pudo borrar el Player");
			}
		} else {
			System.out.println("    Operación cancelada");
		}
	}

	/**
	 * Método para borrar un Game
	 */
	private static void borrarGame() {
		int id = 0;
		String respuesta = "";

		System.out.println("BORRAR GAME");
		System.out.print("Introduzca el ID del Game a borrar: ");
		id = leer.nextInt();
		leer.nextLine();

		Game game = CRUD.buscarGamePorId(id);

		if (game == null) {
			System.err.println("    No se encontró el Game con ID: " + id);
			return;
		}

		System.out.println("    Game a borrar: " + game);

		do {
			System.out.print("    ¿Está seguro de que quiere borrar este Game? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.eliminarGame(id)) {
				System.out.println("    Game borrado correctamente");
			} else {
				System.err.println("    No se pudo borrar el Game");
			}
		} else {
			System.out.println("    Operación cancelada");
		}
	}

	/**
	 * Método para borrar una Compra
	 */
	private static void borrarCompra() {
		int id = 0;
		String respuesta = "";

		System.out.println("BORRAR COMPRA");
		System.out.print("Introduzca el ID de la Compra a borrar: ");
		id = leer.nextInt();
		leer.nextLine();

		Compras compra = CRUD.buscarCompraPorId(id);

		if (compra == null) {
			System.err.println("    No se encontró la Compra con ID: " + id);
			return;
		}

		System.out.println("    Compra a borrar: " + compra);

		do {
			System.out.print("    ¿Está seguro de que quiere borrar esta Compra? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			if (CRUD.eliminarCompra(id)) {
				System.out.println("    Compra borrada correctamente");
			} else {
				System.err.println("    No se pudo borrar la Compra");
			}
		} else {
			System.out.println("    Operación cancelada");
		}
	}

	/**
	 * Método para borrar todos los registros de una tabla
	 */
	private static void borrarTodosRegistros() {
		int opcion = 0;
		String respuesta = "";
		String nombreTabla = "";
		int registrosEliminados = 0;

		System.out.println("BORRAR TODOS LOS REGISTROS");
		do {
			System.out.println("    ¿De qué tabla quiere borrar TODOS los registros?");
			System.out.println("        1. Player");
			System.out.println("        2. Game");
			System.out.println("        3. Compras");
			System.out.print("    Seleccione una opción: ");
			opcion = leer.nextInt();
			leer.nextLine();
		} while (opcion < 1 || opcion > 3);

		switch (opcion) {
		case 1:
			nombreTabla = "Player";
		case 2:
			nombreTabla = "Game";
		case 3:
			nombreTabla = "Compras";
		}

		do {
			System.out
					.print("    ¿Está seguro de que quiere borrar TODOS los registros de " + nombreTabla + "? (S/N): ");
			respuesta = leer.nextLine();
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

		if (respuesta.equalsIgnoreCase("S")) {
			registrosEliminados = CRUD.eliminarTodosRegistros(nombreTabla);
			if (registrosEliminados >= 0) {
				System.out.println("    Se eliminaron " + registrosEliminados + " registros de " + nombreTabla);
			} else {
				System.err.println("    No se pudieron eliminar los registros");
			}
		} else {
			System.out.println("    Operación cancelada");
		}
	}
}