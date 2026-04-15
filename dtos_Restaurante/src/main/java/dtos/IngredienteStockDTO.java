package dtos;

import enumerators.TipoMovimiento;

/**
 * DTO (Data Transfer Object) utilizado para representar un movimiento de stock
 * de un ingrediente.
 *
 * Esta clase permite transferir la información necesaria para actualizar el
 * inventario de un ingrediente, indicando qué ingrediente será afectado, la
 * cantidad del movimiento y el tipo de operación realizada.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class IngredienteStockDTO {

    /**
     * Identificador del ingrediente a modificar.
     */
    private Long idIngrediente;

    /**
     * Cantidad que será aplicada al movimiento.
     */
    private Integer cantidad;

    /**
     * Tipo de movimiento realizado sobre el stock.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Constructor completo.
     *
     * @param idIngrediente identificador del ingrediente
     * @param cantidad cantidad del movimiento
     * @param tipoMovimiento tipo de movimiento a realizar
     */
    public IngredienteStockDTO(Long idIngrediente, Integer cantidad,
            TipoMovimiento tipoMovimiento) {

        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * Constructor vacío.
     */
    public IngredienteStockDTO() {
    }

    /**
     * Obtiene el identificador del ingrediente.
     *
     * @return identificador del ingrediente
     */
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Establece el identificador del ingrediente.
     *
     * @param idIngrediente nuevo identificador
     */
    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * Obtiene la cantidad asociada al movimiento.
     *
     * @return cantidad del movimiento
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad asociada al movimiento.
     *
     * @param cantidad nueva cantidad
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el tipo de movimiento del stock.
     *
     * @return tipo de movimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Establece el tipo de movimiento del stock.
     *
     * @param tipoMovimiento nuevo tipo de movimiento
     */
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
}
