package dtos;

import enumerators.EstadoMesaDTO;

/**
 * Objeto de transferencia de datos (DTO) para la entidad Mesa.
 *
 * Este DTO se utiliza para transportar información de mesas entre las capas de
 * la aplicación sin exponer directamente la entidad.
 *
 * Contiene información básica como identificador, número de mesa y estado.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MesaDTO {

    /**
     * Identificador único de la mesa.
     */
    private Long id;

    /**
     * Número asignado a la mesa dentro del restaurante.
     */
    private int numero;

    /**
     * Estado actual de la mesa.
     */
    private EstadoMesaDTO estado;

    /**
     * Constructor vacío.
     */
    public MesaDTO() {
    }

    /**
     * Constructor con identificador.
     *
     * @param id Identificador de la mesa.
     */
    public MesaDTO(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo.
     *
     * @param id Identificador de la mesa.
     * @param numero Número de la mesa.
     * @param estado Estado de la mesa.
     */
    public MesaDTO(Long id, int numero, EstadoMesaDTO estado) {
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
     * Obtiene el estado de la mesa.
     *
     * @return estado de la mesa.
     */
    public EstadoMesaDTO getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la mesa.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(EstadoMesaDTO estado) {
        this.estado = estado;
    }
}
