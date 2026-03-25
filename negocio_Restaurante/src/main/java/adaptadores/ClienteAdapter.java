package adaptadores;

import dtos.ClienteDTO;
import entidades.Cliente;
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
     * Convierte un objeto Cliente (entidad) a un objeto ClienteDTO.
     *
     * @param cliente objeto Cliente a convertir
     * @return objeto ClienteDTO con los datos del cliente, o null si el
     * parámetro es null
     */
    public static ClienteDTO entidadADTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNombres(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getTelefono(),
                cliente.getCorreo()
        );
    }

    /**
     * Convierte un objeto ClienteDTO a un objeto Cliente (entidad).
     *
     * @param clienteDto objeto ClienteDTO a convertir
     * @return objeto Cliente con los datos del DTO, o null si el parámetro es
     * null
     */
    public static Cliente dtoAEntidad(ClienteDTO clienteDto) {
        if (clienteDto == null) {
            return null;
        }

        return new Cliente(
                clienteDto.getId(),
                clienteDto.getNombres(),
                clienteDto.getApellidoPaterno(),
                clienteDto.getApellidoMaterno(),
                clienteDto.getTelefono(),
                clienteDto.getCorreo()
        );
    }

    /**
     * Convierte una lista de objetos Cliente (entidades) a una lista de
     * ClienteDTO.
     *
     * Recorre la lista de entidades y transforma cada elemento en su
     * representación DTO.
     *
     * @param clientes lista de objetos Cliente
     * @return lista de objetos ClienteDTO
     */
    public static List<ClienteDTO> listaEntidadADTO(List<Cliente> clientes) {
        List<ClienteDTO> listaDtos = new ArrayList<>();

        for (Cliente c : clientes) {
            listaDtos.add(entidadADTO(c));
        }

        return listaDtos;
    }

}
