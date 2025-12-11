package proyecto1.entidades;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * clase que representa la entidad Games en la base de datos esta clase está
 * mapeada con la tabla Games mediante JPA
 */
@Entity
@Table(name = "Games")
public class Game {

	// identificador único del juego (clave primaria)
	// se genera automáticamente mediante autoincremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idGame")
	private int idGame;

	// nombre del juego, campo obligatorio con máximo 45 caracteres
	@Column(name = "nombre", length = 45, nullable = false)
	private String nombre;

	// tiempo jugado en formato TIME (HH:MM:SS)
	@Column(name = "tiempoJugado", nullable = false)
	private Time tiempoJugado;

	// relación uno a muchos con la tabla Compras
	// un juego puede tener muchas compras asociadas
	// se usa lazy loading para no cargar todas las compras automáticamente
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Compras> compras;

	/**
	 * constructor vacío requerido por Hibernate para poder instanciar la clase
	 */
	public Game() {
		// se inicializa la lista de compras como arraylist vacío
		this.compras = new ArrayList<>();
	}

	/**
	 * constructor con parámetros para crear un nuevo juego el id no se incluye
	 * porque es autogenerado por la base de datos
	 * 
	 * @param nombre       el nombre del juego
	 * @param tiempoJugado el tiempo jugado en formato TIME
	 */
	public Game(String nombre, Time tiempoJugado) {
		// se asignan los valores recibidos por parámetro
		this.nombre = nombre;
		this.tiempoJugado = tiempoJugado;
		// se inicializa la lista de compras como arraylist vacío
		this.compras = new ArrayList<>();
	}

	/**
	 * método getter para obtener el id del juego
	 * 
	 * @return el id del juego
	 */
	public int getIdGame() {
		return idGame;
	}

	/**
	 * método setter para asignar el id del juego
	 * 
	 * @param idGame el id a asignar
	 */
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	/**
	 * método getter para obtener el nombre del juego
	 * 
	 * @return el nombre del juego
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * método setter para asignar el nombre del juego
	 * 
	 * @param nombre el nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * método getter para obtener el tiempo jugado
	 * 
	 * @return el tiempo jugado en formato TIME
	 */
	public Time getTiempoJugado() {
		return tiempoJugado;
	}

	/**
	 * método setter para asignar el tiempo jugado
	 * 
	 * @param tiempoJugado el tiempo a asignar
	 */
	public void setTiempoJugado(Time tiempoJugado) {
		this.tiempoJugado = tiempoJugado;
	}

	/**
	 * método getter para obtener la lista de compras asociadas
	 * 
	 * @return la lista de compras
	 */
	public List<Compras> getCompras() {
		return compras;
	}

	/**
	 * método setter para asignar la lista de compras
	 * 
	 * @param compras la lista de compras a asignar
	 */
	public void setCompras(List<Compras> compras) {
		this.compras = compras;
	}

	/**
	 * método que devuelve una representación en string del objeto
	 * 
	 * @return string con los datos del juego
	 */
	@Override
	public String toString() {
		return "Games{" + "idGame=" + idGame + ", nombre='" + nombre + '\'' + ", tiempoJugado=" + tiempoJugado + '}';
	}
}