package dtos;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) que representa el reporte de clientes frecuentes.
 *
 * Esta clase se utiliza para transportar la información agregada de los
 * clientes dentro del sistema, sin exponer directamente las entidades de
 * persistencia.
 *
 * Contiene información como el nombre del cliente, número de visitas,
 * total gastado, fecha de la última comanda y puntos acumulados.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ReporteClientesDTO {

    /**
     * Nombre completo del cliente.
     */
    private String nombres;

    /**
     * Número total de visitas realizadas por el cliente.
     */
    private Long visitas;

    /**
     * Total de dinero gastado por el cliente.
     */
    private Double totalGastado;

    /**
     * Fecha y hora de la última comanda realizada por el cliente.
     */
    private LocalDateTime fechaUltimaComanda;

    /**
     * Puntos acumulados por el cliente dentro del sistema de fidelización.
     */
    private Long puntosAcumulados;

    /**
     * Constructor completo.
     *
     * @param nombres nombre completo del cliente
     * @param visitas número de visitas del cliente
     * @param totalGastado total gastado por el cliente
     * @param fechaUltimaComanda fecha de la última comanda
     * @param puntosAcumulados puntos acumulados del cliente
     */
    public ReporteClientesDTO(String nombres, Long visitas, Double totalGastado,
            LocalDateTime fechaUltimaComanda, Long puntosAcumulados) {
        this.nombres = nombres;
        this.visitas = visitas;
        this.totalGastado = totalGastado;
        this.fechaUltimaComanda = fechaUltimaComanda;
        this.puntosAcumulados = puntosAcumulados;
    }

    /**
     * Constructor vacío.
     */
    public ReporteClientesDTO() {
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return nombre del cliente
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece el nombre completo del cliente.
     *
     * @param nombres nuevo nombre del cliente
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene el número de visitas del cliente.
     *
     * @return número de visitas
     */
    public Long getVisitas() {
        return visitas;
    }

    /**
     * Establece el número de visitas del cliente.
     *
     * @param visitas nuevo número de visitas
     */
    public void setVisitas(Long visitas) {
        this.visitas = visitas;
    }

    /**
     * Obtiene el total gastado por el cliente.
     *
     * @return total gastado
     */
    public Double getTotalGastado() {
        return totalGastado;
    }

    /**
     * Establece el total gastado por el cliente.
     *
     * @param totalGastado nuevo total gastado
     */
    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    /**
     * Obtiene la fecha de la última comanda del cliente.
     *
     * @return fecha de última comanda
     */
    public LocalDateTime getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }

    /**
     * Establece la fecha de la última comanda del cliente.
     *
     * @param fechaUltimaComanda nueva fecha de última comanda
     */
    public void setFechaUltimaComanda(LocalDateTime fechaUltimaComanda) {
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    /**
     * Obtiene los puntos acumulados del cliente.
     *
     * @return puntos acumulados
     */
    public Long getPuntosAcumulados() {
        return puntosAcumulados;
    }

    /**
     * Establece los puntos acumulados del cliente.
     *
     * @param puntosAcumulados nuevos puntos acumulados
     */
    public void setPuntosAcumulados(Long puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
}