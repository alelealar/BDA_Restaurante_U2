/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Define las operaciones de acceso a datos para la generación de reportes.
 * Establece los métodos necesarios para obtener información de clientes y comandas,
 * con o sin filtros aplicados.
 * 
 * Esta interfaz sirve como contrato para las implementaciones DAO encargadas
 * de la consulta de reportes en la base de datos.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public interface IReportesDAO {
    
    /**
     * Obtiene el reporte general de clientes registrados en el sistema.
     * Devuelve la información sin aplicar filtros.
     * @return lista de clientes para el reporte
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    public List<ReporteClientesDTO> obtenerReporteClientes() throws PersistenciaException;
    
    /**
     * Obtiene el reporte de clientes aplicando filtros de búsqueda.
     * Filtra por nombre y número de visitas.
     * @param nombre nombre del cliente a buscar
     * @param visitas número de visitas del cliente
     * @return lista de clientes que cumplen con el filtro
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Long visitas) throws PersistenciaException;
        
    /**
     * Obtiene el reporte de comandas filtrado por un rango de fechas.
     * Permite consultar comandas entre una fecha de inicio y fin.
     * @param inicio fecha inicial del rango
     * @param fin fecha final del rango
     * @return lista de comandas dentro del rango
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException;

}
