package proyecto1.entidades;

import jakarta.persistence.*;

/**
 * clase que representa la entidad Compras en la base de datos esta clase está
 * mapeada con la tabla Compras mediante JPA
 */
@Entity
@Table(name = "Compras")
public class Compras {

	/**
	 * identificador único de la compra (clave primaria) se genera automáticamente
	 * mediante autoincremento
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompra")
	private int idCompra;

	/**
	 * relación muchos a uno con Player muchas compras pueden pertenecer a un solo
	 * jugador la columna idPlayer es clave foránea
	 */
	@ManyToOne
	@JoinColumn(name = "idPlayer", nullable = false)
	private Player player;

	/**
	 * relación muchos a uno con Game muchas compras pueden pertenecer a un solo
	 * juego la columna idGame es clave foránea
	 */
	@ManyToOne
	@JoinColumn(name = "idGame", nullable = false)
	private Game game;

	/**
	 * nombre del artículo comprado no puede ser nulo
	 */
	@Column(name = "cosa", nullable = false, length = 100)
	private String cosa;

	/**
	 * precio de la compra no puede ser nulo
	 */
	@Column(name = "precio", nullable = false)
	private double precio;

	/**
	 * fecha de la compra se guarda como texto porque así lo pides
	 */
	@Column(name = "fechaCompra", nullable = false, length = 20)
	private String fechaCompra;

	/**
	 * constructor vacío requerido por Hibernate
	 */
	public Compras() {
	}

	/**
	 * constructor con parámetros para crear una nueva compra el id no se incluye
	 * porque es autogenerado
	 */
	public Compras(Player player, Game game, String cosa, double precio, String fechaCompra) {
		this.player = player;
		this.game = game;
		this.cosa = cosa;
		this.precio = precio;
		this.fechaCompra = fechaCompra;
	}

	/**
	 * método getter del id de la compra
	 */
	public int getIdCompra() {
		int resultado = idCompra;
		return resultado;
	}

	/**
	 * método setter del id de la compra
	 */
	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	/**
	 * método getter del Player asociado
	 */
	public Player getPlayer() {
		Player jugador = player;
		return jugador;
	}

	/**
	 * método setter del Player asociado
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * método getter del Game asociado
	 */
	public Game getGame() {
		Game juego = game;
		return juego;
	}

	/**
	 * método setter del Game asociado
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * método getter de la descripción de la compra
	 */
	public String getCosa() {
		String descripcion = cosa;
		return descripcion;
	}

	/**
	 * método setter de la descripción de la compra
	 */
	public void setCosa(String cosa) {
		this.cosa = cosa;
	}

	/**
	 * método getter del precio
	 */
	public double getPrecio() {
		double valor = precio;
		return valor;
	}

	/**
	 * método setter del precio
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * método getter de la fecha de compra
	 */
	public String getFechaCompra() {
		String fecha = fechaCompra;
		return fecha;
	}

	/**
	 * método setter de la fecha de compra
	 */
	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	/**
	 * método toString para mostrar la compra
	 */
	@Override
	public String toString() {
		String texto = "Compra{" + "idCompra=" + idCompra + ", player="
				+ (player != null ? player.getIdPlayer() : "null") + ", game="
				+ (game != null ? game.getIdGame() : "null") + ", cosa='" + cosa + '\'' + ", precio=" + precio
				+ ", fechaCompra='" + fechaCompra + '\'' + '}';
		return texto;
	}
}
