/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import excepciones.NegocioException;
import interfaces.IReportesBO;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import notificaciones.DlgNotificacion;
import notificaciones.TipoNotificacion;
import objetosNegocio.ReportesBO;
import pantallas.pantallasModuloReportes.FrmReporteClientes;
import pantallas.pantallasModuloReportes.FrmReporteComandas;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class Coordinador_ModuloReportes {
    
    FrmReporteComandas frmReporteComandas;
    FrmReporteClientes frmReporteClientes;
    IReportesBO bo = ReportesBO.getInstance();
    
    public List<ReporteClientesDTO> obtenerClientes() throws NegocioException{
        return bo.obtenerClientes();
    }
    
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Long visitas) {
        try {
            return bo.obtenerReporteClientesFiltro(nombre, visitas);
        } catch (NegocioException ex) {
            Logger.getLogger(Coordinador_ModuloReportes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin) {
        try{
            return bo.obtenerReporteComandasFiltro(inicio, fin);
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmReporteComandas, "No se pudieron obtener las comandas filtradas según el rango de fecha establecido: " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }

}
