package tema2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ejercicio4 {

	public static void main(String[] args) {
		// constante que almacena la ruta del archivo
		final String RUTA_ARCHIVO = "src\\tema2\\palabrasSeparadas.txt";
		
		final String RUTA_ARCHIVO_CREAR = "src/tema2/palabrasOrdenadas.txt";

		// array para almacenar las palabras
		List<String> listaPalabras = new ArrayList<>();

		// variable que almacena la palabra que se lee
		String linea = "";

		// buffered reader para poder empezar a leer el fichero especificado
		try (BufferedReader leerFichero = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {

			// leer la primera línea y comprobar que esta no sea 0
			linea = leerFichero.readLine();

			// mientras que la línea no sea 0
			while (linea != null) {

				// se almacenan las palabras en una lista
				listaPalabras.add(linea);

				// leer la primera línea y comprobar que esta no sea 0
				linea = leerFichero.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// se ordena la lista
		Collections.sort(listaPalabras);

		// se abre otro buffer para poder escribir
		try (BufferedWriter escribirFichero = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_CREAR, true))) {

			//recorro la lista ordenada
			for (String palabra : listaPalabras) {
				//escribe las palabras que va leyendo de la lista en el fichero especificado
				escribirFichero.write(palabra);
				escribirFichero.write("\n");
			}

			// cerrar el flujo
			escribirFichero.flush();
			escribirFichero.close();

		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

}
