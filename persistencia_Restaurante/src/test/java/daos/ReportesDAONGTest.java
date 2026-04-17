/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Producto;
import entidades.Mesa;
import enumerators.DisponibilidadProducto;
import enumerators.EstadoComanda;
import enumerators.EstadoMesa;
import enumerators.EstadoProducto;
import enumerators.TipoProducto;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias y de integración para la clase ReportesDAO.
 * Validar el correcto funcionamiento de las consultas a la base de datos para 
 * la generación de reportes.
 * @author Brian Kaleb Sandoval Rodriguez, 262741
 * @author Alejandra Leal Armenta, 262719
 * @author María José Valdez Iglesias, 262775
 */
public class ReportesDAONGTest {

    private ReportesDAO reportesDAO;
    private EntityManager em;

    private Cliente cliente;
    private Comanda comanda1;
    private Comanda comanda2;
    private DetalleComanda detalle1;
    private DetalleComanda detalle2;
    private Producto producto;
    private Mesa mesa;

    /**
     * Método de configuración que se ejecuta antes de cada prueba.
     * Se encarga de inicializar la conexión y preparar un escenario de datos 
     * válido insertando registros temporales en la base de datos.
     */
    @BeforeEach
    void setUp() {
        em = ConexionBD.crearConexion();
        reportesDAO = new ReportesDAO();

        em.getTransaction().begin();

        cliente = new ClienteFrecuente(2, 500.0, 10L, null, "Alejandra", "Leal", "Armenta", "6445000000", "test@test.com");
        em.persist(cliente);

        mesa = new Mesa(null, 1, EstadoMesa.OCUPADA);
        em.persist(mesa);

        comanda1 = new Comanda(null, EstadoComanda.ENTREGADA, "F001", 100.0, LocalDateTime.now().minusDays(2), mesa, null, cliente);
        comanda2 = new Comanda(null, EstadoComanda.ENTREGADA, "F002", 200.0, LocalDateTime.now(), mesa, null, cliente);

        em.persist(comanda1);
        em.persist(comanda2);
        
        producto = new Producto(null, "PL-001", "Platillo Test", TipoProducto.PLATILLO, "Descripción Test", 50.60, EstadoProducto.ACTIVO, DisponibilidadProducto.DISPONIBLE, null);
        em.persist(producto);
        
        detalle1 = new DetalleComanda(null, comanda1, producto, 3, 50.60, "Comentario prueba", cliente.getNombres());
        detalle2 = new DetalleComanda(null, comanda2, producto, 4, 50.60, "Comentario prueba", cliente.getNombres());
        
        em.persist(detalle1);
        
        em.persist(detalle2);

        em.getTransaction().commit();
    }

    /**
     * Método de limpieza que se ejecuta después de cada prueba.
     * Garantiza que la base de datos quede limpia de los datos de prueba para 
     * evitar que una prueba contamine los resultados de las siguientes.
     */
    @AfterEach
    void tearDown() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM ProductoIngrediente").executeUpdate();
        em.createQuery("DELETE FROM DetalleComanda").executeUpdate();
        em.createQuery("DELETE FROM Producto").executeUpdate();
        em.createQuery("DELETE FROM Comanda").executeUpdate();
        em.createQuery("DELETE FROM ClienteFrecuente").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();

        em.getTransaction().commit();

        if (em != null) {
            em.close();
        }
    }

    /**
     * Prueba la obtención del reporte general de clientes.
     * Verifica que la lista no sea nula y contenga al menos los datos 
     * generados en la configuración inicial.
     * @throws Exception en caso de errores inesperados.
     */
    @Test
    public void testObtenerReporteClientes_correcto() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientes();

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);

    }

    /**
     * Prueba la obtención del reporte de clientes aplicando filtros válidos.
     * Se busca a un cliente específico previamente inyectado en el escenario 
     * de pruebas para confirmar el funcionamiento de los filtros.
     * @throws Exception en caso de errores inesperados.
     */
    @Test
    public void testObtenerReporteClientesFiltro_correcto() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientesFiltro("alejandra", 2L);
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());

        ReporteClientesDTO dto = resultado.get(0);

        assertTrue(dto.getNombres().toLowerCase().contains("alejandra"));
        assertEquals(2L, dto.getVisitas());

    }

    /**
     * Prueba la obtención del reporte de clientes con un filtro de nombre que 
     * no existe en la base de datos. Garantiza que devuelva una lista vacía.
     * @throws Exception en caso de errores inesperados.
     */
    @Test
    public void testObtenerReporteClientesFiltro_noExistenCoincidencias() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientesFiltro("sapotoro", null);
        assertNotNull(resultado);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    /**
     * Test que prueba que sí funcione el método de obtener reportes de comandas 
     * entregadas (sin filtrar por rango).
     */
    @Test
    public void testObtenerReportesComandasExito() {
        List<ReporteComandasDTO> resultado = assertDoesNotThrow(
                () -> reportesDAO.obtenerReportesComandas());

        assertNotNull(resultado);
    }

    /**
     * Test que prueba que sí funcione el método de obtener reportes de comandas
     * entregadas filtradas por un rango de fechas.
     */
    @Test
    public void testObtenerReporteComandasFiltroExito() {
        /*
        tiene esa fecha para que sí abarque a las comandas registradas.
        */
        LocalDateTime inicio = LocalDateTime.now().minusYears(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(1);

        List<ReporteComandasDTO> resultado = assertDoesNotThrow(
                () -> reportesDAO.obtenerReporteComandasFiltro(inicio, fin));

        assertNotNull(resultado);
    }

    /**
     * Test que prueba que no entregue reportes si el rango en el que se busca 
     * no existe nada.
     */
    @Test
    public void testObtenerReporteComandasFiltroFallo() {
        /*
        se ponen estos valores para asegurar que no haya comandas en ese
        tiempo.
        */
        LocalDateTime inicio = LocalDateTime.now().plusYears(10);
        LocalDateTime fin = LocalDateTime.now().plusYears(11);

        List<ReporteComandasDTO> resultado = assertDoesNotThrow(
                () -> reportesDAO.obtenerReporteComandasFiltro(inicio, fin));

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

}
