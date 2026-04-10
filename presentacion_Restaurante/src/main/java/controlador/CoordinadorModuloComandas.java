package controlador;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import excepciones.NegocioException;
import interfaces.IComandaBO;
import interfaces.IMesaBO;
import interfaces.IMeseroBO;
import interfaces.IProductoBO;
import java.util.List;
import objetosNegocio.ComandaBO;
import objetosNegocio.MesaBO;
import objetosNegocio.MeseroBO;
import objetosNegocio.ProductoBO;
import pantallas.moduloComandas.frmComandas;
import pantallas.moduloComandas.frmISMesero;
import pantallas.moduloComandas.frmMesas;
import pantallas.moduloComandas.frmPedidos;

/**
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class CoordinadorModuloComandas {
    
    private frmISMesero isMesero;
    private frmMesas mesas;
    private frmComandas comandas;
    private frmPedidos pedidos;
    
    private final IComandaBO comandaBO;
    private final IMeseroBO meseroBO;
    private final IMesaBO mesaBO;
    private final IProductoBO productoBO;
    
    public CoordinadorModuloComandas() {
        comandaBO = ComandaBO.getInstance();
        meseroBO = MeseroBO.getInstance();
        mesaBO = MesaBO.getInstance();
        productoBO = ProductoBO.getInstance();
    }
    
    public void mostrarPantallaISMesero() {
        if (isMesero == null) {
            isMesero = new frmISMesero(this);
        }
        isMesero.setVisible(true);
    }
    
    public void mostrarPantallaCrearPedido(MesaDTO mesa) {
        if (pedidos == null) {
            pedidos = new frmPedidos(this, mesa);
        }
        pedidos.setVisible(true);
    }
    
    public void mostrarPantallaComandas(MesaDTO mesa) {
        if (comandas != null) {
            comandas.dispose();
        }
        
        if (pedidos != null) {
            pedidos.dispose();
        }
        comandas = new frmComandas(this, mesa);
        if (mesas != null) {
            mesas.setVisible(false);
        }
        comandas.setVisible(true);
    }
    
    public void mostrarPantallaMesas() {
        if (mesas != null) {
            mesas.dispose();
        }
        mesas = new frmMesas(this);
        
        if (comandas != null) {
            comandas.dispose();
            comandas = null;
        }
        
        mesas.setVisible(true);
        
        if (isMesero != null) {
            isMesero.dispose();
        }
    }
    
    public void registrarComanda(ComandaDTO comanda) throws NegocioException {
        comandaBO.guardarComanda(comanda);
    }
    
    public void actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        comandaBO.actualizarComanda(comandaDTO);
    }
    
    public void eliminarComanda(Long idComanda) throws NegocioException {
        comandaBO.eliminarComanda(idComanda);
    }
    
    public List<ComandaDTO> obtenerComandas() throws NegocioException {
        return comandaBO.obtenerComandas();
    }
    
    public boolean validarMesero(String usuario) throws NegocioException {
        return meseroBO.existeMesero(usuario);
    }
    
    public void registrarMesero(String usuario) throws NegocioException {
        meseroBO.registrarMesero(usuario);
    }
    
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        return mesaBO.obtenerMesas();
    }
    
    public List<ProductoDTO> consultarProductos() throws NegocioException {
        return productoBO.consultarTodosProductos();
    }
    
    public void agregarProductoInterfaz(ProductoDTO producto) {
        this.pedidos.agregarProductosComanda(producto);
    }
    
    public void actualizarTotal(Double monto) {
        this.pedidos.actualizarTotal(monto);
    }
    
}
