package daos;

import conexion.ConexionBD;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Mesa;
import entidades.Producto;
import enumerators.EstadoComanda;
import enumerators.EstadoMesa;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Clase de pruebas unitarias para ComandaDAO.
 *
 * Se validan las operaciones CRUD sobre la entidad Comanda.
 *
 * Antes de cada prueba se crean datos base necesarios: - 10 mesas persistidas
 * en la base de datos. - Se asume que existen productos con ID válidos en la
 * BD.
 *
 * Esto evita errores de persistencia por relaciones ManyToOne.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class ComandaDAOTest {

    private static ComandaDAO comandaDAO;
    private static List<Mesa> mesas;

    /**
     * Inicializa el DAO y crea 10 mesas en la base de datos antes de cada
     * prueba.
     */
    @BeforeAll
    public static void initAll() {
        comandaDAO = ComandaDAO.getInstance();
        mesas = new ArrayList<>();
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            // Solo se ejecuta UNA VEZ al iniciar la clase
            for (int i = 1; i <= 10; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero(i);
                mesa.setEstado(EstadoMesa.DISPONIBLE);
                em.persist(mesa);
                mesas.add(mesa);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Prueba que verifica que una comanda se guarda correctamente.
     */
    @Test
    public void testGuardarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-001");
        comanda.setMesa(mesas.get(0));

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        assertNotNull(guardada);
        assertNotNull(guardada.getId());
    }

    /**
     * Prueba que valida la búsqueda de una comanda por ID.
     */
    @Test
    public void testBuscarComandaPorId() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-002");
        comanda.setMesa(mesas.get(1));
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        Comanda encontrada = comandaDAO.buscarComandaPorId(guardada.getId());

        assertNotNull(encontrada);
        assertEquals(guardada.getId(), encontrada.getId());
    }

    /**
     * Prueba que verifica la actualización de una comanda.
     */
    @Test
    public void testActualizarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-003");
        comanda.setMesa(mesas.get(2));
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        guardada.setEstadoComanda(EstadoComanda.ENTREGADA);

        Comanda actualizada = comandaDAO.actualizarComanda(guardada);

        assertEquals(EstadoComanda.ENTREGADA, actualizada.getEstadoComanda());
    }

    /**
     * Prueba que valida la eliminación de una comanda.
     */
    @Test
    public void testEliminarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-004");
        comanda.setMesa(mesas.get(3));
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        boolean eliminado = comandaDAO.eliminarComanda(guardada.getId());

        Comanda buscada = comandaDAO.buscarComandaPorId(guardada.getId());

        assertTrue(eliminado);
        assertNull(buscada);
    }

    /**
     * Prueba que valida la obtención de todas las comandas.
     */
    @Test
    public void testObtenerComandas() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-005");
        comanda.setMesa(mesas.get(4));
        comanda.setDetalles(new ArrayList<>());

        comandaDAO.guardarComanda(comanda);

        List<Comanda> lista = comandaDAO.obtenerComandas();

        assertNotNull(lista);
        assertTrue(!lista.isEmpty());
    }

    /**
     * Prueba que valida eliminar una comanda inexistente.
     */
    @Test
    public void testEliminarComandaInexistente() throws PersistenciaException {
        boolean resultado = comandaDAO.eliminarComanda(999999L);
        assertFalse(resultado);
    }

    /**
     * Prueba que valida error al actualizar una comanda inexistente.
     */
    @Test
    public void testActualizarComandaInexistente() {
        Comanda comanda = new Comanda();
        comanda.setId(999999L);

        assertThrows(PersistenciaException.class, () -> {
            comandaDAO.actualizarComanda(comanda);
        });
    }
}
