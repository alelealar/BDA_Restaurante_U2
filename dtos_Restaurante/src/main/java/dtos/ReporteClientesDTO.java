/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dtos;

import java.time.LocalDateTime;


/**
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */

public class ReporteClientesDTO {
    
    private String nombre;
    private Long visitas;
    private Double totalGastado;
    private LocalDateTime fechaUltimaComanda;
    private Integer puntosAcumulados;

    public ReporteClientesDTO(String nombre, Long visitas, Double totalGastado, LocalDateTime fechaUltimaComanda, Integer puntosAcumulados) {
        this.nombre = nombre;
        this.visitas = visitas;
        this.totalGastado = totalGastado;
        this.fechaUltimaComanda = fechaUltimaComanda;
        this.puntosAcumulados = puntosAcumulados;
    }

    public ReporteClientesDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getVisitas() {
        return visitas;
    }

    public void setVisitas(Long visitas) {
        this.visitas = visitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public LocalDateTime getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }

    public void setFechaUltimaComanda(LocalDateTime fechaUltimaComanda) {
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    public Integer getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(Integer puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
    
    
}
