/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import entidades.Producto;
import entidades.ProductoIngrediente;
import enumerators.DisponibilidadProducto;
import enumerators.EstadoProducto;
import enumerators.TipoProducto;
import enumerators.Unidad;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import interfaces.IProductoDAO;
import interfaces.IProductoIngredienteDAO;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Alejandra Leal Armenta, 262719
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoIngredienteDAONGTest {
    
    IProductoIngredienteDAO piDao = ProductoIngredienteDAO.getInstance();
    IIngredienteDAO ingredienteDao = IngredienteDAO.getInstance();
    IProductoDAO productoDao = ProductoDAO.getInstance();
    
    @AfterEach
    void limpiar() {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            // primero relaciones (tabla puente)
            em.createQuery("DELETE FROM ProductoIngrediente").executeUpdate();

            // luego entidades principales
            em.createQuery("DELETE FROM Ingrediente").executeUpdate();
            em.createQuery("DELETE FROM Producto").executeUpdate();

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    @Test
    public void existeIngredienteEnReceta_Existe() throws PersistenciaException{
        
        Ingrediente ingrediente = new Ingrediente("IN-001", "Temp-1", Unidad.PIEZA, 20, 10, null );
        Producto producto = new Producto( null, "PR-TEST-01", "Producto de prueba", TipoProducto.BEBIDA, "Descripción de prueba", 50.0, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null );
        ingredienteDao.agregarIngrediente(ingrediente);
        productoDao.agregarProducto(producto);                
        
        ProductoIngrediente proIng = new ProductoIngrediente(1L, 2, ingrediente, producto);
        piDao.agregarProductoIngrediente(proIng);
        
        boolean validacion = piDao.existeIngredienteEnRecetas(ingrediente.getId());
        
        assertTrue(validacion);
    }
    
    @Test
    public void existeIngredienteEnReceta_noExiste() throws PersistenciaException{
        
        Ingrediente ingrediente = new Ingrediente("IN-001", "Temp-1", Unidad.PIEZA, 20, 10, null );
        Producto producto = new Producto( null, "PR-TEST-01", "Producto de prueba", TipoProducto.BEBIDA, "Descripción de prueba", 50.0, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null );             
        
        ProductoIngrediente proIng = new ProductoIngrediente(1L, 2, ingrediente, producto);
        
        boolean validacion = piDao.existeIngredienteEnRecetas(ingrediente.getId());
        assertFalse(validacion);
    }
    
    
    @Test
    public void agregarProductoIngrediente_correcto() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente("IN-001", "Temp-1", Unidad.PIEZA, 20, 10, null );
        Producto producto = new Producto( null, "PR-TEST-01", "Producto de prueba", TipoProducto.BEBIDA, "Descripción de prueba", 50.0, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null );
        ingredienteDao.agregarIngrediente(ingrediente);
        productoDao.agregarProducto(producto);     

        ProductoIngrediente pi = new ProductoIngrediente(null, 2, ingrediente, producto);
        ProductoIngrediente resultado = piDao.agregarProductoIngrediente(pi);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        assertEquals(ingrediente.getId(), resultado.getIngrediente().getId());
        assertEquals(producto.getId(), resultado.getProducto().getId());
    }
    
    @Test
    public void agregarProductoIngrediente_duplicado() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente("IN-001", "Temp-1", Unidad.PIEZA, 20, 10, null );
        Producto producto = new Producto( null, "PR-TEST-01", "Producto de prueba", TipoProducto.BEBIDA, "Descripción de prueba", 50.0, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null );
        ingredienteDao.agregarIngrediente(ingrediente);
        productoDao.agregarProducto(producto);     

        ProductoIngrediente pi1 = new ProductoIngrediente(null, 2, ingrediente, producto);
        ProductoIngrediente pi2 = new ProductoIngrediente(null, 3, ingrediente, producto);

        piDao.agregarProductoIngrediente(pi1);

        assertThrows(PersistenciaException.class, () -> {
            piDao.agregarProductoIngrediente(pi2);
        });
    }
    
    @Test
    public void eliminarProductoIngrediente_correcto() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente("IN-001", "Temp-1", Unidad.PIEZA, 20, 10, null );
        Producto producto = new Producto( null, "PR-TEST-01", "Producto de prueba", TipoProducto.BEBIDA, "Descripción de prueba", 50.0, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null );
        ingredienteDao.agregarIngrediente(ingrediente);
        productoDao.agregarProducto(producto);     

        ProductoIngrediente pi = new ProductoIngrediente(null, 2, ingrediente, producto);

        piDao.agregarProductoIngrediente(pi);

        ProductoIngrediente eliminado = piDao.eliminarProductoIngrediente( producto.getId(), ingrediente.getId() );

        assertNotNull(eliminado);
        assertEquals(ingrediente.getId(), eliminado.getIngrediente().getId());
        assertEquals(producto.getId(), eliminado.getProducto().getId());
    }
    
    @Test
    public void eliminarProductoIngrediente_noExiste() {

        assertThrows(PersistenciaException.class, () -> {
            piDao.eliminarProductoIngrediente(999L, 999L);
        });
    }
    
}
