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
 *
 * @author Alejandra Leal Armenta, 262719
 */

@Entity
@Table(name = "clientes_empresariales")
@DiscriminatorValue("FRECUENTE")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClienteFrecuente extends Cliente implements Serializable{
    private Integer numVisitas;
    private Double totalGastado;
    private Integer puntos;

    public ClienteFrecuente(Integer numVisitas, Double totalGastado, Integer puntos, Long id, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, telefono, correo);
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteFrecuente(Integer numVisitas, Double totalGastado, Integer puntos) {
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteFrecuente() {
        super();
    }

    public Integer getNumVisitas() {
        return numVisitas;
    }

    public void setNumVisitas(Integer numVisitas) {
        this.numVisitas = numVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
    
    

}
