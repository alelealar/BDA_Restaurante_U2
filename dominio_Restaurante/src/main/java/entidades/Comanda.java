package entidades;

import enumerators.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Representa una comanda dentro del sistema del restaurante.
 *
 * Una comanda agrupa los productos solicitados por los clientes en una mesa,
 * incluyendo información como estado, folio, fecha de creación, total y
 * detalles.
 *
 * También puede estar asociada a un cliente frecuente registrado en el sistema.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
@Entity
@Table(name = "comandas")
public class Comanda implements Serializable {

    /**
     * Identificador único de la comanda.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private Long id;

    /**
     * Estado actual de la comanda (ABIERTA, ENTREGADA, CANCELADA).
     */
    @Column(name = "estado_comanda", nullable = false)
    private EstadoComanda estadoComanda;

    /**
     * Folio único de la comanda.
     */
    @Column(name = "folio", nullable = false, length = 100)
    private String folio;

    /**
     * Total acumulado de la comanda.
     */
    @Column(name = "total", nullable = false)
    private Double total;

    /**
     * Fecha y hora de creación de la comanda.
     */
    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    /**
     * Lista de detalles asociados a la comanda.
     */
    @OneToMany(mappedBy = "comanda", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<DetalleComanda> detalles;

    /**
     * Cliente frecuente asociado a la comanda (opcional).
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Método que se ejecuta automáticamente antes de persistir la entidad.
     * Asigna la fecha actual si no ha sido definida. Establece el total en 0 si
     * no se ingresó ninguno.
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaHora == null) {
            this.fechaHora = LocalDate.now();
        }

        if (this.total == null) {
            this.total = 0.0;
        }
    }

    /**
     * Constructor vacío requerido por JPA.
     */
    public Comanda() {
    }

    /**
     * Constructor que inicializa la comanda con un identificador.
     *
     * @param id Identificador de la comanda.
     */
    public Comanda(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo de la comanda.
     */
    public Comanda(Long id, EstadoComanda estadoComanda, String folio, Double total, LocalDate fechaHora, List<DetalleComanda> detalles, Cliente cliente) {
        this.id = id;
        this.estadoComanda = estadoComanda;
        this.folio = folio;
        this.total = total;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
        this.cliente = cliente;
    }

    /**
     * Agrega un detalle a la comanda y establece la relación bidireccional.
     *
     * @param d Detalle de comanda a agregar.
     */
    public void agregarDetalle(DetalleComanda d) {
        detalles.add(d);
        d.setComanda(this);
    }

    /**
     * Elimina un detalle de la comanda y rompe la relación.
     *
     * @param d Detalle de comanda a eliminar.
     */
    public void removerDetalle(DetalleComanda d) {
        detalles.remove(d);
        d.setComanda(null);
    }

    /**
     * Calcula el total de la comanda sumando los subtotales de cada detalle.
     */
    public void calcularTotal() {
        double suma = 0;

        for (DetalleComanda d : detalles) {
            suma += d.getSubtotal();
        }

        this.total = suma;
    }

    /**
     * Obtiene el identificador de la comanda.
     *
     * @return id de la comanda.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la comanda.
     *
     * @param id Nuevo identificador.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el estado de la comanda.
     *
     * @return estado actual.
     */
    public EstadoComanda getEstadoComanda() {
        return estadoComanda;
    }

    /**
     * Establece el estado de la comanda.
     *
     * @param estadoComanda Nuevo estado.
     */
    public void setEstadoComanda(EstadoComanda estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    /**
     * Obtiene el folio de la comanda.
     *
     * @return folio.
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Establece el folio de la comanda.
     *
     * @param folio Nuevo folio.
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Obtiene el total de la comanda.
     *
     * @return total.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Establece el total de la comanda.
     *
     * @param total Nuevo total.
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Obtiene la fecha y hora de la comanda.
     *
     * @return fecha y hora.
     */
    public LocalDate getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la comanda.
     *
     * @param fechaHora Nueva fecha.
     */
    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la lista de detalles de la comanda.
     *
     * @return lista de detalles.
     */
    public List<DetalleComanda> getDetalles() {
        return detalles;
    }

    /**
     * Establece la lista de detalles de la comanda.
     *
     * @param detalles Nueva lista de detalles.
     */
    public void setDetalles(List<DetalleComanda> detalles) {
        this.detalles = detalles;
    }

    /**
     * Obtiene el cliente asociado a la comanda.
     *
     * @return cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente de la comanda.
     *
     * @param cliente Cliente a asociar.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
