/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import objetosNegocio.ClienteBO;

/**
 * Implementación del Coordinador de operaciones de las clases Bussiones Object
 * (BOs). 
 * 
 * Aquí se maneja la lógica del negocio y navega entre las operaciones y su 
 * flujo.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class Coordinador {
    
    // Capa de Negocio BOs
    private ClienteBO clienteBO;
    
    public Coordinador(){
        this.clienteBO = new ClienteBO();
    }
}
