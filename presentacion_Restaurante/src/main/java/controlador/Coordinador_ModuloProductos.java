/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ProductoDTO;
import dtos.ProductoNuevoDTO;
import excepciones.NegocioException;
import interfaces.IProductoBO;
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
    
    private IProductoBO productoBO = ProductoBO.getInstance();
    
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
        frmProductos.setVisible(true);
    }
    
    public void agregarProducto(ProductoNuevoDTO producto) {
        try{
            productoBO.agregarProducto(producto);
        } catch(NegocioException ne){
            dlgNotificacion alerta = new dlgNotificacion(frmAgregarProducto, "Ocurrió un error al agregar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            alerta.setVisible(true);
        }
    }
    
    public void actualizarProducto(ProductoDTO producto){
        try{
            productoBO.actualizarProducto(producto);
        } catch(NegocioException ne){
            dlgNotificacion alerta = new dlgNotificacion(frmAgregarProducto, "Ocurrió un error al actualizar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            alerta.setVisible(true);
        }
    }
    
    public void activarProducto(Long idProducto){
        try{
            productoBO.activarProducto(idProducto);
        } catch(NegocioException ne){
            dlgNotificacion alerta = new dlgNotificacion(frmAgregarProducto, "Ocurrió un error al activar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            alerta.setVisible(true);
        }
    }
    
    public void desactivarProducto(Long idProducto){
        try{
            productoBO.desactivarProducto(idProducto);
        } catch(NegocioException ne){
            dlgNotificacion alerta = new dlgNotificacion(frmAgregarProducto, "Ocurrió un error al desactivar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            alerta.setVisible(true);
        }
    }
    
    public void consultarTodosLosProductos(){
        try{
            productoBO.consultarTodosProductos();
        } catch(NegocioException ne){
            dlgNotificacion alerta = new dlgNotificacion(frmAgregarProducto, "Ocurrió un error al consultar todos los productos: " + ne.getMessage(), TipoNotificacion.ERROR);
            alerta.setVisible(true);
        }
    }
    
    public void consultarProductoPorID(Long idProducto){
        try{
            productoBO.consultarProductoPorID(idProducto);
        } catch(NegocioException ne){
            System.out.println("Error al buscar un producto con el id=" + idProducto + ", CAUSA=" + ne.getMessage());
        }
    }
}
