/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inicio;

import controlador.CoordinadorInterfaces;

/**
 *
 * @author Kaleb
 */
public class IncioAplicacion {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        CoordinadorInterfaces coordinadorInterfaces = new CoordinadorInterfaces();

        coordinadorInterfaces.iniciarSistema();
    }
}
