/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dtos.ReporteClientesDTO;
import excepciones.NegocioException;
import interfaces.IReportesBO;
import java.util.List;
import objetosNegocio.ReportesBO;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class Coordinador_ModuloReportes {
    IReportesBO bo = ReportesBO.getInstance();
    
    public List<ReporteClientesDTO> obtenerClientes() throws NegocioException{
        return bo.obtenerClientes();
    }

}
