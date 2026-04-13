/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dtos;

import enumerators.EstadoComandaDTO;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */

public class ReporteComandasDTO {
    
    private String nombreCliente;
    private Integer mesa;
    private Double totalVenta;
    private EstadoComandaDTO estadoComanda;
    private LocalDateTime fechaHora;
    List<DetalleComandaDTO> pedidos;

    public ReporteComandasDTO(String nombreCliente, Integer mesa, Double totalVenta, EstadoComandaDTO estadoComanda, LocalDateTime fechaHora, List<DetalleComandaDTO> pedidos) {
        this.nombreCliente = nombreCliente;
        this.mesa = mesa;
        this.totalVenta = totalVenta;
        this.estadoComanda = estadoComanda;
        this.fechaHora = fechaHora;
        this.pedidos = pedidos;
    }

    public ReporteComandasDTO() {
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public EstadoComandaDTO getEstadoComanda() {
        return estadoComanda;
    }

    public void setEstadoComanda(EstadoComandaDTO estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public List<DetalleComandaDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<DetalleComandaDTO> pedidos) {
        this.pedidos = pedidos;
    }
    
    
    
    
}
