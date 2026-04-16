package dtos;

import enumerators.EstadoComandaDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) que representa el reporte de comandas del sistema.
 *
 * Esta clase se utiliza para transportar la información de las comandas
 * generadas en el sistema hacia las capas superiores, sin exponer directamente
 * las entidades de persistencia.
 *
 * Incluye información como el cliente asociado, número de mesa, total de venta,
 * estado de la comanda, fecha y hora, así como el detalle de los productos pedidos.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ReporteComandasDTO {

    /**
     * Nombre del cliente asociado a la comanda.
     */
    private String nombreCliente;

    /**
     * Número de mesa asignada a la comanda.
     */
    private Integer mesa;

    /**
     * Total monetario de la venta generada por la comanda.
     */
    private Double totalVenta;

    /**
     * Estado actual de la comanda (por ejemplo: PENDIENTE, PAGADA, CANCELADA).
     */
    private EstadoComandaDTO estadoComanda;

    /**
     * Fecha y hora en la que se generó la comanda.
     */
    private LocalDateTime fechaHora;

    /**
     * Lista de productos o detalles incluidos en la comanda.
     */
    private List<DetalleComandaDTO> pedidos;

    /**
     * Constructor completo.
     *
     * @param nombreCliente nombre del cliente asociado
     * @param mesa número de mesa asignada
     * @param totalVenta total de la venta
     * @param estadoComanda estado actual de la comanda
     * @param fechaHora fecha y hora de la comanda
     * @param pedidos lista de detalles de la comanda
     */
    public ReporteComandasDTO(String nombreCliente, Integer mesa, Double totalVenta,
            EstadoComandaDTO estadoComanda, LocalDateTime fechaHora,
            List<DetalleComandaDTO> pedidos) {
        this.nombreCliente = nombreCliente;
        this.mesa = mesa;
        this.totalVenta = totalVenta;
        this.estadoComanda = estadoComanda;
        this.fechaHora = fechaHora;
        this.pedidos = pedidos;
    }

    /**
     * Constructor vacío.
     */
    public ReporteComandasDTO() {
    }

    /**
     * Obtiene el nombre del cliente asociado a la comanda.
     *
     * @return nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente asociado a la comanda.
     *
     * @param nombreCliente nuevo nombre del cliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el número de mesa de la comanda.
     *
     * @return número de mesa
     */
    public Integer getMesa() {
        return mesa;
    }

    /**
     * Establece el número de mesa de la comanda.
     *
     * @param mesa nueva mesa asignada
     */
    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene el total de la venta de la comanda.
     *
     * @return total de venta
     */
    public Double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establece el total de la venta de la comanda.
     *
     * @param totalVenta nuevo total de venta
     */
    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
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
     * @param estadoComanda nuevo estado de la comanda
     */
    public void setEstadoComanda(EstadoComandaDTO estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    /**
     * Obtiene la fecha y hora de la comanda.
     *
     * @return fecha y hora
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la comanda.
     *
     * @param fechaHora nueva fecha y hora
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la lista de pedidos de la comanda.
     *
     * @return lista de detalles de la comanda
     */
    public List<DetalleComandaDTO> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la lista de pedidos de la comanda.
     *
     * @param pedidos nueva lista de pedidos
     */
    public void setPedidos(List<DetalleComandaDTO> pedidos) {
        this.pedidos = pedidos;
    }
}