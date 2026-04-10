/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import dtos.ProductoNuevoDTO;
import excepciones.NegocioException;
import interfaces.IProductoBO;
import java.util.ArrayList;
import java.util.List;
import notificaciones.TipoNotificacion;
import notificaciones.dlgNotificacion;
import objetosNegocio.ProductoBO;
import pantallasProducto.frmAgregarProducto;
import pantallasProducto.frmProductos;

/**
 *
 * @author María José Valdez Iglesias - 262775
 */
public class Coordinador_ModuloProductos {
    
    private frmProductos frmProductos;
    private frmAgregarProducto frmAgregarProducto;
    /**
     * El coordinador ingredientes es el que ayuda a que podamos entrar a la 
     * ventana de ingredientes para escoger los detallesProducto.
     */
    private Coordinador_ModuloIngredientes coordinadorIngredientes;
    private IProductoBO productoBO = ProductoBO.getInstance();
    /**
     * Aquí se crea esta lista, para que a lo largo de los coordinadores y los
     * frames involucrados, solo se campartan la lista y no creen otras, para no
     * perder la referencia ni los registros guardados.
     */
    private List<ProductoIngredienteDTO> listaIngredientes = new ArrayList<>();
    
    public void abrirFrmProductos(){
        if (frmProductos == null) {
            frmProductos = new frmProductos(this);
        }
        if (frmAgregarProducto == null) {
            frmAgregarProducto = new frmAgregarProducto(this);
        }
        frmProductos.setVisible(true);
        frmAgregarProducto.dispose();
    }
    
    public void abrirFrmAgregarProducto(){
        if (frmAgregarProducto == null) {
            frmAgregarProducto = new frmAgregarProducto(this);
        }
        frmProductos.setVisible(false);
        frmAgregarProducto.setVisible(true);
    }
    
    public void abrirFrmModificarProducto(ProductoDTO producto){
        if (this.frmAgregarProducto == null) {
            this.frmAgregarProducto = new frmAgregarProducto(this);
        }
        frmAgregarProducto.setProductoParaModificar(producto);
        frmAgregarProducto.setVisible(true);
        frmProductos.dispose();
    }
    
    public void refrescarTablaProductos(){
        frmProductos.cargarTabla();
    }
    
    /**
     * Cada que un producto ya fue modificado/agregado, se limpia la lista para 
     * tenerla nueva para el siguiente registro.
     */
    public void limpiarListaIngredientes(){
        listaIngredientes.clear();
    }
    
    /**
     * Esto es para pasarle la lista desde ingredientes hasta el frmAgregarProducto.
     * @param ingredientes La lista con los ProductoIngredienteDTO.
     */
    public void setDetallesProducto(List<ProductoIngredienteDTO> ingredientes){
        frmAgregarProducto.setDetallesProducto(ingredientes);
        frmAgregarProducto.setVisible(true);
    }
    
    /**
     * Por medio del coordinadorIngredientes aquí se abre la pantalla del módulo
     * Ingredientes.
     * 
     * Crea el coordinador, le pasa la referencia a la lista compartida al frm
     * Ingredientes y abre la pantalla.
     */
    public void abrirMenuIngredientes(){
        if(coordinadorIngredientes == null){
            coordinadorIngredientes = new Coordinador_ModuloIngredientes();
            coordinadorIngredientes.setCoordinadorProductos(this);
        }
        coordinadorIngredientes.setListaIngredientes(listaIngredientes);
        coordinadorIngredientes.abrirFrmIngredientesModoProducto();
        frmAgregarProducto.setVisible(false);
    }
    
    public boolean agregarProducto(ProductoNuevoDTO producto) {
        try{
            productoBO.agregarProducto(producto);
            return true;
        } catch(NegocioException ne){
            dlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al agregar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    public boolean actualizarProducto(ProductoDTO producto){
        try{
            productoBO.actualizarProducto(producto);
            return true;
        } catch(NegocioException ne){
            dlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al actualizar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    public boolean activarProducto(Long idProducto){
        try{
            productoBO.activarProducto(idProducto);
            return true;
        } catch(NegocioException ne){
            dlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al activar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    public boolean desactivarProducto(Long idProducto){
        try{
            productoBO.desactivarProducto(idProducto);
            return true;
        } catch(NegocioException ne){
            dlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al desactivar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    public List<ProductoDTO> consultarTodosLosProductos(){
        try{
            return productoBO.consultarTodosProductos();
        } catch(NegocioException ne){
            dlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al consultar todos los productos: " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }
    
    public ProductoDTO consultarProductoPorID(Long idProducto){
        try{
            return productoBO.consultarProductoPorID(idProducto);
        } catch(NegocioException ne){
            System.out.println("Error al buscar un producto con el id=" + idProducto + ", CAUSA=" + ne.getMessage());
            return null;
        }
    }
    
}
