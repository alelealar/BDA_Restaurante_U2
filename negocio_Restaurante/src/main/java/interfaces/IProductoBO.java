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
 *
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoBO {
    
    void agregarProducto(ProductoNuevoDTO producto) throws NegocioException;
    
    void actualizarProducto(ProductoDTO producto) throws NegocioException;
    
    void eliminarProducto(Long idProducto) throws NegocioException;
    
    void activarProducto(Long idProducto) throws NegocioException;
    
    void desactivarProducto(Long idProducto) throws NegocioException;
    
    List<ProductoDTO> consultarTodosProductos() throws NegocioException;
    
    ProductoDTO consultarProductoPorID(Long idProducto) throws NegocioException;
    
}
