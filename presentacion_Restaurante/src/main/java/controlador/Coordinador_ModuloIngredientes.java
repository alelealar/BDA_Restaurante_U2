/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import dtos.ProductoIngredienteDTO;
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
    /**
     * majojo:
     * La lista compartida que se andan rolando entre frms/coordinadores.
     */
    private List<ProductoIngredienteDTO> listaIngredientes;
    /**
     * majojo:
     * El coordinador de productos para que se puedan regresar a la pantalla
     * agregarProducto.
     */
    private Coordinador_ModuloProductos coordinadorProductos;
    
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
        /*
        majojo:
        Nomás le agregué que recibiera el coordinador.
        */
        frmIngredientes.setCoordinadorIngredientes(this);
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
    
    /**
     * majojo:
     * Establece la lista compartida entre frames/coordinadores.
     * @param lista 
     */
    public void setListaIngredientes(List<ProductoIngredienteDTO> lista){
        this.listaIngredientes = lista;
    }
    
    /**
     * majojo:
     * Por medio del coordinador de productos abre el frmAgregarProducto ya que
     * terminó de seleccionar los productos.
     */
    public void abrirFrmAgregarProducto(){
        coordinadorProductos.setDetallesProducto(this.listaIngredientes);
        frmIngredientes.dispose();
        coordinadorProductos.abrirFrmAgregarProducto();
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
    
    /**
     * majojo:
     * Simplemente que le establezca el coordinador a usar y la referencia de 
     * la lista que creó el coordinador de productos.
     */
    public void abrirFrmIngredientesModoProducto() {
        frmIngredientes = new FrmIngredientes();
        frmIngredientes.setCoordinadorIngredientes(this);
        frmIngredientes.activarModoProducto();
        frmIngredientes.setListaIngredientes(listaIngredientes);
        frmIngredientes.setVisible(true);
    }
    
    /**
     * majojo:
     * Es para establecer el coordinador de productos porque si crea el suyo
     * pasa el mismo despapaye de que cada coordinador/frm anda en su rollo y 
     * no se hablan realmente entre sí.
     * @param coordinadorP El coordinador que usará.
     */
    public void setCoordinadorProductos(Coordinador_ModuloProductos coordinadorP){
        this.coordinadorProductos = coordinadorP;
    }
    
    public Integer cantidadStock(){
        return dlgStock.getCantidad(); 
        
    }
    
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException{
        bo.actualizarStock(idIngrediente, cantidad, tipo);
    }
    
    
}
