package tema2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio3 {

	public static void main(String[] args) {

		// ruta del archivo a leer
		final String RUTA_ARCHIVO = "src\\tema2\\palabras.txt";
		
		//ruta del archivo que se creará
		final String RUTA_ARCHIVO_CREAR = "src/tema2/palabrasSeparadas.txt";

		// variable que almacenará la línea leída
		String linea = "";

		// variable que almacenará la palabra
		String palabra = "";

		int mayusculaAnterior = 0;

		// buffered reader para poder empezar a leer el fichero especificado
		try (BufferedReader leerFichero = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {

			// leer la primera línea y comprobar que esta no sea 0
			linea = leerFichero.readLine();

			// mientras que la línea no sea 0
			while (linea != null) {

				// recorro la línea
				for (int i = 1; i < linea.length(); i++) {

					// si el carácter es mayúscula
					if (Character.isUpperCase(linea.charAt(i))) {

						// iguala la palabra a escribir al substring que se haga
						// desde la última mayúscula hasta la posición actual
						palabra = linea.substring(mayusculaAnterior, i);

						try (BufferedWriter escribirFichero = new BufferedWriter(
								new FileWriter(RUTA_ARCHIVO_CREAR, true))) {

							// si el nombre no es nulo
							if (palabra != null) {
								// escribo el nombre en el fichero
								escribirFichero.write(palabra);
								//saltar de linea
								escribirFichero.write("\n");
								// indico que el contenido se ha podido agregar al fichero exitosamente
								System.out.println("Se agregó: " + palabra);
							}

							// cerrar el flujo
							escribirFichero.flush();
							escribirFichero.close();

						} catch (IOException e) {
							System.err.println("Error: " + e.getMessage());
						}

						// iguala mayúscula anterior a la posición actual
						mayusculaAnterior = i;

					}

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
