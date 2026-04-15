package dtos;

import enumerators.UnidadDTO;

/**
 * DTO (Data Transfer Object) utilizado para registrar un nuevo ingrediente.
 *
 * Esta clase contiene únicamente la información necesaria para crear un
 * ingrediente dentro del sistema, permitiendo transferir los datos entre la
 * interfaz y la lógica de negocio.
 *
 * Incluye nombre, unidad de medida, stock inicial, stock mínimo e imagen
 * asociada.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class IngredienteNuevoDTO {

    /**
     * Nombre del nuevo ingrediente.
     */
    private String nombre;

    /**
     * Unidad de medida del ingrediente.
     */
    private UnidadDTO unidadMedida;

    /**
     * Cantidad inicial disponible en inventario.
     */
    private Integer stockInicial;

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
     * @param nombre nombre del ingrediente
     * @param unidadMedida unidad de medida
     * @param stockInicial cantidad inicial en inventario
     * @param stockMinimo cantidad mínima permitida
     * @param urlImagen ruta o URL de la imagen
     */
    public IngredienteNuevoDTO(String nombre, UnidadDTO unidadMedida,
            Integer stockInicial, Integer stockMinimo, String urlImagen) {

        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockInicial = stockInicial;
        this.stockMinimo = stockMinimo;
        this.urlImagen = urlImagen;
    }

    /**
     * Constructor vacío.
     */
    public IngredienteNuevoDTO() {
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
     * @param nombre nuevo nombre
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
     * Obtiene la cantidad inicial disponible en inventario.
     *
     * @return stock inicial
     */
    public Integer getStockInicial() {
        return stockInicial;
    }

    /**
     * Establece la cantidad inicial disponible en inventario.
     *
     * @param stockInicial nuevo stock inicial
     */
    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
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
}
