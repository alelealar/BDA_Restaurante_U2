/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package insertsMasivos;

import adaptadores.MesaAdapter;
import dtos.ClienteNuevoDTO;
import dtos.MesaDTO;
import entidades.Mesa;
import entidades.Mesero;
import enumerators.EstadoMesaDTO;
import excepciones.NegocioException;
import interfaces.IClienteBO;
import interfaces.IMeseroBO;
import java.util.Arrays;
import java.util.List;
import objetosNegocio.ClienteBO;
import objetosNegocio.MesaBO;
import objetosNegocio.MeseroBO;

/**
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class InsertsMasivos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IClienteBO clienteBO = ClienteBO.getInstance();
        MesaBO mesaBO = MesaBO.getInstance();  
        IMeseroBO meseroBO = MeseroBO.getInstance();


        //CLIENTES
        /*
        List<ClienteNuevoDTO> lista = Arrays.asList(
            new ClienteNuevoDTO("Ana Sofía", "Martínez", "López", "6441000001", "a1@gmail.com"),
            new ClienteNuevoDTO("Carlos Eduardo", "Hernández", "García", "6441000002", "a2@gmail.com"),
            new ClienteNuevoDTO("María Fernanda", "Luna", "Ramírez", "6441000003", "a3@gmail.com"),
            new ClienteNuevoDTO("Luis Ángel", "Soto", "Castro", "6441000004", "a4@gmail.com"),
            new ClienteNuevoDTO("José Manuel", "Vega", "Torres", "6441000005", "a5@gmail.com")
        );

        for (ClienteNuevoDTO cliente : lista) {
            try {
                clienteBO.registrarCliente(cliente);
            } catch (NegocioException ex) {
                System.out.println("Error al insertar: " + cliente);
            }
        }
        
        //MESAS
        try {
                
                mesaBO.inicializarMesas(10);

            System.out.println("Mesas creadas correctamente");

        } catch (Exception e) {
            System.out.println("Error al crear mesas: " + e.getMessage());
        }
        
        

        List<Mesero> meseros = Arrays.asList(
            new Mesero("mario"),
            new Mesero("juan"),
            new Mesero("pedro"),
            new Mesero("luis"),
            new Mesero("carlos"),
            new Mesero("david"),
            new Mesero("alex"),
            new Mesero("jorge"),
            new Mesero("ricardo"),
            new Mesero("fernando")
        );

        try {

            for (Mesero m : meseros) {
                meseroBO.registrarMesero(m.getUsuario());
            }

            System.out.println("Meseros creados correctamente");

        } catch (Exception e) {
            System.out.println("Error al crear meseros: " + e.getMessage());
        }
*/
    }

}
