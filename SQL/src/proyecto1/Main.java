package proyecto1;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
			
			try (Connection conexion = Conexion.obtenerConexion()) {
				System.out.println("Se ha conectado a MySQL correctamente");
			} catch (SQLException e) {
				System.err.println("Error de conexión: " + e.getMessage());
			}

		}

}
