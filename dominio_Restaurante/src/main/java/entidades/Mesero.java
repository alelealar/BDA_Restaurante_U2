/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * Subclase de Empleado.
 * 
 * Contiene solo los atirbutos que puede tener un empleado que
 * es mesero.
 * 
 * @author Alejandra Leal Armenta, 262719
 */
@Entity
@Table(name = "meseros")
@DiscriminatorValue("MESERO")
@PrimaryKeyJoinColumn(name = "id_empleado")
public class Mesero extends Empleado implements Serializable{
    private String usuario;

    public Mesero(String usuario, Long idEmpleado, String tipoEmpleado) {
        super(idEmpleado, tipoEmpleado);
        this.usuario = usuario;
    }

    public Mesero(String usuario) {
        this.usuario = usuario;
    }

    public Mesero() {
        super();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
}
