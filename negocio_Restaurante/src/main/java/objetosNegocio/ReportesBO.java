/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package objetosNegocio;

import daos.ReportesDAO;
import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IReportesBO;
import interfaces.IReportesDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase que implementa la lógica de negocio para la generación de reportes.
 * Se encarga de procesar la información de clientes y comandas,
 * aplicando reglas de negocio antes de acceder a los datos.
 * 
 * Esta clase actúa como intermediario entre la capa de presentación
 * y la capa de acceso a datos.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */

public class ReportesBO implements IReportesBO{
    private final IReportesDAO dao = ReportesDAO.getInstance();
    private static final Logger LOG = Logger.getLogger(ReportesBO.class.getName());
    
    
    
     /**
     * Instancia única de la clase ReportesBO.
     * Se utiliza para aplicar el patrón Singleton y evitar múltiples instancias.
     */
    private static ReportesBO instancia;
    
    /**
     * Constructor vacío de la clase ReportesBO.
     * Permite la creación de la instancia interna del BO.
     */
    public ReportesBO(){
    }
    
    /**
     * Obtiene la instancia única de ReportesBO.
     * Si no existe una instancia previa, se crea una nueva.
     * @return instancia única de ReportesBO
     */
    public static ReportesBO getInstance(){
        if(instancia == null){
            instancia = new ReportesBO();
        }
        return instancia;
    }

    @Override
    public List<ReporteClientesDTO> obtenerClientes() throws NegocioException {
        try {
            return dao.obtenerReporteClientes();
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los clientes");
            throw new NegocioException("No fue posible consultar los clientes", ex);
        }
    }

    @Override
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Integer visitas) throws NegocioException {
        try{
            return dao.obtenerReporteClientesFiltro(nombre, visitas);   
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los clientes");
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin) throws NegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
