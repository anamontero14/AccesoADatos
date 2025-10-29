package tema2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Ejercicio5 {

	public static void main(String[] args) {

		// constante que almacena la ruta del archivo
		final String RUTA_ARCHIVO = "C:\\Users\\ana.montero\\Documents\\GitHub\\AccesoADatos\\TEMA2\\src\\tema2\\letraA.txt";
		// ruta del fichero para crear apartado 0
		final String RUTA_ARCHIVO_CREAR = "C:\\Users\\ana.montero\\Documents\\GitHub\\AccesoADatos\\TEMA2\\src\\tema2\\letras.txt";
		// ruta del fichero para crear apartado 1
		final String RUTA_LETRAS_ORDENADAS = "C:\\Users\\ana.montero\\Documents\\GitHub\\AccesoADatos\\TEMA2\\src\\tema2\\letrasOrdenadas.txt";
		// ruta del fichero para crear apartado 2
		final String RUTA_LETRAS_ORDENADAS_INVERSA = "C:\\Users\\ana.montero\\Documents\\GitHub\\AccesoADatos\\TEMA2\\src\\tema2\\letrasOrdenadasInversa.txt";
		// objeto random para generar posiciones aleatorias
		Random rand = new Random();
		// almaceno la longitud del fichero
		int longitudFichero;
		// variable para almacenar una posicion aleatoria
		int posicion;
		// variable que almacena el valor del byte que se lee
		int valorByte;
		// variable que almacena el caracter en si
		char caracter;

		// APARTADO 0: leer una letra "a" y escribirla 5 veces
		try (RandomAccessFile raf = new RandomAccessFile(RUTA_ARCHIVO, "r")) {
			// se almacena la longitud del fichero
			longitudFichero = (int) raf.length();
			// si la longitud del fichero es igual a 0 significa que el fichero está vacío
			if (longitudFichero == 0) {
				System.out.println("Fichero vacío");
			}
			// se almacena una posición aleatoria en la variable
			posicion = rand.nextInt(0, longitudFichero + 1);
			// se pone el puntero en la posición especificada
			raf.seek(posicion);
			// se lee el valor del byte y se almacena en la variable
			valorByte = raf.read();
			// si el valor es diferente a -1 significa que se ha leído algo
			if (valorByte > 0) {
				// en el caracter se guarda el valor del byte que se almacenó antes en una
				// variable pero casteado a char para obtener el caracter
				caracter = (char) valorByte;
				System.out.println("Caracter: " + caracter + " leído en posición " + posicion);

				// se abre otro buffer para poder escribir
				try (BufferedWriter escribirFichero = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_CREAR, true))) {

					for (int i = 0; i < 5; i++) {
						// escribes en el fichero el caracter leido
						escribirFichero.write(caracter);
					}

					// cerrar el flujo
					escribirFichero.flush();
					escribirFichero.close();

				} catch (IOException e) {
					System.err.println("Error: " + e.getMessage());
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// APARTADO 1: leer letras a-e y escribirlas de forma inversa
		System.out.println("\n--- APARTADO 1 ---");
		escribirInverso(RUTA_ARCHIVO, RUTA_LETRAS_ORDENADAS);

		// APARTADO 2: leer letras con números (a1-e5) y escribirlas de forma inversa
		System.out.println("\n--- APARTADO 2 ---");
		escribirInversoConNumeros(RUTA_ARCHIVO, RUTA_LETRAS_ORDENADAS_INVERSA);

	}

	// método que lee letras en líneas diferentes y las escribe de forma inversa
	public static void escribirInverso(String rutaLectura, String rutaEscritura) {
		// variable que almacena la longitud del fichero
		int longitudFichero;
		// variable para almacenar la posición actual de lectura
		int posicion;
		// variable que almacena el valor del byte que se lee
		int valorByte;
		// variable que almacena el caracter en si
		char caracter;

		try (RandomAccessFile raf = new RandomAccessFile(rutaLectura, "r")) {
			// se almacena la longitud del fichero
			longitudFichero = (int) raf.length();
			// si la longitud del fichero es igual a 0 significa que el fichero está vacío
			if (longitudFichero == 0) {
				System.out.println("Fichero vacío");
				return;
			}

			// se abre el fichero para escribir
			try (BufferedWriter escribirFichero = new BufferedWriter(new FileWriter(rutaEscritura, true))) {
				// se posiciona al final del fichero para leer de atrás hacia adelante
				posicion = longitudFichero - 1;

				// se recorre el fichero desde el final hasta el principio
				while (posicion >= 0) {
					// se pone el puntero en la posición especificada
					raf.seek(posicion);
					// se lee el valor del byte y se almacena en la variable
					valorByte = raf.read();

					// si el valor es diferente a -1 y no es salto de línea
					if (valorByte > 0 && valorByte != '\n' && valorByte != '\r') {
						// en el caracter se guarda el valor del byte casteado a char
						caracter = (char) valorByte;
						System.out.println("Caracter: " + caracter + " leído en posición " + posicion);
						// se escribe el caracter en el fichero
						escribirFichero.write(caracter);
						// se escribe un espacio después del caracter
						escribirFichero.write(" ");
					}

					// se decrementa la posición para leer el siguiente byte hacia atrás
					posicion--;
				}

				// se escribe un salto de línea al final
				escribirFichero.newLine();
				// cerrar el flujo
				escribirFichero.flush();
				escribirFichero.close();

			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// método que lee letras con números y las escribe de forma inversa
	public static void escribirInversoConNumeros(String rutaLectura, String rutaEscritura) {
		// variable que almacena la longitud del fichero
		int longitudFichero;
		// variable para almacenar la posición actual de lectura
		int posicion;
		// variable que almacena el valor del byte que se lee
		int valorByte;
		// variable que almacena el caracter en si
		char caracter;
		// variable que indica si estamos leyendo un par letra-número completo
		boolean leyendoNumero;
		// variable que almacena temporalmente la letra
		char letraTemp;

		// se inicializa la variable booleana
		leyendoNumero = false;
		// se inicializa la variable char
		letraTemp = ' ';

		try (RandomAccessFile raf = new RandomAccessFile(rutaLectura, "r")) {
			// se almacena la longitud del fichero
			longitudFichero = (int) raf.length();
			// si la longitud del fichero es igual a 0 significa que el fichero está vacío
			if (longitudFichero == 0) {
				System.out.println("Fichero vacío");
				return;
			}

			// se abre el fichero para escribir
			try (BufferedWriter escribirFichero = new BufferedWriter(new FileWriter(rutaEscritura, true))) {
				// se posiciona al final del fichero para leer de atrás hacia adelante
				posicion = longitudFichero - 1;

				// se recorre el fichero desde el final hasta el principio
				while (posicion >= 0) {
					// se pone el puntero en la posición especificada
					raf.seek(posicion);
					// se lee el valor del byte y se almacena en la variable
					valorByte = raf.read();

					// si el valor es diferente a -1 y no es salto de línea ni espacio
					if (valorByte > 0 && valorByte != '\n' && valorByte != '\r' && valorByte != ' ') {
						// en el caracter se guarda el valor del byte casteado a char
						caracter = (char) valorByte;

						// si el caracter es un dígito
						if (Character.isDigit(caracter)) {
							// marcamos que estamos leyendo un número
							leyendoNumero = true;
							// escribimos el número
							System.out.println("Caracter: " + caracter + " leído en posición " + posicion);
						} else if (leyendoNumero) {
							// si ya leímos el número y ahora leemos la letra
							letraTemp = caracter;
							System.out.println("Caracter: " + letraTemp + " leído en posición " + posicion);
							// escribimos primero la letra
							escribirFichero.write(letraTemp);
							// obtenemos la posición del número que está después de la letra
							raf.seek(posicion + 1);
							// leemos el número
							valorByte = raf.read();
							// escribimos el número
							escribirFichero.write((char) valorByte);
							// escribimos un espacio
							escribirFichero.write(" ");
							// reseteamos la bandera
							leyendoNumero = false;
						}
					}

					// se decrementa la posición para leer el siguiente byte hacia atrás
					posicion--;
				}

				// se escribe un salto de línea al final
				escribirFichero.newLine();
				// cerrar el flujo
				escribirFichero.flush();
				escribirFichero.close();

			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}