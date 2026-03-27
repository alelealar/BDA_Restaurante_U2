package daos;

import conexion.ConexionBD;
import entidades.Cliente;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Implementación de la interfaz IClienteDAO para la gestión de clientes en la
 * base de datos utilizando JPA.
 *
 * Esta clase contiene la lógica necesaria para realizar operaciones CRUD sobre
 * la entidad Cliente, manejando transacciones y excepciones de persistencia.
 *
 * Se apoya en la clase ConexionBD para obtener instancias de EntityManager.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteDAO implements IClienteDAO {
    
    private static ClienteDAO instancia;
    
    public ClienteDAO(){
        
    }
    
    public static ClienteDAO getInstance(){
        if(instancia == null){
            instancia = new ClienteDAO();
        }
        return instancia;
    }
    
    
    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * Inicia una transacción, persiste el objeto Cliente y confirma los
     * cambios. En caso de error, realiza un rollback.
     *
     * @param cliente objeto Cliente que contiene la información a guardar
     * @return el cliente guardado
     * @throws PersistenciaException si ocurre un error durante la operación
     */
    @Override
    public Cliente guardarCliente(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } catch (Exception e) {
            //valida que haya una transaccion activa para poder hacer el rollback, porque no tira la excepcion sin esto
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible guardar al cliente " + cliente.getNombres(), e);             
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     *
     * Utiliza el método merge para sincronizar el estado del objeto con la base
     * de datos. Maneja transacciones y rollback en caso de error.
     *
     * @param cliente objeto Cliente con la información actualizada
     * @return el cliente actualizado
     * @throws PersistenciaException si ocurre un error durante la actualización
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            Cliente clienteBD = em.find(Cliente.class, cliente.getId());

            if (clienteBD == null) {
                throw new PersistenciaException("Cliente no encontrado");
            }

            clienteBD.setNombres(cliente.getNombres());
            clienteBD.setApellidoPaterno(cliente.getApellidoPaterno());
            clienteBD.setApellidoMaterno(cliente.getApellidoMaterno());
            clienteBD.setTelefono(cliente.getTelefono());
            clienteBD.setCorreo(cliente.getCorreo());

            em.getTransaction().commit();
            return clienteBD;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible actualizar al cliente " + cliente.getNombres(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un cliente de la base de datos a partir de su identificador.
     *
     * Primero busca el cliente, y si existe, lo elimina dentro de una
     * transacción. En caso de error, realiza rollback.
     *
     * @param id identificador del cliente a eliminar
     * @return true si el cliente fue eliminado correctamente, false en caso
     * contrario
     * @throws PersistenciaException si ocurre un error durante la eliminación
     */
    @Override
    public boolean eliminarCliente(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Cliente clienteObtenido = em.find(Cliente.class, id);

            if (clienteObtenido == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(clienteObtenido);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible eliminar al cliente con id " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un cliente en la base de datos a partir de su identificador.
     *
     * Utiliza el método find de JPA para recuperar la entidad.
     *
     * @param id identificador del cliente
     * @return el cliente encontrado o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public Cliente buscarClientePorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible buscar al cliente con id " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la lista completa de clientes registrados en la base de datos.
     *
     * Este método permite recuperar todos los clientes almacenados en el
     * sistema, facilitando su visualización en interfaces como tablas o
     * listados dentro de la aplicación.
     *
     * @return lista de clientes registrados
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<Cliente> obtenerClientes() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String JPQL = "SELECT c FROM Cliente c";
            TypedQuery<Cliente> query = em.createQuery(JPQL, Cliente.class);
            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar a los clientes");
        } finally {
            em.close();
        }
    }
    @Override
    public boolean existeTelefono(String telefono, Long ID) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
           //validar telefono existente
           String JPQL;
           TypedQuery<Long> query;

           if(ID == null){
               JPQL = "SELECT COUNT(c) FROM Cliente c WHERE c.telefono = :tel";
               query = em.createQuery(JPQL, Long.class);
               query.setParameter("tel", telefono);
           } else {
               JPQL = "SELECT COUNT(c) FROM Cliente c WHERE c.telefono = :tel AND c.id != :id";
               query = em.createQuery(JPQL, Long.class);
               query.setParameter("tel", telefono);
               query.setParameter("id", ID);
           }
           
           Long conteo = query.getSingleResult();
           return conteo > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar el telefono", e);

        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean existeCorreo(String correo, Long ID) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            String JPQL;
            TypedQuery<Long> query;

            if (ID == null) {
                // ✔️ INSERT
                JPQL = "SELECT COUNT(c) FROM Cliente c WHERE c.correo = :correo";
                query = em.createQuery(JPQL, Long.class);
                query.setParameter("correo", correo);

            } else {
                // ✔️ UPDATE
                JPQL = "SELECT COUNT(c) FROM Cliente c WHERE c.correo = :correo AND c.id != :id";
                query = em.createQuery(JPQL, Long.class);
                query.setParameter("correo", correo);
                query.setParameter("id", ID);
            }

            Long conteo = query.getSingleResult();
            return conteo > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar el correo", e);

        } finally {
            em.close();
        }
    }
}
