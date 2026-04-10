package entidades;

import enumerators.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * Representa una comanda dentro del sistema del restaurante.
 *
 * Una comanda agrupa los productos solicitados por los clientes en una mesa,
 * incluyendo información como estado, folio, fecha y hora de creación, total,
 * mesa asociada y detalles de los productos.
 *
 * El folio de la comanda se genera automáticamente con el formato:
 * OB-YYYYMMDD-XXX, donde: - YYYYMMDD corresponde a la fecha de creación. - XXX
 * es un número consecutivo único por día.
 *
 * También puede estar asociada a un cliente frecuente.
 *
 * Incluye lógica para gestionar detalles y calcular el total acumulado.
 *
 * Esta entidad es persistida mediante JPA.
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
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_comanda", nullable = false)
    private EstadoComanda estadoComanda;

    /**
     * Folio único de la comanda.
     */
    @Column(name = "folio", nullable = false, unique = true, length = 20)
    private String folio;

    /**
     * Total acumulado de la comanda.
     */
    @Column(name = "total", nullable = false)
    private Double total;

    /**
     * Fecha y hora exacta de creación de la comanda.
     */
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /**
     * Mesa asociada a la comanda.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    /**
     * Lista de detalles asociados a la comanda.
     */
    @OneToMany(mappedBy = "comanda", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<DetalleComanda> detalles = new ArrayList<>();

    /**
     * Cliente frecuente asociado a la comanda (opcional).
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Método que se ejecuta antes de persistir la entidad.
     *
     * - Asigna la fecha y hora actual si no ha sido definida. - Inicializa el
     * total en 0 si es null.
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaHora == null) {
            this.fechaHora = LocalDateTime.now();
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
     * Constructor con identificador.
     *
     * @param id Identificador de la comanda.
     */
    public Comanda(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo de la comanda.
     *
     * @param id Identificador.
     * @param estadoComanda Estado actual.
     * @param folio Folio único.
     * @param total Total acumulado.
     * @param fechaHora Fecha y hora de creación.
     * @param mesa Mesa asociada.
     * @param detalles Lista de detalles.
     * @param cliente Cliente asociado.
     */
    public Comanda(Long id, EstadoComanda estadoComanda, String folio,
            Double total, LocalDateTime fechaHora,
            Mesa mesa, List<DetalleComanda> detalles, Cliente cliente) {

        this.id = id;
        this.estadoComanda = estadoComanda;
        this.folio = folio;
        this.total = total;
        this.fechaHora = fechaHora;
        this.mesa = mesa;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
        this.cliente = cliente;
    }

    /**
     * Agrega un detalle a la comanda.
     *
     * @param d Detalle a agregar.
     */
    public void agregarDetalle(DetalleComanda d) {
        if (d != null) {
            detalles.add(d);
            d.setComanda(this);
        }
    }

    /**
     * Elimina un detalle de la comanda.
     *
     * @param d Detalle a eliminar.
     */
    public void removerDetalle(DetalleComanda d) {
        if (d != null && detalles.contains(d)) {
            detalles.remove(d);
            d.setComanda(null);
        }
    }

    /**
     * Calcula el total de la comanda sumando los subtotales.
     */
    public void calcularTotal() {
        double suma = 0;

        for (DetalleComanda d : detalles) {
            if (d != null) {
                suma += d.getSubtotal();
            }
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
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la comanda.
     *
     * @param fechaHora Nueva fecha y hora.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la mesa asociada.
     *
     * @return mesa.
     */
    public Mesa getMesa() {
        return mesa;
    }

    /**
     * Establece la mesa de la comanda.
     *
     * @param mesa Nueva mesa.
     */
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene la lista de detalles.
     *
     * @return lista de detalles.
     */
    public List<DetalleComanda> getDetalles() {
        return detalles;
    }

    /**
     * Establece la lista de detalles.
     *
     * @param detalles Nueva lista de detalles.
     */
    public void setDetalles(List<DetalleComanda> detalles) {
        this.detalles = detalles;
    }

    /**
     * Obtiene el cliente asociado.
     *
     * @return cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente de la comanda.
     *
     * @param cliente Nuevo cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
