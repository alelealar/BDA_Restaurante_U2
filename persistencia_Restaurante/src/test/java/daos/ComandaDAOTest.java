package daos;

import entidades.Comanda;
import entidades.Mesa;
import enumerators.EstadoComanda;
import enumerators.EstadoMesa;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas completas para ComandaDAO.
 *
 * Se validan todos los caminos: - Guardado correcto - Guardado inválido -
 * Actualización correcta - Actualización inexistente - Eliminación correcta -
 * Eliminación inexistente - Consultas
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class ComandaDAOTest {

    private ComandaDAO dao;

    private MesaDAO daoMesa = MesaDAO.getInstance();

    @BeforeAll
    static void prepararMesas() {
        MesaDAO daoMesa = MesaDAO.getInstance();

        try {
            List<Mesa> mesas = daoMesa.obtenerMesas();

            if (mesas.isEmpty()) {
                for (int i = 1; i <= 20; i++) {
                    Mesa mesa = new Mesa();
                    mesa.setNumero(i);
                    mesa.setEstado(EstadoMesa.DISPONIBLE);
                    daoMesa.registrarMesa(mesa);
                }
            }

        } catch (PersistenciaException ex) {
            fail("No fue posible preparar las mesas para las pruebas: " + ex.getMessage());
        }
    }

    @BeforeEach
    public void limpiarBD() throws Exception {
        dao = ComandaDAO.getInstance();

        for (Comanda c : dao.obtenerComandas()) {
            dao.eliminarComanda(c.getId());
        }
    }

    @Test
    public void testGuardarComanda_OK() throws Exception {
        Comanda c = new Comanda();
        c.setFechaHora(LocalDateTime.now());
        c.setDetalles(new ArrayList<>());
        c.setFolio("TEST-001");
        c.setEstadoComanda(EstadoComanda.ABIERTA);
        Mesa mesa = daoMesa.obtenerMesas().get(0);
        c.setMesa(mesa);
        Comanda guardada = dao.guardarComanda(c);

        assertNotNull(guardada.getId());
    }

    @Test
    public void testGuardarComanda_Error() {
        assertThrows(PersistenciaException.class,
                () -> dao.guardarComanda(null));
    }

    @Test
    public void testActualizarComanda_OK() throws Exception {
        Comanda c = new Comanda();
        c.setFechaHora(LocalDateTime.now());
        c.setDetalles(new ArrayList<>());
        c.setFolio("TEST-002");
        c.setEstadoComanda(EstadoComanda.ABIERTA);
        Mesa mesa = daoMesa.obtenerMesas().get(1);
        c.setMesa(mesa);

        Comanda comanda = dao.guardarComanda(c);

        Comanda actualizada = dao.actualizarComanda(comanda);

        assertEquals("TEST-002", actualizada.getFolio());
    }

    @Test
    public void testActualizarComanda_Error_NoExiste() {
        Comanda c = new Comanda();
        c.setFechaHora(LocalDateTime.now());
        c.setDetalles(new ArrayList<>());
        c.setFolio("TEST-003");
        c.setEstadoComanda(EstadoComanda.ABIERTA);
        c.setId(999L);

        assertThrows(PersistenciaException.class,
                () -> dao.actualizarComanda(c));
    }

    @Test
    public void testEliminarComanda_OK() throws Exception {
        Comanda c = new Comanda();
        c.setFechaHora(LocalDateTime.now());
        c.setDetalles(new ArrayList<>());
        c.setFolio("TEST-004");
        c.setEstadoComanda(EstadoComanda.ABIERTA);
        Mesa mesa = daoMesa.obtenerMesas().get(3);
        c.setMesa(mesa);

        Comanda comanda = dao.guardarComanda(c);

        assertTrue(dao.eliminarComanda(comanda.getId()));
    }

    @Test
    public void testEliminarComanda_NoExiste() throws Exception {
        assertFalse(dao.eliminarComanda(999L));
    }

    @Test
    public void testBuscarComanda_OK() throws Exception {
        Comanda c = new Comanda();
        c.setFechaHora(LocalDateTime.now());
        c.setDetalles(new ArrayList<>());
        c.setFolio("TEST-005");
        c.setEstadoComanda(EstadoComanda.ABIERTA);
        Mesa mesa = daoMesa.obtenerMesas().get(4);
        c.setMesa(mesa);

        Comanda comanda = dao.guardarComanda(c);

        assertNotNull(dao.buscarComandaPorId(comanda.getId()));
    }

    @Test
    public void testBuscarComanda_NoExiste() throws Exception {
        assertNull(dao.buscarComandaPorId(999L));
    }
}
