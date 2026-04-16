package interfaces;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import enumerators.TipoMovimiento;
import enumerators.UnidadDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de ingredientes.
 *
 * Establece los métodos que deben implementarse para administrar los ingredientes
 * dentro del sistema, aplicando validaciones, reglas de negocio y control de stock.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public interface IIngredienteBO {

    /**
     * Registra un nuevo ingrediente en el sistema.
     *
     * Valida que la información sea correcta y que el ingrediente no exista previamente.
     *
     * @param ingrediente objeto IngredienteNuevoDTO con los datos del ingrediente a registrar
     * @throws NegocioException si los datos no son válidos o el ingrediente ya existe
     */
    void agregarIngrediente(IngredienteNuevoDTO ingrediente) throws NegocioException;

    /**
     * Actualiza el stock de un ingrediente según el tipo de movimiento.
     *
     * Permite aumentar o disminuir la cantidad disponible, validando que la operación sea válida.
     *
     * @param idIngrediente identificador del ingrediente
     * @param cantidad cantidad a modificar
     * @param tipo tipo de movimiento (ENTRADA o SALIDA)
     * @throws NegocioException si la operación no es válida o ocurre un error de negocio
     */
    void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException;

    /**
     * Elimina un ingrediente del sistema.
     *
     * Verifica que el ingrediente no esté en uso antes de realizar la eliminación.
     *
     * @param ingrediente objeto IngredienteDTO a eliminar
     * @throws NegocioException si el ingrediente está en uso o no existe
     */
    void eliminarIngrediente(IngredienteDTO ingrediente) throws NegocioException;

    /**
     * Actualiza la información de un ingrediente existente.
     *
     * Valida los datos y aplica los cambios correspondientes en el sistema.
     *
     * @param ingrediente objeto IngredienteDTO con la información actualizada
     * @throws NegocioException si los datos no son válidos o ocurre un error de persistencia
     */
    void actualizarIngrediente(IngredienteDTO ingrediente) throws NegocioException;

    /**
     * Obtiene la lista de ingredientes registrados en el sistema.
     *
     * @return lista de ingredientes en formato DTO
     * @throws NegocioException si ocurre un error al consultar los datos
     */
    List<IngredienteDTO> obtenerIngredientes() throws NegocioException;

    /**
     * Busca ingredientes por nombre y unidad de medida.
     *
     * @param nombre nombre o parte del nombre del ingrediente
     * @param unidadDTO unidad de medida del ingrediente
     * @return lista de ingredientes encontrados en formato DTO
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    List<IngredienteDTO> buscarIngredientes(String nombre, UnidadDTO unidadDTO) throws NegocioException;
}