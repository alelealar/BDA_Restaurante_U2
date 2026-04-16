/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package daos;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import conexion.ConexionBD;
import enumerators.EstadoComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.ReporteClientesDTO;
import dtos.ReporteComandasDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import enumerators.EstadoComandaDTO;
import excepciones.PersistenciaException;
import interfaces.IReportesDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Clase que implementa las operaciones de acceso a datos para la generación de reportes.
 * Se encarga de realizar las consultas a la base de datos para obtener información
 * de clientes y comandas, aplicando filtros cuando sea necesario.
 * 
 * Esta clase contiene la lógica de acceso a datos relacionada con los reportes
 * del sistema.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */

public class ReportesDAO implements IReportesDAO{
    
    /**
     * Instancia única de la clase ReportesDAO.
     * Se utiliza para aplicar el patrón Singleton y evitar múltiples instancias.
     */
    private static ReportesDAO instancia;
    
    /**
     * Constructor vacío de la clase ReportesDAO.
     * Permite la creación de la instancia interna del DAO.
     */
    public ReportesDAO(){
    }
    
    /**
     * Obtiene la instancia única de ReportesDAO.
     * Si no existe una instancia previa, se crea una nueva.
     * @return instancia única de ReportesDAO
     */
    public static ReportesDAO getInstance(){
        if(instancia == null){
            instancia = new ReportesDAO();
        }
        return instancia;
    }

    /**
     * Obtiene el reporte general de clientes registrados en el sistema.
     * Devuelve la información sin aplicar filtros.
     * @return lista de clientes para el reporte
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    @Override
    public List<ReporteClientesDTO> obtenerReporteClientes() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            em.getTransaction().begin();
            
            String JPQL = """
                          SELECT NEW dtos.ReporteClientesDTO(
                            CONCAT( CONCAT( CONCAT(c.nombres, ' '), CONCAT(c.apellidoPaterno, ' ') ), c.apellidoMaterno ),
                            COUNT(co),
                            SUM(co.total),
                            MAX(co.fechaHora),
                            TREAT(c AS ClienteFrecuente).puntos
                          )
                          FROM Comanda co
                          LEFT JOIN co.cliente c
                          GROUP by c.nombres, c.apellidoPaterno, c.apellidoMaterno, TREAT(c AS ClienteFrecuente).puntos          
                          """;
            
            TypedQuery<ReporteClientesDTO> query = em.createQuery(JPQL,ReporteClientesDTO.class);
            em.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al obtener el reporte de los clientes", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene el reporte de clientes aplicando filtros de búsqueda.
     * Filtra por nombre y número de visitas.
     * @param nombre nombre del cliente a buscar
     * @param visitas número de visitas del cliente
     * @return lista de clientes que cumplen con el filtro
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    @Override
    public List<ReporteClientesDTO> obtenerReporteClientesFiltro(String nombre, Integer visitas) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ReporteClientesDTO> query = cb.createQuery(ReporteClientesDTO.class);
        Root<Comanda> comanda = query.from(Comanda.class);
        Join<Comanda, Cliente> cliente = comanda.join("cliente");
        
        List<Predicate> filtros = new ArrayList<>();
        
        Expression<String> nombreCompleto = cb.concat( cb.concat( cb.concat(cliente.get("nombres"), " "), cb.concat(cliente.get("apellidoPaterno"), " ") ), cliente.get("apellidoMaterno") );
        
        if (nombre != null && !nombre.trim().isEmpty()){
            filtros.add( cb.like( cb.lower(nombreCompleto), "%" + nombre.toLowerCase() + "%" ) );
        }  
        
        //new ReporteClientesDTO(campo1, campo2, campo3);
        query.select(cb.construct(
                        ReporteClientesDTO.class,
                        nombreCompleto,
                        cb.count(comanda),
                        cb.coalesce(cb.sum(comanda.get("total")), 0.0),
                        cb.greatest(comanda.get("fechaHora").as(LocalDateTime.class)),
                        cb.treat(cliente, ClienteFrecuente.class).get("puntos")
                ));
        
        if (!filtros.isEmpty()) {
            query.where(cb.and(filtros.toArray(Predicate[]::new)));
        }
        
        query.groupBy(
                cliente.get("id")
        );
        
        if(visitas != null){
            query.having(cb.equal(cb.count(comanda), visitas));
        }
        
        return em.createQuery(query).getResultList();
    }

    /**
     * Obtiene el reporte de comandas filtrado por un rango de fechas.
     * Permite consultar comandas entre una fecha de inicio y fin.
     * @param inicio fecha inicial del rango
     * @param fin fecha final del rango
     * @return lista de comandas dentro del rango
     * @throws PersistenciaException en caso de error al consultar la base de datos
     */
    @Override
    public List<ReporteComandasDTO> obtenerReporteComandasFiltro(LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            em.getTransaction().begin();
            
            /*
            
            */
            String jpql = 
                        """
                        select distinct c 
                        from Comanda c 
                        join fetch c.cliente 
                        left join fetch c.detalles 
                        where c.fechaHora between :inicio and :fin
                        """;

            /*
            
            */
            List<Comanda> comandas = em
                    .createQuery(jpql, Comanda.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .getResultList();
            
            /*
            
            */
            List<ReporteComandasDTO> listaReportes = comandas.stream().map(co -> {
                /*
                
                */
                List<DetalleComandaDTO> detallesDto = co.getDetalles().stream().map(det -> {
                    DetalleComandaDTO dto = new DetalleComandaDTO();
                    dto.setCantidad(det.getCantidad());
                    dto.setComentario(det.getComentario());
                    dto.setPrecioUnitario(det.getPrecioUnitario());
                    dto.setIdProducto(det.getProducto().getId());
                    return dto;
                }).collect(Collectors.toList());

                String nombreCompleto = co.getCliente().getNombres() + " " + co.getCliente().getApellidoPaterno();

                /*
                
                */
                return new ReporteComandasDTO(
                        nombreCompleto,
                        co.getMesa().getNumero(),
                        co.getTotal(),
                        EstadoComandaDTO.valueOf(co.getEstadoComanda().name()),
                        co.getFechaHora(),
                        detallesDto 
                );
            }).collect(Collectors.toList());
            
            em.getTransaction().commit();
            
            return listaReportes;
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al obtener el reporte de las comandas", e);
        } finally {
            em.close();
        }
    }

}
