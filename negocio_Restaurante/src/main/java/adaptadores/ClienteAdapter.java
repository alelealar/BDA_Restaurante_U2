package adaptadores;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.NegocioException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora encargada de convertir objetos entre la entidad Cliente y su
 * correspondiente DTO (ClienteDTO).
 *
 * Esta clase permite desacoplar la capa de persistencia de la capa de
 * presentación o lógica de negocio, facilitando el manejo de datos y evitando
 * exponer directamente las entidades.
 *
 * Proporciona métodos para convertir objetos individuales y listas de entidades
 * a DTOs.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteAdapter {

    /**
     * Convierte un objeto Cliente (entidad) a un objeto ClienteNuevoDTO.
     *
     * @param cliente objeto Cliente a convertir
     * @return objeto ClienteNuevoDTO con los datos del cliente, o null si el
     * parámetro es null
     */
    public static ClienteDTO entidadADTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        if (cliente instanceof ClienteFrecuente cf) {
            return new ClienteDTO(
                    cf.getId(),
                    cf.getNombres(),
                    cf.getApellidoPaterno(),
                    cf.getApellidoMaterno(),
                    cf.getTelefono(),
                    cf.getCorreo(),
                    cf.getFechaRegistro(),
                    cf.getNumVisitas(),
                    cf.getTotalGastado(),
                    cf.getPuntos()
            );
        }

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNombres(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getFechaRegistro()
        );
    }

    /**
     * Convierte un objeto ClienteNuevoDTO a un objeto Cliente (entidad).
     *
     * @param dto dto a convertir
     * @return objeto Cliente con los datos del DTO, o null si el parámetro es
     * null
     */
    public static Cliente dtoAEntidad(Object dto) throws NegocioException {
        if (dto == null) {
            return null;
        }

        if (dto instanceof ClienteNuevoDTO c) {
            return new Cliente(
                    null,
                    c.getNombres(),
                    c.getApellidoPaterno(),
                    c.getApellidoMaterno(),
                    c.getTelefono(),
                    c.getCorreo()
            );
        }

        if (dto instanceof ClienteDTO c) {
            return new Cliente(
                    c.getId(),
                    c.getNombres(),
                    c.getApellidoPaterno(),
                    c.getApellidoMaterno(),
                    c.getTelefono(),
                    c.getCorreo()
            );
        }
        throw new NegocioException("DTO no aceptado: " + dto.getClass());
    }

    /**
     * Convierte un ClienteDTO a ClienteFrecuente.
     *
     * Se utiliza cuando se necesita actualizar información de fidelidad.
     *
     * @param dto cliente DTO
     * @return entidad ClienteFrecuente
     * @throws excepciones.NegocioException
     */
    public static ClienteFrecuente dtoAFrecuente(Object dto) throws NegocioException {
        if (dto == null) {
            return null;
        }

        if (dto instanceof ClienteNuevoDTO c) {
            return new ClienteFrecuente(
                    c.getNumVisitas(),
                    c.getTotalGastado(),
                    c.getPuntos(),
                    null,
                    c.getNombres(),
                    c.getApellidoPaterno(),
                    c.getApellidoMaterno(),
                    c.getTelefono(),
                    c.getCorreo()
            );
        }

        if (dto instanceof ClienteDTO c) {
            return new ClienteFrecuente(
                    c.getNumVisitas(),
                    c.getTotalGastado(),
                    c.getPuntos(),
                    c.getId(),
                    c.getNombres(),
                    c.getApellidoPaterno(),
                    c.getApellidoMaterno(),
                    c.getTelefono(),
                    c.getCorreo()
            );
        }

        throw new NegocioException("DTO no aceptado: " + dto.getClass().getSimpleName());
    }

    /**
     * Convierte una lista de objetos Cliente (entidades) a una lista de
     * ClienteDTO.Recorre la lista de entidades y transforma cada elemento en su
     * representación DTO.
     *
     *
     * @param clientes lista de objetos Cliente
     * @return lista de objetos ClienteNuevoDTO
     */
    public static List<ClienteDTO> listaEntidadADTO(List<Cliente> clientes) {
        List<ClienteDTO> listaDtos = new ArrayList<>();

        for (Cliente c : clientes) {
            listaDtos.add(entidadADTO(c));
        }

        return listaDtos;
    }

}
