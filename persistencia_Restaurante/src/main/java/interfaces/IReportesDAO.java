/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public interface IReportesDAO {
    
    
    public List<ReporteClientesDTO> obtenerReporteClientes();
    
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Integer visitas);
    
    public List<ReporteComandasDTO> obtenerReporteComandas();
    
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin);

}
