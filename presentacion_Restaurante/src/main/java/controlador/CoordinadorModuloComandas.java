package controlador;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import excepciones.NegocioException;
import interfaces.IComandaBO;
import interfaces.IMesaBO;
import interfaces.IMeseroBO;
import java.util.List;
import objetosNegocio.ComandaBO;
import objetosNegocio.MesaBO;
import objetosNegocio.MeseroBO;
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

    public CoordinadorModuloComandas() {
        comandaBO = ComandaBO.getInstance();
        meseroBO = MeseroBO.getInstance();
        mesaBO = MesaBO.getInstance();
    }

    public void mostrarPantallaISMesero() {
        if (isMesero == null) {
            isMesero = new frmISMesero(this);
        }
        isMesero.setVisible(true);
    }

    public void mostrarPantallaComandas() {
        if (comandas == null) {
            comandas = new frmComandas(this);
        }
        isMesero.dispose();
        comandas.setVisible(true);
    }

    public void mostrarPantallaCrearPedido() {
        if (pedidos == null) {
            pedidos = new frmPedidos(this);
        }
        pedidos.setVisible(true);
    }

    public void mostrarPantallaMesas() {
        if (mesas == null) {
            mesas = new frmMesas(this);
        }
        mesas.setVisible(true);
        isMesero.dispose();
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

    public boolean validarMesero(String usuario) throws NegocioException {
        return meseroBO.existeMesero(usuario);
    }

    public void registrarMesero(String usuario) throws NegocioException {
        meseroBO.registrarMesero(usuario);
    }

    public List<MesaDTO> obtenerMesas() throws NegocioException {
        return mesaBO.obtenerMesas();
    }

}
