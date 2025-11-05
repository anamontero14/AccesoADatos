package proyecto1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	// clase connection para poder conectarse a la base de datos MySQL
	public static Connection obtenerConexion() throws SQLException {
		// url de la bbdd
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_ana_montero";
		// usuario de la bbdd
		String usuario = "ana_montero";
		// contraseña del usuario
		String clave = "12345";

		// objeto conexion que establece la conexion con la bbdd
		Connection conexion = DriverManager.getConnection(url, usuario, clave);
		// se devuelve dicha conexion
		return conexion;
	}
}
