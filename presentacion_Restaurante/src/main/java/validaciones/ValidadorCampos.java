/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

/**
 * Validaciones de los componentes de la UI.
 * @author Alejandra Leal Armenta, 262719
 */
public class ValidadorCampos {

    // Nombres
    public static boolean nombreVacio(String nombre) {
        return nombre == null || nombre.isBlank();
    }

    public static boolean nombreValido(String nombre) {
        return nombre.matches("^[\\p{L} \\.]+$");
    }

    // Apellido paterno
    public static boolean apellidoPVacio(String apellido) {
        return apellido == null || apellido.isBlank();
    }

    public static boolean apellidoPValido(String apellido) {
        return apellido.matches("^[\\p{L} \\.]+$");
    }

    // Apellido materno (opcional)
    public static boolean apellidoMValido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            return true;
        }
        return apellido.matches("^[\\p{L} \\.]+$");
    }

    // Teléfono
    public static boolean telefonoVacio(String telefono) {
        return telefono == null || telefono.isBlank();
    }

    public static boolean telefonoValido(String telefono) {
        return telefono.matches("^\\d{10}$");
    }

    // Correo (opcional)
    public static boolean correoValido(String correo) {
        if (correo == null || correo.isBlank()) {
            return true;
        }
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Usuario 
    public static boolean usuarioVacio(String usuario) {
        return usuario == null || usuario.trim().isEmpty();
    }

    public static boolean usuarioValido(String usuario) {
        if (usuario == null) {
            return false;
        }
        // ^[\\p{L}0-9]+$ significa:
        // \\p{L} -> Cualquier letra (incluye acentos y ñ)
        // 0-9    -> Permite números
        // +      -> Al menos un carácter
        return usuario.matches("^[\\p{L}0-9]+$");
    }
    
    public static boolean soloNumeros(String numeros){
        return numeros.matches("\\d+");
    }
}
