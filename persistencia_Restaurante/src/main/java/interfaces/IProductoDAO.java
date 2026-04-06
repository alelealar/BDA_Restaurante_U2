/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Producto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoDAO {
    
    Producto agregarProducto(Producto producto) throws PersistenciaException;
    
    Producto actualizarProducto(Producto producto) throws PersistenciaException;
    
    boolean eliminarProducto(Long idProducto) throws PersistenciaException;
    
    List<Producto> consultarTodos() throws PersistenciaException;
    
    boolean activarProducto(Long idProducto) throws PersistenciaException;
    
    boolean desactivarProducto(Long idProducto) throws PersistenciaException;
    
    Producto consultarProductoPorID(Long idProducto) throws PersistenciaException;
    
}
