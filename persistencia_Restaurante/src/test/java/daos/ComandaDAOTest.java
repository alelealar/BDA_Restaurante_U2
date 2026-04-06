package daos;

import entidades.Comanda;
import entidades.DetalleComanda;
import enumerators.EstadoComanda;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Clase de pruebas unitarias para ComandaDAO.
 *
 * Se validan las operaciones CRUD principales sobre la entidad Comanda,
 * verificando el correcto funcionamiento de persistencia, actualización,
 * eliminación y consultas.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class ComandaDAOTest {

    private ComandaDAO comandaDAO;

    @BeforeEach
    public void setUp() {
        comandaDAO = ComandaDAO.getInstance();
    }

    /**
     * Prueba que verifica que una comanda se guarda correctamente en la base de
     * datos.
     *
     * Se crea una comanda con un detalle y se valida que: - No sea null al
     * guardarse. - Se le haya asignado un ID.
     */
    @Test
    public void testGuardarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-001");

        DetalleComanda detalle = new DetalleComanda();
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(50);

        List<DetalleComanda> detalles = new ArrayList<>();
        detalles.add(detalle);

        comanda.setDetalles(detalles);
        comanda.calcularTotal();

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        assertNotNull(guardada);
        assertNotNull(guardada.getId());
    }

    /**
     * Prueba que valida la búsqueda de una comanda por su ID.
     *
     * Primero se guarda una comanda y posteriormente se busca, verificando que:
     * - La comanda encontrada no sea null. - El ID coincida con el guardado.
     */
    @Test
    public void testBuscarComandaPorId() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-002");
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        Comanda encontrada = comandaDAO.buscarComandaPorId(guardada.getId());

        assertNotNull(encontrada);
        assertEquals(guardada.getId(), encontrada.getId());
    }

    /**
     * Prueba que verifica la actualización de una comanda.
     *
     * Se guarda una comanda y posteriormente se modifica su estado, validando
     * que: - El cambio se refleje correctamente en la base de datos.
     */
    @Test
    public void testActualizarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-003");
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        guardada.setEstadoComanda(EstadoComanda.CERRADA);

        Comanda actualizada = comandaDAO.actualizarComanda(guardada);

        assertEquals(EstadoComanda.CERRADA, actualizada.getEstadoComanda());
    }

    /**
     * Prueba que valida la eliminación de una comanda.
     *
     * Se guarda una comanda y posteriormente se elimina, verificando que: - El
     * método retorne true. - La comanda ya no exista en la base de datos.
     */
    @Test
    public void testEliminarComanda() throws PersistenciaException {
        Comanda comanda = new Comanda();
        comanda.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda.setFolio("TEST-004");
        comanda.setDetalles(new ArrayList<>());

        Comanda guardada = comandaDAO.guardarComanda(comanda);

        boolean eliminado = comandaDAO.eliminarComanda(guardada.getId());

        Comanda buscada = comandaDAO.buscarComandaPorId(guardada.getId());

        assertTrue(eliminado);
        assertNull(buscada);
    }

    /**
     * Prueba que valida la obtención de todas las comandas.
     *
     * Se guardan varias comandas y se verifica que: - La lista obtenida no sea
     * null. - Contenga al menos un elemento.
     */
    @Test
    public void testObtenerComandas() throws PersistenciaException {
        Comanda comanda1 = new Comanda();
        comanda1.setEstadoComanda(EstadoComanda.ABIERTA);
        comanda1.setFolio("TEST-005");
        comanda1.setDetalles(new ArrayList<>());

        comandaDAO.guardarComanda(comanda1);

        List<Comanda> lista = comandaDAO.obtenerComandas();

        assertNotNull(lista);
        assertTrue(lista.size() > 0);
    }

    /**
     * Prueba que valida el comportamiento cuando se intenta eliminar una
     * comanda inexistente.
     *
     * Se intenta eliminar un ID que no existe y se verifica que: - El método
     * retorne false.
     */
    @Test
    public void testEliminarComandaInexistente() throws PersistenciaException {
        boolean resultado = comandaDAO.eliminarComanda(999999L);

        assertFalse(resultado);
    }

    /**
     * Prueba que valida el manejo de error al actualizar una comanda
     * inexistente.
     *
     * Se intenta actualizar una comanda con un ID que no existe, esperando que
     * se lance una excepción.
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
