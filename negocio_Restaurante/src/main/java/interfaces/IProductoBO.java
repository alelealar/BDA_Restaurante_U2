/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ProductoDTO;
import dtos.ProductoNuevoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de la lógica de negocio (BO) para la
 * gestión de productos.
 *
 * Esta interfaz actúa como intermediaria entre la capa de presentación y la
 * capa de acceso a datos (DAO), aplicando reglas de negocio, validaciones y
 * controlando el flujo de la información.
 *
 * Utiliza objetos DTO para evitar exponer directamente las entidades de
 * persistencia.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoBO {
    
    /**
     * Registra un nuevo producto en el sistema. Este método valida la información
     * del producto antes de enviarla a la capa de persistencia.
     *
     * @param producto objeto ProductoDTO con los datos del producto a
     * registrar
     * @throws NegocioException si ocurre un error en las reglas de negocio.
     */
    void agregarProducto(ProductoNuevoDTO producto) throws NegocioException;
    
    /**
     * Actualiza la información de un producto existente. Este método valida la 
     * información del producto antes de actualizarlo.
     *
     * @param producto objeto ProductoDTO con la información actualizada.
     * @throws NegocioException si ocurre un error en las reglas de negocio.
     */
    void actualizarProducto(ProductoDTO producto) throws NegocioException;
    
    /**
     * Cambia el estado de un producto existente a 'ACTIVO'. Este método valida
     * que exista un producto con el ID proporcionado antes de modificar su 
     * estado.
     * 
     * @param idProducto identificador del producto.
     * @throws NegocioException Si ocurre un error en las reglas del negocio.
     */
    void activarProducto(Long idProducto) throws NegocioException;
    
    /**
     * Cambia el estado de un producto existente a 'INACTIVO'. Este método valida
     * que exista un producto con el ID proporcionado antes de modificar su 
     * estado.
     * 
     * @param idProducto identificador del producto.
     * @throws NegocioException Si ocurre un error en las reglas del negocio.
     */
    void desactivarProducto(Long idProducto) throws NegocioException;
    
    /**
     * Obtiene todos los productos registrados en la BD.
     * 
     * @return Una lista con todos los productos que se encuentren registrados.
     * @throws NegocioException Si ocurre un error en las reglas del negocio.
     */
    List<ProductoDTO> consultarTodosProductos() throws NegocioException;
    
    /**
     * Busca un producto en la BD a partir de su ID.
     * 
     * @param idProducto identificador del producto.
     * @return El producto que encuentra o null si no halla nada.
     * @throws NegocioException Si ocurre un error en las reglas del negocio.
     */
    ProductoDTO consultarProductoPorID(Long idProducto) throws NegocioException;
    
    /**
     * Obtiene todos los productos registrados en la BD que su estado sea 
     * 'ACTIVO'.
     * 
     * @return Una lista con todos los productos activos en la BD.
     * @throws NegocioException Si ocurre un error en las reglas del negocio.
     */
    List<ProductoDTO> consultarProductosActivos() throws NegocioException;
    
}
