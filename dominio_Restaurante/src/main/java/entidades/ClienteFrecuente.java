/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Subclase de Cliente.
 *
 * Tiene solo los atributos que puede tener un cliente frecuente
 *
 * @author Alejandra Leal Armenta, 262719
 */
@Entity
@Table(name = "clientes_frecuentes")
@DiscriminatorValue("FRECUENTE")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClienteFrecuente extends Cliente implements Serializable {

    /**
     * Número de visitas realizadas por el cliente frecuente.
     */
    @Column(name = "num_visitas", nullable = false)
    private Integer numVisitas;

    /**
     * Total de dinero gastado por el cliente frecuente.
     */
    @Column(name = "total_gastado", nullable = false)
    private Double totalGastado;

    /**
     * Puntos acumulados por el cliente frecuente.
     */
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    /**
     * Método que se ejecuta automáticamente antes de persistir la entidad.
     *
     * Asigna los puntos, las visitas y el total gastado en 0, ademas de
     * utilizar el prePersiste de la clase padre Cliente.
     */
    @PrePersist
    public void prePersist() {
        super.prePersist();

        if (puntos == null) {
            puntos = 0;
        }
        if (numVisitas == null) {
            numVisitas = 0;
        }
        if (totalGastado == null) {
            totalGastado = 0.0;
        }
    }

    /**
     * Constructor completo que inicializa tanto los atributos heredados como
     * los propios de ClienteFrecuente.
     *
     * @param numVisitas número de visitas del cliente
     * @param totalGastado total gastado por el cliente
     * @param puntos puntos acumulados
     * @param id identificador del cliente
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno
     * @param apellidoMaterno apellido materno
     * @param telefono número telefónico
     * @param correo correo electrónico
     */
    public ClienteFrecuente(Integer numVisitas, Double totalGastado, Integer puntos,
            Long id, String nombres, String apellidoPaterno,
            String apellidoMaterno, String telefono, String correo) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, telefono, correo);
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /**
     * Constructor que inicializa solo los atributos propios de
     * ClienteFrecuente.
     *
     * @param numVisitas número de visitas del cliente
     * @param totalGastado total gastado por el cliente
     * @param puntos puntos acumulados
     */
    public ClienteFrecuente(Integer numVisitas, Double totalGastado, Integer puntos) {
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /**
     * Constructor vacío requerido por JPA.
     */
    public ClienteFrecuente() {
        super();
    }

    /**
     * Obtiene el número de visitas del cliente frecuente.
     *
     * @return número de visitas
     */
    public Integer getNumVisitas() {
        return numVisitas;
    }

    /**
     * Establece el número de visitas del cliente frecuente.
     *
     * @param numVisitas nuevo número de visitas
     */
    public void setNumVisitas(Integer numVisitas) {
        this.numVisitas = numVisitas;
    }

    /**
     * Obtiene el total gastado por el cliente frecuente.
     *
     * @return total gastado
     */
    public Double getTotalGastado() {
        return totalGastado;
    }

    /**
     * Establece el total gastado por el cliente frecuente.
     *
     * @param totalGastado nuevo total gastado
     */
    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    /**
     * Obtiene los puntos acumulados del cliente frecuente.
     *
     * @return puntos del cliente
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos del cliente frecuente.
     *
     * @param puntos nuevos puntos
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
}
