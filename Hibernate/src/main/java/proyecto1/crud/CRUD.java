package proyecto1.crud;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import proyecto1.entidades.Compras;
import proyecto1.entidades.Game;
import proyecto1.entidades.Player;
import proyecto1.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * clase que implementa las operaciones CRUD para las entidades usa Hibernate
 * para interactuar con la base de datos
 */
public class CRUD {

	/**
	 * método que verifica si la conexión con Hibernate está activa
	 * 
	 * @return true si la conexión es exitosa, false en caso contrario
	 */
	public static boolean conectar() {
		// variable que almacena el resultado de la verificación
		boolean resultado = false;

		// se llama al método de HibernateUtil que verifica la conexión
		resultado = HibernateUtil.verificarConexion();

		// se devuelve el resultado
		return resultado;
	}

	/**
	 * método para insertar un nuevo Player en la base de datos
	 * 
	 * @param player el objeto Player a insertar
	 * @return true si se insertó correctamente, false en caso contrario
	 */
	public static boolean insertarPlayer(Player player) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se persiste el objeto player en la base de datos
			session.persist(player);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al insertar Player: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para insertar un nuevo Game en la base de datos
	 * 
	 * @param game el objeto Game a insertar
	 * @return true si se insertó correctamente, false en caso contrario
	 */
	public static boolean insertarGame(Game game) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se persiste el objeto game en la base de datos
			session.persist(game);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al insertar Game: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para insertar una nueva Compra en la base de datos
	 * 
	 * @param compra el objeto Compras a insertar
	 * @return true si se insertó correctamente, false en caso contrario
	 */
	public static boolean insertarCompras(Compras compra) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se persiste el objeto compra en la base de datos
			session.persist(compra);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al insertar Compra: " + e.getMessage());
			System.err.println("Verifique que el Player y el Game existen en la base de datos.");
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para listar todos los Players de la base de datos
	 * 
	 * @return lista con todos los Players encontrados
	 */
	public static List<Player> listarPlayers() {
		// lista que almacenará los players encontrados
		List<Player> players = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL para obtener todos los Players
			Query<Player> query = session.createQuery("FROM Player", Player.class);
			// se ejecuta la query y se obtiene la lista de resultados
			players = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Players: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de players
		return players;
	}

	/**
	 * método para listar Players filtrados por nick usando LIKE
	 * 
	 * @param nick el nick a buscar (se busca con LIKE %nick%)
	 * @return lista con los Players que coinciden
	 */
	public static List<Player> listarPlayersPorNick(String nick) {
		// lista que almacenará los players encontrados
		List<Player> players = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL con filtro LIKE
			Query<Player> query = session.createQuery("FROM Player WHERE nick LIKE :nick", Player.class);
			// se asigna el parámetro con comodines para búsqueda parcial
			query.setParameter("nick", "%" + nick + "%");
			// se ejecuta la query y se obtiene la lista de resultados
			players = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Players por nick: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de players encontrados
		return players;
	}

	/**
	 * método para listar todos los Game de la base de datos
	 * 
	 * @return lista con todos los Game encontrados
	 */
	public static List<Game> listarGame() {
		// lista que almacenará los Game encontrados
		List<Game> games = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL para obtener todos los Game
			Query<Game> query = session.createQuery("FROM Game", Game.class);
			// se ejecuta la query y se obtiene la lista de resultados
			games = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Game: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de games
		return games;
	}

	/**
	 * método para listar Game filtrados por nombre usando LIKE
	 * 
	 * @param nombre el nombre a buscar (se busca con LIKE %nombre%)
	 * @return lista con los Game que coinciden
	 */
	public static List<Game> listarGamePorNombre(String nombre) {
		// lista que almacenará los games encontrados
		List<Game> games = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL con filtro LIKE
			Query<Game> query = session.createQuery("FROM Game WHERE nombre LIKE :nombre", Game.class);
			// se asigna el parámetro con comodines para búsqueda parcial
			query.setParameter("nombre", "%" + nombre + "%");
			// se ejecuta la query y se obtiene la lista de resultados
			games = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Game por nombre: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de games encontrados
		return games;
	}

	/**
	 * método para listar todas las Compras de la base de datos
	 * 
	 * @return lista con todas las Compras encontradas
	 */
	public static List<Compras> listarCompras() {
		// lista que almacenará las compras encontradas
		List<Compras> compras = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL para obtener todas las Compras
			Query<Compras> query = session.createQuery("FROM Compras", Compras.class);
			// se ejecuta la query y se obtiene la lista de resultados
			compras = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Compras: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de compras
		return compras;
	}

	/**
	 * método para listar Compras filtradas por cosa usando LIKE
	 * 
	 * @param cosa la cosa a buscar (se busca con LIKE %cosa%)
	 * @return lista con las Compras que coinciden
	 */
	public static List<Compras> listarComprasPorCosa(String cosa) {
		// lista que almacenará las compras encontradas
		List<Compras> compras = new ArrayList<>();
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se crea una query HQL con filtro LIKE
			Query<Compras> query = session.createQuery("FROM Compras WHERE cosa LIKE :cosa", Compras.class);
			// se asigna el parámetro con comodines para búsqueda parcial
			query.setParameter("cosa", "%" + cosa + "%");
			// se ejecuta la query y se obtiene la lista de resultados
			compras = query.list();

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al listar Compras por cosa: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la lista de compras encontradas
		return compras;
	}

