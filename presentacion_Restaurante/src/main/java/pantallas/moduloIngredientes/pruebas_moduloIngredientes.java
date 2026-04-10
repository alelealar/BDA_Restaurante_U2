/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package pantallas.moduloIngredientes;

import controlador.Coordinador_ModuloIngredientes;

/**
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class pruebas_moduloIngredientes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FrmIngredientes frm = new FrmIngredientes();
        /*
        majojo: nomás le puse un coordinador.
        */
        frm.setCoordinadorIngredientes(new Coordinador_ModuloIngredientes());
        frm.desactivarModoProducto();
        frm.setVisible(true);
    }

}
