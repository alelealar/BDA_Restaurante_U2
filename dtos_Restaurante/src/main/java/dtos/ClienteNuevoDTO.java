package dtos;

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
public class ClienteNuevoDTO {

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
     * Número de teléfono del cliente.
     */
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    private String correo;

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
    private Integer puntos;

    /**
     * Constructor vacío requerido para frameworks y serialización.
     */
    public ClienteNuevoDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos del cliente.
     *
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno del cliente
     * @param apellidoMaterno apellido materno del cliente
     * @param telefono número de teléfono del cliente
     * @param correo correo electrónico del cliente
     */
    public ClienteNuevoDTO(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
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
     * @param nombres nuevos nombres del cliente
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
     * Obtiene el número de teléfono del cliente.
     *
     * @return teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono nuevo número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo del cliente
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
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos acumulados del cliente frecuente.
     *
     * @param puntos nuevos puntos
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    /**
     * Devuelve una representación en cadena del objeto ClienteNuevoDTO.
     *
     * @return cadena con los datos del cliente
     */
    @Override
    public String toString() {
        return "nombres:" + nombres + ", apellidoPaterno:" + apellidoPaterno + ", apellidoMaterno:" + apellidoMaterno + ", telefono:" + telefono + ", correo:" + correo;
    }

}
