package controlador;

import pantallas.frmAgregarCliente;
import pantallas.frmClientes;
import pantallas.frmInicio;

/**
 *
 * @author Kaleb
 */
public class CoordinadorInterfaces {

    private frmInicio formInicio;
    private frmClientes formClientes;
    private frmAgregarCliente formAgregarClientes;

    public CoordinadorInterfaces() {
    }

    public void iniciarSistema() {
        if (formInicio == null) {
            formInicio = new frmInicio(this);
        }
        formInicio.setVisible(true);
    }

    public void mostrarFormularioClientes() {
        if (formClientes == null) {
            formClientes = new frmClientes(this);
        }
        formInicio.dispose();
        formClientes.setVisible(true);
    }

    public void mostrarFormularioAgregarClientes() {
        if (formAgregarClientes == null) {
            formAgregarClientes = new frmAgregarCliente(this);
        }
        formClientes.setVisible(false);
        formAgregarClientes.setVisible(true);
    }

}
