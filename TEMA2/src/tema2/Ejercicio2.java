package tema2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio2 {

	public static void main(String[] args) {

		// constante que almacena la ruta del archivo
		final String RUTA_ARCHIVO = "C:\\Users\\ana.montero\\carpetas.txt";

		// constante que contiene el indice de la pagina html
		final String CONTENIDO_ARCHIVO = "<html>\r\n" + "   <head>\r\n"
				+ "      <title> [Nombre de la carpeta] </title>\r\n" + "   </head>\r\n" + "   <body>\r\n"
				+ "      <h1>[Ruta + nombre de la carpeta]</h1>\r\n" + "      <h3>Autor: [nombre_del_alumno]</h3>\r\n"
				+ "   </body>\r\n" + "</html>";

		// variable que almacena la ruta que se lee
		String linea = "";

		// buffered reader para poder empezar a leer el fichero especificado
		try (BufferedReader leerFichero = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {

			// leer la primera línea y comprobar que esta no sea 0
			linea = leerFichero.readLine();

			// mientras que la línea no sea 0
			while (linea != null) {

				// este try catch me sirve para crear una variable con la que escribir en un
				// fichero especifico, este fichero se creará en la carpeta users -> ana.montero
				// -> la ruta que lea desde el archivo y el cuál se llamará index
				try (BufferedWriter escribirFichero = new BufferedWriter(
						new FileWriter("C:\\Users\\ana.montero\\" + linea + "\\index.html", true))) {

					// si el nombre no es nulo
					if (CONTENIDO_ARCHIVO != null) {
						// escribo el nombre en el fichero
						escribirFichero.write(CONTENIDO_ARCHIVO);
						// indico que el contenido se ha podido agregar al fichero exitosamente
						System.out.println("El contenido se agregó al index.html");
					}

					// cerrar el flujo
					escribirFichero.flush();
					escribirFichero.close();

				} catch (IOException e) {
					System.err.println("Error: " + e.getMessage());
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
