package controlador;

import pantallas.moduloComandas.frmISMesero;

/**
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class CoordinadorModuloComandas {
    
    frmISMesero isMesero;
    
    public void mostrarPantallaISMesero() {
        if (isMesero == null) {
            isMesero = new frmISMesero(this);
        }
        isMesero.setVisible(true);
    }
    
}
