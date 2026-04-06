package pantallas.moduloComandas;

import controlador.CoordinadorModuloComandas;

/**
 *
 * @author Kaleb
 */
public class pruebaInterfazComandas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CoordinadorModuloComandas coordinador = new CoordinadorModuloComandas();
//        coordinador.mostrarPantallaISMesero();
        frmMesas mesas = new frmMesas();
        mesas.setVisible(true);
    }
    
}
