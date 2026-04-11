/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import entidades.ProductoIngrediente;
import excepciones.PersistenciaException;
import excepciones.PersistenciaException;

/**
 * Define las operaciones de acceso a datos para la relación producto-ingrediente.
 * 
 * Establece los métodos necesarios para gestionar la asociación entre productos
 * e ingredientes en la base de datos.
 * 
 * @author Alejandra Leal Armenta, 262719
 * @author María José Valdez Iglesias - 262775
 */
public interface IProductoIngredienteDAO {
    
    /**
     * Verifica si un ingrediente está siendo utilizado en alguna receta.
     * @param idIngrediente
     * @return
     * @throws PersistenciaException 
     */
    boolean existeIngredienteEnRecetas(Long idIngrediente) throws PersistenciaException;
    
    /**
     * Inserta una relación producto-ingrediente en la base de datos.
     * @param pi
     * @return
     * @throws PersistenciaException 
     */
    public ProductoIngrediente agregarProductoIngrediente(ProductoIngrediente pi) throws PersistenciaException;
    
    /**
     * Elimina la relación entre un producto y un ingrediente.
     * @param idProducto
     * @param idIngrediente
     * @return
     * @throws PersistenciaException 
     */
    public ProductoIngrediente eliminarProductoIngrediente(Long idProducto, Long idIngrediente) throws PersistenciaException;
    
}
