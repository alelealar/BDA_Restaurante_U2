///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
// */
//package daos;
//
//import conexion.ConexionBD;
//import entidades.Ingrediente;
//import enumerators.Unidad;
//import excepciones.PersistenciaException;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import org.junit.jupiter.api.Test;
//import static org.testng.Assert.*;
//
///**
// *
// * @author Home
// */
//public class IngredienteDAONGTest {
//    IngredienteDAO dao = new IngredienteDAO();
//    
//    @AfterEach
//    void limpiar() {
//        EntityManager em = ConexionBD.crearConexion();
//        em.getTransaction().begin();
//
//        Query q = em.createQuery("DELETE FROM Ingrediente i WHERE i.nombre LIKE 'Temp-%'");
//        q.executeUpdate();
//
//        em.getTransaction().commit();
//        em.close();
//    }
//    //passed
//    @Test
//    public void agregarIngrediente_guardarCorrectamente() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);
//
//            Ingrediente resultado = dao.agregarIngrediente(ingrediente);
//            
//            assertNotNull(resultado);
//            assertNotNull(resultado.getId());
//            assertEquals("Temp-1", resultado.getNombre());
//
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }  
//    }
//    
//    //passed
//    @Test
//    public void agregarIngrediente_duplicados() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);    
//            Ingrediente ingrediente2 = new Ingrediente(null, "IN-001", "Temp-2", Unidad.PIEZA, 20, null);
//
//            Ingrediente resultado = dao.agregarIngrediente(ingrediente);
//            
//            assertThrows(PersistenciaException.class, () -> {
//                dao.agregarIngrediente(ingrediente2);
//            });
//
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }  
//    }
//    
//    @Test
//    public void actualizarStock_correcto() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//
//        try {
//            em.getTransaction().begin();
//
//            // Arrange
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);
//            dao.agregarIngrediente(ingrediente);
//
//            // Simular nuevo stock (como lo haría el BO)
//            ingrediente.setStock(70);
//
//            // Act
//            Ingrediente resultado = dao.actualizarStock(ingrediente);
//
//            // Assert
//            assertEquals(70, resultado.getStock().intValue());
//
//            em.getTransaction().rollback();
//
//        } finally {
//            em.close();
//        }
//    }
//    
//    @Test
//    public void actualizarStock_IngredienteInexistente() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 50, null);
//            
//            assertThrows(PersistenciaException.class, () -> {
//                dao.actualizarStock(ingrediente);
//            });
//            
//            em.getTransaction().rollback();
//            
//        } finally {
//            em.close();
//        }       
//    }
//    
//    @Test
//    public void buscarPorId_correcto() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//            
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);
//            dao.agregarIngrediente(ingrediente);
//            
//            Ingrediente buscado = dao.buscarPorId(ingrediente.getId());
//            
//            assertNotNull(buscado);
//            assertEquals(ingrediente.getId(), buscado.getId());
//            assertEquals(ingrediente.getNombre(), buscado.getNombre());         
//            
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }       
//    }
//    
//    @Test
//    public void buscarPorId_noEncontrado() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//            
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);
//            
//            
//            assertThrows(PersistenciaException.class, () -> {
//                dao.buscarPorId(ingrediente.getId());
//            });
//            
//            
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }       
//    }
//    
//    @Test
//    public void obtenerIngredientes_correcto() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//            
//            Ingrediente ingrediente = new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null);
//            dao.agregarIngrediente(ingrediente);
//            
//            Ingrediente ingrediente2 = new Ingrediente(null, "IN-002", "Temp-2", Unidad.PIEZA, 1000, null);
//            dao.agregarIngrediente(ingrediente2);
//            
//            Ingrediente ingrediente3 = new Ingrediente(null, "IN-003", "Temp-3", Unidad.PIEZA, 80, null);
//            dao.agregarIngrediente(ingrediente3);
//            
//            List<Ingrediente> ingredientes = dao.obtenerIngredientes();
//            
//            assertNotNull(ingredientes);
//            assertFalse(ingredientes.isEmpty());
//            assertEquals(ingredientes.get(0).getId(), ingrediente.getId());
//            assertEquals(ingredientes.get(1).getId(), ingrediente2.getId());
//            assertNotEquals(ingredientes.get(1).getId(), ingrediente3.getId());
//            assertEquals(ingredientes.get(2).getId(), ingrediente3.getId());
//            
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }       
//    }
//    
//    @Test
//    public void obtenerIngredientes_NoHay() throws PersistenciaException{
//        EntityManager em = ConexionBD.crearConexion();
//        try{
//            em.getTransaction().begin();
//            List<Ingrediente> ingredientes = dao.obtenerIngredientes();
//
//            assertNotNull(ingredientes);
//            assertTrue(ingredientes.isEmpty());
//            
//            em.getTransaction().rollback(); 
//            
//        } finally {
//            em.close();
//        }       
//    }
//    
//}
