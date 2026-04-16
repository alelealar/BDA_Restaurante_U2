/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Define las operaciones de lógica de negocio para la generación de reportes.
 * Establece los métodos necesarios para procesar y obtener información de clientes y comandas,
 * aplicando reglas de negocio antes de acceder a los datos.
 * 
 * Esta clase (o interfaz) sirve como capa intermedia entre la presentación y el acceso a datos.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public interface IReportesBO {
    
    /**
     * Obtiene el reporte general de clientes.
     * Retorna la información sin aplicar filtros.
     * @return lista de clientes para el reporte
     * @throws NegocioException en caso de error en la lógica de negocio
     */
    public List<ReporteClientesDTO> obtenerClientes() throws NegocioException;
    
    /**
     * Obtiene el reporte de clientes aplicando filtros.
     * Filtra por nombre y número de visitas.
     * @param nombre nombre del cliente a buscar
     * @param visitas número de visitas del cliente
     * @return lista de clientes que cumplen con el filtro
     * @throws NegocioException en caso de error en la lógica de negocio
     */
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Long visitas) throws NegocioException;
    
    /**
     * Obtiene el reporte de comandas filtrado por rango de fechas.
     * Permite consultar comandas entre una fecha de inicio y fin.
     * @param inicio fecha inicial del rango
     * @param fin fecha final del rango
     * @return lista de comandas dentro del rango especificado
     * @throws NegocioException en caso de error en la lógica de negocio
     */
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin) throws NegocioException;
}
