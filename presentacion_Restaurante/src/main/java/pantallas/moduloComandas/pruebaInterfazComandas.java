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

        try {

            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        CoordinadorModuloComandas coordinador = new CoordinadorModuloComandas();
        coordinador.mostrarPantallaISMesero();
    }

}
