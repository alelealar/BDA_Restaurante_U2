/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import dtos.ProductoNuevoDTO;
import enumerators.TipoProductoDTO;
import excepciones.NegocioException;
import interfaces.IProductoBO;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import notificaciones.TipoNotificacion;
import notificaciones.DlgNotificacion;
import objetosNegocio.ProductoBO;
import pantallasProducto.FrmAgregarProducto;
import pantallasProducto.FrmProductos;

/**
 * Clase coordinadora de las operaciones y los frames en el módulo de productos.
 * 
 * Esta se emcarga de la conexión y de estar pasando las referencias a los objetos
 * que se están intercambiando, en caso de requerirse.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class Coordinador_ModuloProductos {
    
    /**
     * Frame Productos que controla y comunica el coordinador.
     */
    private FrmProductos frmProductos;
    
    /**
     * Frame AgregarProducto que controla y comunica el coordinador.
     */
    private FrmAgregarProducto frmAgregarProducto;
    
    /**
     * El coordinador ingredientes es el que ayuda a que podamos entrar a la 
     * ventana de ingredientes para escoger los detallesProducto.
     */
    private Coordinador_ModuloIngredientes coordinadorIngredientes;
    
    /**
     * Instancia de producto BO usada para todas las operaciones que involucren 
     * a la base de datos.
     */
    private IProductoBO productoBO = ProductoBO.getInstance();
    
    /**
     * Aquí se crea esta lista, para que a lo largo de los coordinadores y los
     * frames involucrados, solo se campartan la lista y no creen otras, para no
     * perder la referencia ni los registros guardados.
     */
    private List<ProductoIngredienteDTO> listaIngredientes = new ArrayList<>();
    
    /**
     * Método para abrir y usar el frmProductos.
     * 
     * En caso de no exstir lo crea.
     * 
     * Prepara el frame para ser utilizado y descarta aquellos frames que pasan
     * a segundo plano/ya no se requieren.
     */
    public void abrirFrmProductos(){
        if (frmProductos == null) {
            frmProductos = new FrmProductos(this);
        }
        if (frmAgregarProducto == null) {
            frmAgregarProducto = new FrmAgregarProducto(this);
        }
        frmProductos.setVisible(true);
        frmAgregarProducto.dispose();
    }
    
    /**
     * Método para abrir y usar el frmAgregarProductos.
     * 
     * En caso de no exstir lo crea.
     * 
     * Prepara el frame para ser utilizado y descarta aquellos frames que pasan
     * a segundo plano/ya no se requieren.
     */
    public void abrirFrmAgregarProducto(){
        if (frmAgregarProducto == null) {
            frmAgregarProducto = new FrmAgregarProducto(this);
        }
        frmProductos.setVisible(false);
        frmAgregarProducto.setVisible(true);
    }
    
    /**
     * Método que modifica el FrmAgregarProducto para darle la vista de modificar.
     * @param producto Recibe el producto que sea desea modificar para pasarle
     * la información al frame y que trabaje con este.
     */
    public void abrirFrmModificarProducto(ProductoDTO producto){
        if (this.frmAgregarProducto == null) {
            this.frmAgregarProducto = new FrmAgregarProducto(this);
        }
        frmAgregarProducto.setProductoParaModificar(producto);
        frmAgregarProducto.setVisible(true);
        frmProductos.dispose();
    }
    
    /**
     * Método que vuelve a cargar la tabla de los registros de productos en el
FrmProductos.
     */
    public void refrescarTablaProductos(){
        frmProductos.cargarTabla();
    }
    
    /**
     * Método usado cada que un producto ya fue modificado/agregado, se limpia 
     * la lista para tenerla nueva para el siguiente registro.
     * 
     * Esta lista es la referencia compartida que se van pasando entre frames y 
     * coordinadores.
     */
    public void limpiarListaIngredientes(){
        listaIngredientes.clear();
    }
    
    /**
     * Método para pasarle la lista desde ingredientes hasta el FrmAgregarProducto.
     * @param ingredientes La lista con los ProductoIngredienteDTO.
     */
    public void setDetallesProducto(List<ProductoIngredienteDTO> ingredientes){
        frmAgregarProducto.setDetallesProducto(ingredientes);
        frmAgregarProducto.setVisible(true);
    }
    
    /**
     * Método para por medio del coordinadorIngredientes abrir la pantalla del 
     * módulo Ingredientes.
     * 
     * Crea el coordinador, le pasa la referencia de la lista compartida al frm
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
    
    /**
     * Método para comunicarse con la DAO y agregar productos, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos
     * tener una instancia de ProductoBO.
     * @param producto ProductoNuevoDTO que se quiere agregar.
     * @return true si la operación fu éxitosa, false si falló.
     */
    public boolean agregarProducto(ProductoNuevoDTO producto) {
        try{
            productoBO.agregarProducto(producto);
            return true;
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al agregar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y actualizar productos, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos
     * tener una instancia de ProductoBO.
     * @param producto ProductoDTO que se quiere actualizar.
     * @return true si la operación fue éxitosa, false si falló.
     */
    public boolean actualizarProducto(ProductoDTO producto){
        try{
            productoBO.actualizarProducto(producto);
            return true;
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al actualizar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y activar un producto, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos 
     * tener una instancia de ProductoBO.
     * @param idProducto ID del producto que se desea activar.
     * @return true si la operación fue éxitosa, false si falló.
     */
    public boolean activarProducto(Long idProducto){
        try{
            productoBO.activarProducto(idProducto);
            return true;
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al activar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y desactivar un producto, por medio de 
     * la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos 
     * tener una instancia de ProductoBO.
     * @param idProducto ID del producto que se desea desactivar.
     * @return true si la operación fue éxitosa, false si falló.
     */
    public boolean desactivarProducto(Long idProducto){
        try{
            productoBO.desactivarProducto(idProducto);
            return true;
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al desactivar el producto: " + ne.getMessage(), TipoNotificacion.ERROR);
            return false;
        }
    }
    
    /**
     * Método para comunicase con la DAO y consultar todos los productos en la 
     * base de datos, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos 
     * tener una instancia de ProductoBO.
     * @return Una lista de ProductoDTO con los registros.
     */
    public List<ProductoDTO> consultarTodosLosProductos(){
        try{
            return productoBO.consultarTodosProductos();
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al consultar todos los productos: " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y consultar un producto con su ID, por
     * medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos
     * tener una instancia de ProductoBO.
     * @param idProducto ID del producto que se quiere consultar.
     * @return El ProductoDTO con todos sus datos.
     */
    public ProductoDTO consultarProductoPorID(Long idProducto){
        try{
            return productoBO.consultarProductoPorID(idProducto);
        } catch(NegocioException ne){
            System.out.println("Error al buscar un producto con el id=" + idProducto + ", CAUSA=" + ne.getMessage());
            return null;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y consultar todos los productos activos
     * en la base de datos, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos 
     * tener una instancia de ProductoBO.
     * @return Una lista de ProductoDTO con los registros que tengan estado
     * activo.
     */
    public List<ProductoDTO> consultarProductoActivos(){
        try{
            return productoBO.consultarProductosActivos();
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al consultar todos los productos activos: " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }
    
    /**
     * Método para comunicarse con la DAO y consultar todos los productos que 
     * cumplan con los filtros en la base de datos, por medio de la BO.
     * Esto es para que los frames puedan realizar dichas operaciones sin ellos 
     * tener una instancia de ProductoBO.
     * @param nombre Nombre a buscar.
     * @param tipo Tipo de producto a buscar.
     * @return Una lista de ProductoDTO con los registros que coincidan con los
     * filtros aplicados.
     */
    public List<ProductoDTO> buscarProductos(String nombre, TipoProductoDTO tipo){
        try{
            return productoBO.buscarProductos(nombre, tipo);
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frmAgregarProducto, "Ocurrió un error al buscar coincidencias con=" + nombre + ", " + tipo + ": " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }
    
    /**
     * Método para buscar todos los productos activos con cierto nombre, esta es
     * una versión gráfica para implementar en frames.
     * @param frame Frame donde se quiera mostrar.
     * @return La lista de coincidencias de tipo ProductoDTO.
     */
    public List<ProductoDTO> buscarProductosActivos(Frame frame){
        try{
            List<ProductoDTO> busqueda = new ArrayList<>();
            int opcion = DlgNotificacion.mostrarNotificacion(frame, "Ingrese nombre y tipo (opcional) de productos a buscar: ", TipoNotificacion.BUSCADOR);
            if(opcion == DlgNotificacion.RET_ACEPTAR){
                busqueda = productoBO.buscarProductosActivos(DlgNotificacion.getBusqueda(), DlgNotificacion.getTipoSeleccionado());
            }
            return busqueda;
        } catch(NegocioException ne){
            DlgNotificacion.mostrarNotificacion(frame, "Ocurrió un error al buscar coincidencias con=" + DlgNotificacion.getBusqueda() + ", " + DlgNotificacion.getTipoSeleccionado() + ": " + ne.getMessage(), TipoNotificacion.ERROR);
            return null;
        }
    }
    
}
