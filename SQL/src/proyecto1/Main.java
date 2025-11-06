package proyecto1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner leer = new Scanner(System.in);

		// variable que almacena el nombre de la tabla que quiere crear
		String nombreTabla = "";

		// variable para almacenar la opción que se escoge del menú
		int opcion = 0;
		// variable que almacena opciones internas del bucle
		int opcionInterna = 0;

		do {

			// muestra el menú de opciones que tiene el usuario
			menu();
			// le pide que introduzca una opción
			System.out.print("Selecciona una opción: ");
			opcion = leer.nextInt();

			switch (opcion) {
			case 1: {
				do {
					System.out.println("CREAR TABLAS");
					System.out.println("¿Quieres crear TODAS LAS TABLAS o LAS TABLAS POR SEPARADO?");
					System.out.println("	1. Crear todas las tablas");
					System.out.println("	2. Crear las tablas por separado");
					System.out.print("Selecciona una opción: ");
					opcionInterna = leer.nextInt();
					// se repetirá este bucle mientras la opción siga siendo 0 (si el usuario pulsa
					// enter) o si la opción es inválida
				} while (opcionInterna == 0 && opcionInterna >= 2 && opcionInterna <= 1);

				// dependiendo de la opción que se haya escogido
				switch (opcionInterna) {
				// si quiere crear todas las tablas
				case 1: {
					System.out.println("CREAR TODAS LAS TABLAS");
					// se llama al método del crud
					if (CRUD.crearTablas()) {
						// si se pudieron crear manda un mensaje indicándolo
						System.out.println("	Las tablas se crearon correctamente");
					} else {
						System.err.println("	Las tablas no se pudieron crear");
					}
				}
				// si quiere crear las tablas por separado
				case 2: {
					System.out.println("CREAR LAS TABLAS POR SEPARADO");
					System.out.print("Introduca el nombre de la tabla que quiere crear: ");
					nombreTabla = leer.nextLine();

					// se llama al método del crud
					if (CRUD.crearTablasSeparadas(nombreTabla)) {
						System.out.println("	Las tablas se crearon correctamente");
					} else {
						System.err.println("	Las tablas no se pudieron crear");
					}
				}
				}
			}
			case 2: {
				
			}
			}

		} while (opcion >= 1 && opcion <= 7);

		leer.close();
	}

	// función para menú
	private static void menu() {
		System.out.println("ESCOJA UNA OPCIÓN:");
		System.out.println("	1. Crear tablas");
		System.out.println("	2. Insertar datos en tablas");
		System.out.println("	3. Listar datos de tablas");
		System.out.println("	4. Modificar datos de tablas");
		System.out.println("	5. Borrar datos de tablas");
		System.out.println("	6. Borrar tablas");
		System.out.println("	7. Salir");
	}

}
