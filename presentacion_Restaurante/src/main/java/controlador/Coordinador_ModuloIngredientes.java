/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import dtos.ProductoIngredienteDTO;
import enumerators.TipoMovimiento;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import excepciones.NegocioException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import objetosNegocio.IngredienteBO;
import pantallas.moduloIngredientes.DlgFileChooser;
import pantallas.moduloIngredientes.DlgModificarStock;
import pantallas.moduloIngredientes.FrmAgregarIngrediente;
import pantallas.moduloIngredientes.FrmIngredientes;


/**
 * Coordinador/controlador de las interfaces del modulo Ingredientes.
 * 
 * Hace que las pantallas no se comuniquen entre si que las pantallas no tengan contacto directo
 * con los Objetos de negocio
 * @author Alejandra Leal Armenta, 262719
 */

public class Coordinador_ModuloIngredientes {
    private FrmIngredientes frmIngredientes;
    private FrmAgregarIngrediente frmAgregarIngrediente;
    private DlgFileChooser dlgFile;
    private DlgModificarStock dlgStock;
    private List<ProductoIngredienteDTO> listaIngredientes;
    /**
     * El coordinador de productos para que se puedan regresar a la pantalla
     * agregarProducto.
     */
    private Coordinador_ModuloProductos coordinadorProductos;
    
    private IngredienteBO bo = IngredienteBO.getInstance();
    
    /**
     * abre la pantalla de agregar/actualizar ingrediente.
     */
    public void abrirFrmAgregarIngrediente(){
        if (frmIngredientes != null) {
            frmIngredientes.desactivarModoProducto();
            frmIngredientes.dispose();
        }

        frmAgregarIngrediente = new FrmAgregarIngrediente(this);
        frmAgregarIngrediente.setVisible(true);
    }
    
    /**
     * abre la pantalla de los ingredientes.
     */
    public void abrirFrmIngredientes(){
        frmIngredientes = new FrmIngredientes();
        frmIngredientes.setCoordinadorIngredientes(this);
        frmIngredientes.desactivarModoProducto();
        frmIngredientes.setVisible(true);
    }
    
    /**
     * Abre el panel file chooser, donde se buscan las imagenes de tu compu.
     */
    public void fileChooser(){
        dlgFile = new DlgFileChooser(frmAgregarIngrediente, true, this);
        dlgFile.setVisible(true);      
    }
    
    /**
     * Manda la imagen del fileChooser a la pantalla de agregarIngrediente.
     * @param archivo 
     */
    public void archivoAFrmAgregarIngrediente(File archivo){
        if (frmAgregarIngrediente == null) {
            System.out.println("ERROR: formulario no inicializado");
            return;
        }
        
        try{
            File carpeta = new File("imagenes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            
            File destino = new File(carpeta, archivo.getName());
            
            Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frmAgregarIngrediente.setImagenOpcional(archivo);
    }
    
    /**
     * Establece la lista compartida entre frames/coordinadores.
     * @param lista 
     */
    public void setListaIngredientes(List<ProductoIngredienteDTO> lista){
        this.listaIngredientes = lista;
    }
    
    /**
     * Por medio del coordinador de productos abre el frmAgregarProducto ya que
     * terminó de seleccionar los productos.
     */
    public void abrirFrmAgregarProducto(){
        coordinadorProductos.setDetallesProducto(this.listaIngredientes);
        frmIngredientes.dispose();
        coordinadorProductos.abrirFrmAgregarProducto();
    }
    
    /**
     * Agrega el ingrediente dado por la UI.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void agregarIngrediente(IngredienteNuevoDTO ingrediente) throws NegocioException{
        bo.agregarIngrediente(ingrediente);
    }
    
    /**
     * Obtiene los ingredientes registrados en la BD.
     * @return
     * @throws NegocioException 
     */
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException{
        return bo.obtenerIngredientes();
    }
    
    /**
    * Abre el diálogo para modificar el stock de un ingrediente.
    * @param nombreIngrediente
    * @param tipo 
    */
    public void frmIngredientesADlgModificarStock(String nombreIngrediente, TipoMovimiento tipo){
        dlgStock = new DlgModificarStock(frmIngredientes, true, this);
        
        dlgStock.setTipo(tipo);
        dlgStock.setNombreProducto(nombreIngrediente);
        
        dlgStock.setVisible(true);
    }
    
    /**
     * Abre el formulario en modo actualización con los datos del ingrediente.
     * @param ingrediente 
     */
    public void frmIngredientesAFrmActualizarIngrediente(IngredienteDTO ingrediente){
        frmAgregarIngrediente = new FrmAgregarIngrediente(this);
        frmAgregarIngrediente.modoActualizar(ingrediente);
        frmAgregarIngrediente.setVisible(true);
    }
    
    /**
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
    
    /**
     * Obtiene la cantidad capturada en el diálogo de stock.
     * @return 
     */
    public Integer cantidadStock(){
        return dlgStock.getCantidad(); 
        
    }
    
    /**
     * Actualiza el stock de un ingrediente.
     * @param idIngrediente
     * @param cantidad
     * @param tipo
     * @throws NegocioException 
     */
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException{
        bo.actualizarStock(idIngrediente, cantidad, tipo);
    }
    
     /**
     * Elimina un ingrediente ya resgistrado en la BD.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void eliminarIngrediente(IngredienteDTO ingrediente) throws NegocioException{
        bo.eliminarIngrediente(ingrediente);
    }
    
    /**
     * Actualiza un ingrediente ya registrado en la BD.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws NegocioException{
        bo.actualizarIngrediente(ingrediente);
    }
    
    public List<IngredienteDTO> filtrarIngredientes(String nombre, UnidadDTO unidad) throws NegocioException{
        return bo.buscarIngredientes(nombre, unidad);
    }
    
    
    
}
