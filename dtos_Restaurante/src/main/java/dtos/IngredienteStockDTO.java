/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dtos;

import enumerators.TipoMovimiento;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteStockDTO {
    private Long idIngrediente;
    private Integer cantidad;
    private TipoMovimiento tipoMovimiento;

    public IngredienteStockDTO(Long idIngrediente, Integer cantidad, TipoMovimiento tipoMovimiento) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
    }

    public IngredienteStockDTO() {
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    
    
}
