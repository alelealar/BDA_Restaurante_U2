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
    
    /**
     * Identificador único del empleado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;

    /**
     * Tipo de empleado dentro del sistema (ejemplo: mesero, gerente, etc.).
     */
    @Column(name = "tipo_empleado", nullable = false, length = 25)
    private String tipoEmpleado;

    /**
     * Constructor que inicializa todos los atributos del empleado.
     *
     * @param idEmpleado identificador único del empleado
     * @param tipoEmpleado tipo de empleado
     */
    public Empleado(Long idEmpleado, String tipoEmpleado) {
        this.idEmpleado = idEmpleado;
        this.tipoEmpleado = tipoEmpleado;
    }

    /**
     * Constructor vacío requerido por JPA.
     */
    public Empleado() {
    }

    /**
     * Obtiene el identificador del empleado.
     *
     * @return id del empleado
     */
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Establece el identificador del empleado.
     *
     * @param idEmpleado nuevo id del empleado
     */
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el tipo de empleado.
     *
     * @return tipo de empleado
     */
    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    /**
     * Establece el tipo de empleado.
     *
     * @param tipoEmpleado nuevo tipo de empleado
     */
    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
}
