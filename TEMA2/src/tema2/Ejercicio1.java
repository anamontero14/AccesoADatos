package tema2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {

	public static void main(String[] args) {

		// constante que almacena la ruta del archivo
		final String RUTA_ARCHIVO = "C:\\Users\\ana.montero\\carpetas.txt";

		// variable que almacena la ruta que se lee
		String linea = "";

		// buffered reader para poder empezar a leer el fichero especificado
		try (BufferedReader leerFichero = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {

			// leer la primera línea y comprobar que esta no sea 0
			linea = leerFichero.readLine();

			// mientras que la línea no sea 0
			while (linea != null) {
				
				//se crea un directorio nuevo en la ruta especificada
				File carpetasAnidadas = new File("C:\\Users\\ana.montero\\" + linea);
				
				//si mkdir crea la carpeta correctamente
				if (carpetasAnidadas.mkdirs()) {
					//muestra un mensaje
		            System.out.println("Directorios anidados creados");
		        } else {
		        	//muestra un mensaje de error
		            System.err.println("No se pudieron crear los directorios o ya existen");
		        }

				// leer la primera línea y comprobar que esta no sea 0
				linea = leerFichero.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
