package dtos;

import enumerators.UnidadDTO;

/**
 * DTO (Data Transfer Object) que representa la información de un ingrediente.
 *
 * Esta clase se utiliza para transportar los datos de un ingrediente entre las
 * distintas capas del sistema, sin exponer directamente la entidad de
 * persistencia.
 *
 * Contiene información como identificador, nombre, unidad de medida, niveles de
 * stock e imagen asociada.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class IngredienteDTO {

    /**
     * Identificador único del ingrediente.
     */
    private Long id;

    /**
     * Código o identificador interno del ingrediente.
     */
    private String identificador;

    /**
     * Nombre del ingrediente.
     */
    private String nombre;

    /**
     * Unidad de medida utilizada para el ingrediente.
     */
    private UnidadDTO unidadMedida;

    /**
     * Cantidad actual disponible en inventario.
     */
    private Integer stockActual;

    /**
     * Cantidad mínima permitida en inventario.
     */
    private Integer stockMinimo;

    /**
     * Ruta o URL de la imagen del ingrediente.
     */
    private String urlImagen;

    /**
     * Constructor completo.
     *
     * @param id identificador único del ingrediente
     * @param identificador código o identificador interno
     * @param nombre nombre del ingrediente
     * @param unidadMedida unidad de medida del ingrediente
     * @param stockActual cantidad actual en inventario
     * @param stockMinimo cantidad mínima permitida
     * @param urlImagen ruta o URL de la imagen
     */
    public IngredienteDTO(Long id, String identificador, String nombre,
            UnidadDTO unidadMedida, Integer stockActual,
            Integer stockMinimo, String urlImagen) {

        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.urlImagen = urlImagen;
    }

    /**
     * Constructor vacío.
     */
    public IngredienteDTO() {
    }

    /**
     * Obtiene el identificador único del ingrediente.
     *
     * @return identificador del ingrediente
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del ingrediente.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el código o identificador interno del ingrediente.
     *
     * @return identificador interno
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el código o identificador interno del ingrediente.
     *
     * @param identificador nuevo identificador interno
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el nombre del ingrediente.
     *
     * @return nombre del ingrediente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ingrediente.
     *
     * @param nombre nuevo nombre del ingrediente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la unidad de medida del ingrediente.
     *
     * @return unidad de medida
     */
    public UnidadDTO getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Establece la unidad de medida del ingrediente.
     *
     * @param unidadMedida nueva unidad de medida
     */
    public void setUnidadMedida(UnidadDTO unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * Obtiene la cantidad actual disponible en inventario.
     *
     * @return stock actual
     */
    public Integer getStockActual() {
        return stockActual;
    }

    /**
     * Establece la cantidad actual disponible en inventario.
     *
     * @param stock nueva cantidad disponible
     */
    public void setStockActual(Integer stock) {
        this.stockActual = stock;
    }

    /**
     * Obtiene la cantidad mínima permitida en inventario.
     *
     * @return stock mínimo
     */
    public Integer getStockMinimo() {
        return stockMinimo;
    }

    /**
     * Establece la cantidad mínima permitida en inventario.
     *
     * @param stockMinimo nuevo stock mínimo
     */
    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    /**
     * Obtiene la ruta o URL de la imagen del ingrediente.
     *
     * @return ruta o URL de la imagen
     */
    public String getUrlImagen() {
        return urlImagen;
    }

    /**
     * Establece la ruta o URL de la imagen del ingrediente.
     *
     * @param urlImagen nueva ruta o URL de la imagen
     */
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    /**
     * Devuelve la representación en texto del ingrediente.
     *
     * @return identificador del ingrediente
     */
    @Override
    public String toString() {
        return identificador;
    }
}
