/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDate;

/**
 * Clase DTO (Data Transfer Object) que representa la información de un cliente.
 *
 * Esta clase se utiliza para transferir datos entre las diferentes capas del
 * sistema (por ejemplo, entre la capa de presentación y la lógica de negocio),
 * evitando exponer directamente la entidad de persistencia.
 *
 * Contiene únicamente los atributos necesarios para representar un cliente sin
 * incluir lógica de negocio.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteDTO {

    private Long id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private LocalDate fechaRegistro;

    private Integer numVisitas;

    private Double totalGastado;

    private Integer puntos;

    public ClienteDTO(Long id, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, LocalDate fechaRegistro) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    public ClienteDTO(Long id, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, LocalDate fechaRegistro, Integer numVisitas, Double totalGastado, Integer puntos) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    @Override
    public String toString() {
        return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }

}
