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
     * Estado actual de la comanda (ABIERTA, ENTREGADA, CANCELADA).
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
     * Cliente frecuente asociado a la comanda (opcional).
     */
    private ClienteDTO cliente;

    /**
     * Constructor vacío.
     */
    public ComandaDTO() {
    }

    /**
     * Constructor con identificador.
     *
     * @param id Identificador de la comanda.
     */
    public ComandaDTO(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo.
     *
     * @param id Identificador de la comanda.
     * @param estadoComanda Estado actual de la comanda.
     * @param folio Folio único.
     * @param total Total acumulado.
     * @param fechaHora Fecha de creación.
     * @param detalles Lista de detalles.
     * @param mesa Mesa de la comanda.
     * @param cliente Cliente asociado.
     */
    public ComandaDTO(Long id, EstadoComandaDTO estadoComanda, String folio, Double total, LocalDateTime fechaHora, List<DetalleComandaDTO> detalles, MesaDTO mesa, ClienteDTO cliente) {
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
    public EstadoComandaDTO getEstadoComanda() {
        return estadoComanda;
    }

    /**
     * Establece el estado de la comanda.
     *
     * @param estadoComanda Nuevo estado.
     */
    public void setEstadoComanda(EstadoComandaDTO estadoComanda) {
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
     * @return fecha de creación.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la comanda.
     *
     * @param fechaHora Nueva fecha.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la lista de detalles de la comanda.
     *
     * @return lista de detalles.
     */
    public List<DetalleComandaDTO> getDetalles() {
        return detalles;
    }

    /**
     * Establece la lista de detalles de la comanda.
     *
     * @param detalles Nueva lista de detalles.
     */
    public void setDetalles(List<DetalleComandaDTO> detalles) {
        this.detalles = detalles;
    }

    /**
     * Obtiene la mesa de la comanda.
     *
     * @return mesa de la comanda.
     */
    public MesaDTO getMesa() {
        return mesa;
    }

    /**
     * Establece una mesa para la comanda.
     *
     * @param mesa Mesa de la comanda.
     */
    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene el cliente asociado a la comanda.
     *
     * @return cliente.
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente de la comanda.
     *
     * @param cliente Cliente a asociar.
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

}