	/**
	 * método para buscar un Player específico por su ID
	 * 
	 * @param id el ID del Player a buscar
	 * @return el Player encontrado o null si no existe
	 */
	public static Player buscarPlayerPorId(int id) {
		// variable que almacenará el player encontrado
		Player player = null;
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se busca el player por su clave primaria
			player = session.get(Player.class, id);

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al buscar Player por ID: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el player encontrado (puede ser null)
		return player;
	}

	/**
	 * método para buscar un Game específico por su ID
	 * 
	 * @param id el ID del Game a buscar
	 * @return el Game encontrado o null si no existe
	 */
	public static Game buscarGamePorId(int id) {
		// variable que almacenará el game encontrado
		Game game = null;
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se busca el game por su clave primaria
			game = session.get(Game.class, id);

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al buscar Game por ID: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el game encontrado (puede ser null)
		return game;
	}

	/**
	 * método para buscar una Compra específica por su ID
	 * 
	 * @param id el ID de la Compra a buscar
	 * @return la Compra encontrada o null si no existe
	 */
	public static Compras buscarCompraPorId(int id) {
		// variable que almacenará la compra encontrada
		Compras compra = null;
		// variable para almacenar la sesión de Hibernate
		Session session = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se busca la compra por su clave primaria
			compra = session.get(Compras.class, id);

		} catch (Exception e) {
			// se muestra mensaje de error al usuario
			System.err.println("Error al buscar Compra por ID: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve la compra encontrada (puede ser null)
		return compra;
	}

	/**
	 * método para actualizar un Player existente en la base de datos
	 * 
	 * @param player el Player con los datos actualizados
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public static boolean actualizarPlayer(Player player) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se actualiza el objeto player en la base de datos
			session.merge(player);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al actualizar Player: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para actualizar un Game existente en la base de datos
	 * 
	 * @param game el Game con los datos actualizados
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public static boolean actualizarGame(Game game) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se actualiza el objeto game en la base de datos
			session.merge(game);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al actualizar Game: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para actualizar una Compra existente en la base de datos
	 * 
	 * @param compra la Compra con los datos actualizados
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public static boolean actualizarCompra(Compras compra) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se actualiza el objeto compra en la base de datos
			session.merge(compra);

			// se confirma la transacción
			transaction.commit();
			// si llega aquí es que todo fue bien
			exito = true;

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al actualizar Compra: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para eliminar un Player por su ID
	 * 
	 * @param id el ID del Player a eliminar
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public static boolean eliminarPlayer(int id) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se busca el player por su ID
			Player player = session.get(Player.class, id);
			// se verifica que el player existe
			if (player != null) {
				// se elimina el player de la base de datos
				session.remove(player);
				// se marca como exitoso
				exito = true;
			}

			// se confirma la transacción
			transaction.commit();

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al eliminar Player: " + e.getMessage());
			System.err.println("No se puede eliminar un Player que tiene Compras asociadas.");
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para eliminar un Game por su ID
	 * 
	 * @param id el ID del Game a eliminar
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public static boolean eliminarGame(int id) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se busca el game por su ID
			Game game = session.get(Game.class, id);
			// se verifica que el game existe
			if (game != null) {
				// se elimina el game de la base de datos
				session.remove(game);
				// se marca como exitoso
				exito = true;
			}

			// se confirma la transacción
			transaction.commit();

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al eliminar Game: " + e.getMessage());
			System.err.println("No se puede eliminar un Game que tiene Compras asociadas.");
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para eliminar una Compra por su ID
	 * 
	 * @param id el ID de la Compra a eliminar
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public static boolean eliminarCompra(int id) {
		// variable que almacena si la operación fue exitosa
		boolean exito = false;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se busca la compra por su ID
			Compras compra = session.get(Compras.class, id);
			// se verifica que la compra existe
			if (compra != null) {
				// se elimina la compra de la base de datos
				session.remove(compra);
				// se marca como exitoso
				exito = true;
			}

			// se confirma la transacción
			transaction.commit();

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al eliminar Compra: " + e.getMessage());
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el resultado de la operación
		return exito;
	}

	/**
	 * método para eliminar todos los registros de una tabla específica
	 * 
	 * @param nombreTabla el nombre de la tabla (Player, Game o Compras)
	 * @return el número de registros eliminados, -1 si hubo error
	 */
	public static int eliminarTodosRegistros(String nombreTabla) {
		// variable que almacena el número de registros eliminados
		int registrosEliminados = -1;
		// variable para almacenar la sesión de Hibernate
		Session session = null;
		// variable para gestionar la transacción
		Transaction transaction = null;

		try {
			// se abre una nueva sesión de Hibernate
			session = HibernateUtil.getSessionFactory().openSession();
			// se inicia una transacción
			transaction = session.beginTransaction();

			// se construye la query HQL para eliminar todos los registros
			String hql = "DELETE FROM " + nombreTabla;
			// se crea la query
			Query query = session.createQuery(hql);
			// se ejecuta la query y se obtiene el número de registros afectados
			registrosEliminados = query.executeUpdate();

			// se confirma la transacción
			transaction.commit();

		} catch (Exception e) {
			// si hay error se hace rollback de la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			// se muestra mensaje de error al usuario
			System.err.println("Error al eliminar todos los registros de " + nombreTabla + ": " + e.getMessage());
			System.err.println("Verifique las restricciones de integridad referencial.");
			// se imprime el stacktrace para depuración
			e.printStackTrace();
		} finally {
			// se cierra la sesión si está abierta
			if (session != null) {
				session.close();
			}
		}

		// se devuelve el número de registros eliminados
		return registrosEliminados;
	}
}