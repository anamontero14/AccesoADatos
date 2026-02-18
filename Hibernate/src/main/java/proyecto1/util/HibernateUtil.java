package proyecto1.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import proyecto1.entidades.Compras;
import proyecto1.entidades.Game;
import proyecto1.entidades.Player;

/**
 * clase utilitaria para gestionar la configuración de Hibernate se encarga de
 * crear y proporcionar la SessionFactory
 */
public class HibernateUtil {

	// variable estática que almacena la única instancia de SessionFactory
	private static SessionFactory sessionFactory;

	// bloque estático que se ejecuta una sola vez al cargar la clase
	// inicializa la SessionFactory
	static {
		// se llama al método que construye la SessionFactory
		sessionFactory = construirSessionFactory();
	}

	/**
	 * método privado que construye y configura la SessionFactory de Hibernate
	 * establece todas las propiedades de conexión y configuración
	 * 
	 * @return la SessionFactory configurada o null si hay error
	 */
	private static SessionFactory construirSessionFactory() {
		// variable que almacenará la factory construida
		SessionFactory factory = null;

		try {
			// se crea una nueva instancia de Configuration
			Configuration configuration = new Configuration();

			// configuración del driver JDBC para MySQL
			configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

			// url de conexión a la base de datos
			configuration.setProperty("hibernate.connection.url",
					"jdbc:mysql://dns11036.phdns11.es:3306/ad2526_ana_montero");

			// nombre de usuario para conectarse a la base de datos
			configuration.setProperty("hibernate.connection.username", "ad2526_ana_montero");

			// contraseña del usuario
			configuration.setProperty("hibernate.connection.password", "12345");

			// dialecto de MySQL que usará Hibernate para generar el SQL
			configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

			// modo de actualización automática del esquema
			// update: actualiza el esquema sin perder datos
			configuration.setProperty("hibernate.hbm2ddl.auto", "update");

			// muestra las sentencias SQL que ejecuta Hibernate en la consola
			configuration.setProperty("hibernate.show_sql", "true");

			// formatea el SQL mostrado para que sea más legible
			configuration.setProperty("hibernate.format_sql", "true");

			// configuración del pool de conexiones C3P0
			// número mínimo de conexiones en el pool
			configuration.setProperty("hibernate.c3p0.min_size", "5");

			// número máximo de conexiones en el pool
			configuration.setProperty("hibernate.c3p0.max_size", "20");

			// tiempo en segundos antes de que una conexión expire
			configuration.setProperty("hibernate.c3p0.timeout", "300");

			// número máximo de sentencias preparadas en caché
			configuration.setProperty("hibernate.c3p0.max_statements", "50");

			// tiempo en segundos para probar conexiones inactivas
			configuration.setProperty("hibernate.c3p0.idle_test_period", "3000");

			// se agregan las clases entidad anotadas que Hibernate debe mapear
			configuration.addAnnotatedClass(Player.class);
			configuration.addAnnotatedClass(Game.class);
			configuration.addAnnotatedClass(Compras.class);

			// se construye la SessionFactory con toda la configuración
			factory = configuration.buildSessionFactory();

		} catch (Exception e) {
			// si ocurre algún error durante la construcción se muestra el mensaje
			System.err.println("Error al crear SessionFactory: " + e.getMessage());
			// se imprime el stacktrace completo para depuración
			e.printStackTrace();
		}

		// se devuelve la factory construida (puede ser null si hubo error)
		return factory;
	}

	/**
	 * método público estático para obtener la SessionFactory permite acceder a la
	 * única instancia de SessionFactory desde cualquier clase
	 * 
	 * @return la SessionFactory configurada
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * método para cerrar la SessionFactory cuando ya no se necesite debe llamarse
	 * al finalizar la aplicación para liberar recursos
	 */
	public static void cerrarSessionFactory() {
		// se verifica que la factory exista y no esté cerrada
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			// se cierra la SessionFactory
			sessionFactory.close();
		}
	}

	/**
	 * método para verificar si la conexión con Hibernate es exitosa
	 * 
	 * @return true si la conexión está activa, false en caso contrario
	 */
	public static boolean verificarConexion() {
		// variable que almacena el resultado de la verificación
		boolean exito = false;

		// se verifica que la factory exista y no esté cerrada
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			// si la factory está activa la conexión es exitosa
			exito = true;
		}

		// se devuelve el resultado
		return exito;
	}
}