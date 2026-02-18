package proyecto1.main;

// import de las entidades y utilidades necesarias
import proyecto1.entidades.Compras;
import proyecto1.entidades.Game;
import proyecto1.entidades.Player;
import proyecto1.util.HibernateUtil;
import proyecto1.crud.CRUD;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    // scanner para leer entrada por consola
    private static Scanner leer = new Scanner(System.in);

    // metodo principal que arranca la aplicacion
    public static void main(String[] args) {

        // variables para controlar el menu y opciones internas
        int opcion = 0;
        int opcionInterna = 0;
        boolean conexionOk = false;

        // verificar conexion con hibernate al inicio
        conexionOk = CRUD.conectar();
        if (conexionOk) {
            // si la conexion es correcta mostramos mensajes informativos
            System.out.println("Conexión exitosa con Hibernate");
            System.out.println("Las tablas se crearán automáticamente si no existen");
            System.out.println();
        } else {
            // si no hay conexion informamos y cerramos recursos
            System.err.println("Error al conectar con Hibernate");
            System.out.println("FIN DEL PROGRAMA");
            HibernateUtil.cerrarSessionFactory();
            leer.close();
            return;
        }

        // bucle principal del menu, se repite mientras la opcion sea valida
        do {
            menu();
            System.out.print("Selecciona una opción: ");
            opcion = leer.nextInt();
            leer.nextLine();

            // switch principal para elegir la accion
            switch (opcion) {
                case 1 -> {
                    // opcion insertar datos en tablas
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

                    // ejecutar solo la opcion seleccionada
                    switch (opcionInterna) {
                        case 1 -> insertarPlayer();
                        case 2 -> insertarGame();
                        case 3 -> insertarCompra();
                    }
                }
                case 2 -> {
                    // opcion listar datos de tablas
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

                    // ejecutar solo la opcion seleccionada
                    switch (opcionInterna) {
                        case 1 -> listarPlayers();
                        case 2 -> listarGame();
                        case 3 -> listarCompras();
                    }
                }
                case 3 -> {
                    // opcion modificar datos de tablas
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

                    // ejecutar solo la opcion seleccionada
                    switch (opcionInterna) {
                        case 1 -> modificarPlayer();
                        case 2 -> modificarGame();
                        case 3 -> modificarCompra();
                    }
                }
                case 4 -> {
                    // opcion borrar datos de tablas
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

                    // ejecutar solo la opcion seleccionada
                    switch (opcionInterna) {
                        case 1 -> borrarPlayer();
                        case 2 -> borrarGame();
                        case 3 -> borrarCompra();
                        case 4 -> borrarTodosRegistros();
                    }
                }
                case 5 -> System.out.println("Saliendo del programa...");
                default -> System.err.println("Opción no válida");
            }

            System.out.println();

        } while (opcion >= 1 && opcion <= 4);

        // fin del programa, cerramos recursos
        System.out.println("FIN DEL PROGRAMA");
        HibernateUtil.cerrarSessionFactory();
        leer.close();
    }

    // metodo que muestra el menu principal
    private static void menu() {
        System.out.println("ESCOJA UNA OPCIÓN:");
        System.out.println("    1. Insertar datos en tablas");
        System.out.println("    2. Listar datos de tablas");
        System.out.println("    3. Modificar datos de tablas");
        System.out.println("    4. Borrar datos de tablas");
        System.out.println("    5. Salir");
    }

    // metodo para insertar un nuevo player en la base de datos
    private static void insertarPlayer() {
        // inicializacion de variables al inicio del metodo
        String nick = "";
        String password = "";
        String email = "";
        boolean ok = false;

        // pedir datos al usuario
        System.out.println("INSERTAR NUEVO PLAYER");
        System.out.print("Introduzca el nick del jugador: ");
        nick = leer.nextLine();
        System.out.print("Introduzca la contraseña del jugador: ");
        password = leer.nextLine();
        System.out.print("Introduzca el email del jugador: ");
        email = leer.nextLine();

        // crear objeto player y llamar al crud para insertarlo
        Player player = new Player(nick, password, email);
        ok = CRUD.insertarPlayer(player);

        // informar resultado
        if (ok) {
            System.out.println("    Player insertado correctamente con ID: " + player.getIdPlayer());
        } else {
            System.err.println("    No se pudo insertar el Player");
        }
    }

    // metodo para insertar un nuevo game en la base de datos
    private static void insertarGame() {
        // inicializacion de variables
        String nombre = "";
        String tiempoJugado = "";
        boolean ok = false;

        // pedir datos al usuario
        System.out.println("INSERTAR NUEVO GAME");
        System.out.print("Introduzca el nombre del juego: ");
        nombre = leer.nextLine();
        System.out.print("Introduzca el tiempo jugado (HH:MM:SS) - horas < 24: ");
        tiempoJugado = leer.nextLine();

        // validar formato de tiempo para evitar que java.sql.Time haga wrap around
        boolean tiempoValido = validarTiempoFormato(tiempoJugado);
        if (!tiempoValido) {
            // si no es valido informamos y no insertamos
            System.err.println("    Formato de tiempo invalido o horas >= 24. Insercion cancelada.");
        } else {
            // si es valido convertimos a Time y guardamos
            Time tiempo = Time.valueOf(tiempoJugado);
            Game game = new Game(nombre, tiempo);
            ok = CRUD.insertarGame(game);
            if (ok) {
                System.out.println("    Game insertado correctamente con ID: " + game.getIdGame());
            } else {
                System.err.println("    No se pudo insertar el Game");
            }
        }
    }

    // metodo para insertar una nueva compra en la base de datos
    private static void insertarCompra() {
        // inicializacion de variables
        String nickPlayer = "";
        String nombreGame = "";
        String cosa = "";
        String precioStr = "";
        String fechaCompraStr = "";
        int idPlayerSeleccionado = 0;
        int idGameSeleccionado = 0;
        boolean proceed = true;
        boolean ok = false;

        // pedir nick del jugador y buscar coincidencias
        System.out.println("INSERTAR NUEVA COMPRA");
        System.out.print("Introduzca el nick del jugador: ");
        nickPlayer = leer.nextLine();
        List<Player> playersEncontrados = CRUD.listarPlayersPorNick(nickPlayer);

        // si no hay coincidencias cancelamos
        if (playersEncontrados.isEmpty()) {
            System.err.println("    No se encontro ningun jugador con ese nick");
            proceed = false;
        } else if (playersEncontrados.size() == 1) {
            // si hay una coincidencia la usamos
            idPlayerSeleccionado = playersEncontrados.get(0).getIdPlayer();
            System.out.println("    Player seleccionado: " + playersEncontrados.get(0).getNick());
        } else {
            // si hay varias coincidencias pedimos seleccionar una
            System.out.println("    Se encontraron varios jugadores:");
            for (int i = 0; i < playersEncontrados.size(); i++) {
                System.out.println("        " + (i + 1) + ". " + playersEncontrados.get(i));
            }
            System.out.print("    Seleccione el numero del jugador: ");
            int seleccion = leer.nextInt();
            leer.nextLine();
            if (seleccion >= 1 && seleccion <= playersEncontrados.size()) {
                idPlayerSeleccionado = playersEncontrados.get(seleccion - 1).getIdPlayer();
            } else {
                System.err.println("    Seleccion invalida");
                proceed = false;
            }
        }

        // buscar game por nombre si seguimos adelante
        if (proceed) {
            System.out.print("Introduzca el nombre del juego: ");
            nombreGame = leer.nextLine();
            List<Game> gamesEncontrados = CRUD.listarGamePorNombre(nombreGame);

            if (gamesEncontrados.isEmpty()) {
                System.err.println("    No se encontro ningun juego con ese nombre");
                proceed = false;
            } else if (gamesEncontrados.size() == 1) {
                idGameSeleccionado = gamesEncontrados.get(0).getIdGame();
                System.out.println("    Game seleccionado: " + gamesEncontrados.get(0).getNombre());
            } else {
                System.out.println("    Se encontraron varios juegos:");
                for (int i = 0; i < gamesEncontrados.size(); i++) {
                    System.out.println("        " + (i + 1) + ". " + gamesEncontrados.get(i));
                }
                System.out.print("    Seleccione el numero del juego: ");
                int seleccion = leer.nextInt();
                leer.nextLine();
                if (seleccion >= 1 && seleccion <= gamesEncontrados.size()) {
                    idGameSeleccionado = gamesEncontrados.get(seleccion - 1).getIdGame();
                } else {
                    System.err.println("    Seleccion invalida");
                    proceed = false;
                }
            }
        }

        // obtener objetos completos player y game y validar existencia
        Player player = null;
        Game game = null;
        if (proceed) {
            player = CRUD.buscarPlayerPorId(idPlayerSeleccionado);
            game = CRUD.buscarGamePorId(idGameSeleccionado);
            if (player == null) {
                System.err.println("    Player seleccionado no existe");
                proceed = false;
            }
            if (game == null) {
                System.err.println("    Game seleccionado no existe");
                proceed = false;
            }
        }

        // pedir resto de datos si todo ok
        if (proceed) {
            System.out.print("Introduzca la cosa: ");
            cosa = leer.nextLine();
            System.out.print("Introduzca el precio: ");
            precioStr = leer.nextLine();
            System.out.print("Introduzca la fecha de compra (YYYY-MM-DD): ");
            fechaCompraStr = leer.nextLine();

            double precio = 0.0;
            Date fechaCompra = null;
            try {
                // parseo de precio y fecha
                precio = Double.parseDouble(precioStr);
                fechaCompra = Date.valueOf(fechaCompraStr);
            } catch (Exception e) {
                // si hay error en el parseo cancelamos la insercion
                System.err.println("    Formato de precio o fecha invalido. Insercion cancelada.");
                proceed = false;
            }

            // si todo es correcto creamos la compra y la insertamos
            if (proceed) {
                Compras compra = new Compras(player, game, cosa, precio, fechaCompra);
                ok = CRUD.insertarCompras(compra);
                if (ok) {
                    System.out.println("    Compra insertada correctamente con ID: " + compra.getIdCompra());
                } else {
                    System.err.println("    No se pudo insertar la Compra");
                }
            }
        }

        // si en algun punto se cancelo la operacion informamos
        if (!proceed) {
            System.out.println("    Operacion de insercion cancelada.");
        }
    }

    // metodo para listar players, con opcion de filtrar por nick
    private static void listarPlayers() {
        String respuesta = "";
        String nick = "";
        List<Player> players = null;

        System.out.println("LISTAR PLAYERS");
        do {
            System.out.print("¿Quiere filtrar por nick? (S/N): ");
            respuesta = leer.nextLine();
        } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

        // si el usuario quiere filtrar pedimos el nick y usamos el metodo correspondiente
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduzca el nick a buscar: ");
            nick = leer.nextLine();
            players = CRUD.listarPlayersPorNick(nick);
        } else {
            // si no, listamos todos los players
            players = CRUD.listarPlayers();
        }

        // mostramos resultados o mensaje si no hay registros
        if (players == null || players.isEmpty()) {
            System.out.println("    No se encontraron registros");
        } else {
            System.out.println("    PLAYERS ENCONTRADOS:");
            for (Player p : players) {
                System.out.println("    " + p);
            }
        }
    }

    // metodo para listar games, con opcion de filtrar por nombre
    private static void listarGame() {
        String respuesta = "";
        String nombre = "";
        List<Game> games = null;

        System.out.println("LISTAR GAMES");
        do {
            System.out.print("¿Quiere filtrar por nombre? (S/N): ");
            respuesta = leer.nextLine();
        } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

        // si el usuario quiere filtrar pedimos el nombre y usamos el metodo correspondiente
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduzca el nombre a buscar: ");
            nombre = leer.nextLine();
            games = CRUD.listarGamePorNombre(nombre);
        } else {
            // si no, listamos todos los games
            games = CRUD.listarGame();
        }

        // mostramos resultados o mensaje si no hay registros
        if (games == null || games.isEmpty()) {
            System.out.println("    No se encontraron registros");
        } else {
            System.out.println("    GAMES ENCONTRADOS:");
            for (Game g : games) {
                System.out.println("    " + g);
            }
        }
    }

    // metodo para listar compras, con opcion de filtrar por cosa
    private static void listarCompras() {
        String respuesta = "";
        String cosa = "";
        List<Compras> compras = null;

        System.out.println("LISTAR COMPRAS");
        do {
            System.out.print("¿Quiere filtrar por cosa? (S/N): ");
            respuesta = leer.nextLine();
        } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

        // si el usuario quiere filtrar pedimos la cosa y usamos el metodo correspondiente
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduzca la cosa a buscar: ");
            cosa = leer.nextLine();
            compras = CRUD.listarComprasPorCosa(cosa);
        } else {
            // si no, listamos todas las compras
            compras = CRUD.listarCompras();
        }

        // mostramos resultados o mensaje si no hay registros
        if (compras == null || compras.isEmpty()) {
            System.out.println("    No se encontraron registros");
        } else {
            System.out.println("    COMPRAS ENCONTRADAS:");
            for (Compras c : compras) {
                System.out.println("    " + c);
            }
        }
    }

    // metodo para modificar un player existente
    private static void modificarPlayer() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        String nuevoValor = "";
        int campoModificar = 0;
        boolean proceed = true;
        boolean ok = false;

        System.out.println("MODIFICAR PLAYER");
        System.out.print("Introduzca el ID del Player a modificar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar player por id
        Player player = CRUD.buscarPlayerPorId(id);

        if (player == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro el Player con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Player encontrado: " + player);
        }

        // si existe pedimos que campo modificar y el nuevo valor
        if (proceed) {
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

            // aplicar cambio segun la opcion
            switch (campoModificar) {
                case 1 -> player.setNick(nuevoValor);
                case 2 -> player.setPassword(nuevoValor);
                case 3 -> player.setEmail(nuevoValor);
            }

            // mostrar datos despues de la modificacion
            System.out.println("    Datos despues de la modificacion: " + player);

            // confirmar cambios antes de actualizar en la base de datos
            do {
                System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
                respuesta = leer.nextLine();
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

            if (respuesta.equalsIgnoreCase("S")) {
                ok = CRUD.actualizarPlayer(player);
                if (ok) {
                    System.out.println("    Player actualizado correctamente");
                } else {
                    System.err.println("    No se pudo actualizar el Player");
                }
            } else {
                System.out.println("    Cambios cancelados");
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para modificar un game existente
    private static void modificarGame() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        String nuevoValor = "";
        int campoModificar = 0;
        boolean proceed = true;
        boolean ok = false;

        System.out.println("MODIFICAR GAME");
        System.out.print("Introduzca el ID del Game a modificar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar game por id
        Game game = CRUD.buscarGamePorId(id);

        if (game == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro el Game con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Game encontrado: " + game);
        }

        // si existe pedimos que campo modificar y el nuevo valor
        if (proceed) {
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

            // aplicar cambio segun la opcion
            switch (campoModificar) {
                case 1 -> game.setNombre(nuevoValor);
                case 2 -> {
                    // validar formato de tiempo antes de asignar
                    if (!validarTiempoFormato(nuevoValor)) {
                        System.err.println("    Formato de tiempo invalido o horas >= 24. Modificacion cancelada.");
                        proceed = false;
                    } else {
                        Time tiempo = Time.valueOf(nuevoValor);
                        game.setTiempoJugado(tiempo);
                    }
                }
            }

            // si todo ok confirmamos y actualizamos
            if (proceed) {
                System.out.println("    Datos despues de la modificacion: " + game);

                do {
                    System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
                    respuesta = leer.nextLine();
                } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

                if (respuesta.equalsIgnoreCase("S")) {
                    ok = CRUD.actualizarGame(game);
                    if (ok) {
                        System.out.println("    Game actualizado correctamente");
                    } else {
                        System.err.println("    No se pudo actualizar el Game");
                    }
                } else {
                    System.out.println("    Cambios cancelados");
                }
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para modificar una compra existente
    private static void modificarCompra() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        String nuevoValor = "";
        int campoModificar = 0;
        boolean proceed = true;
        boolean ok = false;

        System.out.println("MODIFICAR COMPRA");
        System.out.print("Introduzca el ID de la Compra a modificar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar compra por id
        Compras compra = CRUD.buscarCompraPorId(id);

        if (compra == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro la Compra con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Compra encontrada: " + compra);
        }

        // si existe pedimos que campo modificar y el nuevo valor
        if (proceed) {
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

            // aplicar cambio segun la opcion con validaciones
            switch (campoModificar) {
                case 1 -> compra.setCosa(nuevoValor);
                case 2 -> {
                    try {
                        double precio = Double.parseDouble(nuevoValor);
                        compra.setPrecio(precio);
                    } catch (Exception e) {
                        System.err.println("    Precio invalido. Modificacion cancelada.");
                        proceed = false;
                    }
                }
                case 3 -> {
                    try {
                        Date fecha = Date.valueOf(nuevoValor);
                        compra.setFechaCompra(fecha);
                    } catch (Exception e) {
                        System.err.println("    Fecha invalida. Modificacion cancelada.");
                        proceed = false;
                    }
                }
            }

            // si todo ok confirmamos y actualizamos
            if (proceed) {
                System.out.println("    Datos despues de la modificacion: " + compra);

                do {
                    System.out.print("    ¿Desea confirmar los cambios? (S/N): ");
                    respuesta = leer.nextLine();
                } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

                if (respuesta.equalsIgnoreCase("S")) {
                    ok = CRUD.actualizarCompra(compra);
                    if (ok) {
                        System.out.println("    Compra actualizada correctamente");
                    } else {
                        System.err.println("    No se pudo actualizar la Compra");
                    }
                } else {
                    System.out.println("    Cambios cancelados");
                }
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para borrar un player por id
    private static void borrarPlayer() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        boolean proceed = true;
        boolean ok = false;

        System.out.println("BORRAR PLAYER");
        System.out.print("Introduzca el ID del Player a borrar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar player por id
        Player player = CRUD.buscarPlayerPorId(id);

        if (player == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro el Player con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Player a borrar: " + player);
        }

        // confirmar borrado y ejecutar
        if (proceed) {
            do {
                System.out.print("    ¿Esta seguro de que quiere borrar este Player? (S/N): ");
                respuesta = leer.nextLine();
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

            if (respuesta.equalsIgnoreCase("S")) {
                ok = CRUD.eliminarPlayer(id);
                if (ok) {
                    System.out.println("    Player borrado correctamente");
                } else {
                    System.err.println("    No se pudo borrar el Player");
                }
            } else {
                System.out.println("    Operacion cancelada");
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para borrar un game por id
    private static void borrarGame() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        boolean proceed = true;
        boolean ok = false;

        System.out.println("BORRAR GAME");
        System.out.print("Introduzca el ID del Game a borrar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar game por id
        Game game = CRUD.buscarGamePorId(id);

        if (game == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro el Game con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Game a borrar: " + game);
        }

        // confirmar borrado y ejecutar
        if (proceed) {
            do {
                System.out.print("    ¿Esta seguro de que quiere borrar este Game? (S/N): ");
                respuesta = leer.nextLine();
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

            if (respuesta.equalsIgnoreCase("S")) {
                ok = CRUD.eliminarGame(id);
                if (ok) {
                    System.out.println("    Game borrado correctamente");
                } else {
                    System.err.println("    No se pudo borrar el Game");
                }
            } else {
                System.out.println("    Operacion cancelada");
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para borrar una compra por id
    private static void borrarCompra() {
        // inicializacion de variables
        int id = 0;
        String respuesta = "";
        boolean proceed = true;
        boolean ok = false;

        System.out.println("BORRAR COMPRA");
        System.out.print("Introduzca el ID de la Compra a borrar: ");
        id = leer.nextInt();
        leer.nextLine();

        // buscar compra por id
        Compras compra = CRUD.buscarCompraPorId(id);

        if (compra == null) {
            // si no existe cancelamos
            System.err.println("    No se encontro la Compra con ID: " + id);
            proceed = false;
        } else {
            System.out.println("    Compra a borrar: " + compra);
        }

        // confirmar borrado y ejecutar
        if (proceed) {
            do {
                System.out.print("    ¿Esta seguro de que quiere borrar esta Compra? (S/N): ");
                respuesta = leer.nextLine();
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

            if (respuesta.equalsIgnoreCase("S")) {
                ok = CRUD.eliminarCompra(id);
                if (ok) {
                    System.out.println("    Compra borrada correctamente");
                } else {
                    System.err.println("    No se pudo borrar la Compra");
                }
            } else {
                System.out.println("    Operacion cancelada");
            }
        } else {
            System.out.println("    Operacion cancelada");
        }
    }

    // metodo para borrar todos los registros de una tabla seleccionada
    private static void borrarTodosRegistros() {
        // inicializacion de variables
        int opcion = 0;
        String respuesta = "";
        String nombreTabla = "";
        int registrosEliminados = 0;

        System.out.println("BORRAR TODOS LOS REGISTROS");
        do {
            System.out.println("    ¿De que tabla quiere borrar TODOS los registros?");
            System.out.println("        1. Player");
            System.out.println("        2. Game");
            System.out.println("        3. Compras");
            System.out.print("    Seleccione una opcion: ");
            opcion = leer.nextInt();
            leer.nextLine();
        } while (opcion < 1 || opcion > 3);

        // asignar el nombre de la tabla segun la opcion
        switch (opcion) {
            case 1 -> nombreTabla = "Player";
            case 2 -> nombreTabla = "Game";
            case 3 -> nombreTabla = "Compras";
        }

        // confirmar la operacion antes de ejecutar
        do {
            System.out.print("    ¿Esta seguro de que quiere borrar TODOS los registros de " + nombreTabla + "? (S/N): ");
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
            System.out.println("    Operacion cancelada");
        }
    }

    // utilitario que valida que el tiempo tenga formato HH:MM:SS y horas < 24
    private static boolean validarTiempoFormato(String tiempo) {
        try {
            // dividir la cadena por dos puntos
            String[] partes = tiempo.split(":");
            if (partes.length != 3) {
                return false;
            }
            // parsear horas minutos y segundos
            int hh = Integer.parseInt(partes[0]);
            int mm = Integer.parseInt(partes[1]);
            int ss = Integer.parseInt(partes[2]);
            // validar valores no negativos
            if (hh < 0 || mm < 0 || ss < 0) {
                return false;
            }
            // validar minutos y segundos menores que 60
            if (mm >= 60 || ss >= 60) {
                return false;
            }
            // rechazamos horas mayores o iguales a 24 para evitar wrap around de java.sql.Time
            if (hh >= 24) {
                return false;
            }
            // usar LocalTime para validar limites
            LocalTime.of(hh, mm, ss);
            return true;
        } catch (Exception e) {
            // si ocurre cualquier error devolvemos false
            return false;
        }
    }
}
