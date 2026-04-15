package dtos;

import enumerators.EstadoComandaDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO que representa una comanda dentro del sistema del restaurante.
 *
 * Contiene la información necesaria para transferir datos de una comanda entre
 * las distintas capas de la aplicación, incluyendo su estado, folio, total,
 * fecha de creación, detalles asociados y cliente.
 *
 * Este objeto no contiene lógica de negocio y se utiliza únicamente como
 * estructura de transporte de datos.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class ComandaDTO {

    /**
     * Identificador único de la comanda.
     */
    private Long id;

    /**
     * Estado actual de la comanda.
     */
    private EstadoComandaDTO estadoComanda;

    /**
     * Folio único de la comanda.
     */
    private String folio;

    /**
     * Total acumulado de la comanda.
     */
    private Double total;

    /**
     * Fecha y hora de creación de la comanda.
     */
    private LocalDateTime fechaHora;

    /**
     * Lista de detalles asociados a la comanda.
     */
    private List<DetalleComandaDTO> detalles;

    /**
     * Mesa donde fue emitida la comanda.
     */
    private MesaDTO mesa;

    /**
     * Cliente asociado a la comanda.
     */
    private ClienteDTO cliente;

    /**
     * Constructor vacío.
     */
    public ComandaDTO() {
    }

    /**
     * Constructor que inicializa únicamente el identificador.
     *
     * @param id identificador de la comanda
     */
    public ComandaDTO(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo.
     *
     * @param id identificador de la comanda
     * @param estadoComanda estado actual
     * @param folio folio único
     * @param total total acumulado
     * @param fechaHora fecha y hora de creación
     * @param detalles lista de detalles asociados
     * @param mesa mesa relacionada
     * @param cliente cliente asociado
     */
    public ComandaDTO(Long id, EstadoComandaDTO estadoComanda, String folio,
            Double total, LocalDateTime fechaHora,
            List<DetalleComandaDTO> detalles, MesaDTO mesa,
            ClienteDTO cliente) {

        this.id = id;
        this.estadoComanda = estadoComanda;
        this.folio = folio;
        this.total = total;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
        this.mesa = mesa;
        this.cliente = cliente;
    }

    /**
     * Obtiene el identificador de la comanda.
     *
     * @return identificador de la comanda
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la comanda.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el estado actual de la comanda.
     *
     * @return estado de la comanda
     */
    public EstadoComandaDTO getEstadoComanda() {
        return estadoComanda;
    }

    /**
     * Establece el estado actual de la comanda.
     *
     * @param estadoComanda nuevo estado
     */
    public void setEstadoComanda(EstadoComandaDTO estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    /**
     * Obtiene el folio de la comanda.
     *
     * @return folio de la comanda
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Establece el folio de la comanda.
     *
     * @param folio nuevo folio
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Obtiene el total acumulado de la comanda.
     *
     * @return total de la comanda
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Establece el total acumulado de la comanda.
     *
     * @param total nuevo total
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Obtiene la fecha y hora de creación de la comanda.
     *
     * @return fecha y hora de creación
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de creación de la comanda.
     *
     * @param fechaHora nueva fecha y hora
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la lista de detalles de la comanda.
     *
     * @return lista de detalles
     */
    public List<DetalleComandaDTO> getDetalles() {
        return detalles;
    }

    /**
     * Establece la lista de detalles de la comanda.
     *
     * @param detalles nueva lista de detalles
     */
    public void setDetalles(List<DetalleComandaDTO> detalles) {
        this.detalles = detalles;
    }

    /**
     * Obtiene la mesa asociada a la comanda.
     *
     * @return mesa asociada
     */
    public MesaDTO getMesa() {
        return mesa;
    }

    /**
     * Establece la mesa asociada a la comanda.
     *
     * @param mesa nueva mesa asociada
     */
    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene el cliente asociado a la comanda.
     *
     * @return cliente asociado
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la comanda.
     *
     * @param cliente nuevo cliente asociado
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Agrega un detalle a la comanda.
     *
     * Si ya existe un detalle con el mismo producto, se actualizan sus datos.
     * En caso contrario, se agrega como nuevo elemento a la lista.
     *
     * @param nuevo detalle a agregar
     */
    public void agregarDetalle(DetalleComandaDTO nuevo) {
        if (nuevo == null) {
            return;
        }

        for (DetalleComandaDTO d : detalles) {
            if (d.getIdProducto().equals(nuevo.getIdProducto())) {
                d.setCantidad(nuevo.getCantidad());
                d.setComentario(nuevo.getComentario());
                d.setPrecioUnitario(nuevo.getPrecioUnitario());
                return;
            }
        }

        detalles.add(nuevo);
        nuevo.setIdComanda(null);
    }

    /**
     * Elimina un detalle de la comanda.
     *
     * @param d detalle a eliminar
     */
    public void removerDetalle(DetalleComandaDTO d) {
        if (d != null && detalles.contains(d)) {
            detalles.remove(d);
            d.setIdComanda(null);
        }
    }
}
