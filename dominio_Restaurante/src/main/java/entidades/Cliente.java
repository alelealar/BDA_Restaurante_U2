/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;
import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.time.LocalDate;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;


/**
 * Clase padre cliente.
 * 
 * tiene todos los atirbutos que cualquier cliente de cualquier tipo debe de tener
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 262741
 * @author Alejandra Leal Armenta - 262719
 * @author Maria Jose Valdez Iglesias - 262775
 */
@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente")
public class Cliente implements Serializable{
    /**
     * Identificador único del cliente.
     * Se genera automáticamente mediante la estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    /**
     * Nombres del cliente.
     */
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    /**
     * Apellido paterno del cliente.
     */
    @Column(name = "apellidoPaterno", nullable = false, length = 100)
    private String apellidoPaterno;

    /**
     * Apellido materno del cliente.
     */
    @Column(name = "apellidoMaterno", nullable = true, length = 100)
    private String apellidoMaterno;

    /**
     * Número de teléfono del cliente.
     */
    @Column(name = "numTelefono", nullable = false, length = 10, unique = true)
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    @Column(name = "correo", nullable = true, length = 200, unique = true)
    private String correo;

    /**
     * Fecha en la que el cliente fue registrado en el sistema.
     */
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    /**
     * Método que se ejecuta automáticamente antes de persistir la entidad.
     *
     * Asigna la fecha actual como fecha de registro si no ha sido definida.
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
    }

    /**
     * Constructor que inicializa todos los atributos del cliente incluyendo el ID.
     *
     * @param id identificador único del cliente
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno
     * @param apellidoMaterno apellido materno
     * @param telefono número telefónico
     * @param correo correo electrónico
     */
    public Cliente(Long id, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Constructor sin ID para creación de nuevos clientes.
     *
     * @param nombres nombres del cliente
     * @param apellidoPaterno apellido paterno
     * @param apellidoMaterno apellido materno
     * @param telefono número telefónico
     * @param correo correo electrónico
     */
    public Cliente(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Constructor vacío requerido por JPA.
     */
    public Cliente() {
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return id del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param id nuevo identificador del cliente
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
     * @param nombres nuevos nombres del cliente
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene el apellido paterno del cliente.
     *
     * @return apellido paterno del cliente
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del cliente.
     *
     * @param apellidoPaterno nuevo apellido paterno del cliente
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del cliente.
     *
     * @return apellido materno del cliente
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del cliente.
     *
     * @param apellidoMaterno nuevo apellido materno del cliente
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
     * @param telefono nuevo número de teléfono del cliente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo electrónico del cliente
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo nuevo correo electrónico del cliente
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la fecha en la que el cliente fue registrado.
     *
     * @return fecha de registro del cliente
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del cliente.
     *
     * @param fechaRegistro nueva fecha de registro del cliente
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }  
}
