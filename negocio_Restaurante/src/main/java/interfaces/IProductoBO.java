package interfaces;

import dtos.ProductoDTO;
import dtos.ProductoNuevoDTO;
import enumerators.TipoProductoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio (BO) para la gestión de productos.
 *
 * Esta interfaz actúa como intermediaria entre la capa de presentación y la
 * capa de acceso a datos (DAO), aplicando reglas de negocio, validaciones y
 * controlando el flujo de la información.
 *
 * Utiliza objetos DTO para evitar exponer directamente las entidades de persistencia.
 *
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoBO {

    /**
     * Registra un nuevo producto en el sistema.
     *
     * Valida la información del producto antes de enviarla a la capa de persistencia.
     *
     * @param producto objeto ProductoNuevoDTO con los datos del producto a registrar
     * @throws NegocioException si ocurre un error de validación o reglas de negocio
     */
    void agregarProducto(ProductoNuevoDTO producto) throws NegocioException;

    /**
     * Actualiza la información de un producto existente.
     *
     * Valida los datos antes de realizar la actualización en la base de datos.
     *
     * @param producto objeto ProductoDTO con la información actualizada
     * @throws NegocioException si ocurre un error de validación o reglas de negocio
     */
    void actualizarProducto(ProductoDTO producto) throws NegocioException;

    /**
     * Activa un producto existente cambiando su estado a 'ACTIVO'.
     *
     * Verifica que el producto exista antes de modificar su estado.
     *
     * @param idProducto identificador único del producto
     * @throws NegocioException si ocurre un error o el producto no existe
     */
    void activarProducto(Long idProducto) throws NegocioException;

    /**
     * Desactiva un producto existente cambiando su estado a 'INACTIVO'.
     *
     * Verifica que el producto exista antes de modificar su estado.
     *
     * @param idProducto identificador único del producto
     * @throws NegocioException si ocurre un error o el producto no existe
     */
    void desactivarProducto(Long idProducto) throws NegocioException;

    /**
     * Obtiene todos los productos registrados en el sistema.
     *
     * @return lista de todos los productos
     * @throws NegocioException si ocurre un error en la consulta
     */
    List<ProductoDTO> consultarTodosProductos() throws NegocioException;

    /**
     * Busca un producto por su identificador.
     *
     * @param idProducto identificador único del producto
     * @return producto encontrado o null si no existe
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    ProductoDTO consultarProductoPorID(Long idProducto) throws NegocioException;

    /**
     * Obtiene todos los productos con estado 'ACTIVO'.
     *
     * @return lista de productos activos
     * @throws NegocioException si ocurre un error en la consulta
     */
    List<ProductoDTO> consultarProductosActivos() throws NegocioException;

    /**
     * Busca productos por nombre y tipo.
     *
     * @param nombre nombre o parte del nombre del producto
     * @param tipo tipo de producto a filtrar
     * @return lista de productos que coinciden con los criterios
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    List<ProductoDTO> buscarProductos(String nombre, TipoProductoDTO tipo) throws NegocioException;

    /**
     * Obtiene una lista con los productos activos que cumplan con los filtros 
     * especificados en los parámetros.
     * 
     * @param nombre Nombre que se desea buscar.
     * @param tipo Tipo de producto que se desea buscar.
     * @return La lista con los productos activos que cumplan con dicho filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    List<ProductoDTO> buscarProductosActivos(String nombre, TipoProductoDTO tipo) throws NegocioException;
}