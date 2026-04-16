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
 * sistema, como la capa de presentación y la lógica de negocio, evitando
 * exponer directamente la entidad persistente.
 *
 * Contiene únicamente los datos necesarios para representar a un cliente, sin
 * incluir lógica adicional.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteDTO {

    /**
     * Identificador único del cliente.
     */
    private Long id;

    /**
     * Nombres del cliente.
     */
    private String nombres;

    /**
     * Apellido paterno del cliente.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del cliente.
     */
    private String apellidoMaterno;

    /**
     * Número telefónico del cliente.
     */
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    private String correo;

    /**
     * Fecha de registro del cliente en el sistema.
     */
    private LocalDate fechaRegistro;

    /**
     * Número de visitas realizadas por el cliente frecuente.
     */
    private Integer numVisitas;

    /**
     * Total acumulado gastado por el cliente frecuente.
     */
    private Double totalGastado;

    /**
     * Puntos acumulados por el cliente frecuente.
     */
    private Long puntos;

    /**
     * Constructor que inicializa los datos básicos del cliente.
     *
     * @param id identificador del cliente
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno
     * @param apellidoMaterno apellido materno
     * @param telefono número telefónico
     * @param correo correo electrónico
     * @param fechaRegistro fecha de registro
     */
    public ClienteDTO(Long id, String nombres, String apellidoPaterno,
            String apellidoMaterno, String telefono, String correo,
            LocalDate fechaRegistro) {

        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor que inicializa todos los datos del cliente, incluyendo
     * información de cliente frecuente.
     *
     * @param id identificador del cliente
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno
     * @param apellidoMaterno apellido materno
     * @param telefono número telefónico
     * @param correo correo electrónico
     * @param fechaRegistro fecha de registro
     * @param numVisitas número de visitas realizadas
     * @param totalGastado total gastado acumulado
     * @param puntos puntos acumulados
     */
    public ClienteDTO(Long id, String nombres, String apellidoPaterno,
            String apellidoMaterno, String telefono, String correo,
            LocalDate fechaRegistro, Integer numVisitas,
            Double totalGastado, Long puntos) {

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

    /**
     * Constructor vacío.
     *
     * Requerido para inicializaciones manuales o frameworks.
     */
    public ClienteDTO() {
    }

    /**
     * Obtiene el identificador del cliente.
     *
     * @return identificador del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene los nombres del cliente.
     *
     * @return nombres del cliente
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del cliente.
     *
     * @param nombres nuevos nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene el apellido paterno del cliente.
     *
     * @return apellido paterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del cliente.
     *
     * @param apellidoPaterno nuevo apellido paterno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del cliente.
     *
     * @return apellido materno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del cliente.
     *
     * @param apellidoMaterno nuevo apellido materno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el número telefónico del cliente.
     *
     * @return número telefónico
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número telefónico del cliente.
     *
     * @param telefono nuevo número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo nuevo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la fecha de registro del cliente.
     *
     * @return fecha de registro
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del cliente.
     *
     * @param fechaRegistro nueva fecha de registro
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
     * @return puntos acumulados
     */
    public Long getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos acumulados del cliente frecuente.
     *
     * @param puntos nuevos puntos
     */
    public void setPuntos(Long puntos) {
        this.puntos = puntos;
    }

    /**
     * Devuelve una representación en texto del cliente.
     *
     * @return nombre completo del cliente
     */
    @Override
    public String toString() {
        return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
