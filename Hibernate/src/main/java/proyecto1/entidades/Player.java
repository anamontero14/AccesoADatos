package proyecto1.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * clase que representa la entidad Player en la base de datos esta clase está
 * mapeada con la tabla Player mediante JPA
 */
@Entity
@Table(name = "Player")
public class Player {

	// identificador único del jugador (clave primaria)
	// se genera automáticamente mediante autoincremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPlayer")
	private int idPlayer;

	// nick del jugador, campo obligatorio con máximo 45 caracteres
	@Column(name = "nick", length = 45, nullable = false)
	private String nick;

	// contraseña del jugador, campo obligatorio con máximo 128 caracteres
	@Column(name = "password", length = 128, nullable = false)
	private String password;

	// email del jugador, campo obligatorio con máximo 100 caracteres
	@Column(name = "email", length = 100, nullable = false)
	private String email;

	// relación uno a muchos con la tabla Compras
	// un jugador puede tener muchas compras asociadas
	// se usa lazy loading para no cargar todas las compras automáticamente
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Compras> compras;

	/**
	 * constructor vacío requerido por Hibernate para poder instanciar la clase
	 */
	public Player() {
		// se inicializa la lista de compras como arraylist vacío
		this.compras = new ArrayList<>();
	}

	/**
	 * constructor con parámetros para crear un nuevo jugador el id no se incluye
	 * porque es autogenerado por la base de datos
	 * 
	 * @param nick     el nick del jugador
	 * @param password la contraseña del jugador
	 * @param email    el email del jugador
	 */
	public Player(String nick, String password, String email) {
		// se asignan los valores recibidos por parámetro
		this.nick = nick;
		this.password = password;
		this.email = email;
		// se inicializa la lista de compras como arraylist vacío
		this.compras = new ArrayList<>();
	}

	/**
	 * método getter para obtener el id del jugador
	 * 
	 * @return el id del jugador
	 */
	public int getIdPlayer() {
		return idPlayer;
	}

	/**
	 * método setter para asignar el id del jugador
	 * 
	 * @param idPlayer el id a asignar
	 */
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	/**
	 * método getter para obtener el nick del jugador
	 * 
	 * @return el nick del jugador
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * método setter para asignar el nick del jugador
	 * 
	 * @param nick el nick a asignar
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * método getter para obtener la contraseña del jugador
	 * 
	 * @return la contraseña del jugador
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * método setter para asignar la contraseña del jugador
	 * 
	 * @param password la contraseña a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * método getter para obtener el email del jugador
	 * 
	 * @return el email del jugador
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * método setter para asignar el email del jugador
	 * 
	 * @param email el email a asignar
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return string con los datos del jugador
	 */
	@Override
	public String toString() {
		return "Player{" + "idPlayer=" + idPlayer + ", nick='" + nick + '\'' + ", password='" + password + '\''
				+ ", email='" + email + '\'' + '}';
	}
}