/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import dtos.ReporteClientesDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.Mesa;
import enumerators.EstadoComanda;
import enumerators.EstadoMesa;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;


/**
 *
 * @author Home
 */
public class ReportesDAONGTest {
    
    private ReportesDAO reportesDAO;
    private EntityManager em;

    private Cliente cliente;
    private Comanda comanda1;
    private Comanda comanda2;
    private Mesa mesa;
    
    @BeforeEach
    void setUp() {
        em = ConexionBD.crearConexion();
        reportesDAO = new ReportesDAO();

        em.getTransaction().begin();

        cliente = new ClienteFrecuente(2, 500.0, 10L, null, "Alejandra", "Leal", "Armenta", "6440000000", "test@test.com");
        em.persist(cliente);
        
        mesa = new Mesa(null, 1, EstadoMesa.OCUPADA);
        em.persist(mesa);

        comanda1 = new Comanda(null, EstadoComanda.ABIERTA, "F001", 100.0, LocalDateTime.now().minusDays(2), mesa, null, cliente);
        comanda2 = new Comanda(null, EstadoComanda.ABIERTA, "F002", 200.0, LocalDateTime.now(), mesa, null, cliente);

        em.persist(comanda1);
        em.persist(comanda2);

        em.getTransaction().commit();
    }
    
    @AfterEach
    void tearDown() {
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Comanda").executeUpdate();
        em.createQuery("DELETE FROM ClienteFrecuente").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();

        em.getTransaction().commit();

        if (em != null) {
            em.close();
        }
    }
    
    
    @Test
    public void testObtenerReporteClientes_correcto() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientes();

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);
        
    }
    
    @Test
    public void testObtenerReporteClientesFiltro_correcto() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientesFiltro("alejandra", 2L);
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());

        ReporteClientesDTO dto = resultado.get(0);

        assertTrue(dto.getNombre().toLowerCase().contains("alejandra"));
        assertEquals(2L, dto.getVisitas());
                
    }
    
    @Test
    public void testObtenerReporteClientesFiltro_noExistenCoincidencias() throws Exception {
        List<ReporteClientesDTO> resultado = reportesDAO.obtenerReporteClientesFiltro("sapotoro", null);
        assertNotNull(resultado);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    public void testObtenerReporteComandasFiltro() throws Exception {
    }
    
}
