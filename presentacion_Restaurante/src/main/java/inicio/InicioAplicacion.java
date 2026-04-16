package inicio;

import controlador.CoordinadorInterfaces;

/**
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class InicioAplicacion {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        try {
            insertsMasivos.DataInitializer.ejecutar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CoordinadorInterfaces coordinadorInterfaces = new CoordinadorInterfaces();

        coordinadorInterfaces.iniciarSistema();
    }
}
