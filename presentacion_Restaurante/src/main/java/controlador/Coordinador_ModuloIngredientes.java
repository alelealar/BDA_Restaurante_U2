/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import java.io.File;
import java.util.List;
import objetosNegocio.IngredienteBO;
import pantallas.moduloIngredientes.DlgFileChooser;
import pantallas.moduloIngredientes.DlgModificarStock;
import pantallas.moduloIngredientes.FrmAgregarIngrediente;
import pantallas.moduloIngredientes.FrmIngredientes;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class Coordinador_ModuloIngredientes {
    private FrmIngredientes frmIngredientes;
    private FrmAgregarIngrediente frmAgregarIngrediente;
    private DlgFileChooser dlgFile;
    private DlgModificarStock dlgStock;
    
    private IngredienteBO bo = IngredienteBO.getInstance();
    
    public void abrirFrmAgregarIngrediente(){
        if (frmIngredientes != null) {
            frmIngredientes.desactivarModoProducto();
            frmIngredientes.dispose();
        }

        frmAgregarIngrediente = new FrmAgregarIngrediente(this);
        frmAgregarIngrediente.setVisible(true);
    }
    
    public void abrirFrmIngredientes(){
        frmIngredientes = new FrmIngredientes();
        frmIngredientes.desactivarModoProducto();
        frmIngredientes.setVisible(true);
    }
    
    public void fileChooser(){
        dlgFile = new DlgFileChooser(frmAgregarIngrediente, true, this);
        dlgFile.setVisible(true);      
    }
    
    public void archivoAFrmAgregarIngrediente(File archivo){
        if (frmAgregarIngrediente == null) {
            System.out.println("ERROR: formulario no inicializado");
            return;
        }

        frmAgregarIngrediente.setImagenOpcional(archivo);
    }
    
    public void agregarIngrediente(IngredienteNuevoDTO ingrediente) throws NegocioException{
        bo.agregarIngrediente(ingrediente);
    }
    
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException{
        return bo.obtenerIngredientes();
    }
    
    public void frmIngredientesADlgModificarStock(String nombreIngrediente, TipoMovimiento tipo){
        dlgStock = new DlgModificarStock(frmIngredientes, true, this);
        
        dlgStock.setTipo(tipo);
        dlgStock.setNombreProducto(nombreIngrediente);
        
        dlgStock.setVisible(true);
    }
    
    public void frmIngredientesAFrmActualizarIngrediente(IngredienteDTO ingrediente){
        frmAgregarIngrediente = new FrmAgregarIngrediente(this);
        frmAgregarIngrediente.modoActualizar(ingrediente);
        frmAgregarIngrediente.setVisible(true);
    }
    
    public void abrirFrmIngredientesModoProducto() {
        FrmIngredientes frm = new FrmIngredientes();
        frm.activarModoProducto();
        frm.setVisible(true);
    }
    
    public Integer cantidadStock(){
        return dlgStock.getCantidad(); 
        
    }
    
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException{
        bo.actualizarStock(idIngrediente, cantidad, tipo);
    }
    
    
}
