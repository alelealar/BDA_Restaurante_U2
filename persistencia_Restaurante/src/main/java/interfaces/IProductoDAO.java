/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Producto;
import enumerators.TipoProducto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de productos en la
 * capa de acceso a datos (DAO).
 *
 * Esta interfaz establece los métodos que deben ser implementados para realizar
 * operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre la entidad
 * Producto en la base de datos.
 *
 * Su uso permite desacoplar la lógica de negocio de la lógica de persistencia,
 * facilitando el mantenimiento y la escalabilidad del sistema.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoDAO {
    
    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * Este método implementa la lógica necesaria para insertar un registro de
     * producto en el sistema.
     *
     * @param producto objeto Producto que contiene la información a guardar
     * @return el producto guardado con los cambios aplicados (por ejemplo, ID
     * generado).
     * @throws PersistenciaException si ocurre un error durante la operación de
     * persistencia.
     */
    Producto agregarProducto(Producto producto) throws PersistenciaException;
    
    /**
     * Actualiza la información de un producto existente en la base de datos.
     *
     * Este método modifica los datos de un producto previamente registrado.
     *
     * @param producto objeto Producto con la información actualizada
     * @return el producto actualizado.
     * @throws PersistenciaException si ocurre un error durante la actualización.
     */
    Producto actualizarProducto(Producto producto) throws PersistenciaException;
    
    /**
     * Obtiene la lista completa de productos registrados en la base de datos.
     *
     * Este método permite recuperar todos los productos almacenados en el
     * sistema, facilitando su visualización en interfaces como tablas o
     * listados dentro de la aplicación.
     *
     * @return lista de productos registrados.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
    List<Producto> consultarTodos() throws PersistenciaException;
    
    /**
     * Cambia el estado de un producto a 'ACTIVO' por medio de su ID.
     * 
     * Este método ayuda a poder modificar de forma directa el estado de un 
     * producto para poder activarlo.
     * 
     * @param idProducto ID del producto que se quiere activar.
     * @return true si se activó, false si falló.
     * @throws PersistenciaException si ocurre un error durante la activación.
     */
    boolean activarProducto(Long idProducto) throws PersistenciaException;
    
    /**
     * Cambia el estado de un producto a 'INACTIVO' por medio de su ID.
     * 
     * Este método ayuda a poder modificar de forma directa el estado de un 
     * producto para poder desactivarlo.
     * 
     * @param idProducto ID del producto que se quiere desactivar.
     * @return true si se desactivó, false si falló.
     * @throws PersistenciaException si ocurre un error durante la operación.
     */
    boolean desactivarProducto(Long idProducto) throws PersistenciaException;
    
    /**
     * Consulta un producto en la base de datos a partir de su identificador.
     *
     * Este método recupera la información de un producto específico utilizando
     * su ID.
     *
     * @param idProducto ID del producto a consultar.
     * @return el producto encontrado.
     * @throws PersistenciaException si ocurre un error durante la búsqueda.
     */
    Producto consultarProductoPorID(Long idProducto) throws PersistenciaException;
    
    /**
     * Consulta el último identificador generado que se encuentra registrado en
     * la base de datos.
     * 
     * Permite recuperar el número siguiente de identificador a generar según el
     * tipo de producto.
     * @param tipo Tipo de producto.
     * @return Una cadena con el último identificador generado.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
    public String obtenerUltimoIdentificador(TipoProducto tipo) throws PersistenciaException;
    
    /**
     * Obtiene la lista completa de productos registrados en la base de datos 
     * que tengan estado 'ACTIVO'.
     *
     * Este método permite recuperar todos los productos almacenados en el
     * sistema con estado 'ACTIVO', esto para facilitar los productos que se 
     * muestran a la hora de levantar comandas.
     *
     * @return lista de productos activos.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
    public List<Producto> consultarProductosActivos() throws PersistenciaException;
    
    /**
     * Obtiene una lista completa de todos los productos que cumplan con los
     * filtros establecidos en los parámetros, útil para búsquedas dinámicas.
     * 
     * @param nombre Nombre del producto que se busca.
     * @param tipo Tipo de producto que se busca.
     * @return La lista de los productos que cumplan con los parámetros.
     * @throws PersistenciaException si ocurre un error durante la búsqueda.
     */
    public List<Producto> buscarProductos(String nombre, TipoProducto tipo) throws PersistenciaException;
    
    /**
     * 
     * @param nombre
     * @param tipo
     * @return
     * @throws PersistenciaException 
     */
    public List<Producto> buscarProductosActivos(String nombre, TipoProducto tipo) throws PersistenciaException;
    
}
