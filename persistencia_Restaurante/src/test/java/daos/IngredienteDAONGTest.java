/*
package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import enumerators.Unidad;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredienteDAONGTest {

    IIngredienteDAO dao = IngredienteDAO.getInstance();

    @AfterEach
    void limpiar() {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM Ingrediente").executeUpdate();

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @Test
    public void agregarIngrediente_guardarCorrectamente() throws PersistenciaException {
        Ingrediente ingrediente = new Ingrediente(
                null,
                "IN-001",
                "Temp-1",
                Unidad.PIEZA,
                20,
                null
        );

        Ingrediente resultado = dao.agregarIngrediente(ingrediente);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Temp-1", resultado.getNombre());
    }

    @Test
    public void agregarIngrediente_duplicados() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente( null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null );

        Ingrediente ingrediente2 = new Ingrediente( null, "IN-001", "Temp-2", Unidad.PIEZA, 20, null );

        dao.agregarIngrediente(ingrediente);

        assertThrows(PersistenciaException.class, () -> {
            dao.agregarIngrediente(ingrediente2);
        });
    }

    @Test
    public void actualizarStock_correcto() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente( null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null );

        Ingrediente guardado = dao.agregarIngrediente(ingrediente);

        guardado.setStock(70);

        Ingrediente resultado = dao.actualizarStock(guardado);

        assertEquals(70, resultado.getStock().intValue());
    }

    @Test
    public void actualizarStock_IngredienteInexistente() {

        Ingrediente ingrediente = new Ingrediente( null, "IN-999", "Temp-1", Unidad.PIEZA, 50, null );

        assertThrows(PersistenciaException.class, () -> {
            dao.actualizarStock(ingrediente);
        });
    }

    @Test
    public void buscarPorId_correcto() throws PersistenciaException {

        Ingrediente ingrediente = new Ingrediente( null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null );

        Ingrediente guardado = dao.agregarIngrediente(ingrediente);

        Ingrediente buscado = dao.buscarPorId(guardado.getId());

        assertNotNull(buscado);
        assertEquals(guardado.getId(), buscado.getId());
        assertEquals(guardado.getNombre(), buscado.getNombre());
    }

    @Test
    public void buscarPorId_noEncontrado() {

        assertThrows(PersistenciaException.class, () -> {
            dao.buscarPorId(9999L);
        });
    }

    @Test
    public void obtenerIngredientes_correcto() throws PersistenciaException {

        dao.agregarIngrediente(new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null));
        dao.agregarIngrediente(new Ingrediente(null, "IN-002", "Temp-2", Unidad.PIEZA, 1000, null));
        dao.agregarIngrediente(new Ingrediente(null, "IN-003", "Temp-3", Unidad.PIEZA, 80, null));

        List<Ingrediente> ingredientes = dao.obtenerIngredientes();

        assertNotNull(ingredientes);
        assertFalse(ingredientes.isEmpty());
        assertEquals(3, ingredientes.size());
    }

    @Test
    public void obtenerIngredientes_NoHay() throws PersistenciaException {

        List<Ingrediente> ingredientes = dao.obtenerIngredientes();

        assertNotNull(ingredientes);
        assertTrue(ingredientes.isEmpty());
    }

    @Test
    public void obtenerUltimoIdentificador_NoHay() throws PersistenciaException {

        String ultimoId = dao.obtenerUltimoIdentificador();

        assertNull(ultimoId);
    }

    @Test
    public void obtenerUltimoIdentificador_SiHay() throws PersistenciaException {

        Ingrediente i1 = dao.agregarIngrediente( new Ingrediente(null, "IN-001", "Temp-1", Unidad.PIEZA, 20, null) );

        Ingrediente i2 = dao.agregarIngrediente( new Ingrediente(null, "IN-002", "Temp-2", Unidad.PIEZA, 1000, null) );

        Ingrediente i3 = dao.agregarIngrediente( new Ingrediente(null, "IN-003", "Temp-3", Unidad.PIEZA, 80, null) );

        String ultimoId = dao.obtenerUltimoIdentificador();

        assertNotNull(ultimoId);
        assertEquals("IN-003", ultimoId);
    }
    
    @Test
    public void eliminarIngrediente_correcto() throws PersistenciaException {
            
        Ingrediente ing = new Ingrediente(null, "IN-001", "Temp-Elim", Unidad.PIEZA, 10, null);
        dao.agregarIngrediente(ing);
        
        Ingrediente eliminado = dao.eliminarIngrediente(ing);

        assertNotNull(eliminado);
        assertEquals(ing.getId(), eliminado.getId());
    }
    
    @Test
    public void eliminarIngrediente_noExiste() {
        assertThrows(PersistenciaException.class, () -> {
            Ingrediente ing = new Ingrediente();
            ing.setId(9999L);

            dao.eliminarIngrediente(ing);
        });
    }
    
    @Test
    public void actualizarIngrediente_correcto() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "IN-001", "Temp-Act", Unidad.PIEZA, 10, null);
        dao.agregarIngrediente(ing);

        ing.setNombre("Temp-ActEditado");
        ing.setStock(50);

        Ingrediente actualizado = dao.actualizarIngrediente(ing);

        assertEquals("Temp-ActEditado", actualizado.getNombre());
        assertEquals(50, actualizado.getStock());
    }
    
    @Test
    public void actualizarIngrediente_noExiste() {
        assertThrows(PersistenciaException.class, () -> {
            Ingrediente ing = new Ingrediente();
            ing.setId(9999L);

            dao.actualizarIngrediente(ing);
        });
    }
    
    @Test
    public void existeIngredienteDuplicado_siExiste() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "IN-001", "Tomate", Unidad.PIEZA, 10, null);
        dao.agregarIngrediente(ing);

        boolean duplicado = dao.existeIngredienteDuplicado( null, "Tomate", Unidad.PIEZA);

        assertTrue(duplicado);
    }
    
    @Test
    public void existeIngredienteDuplicado_noExiste() throws PersistenciaException {
        boolean duplicado = dao.existeIngredienteDuplicado( null, "TomateX", Unidad.PIEZA );

        assertFalse(duplicado);
    }
}
*/