/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


/**
 * Clase padre Empleado.
 * 
 * Contiene todos los atributos que todos los empleados pueden tener
 * @author Alejandra Leal Armenta, 262719
 */

@Entity
@Table(name = "empleados")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "tipo_empleado")
public class Empleado implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;
    
    @Column(name = "tipo_empleado", nullable = false, length = 25)
    private String tipoEmpleado;

    public Empleado(Long idEmpleado, String tipoEmpleado) {
        this.idEmpleado = idEmpleado;
        this.tipoEmpleado = tipoEmpleado;
    }

    public Empleado() {
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
   
    
}
