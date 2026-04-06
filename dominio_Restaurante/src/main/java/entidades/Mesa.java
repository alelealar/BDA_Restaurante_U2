package entidades;

import enumerators.EstadoMesa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa una mesa dentro del restaurante.
 *
 * Cada mesa cuenta con un identificador único, un número de mesa y un estado
 * que indica si está disponible, ocupada u otro estado definido en el
 * enumerador EstadoMesa.
 *
 * Esta entidad es persistida en la base de datos mediante JPA.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {

    /**
     * Identificador único de la mesa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Long id;

    /**
     * Número asignado a la mesa dentro del restaurante.
     */
    @Column(name = "numero_mesa")
    private int numero;

    /**
     * Estado actual de la mesa (disponible, ocupada, etc.).
     */
    @Column(name = "estado_mesa")
    private EstadoMesa estado;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Mesa() {
    }

    /**
     * Constructor que inicializa la mesa con un identificador.
     *
     * @param id Identificador de la mesa.
     */
    public Mesa(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo de la mesa.
     *
     * @param id Identificador de la mesa.
     * @param numero Número de la mesa.
     * @param estado Estado actual de la mesa.
     */
    public Mesa(Long id, int numero, EstadoMesa estado) {
        this.id = id;
        this.numero = numero;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la mesa.
     *
     * @return id de la mesa.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la mesa.
     *
     * @param id Nuevo identificador.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el número de la mesa.
     *
     * @return número de mesa.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número de la mesa.
     *
     * @param numero Nuevo número de mesa.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el estado actual de la mesa.
     *
     * @return estado de la mesa.
     */
    public EstadoMesa getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la mesa.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

}
