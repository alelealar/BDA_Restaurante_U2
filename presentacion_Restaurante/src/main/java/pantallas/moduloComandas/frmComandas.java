package pantallas.moduloComandas;

import controlador.CoordinadorModuloComandas;
import dtos.ClienteDTO;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import enumerators.EstadoComandaDTO;
import enumerators.EstadoMesaDTO;
import excepciones.NegocioException;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import pantallas.moduloComandas.vistas.panPedidos;

/**
 * Pantalla principal para la gestión de comandas de una mesa. Permite crear,
 * visualizar pedidos y cerrar la comanda.
 *
 * @author Kaleb
 */
public class frmComandas extends javax.swing.JFrame {

    private boolean creada = false;

    private final CoordinadorModuloComandas coordinador;
    private ClienteDTO cliente;
    private ComandaDTO comanda;
    private final MesaDTO mesa;

    /**
     * Constructor de la pantalla de comandas.
     *
     * @param coordinador Coordinador del módulo
     * @param mesa Mesa seleccionada
     */
    public frmComandas(CoordinadorModuloComandas coordinador, MesaDTO mesa) {
        initComponents();
        this.coordinador = coordinador;
        this.mesa = mesa;
        cargarEstado();

        if (creada) {
            agregarPedidos();
        }
    }

    /**
     * Verifica si ya existe una comanda abierta para la mesa y actualiza el
     * estado visual.
     */
    public void cargarEstado() {
        try {
            creada = false;

            for (ComandaDTO c : coordinador.obtenerComandas()) {
                if (c.getMesa() != null
                        && c.getEstadoComanda() == EstadoComandaDTO.ABIERTA
                        && c.getMesa().getId().equals(mesa.getId())) {

                    creada = true;
                    comanda = c;
                    break;
                }
            }

        } catch (NegocioException ex) {
            mostrarMensajeError(ex.getMessage());
        }

        btnCrear.setText(creada ? "+Crear Pedido" : "+Crear Comanda");
        jblAviso.setVisible(!creada);
    }

    /**
     * Muestra un mensaje de error en pantalla.
     *
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Carga los pedidos actuales de la comanda en pantalla.
     */
    public void agregarPedidos() {
        try {
            ComandaDTO actual = coordinador.obtenerComanda(comanda.getId());

            panContenedorPedidos.removeAll();
            panContenedorPedidos.setLayout(new BoxLayout(panContenedorPedidos, BoxLayout.Y_AXIS));

            panContenedorPedidos.add(new panPedidos(actual, coordinador));

            panContenedorPedidos.revalidate();
            panContenedorPedidos.repaint();

        } catch (NegocioException ex) {
            mostrarMensajeError(ex.getMessage());
        }
    }

    /**
     * Construye el mensaje detallado de la comanda.
     *
     * @param comanda Comanda a mostrar
     * @return Texto formateado
     */
    private String construirMensajeComanda(ComandaDTO comanda) {
        StringBuilder mensaje = new StringBuilder();

        if (comanda.getCliente() != null) {
            mensaje.append("Cliente: ")
                    .append(comanda.getCliente().getNombres())
                    .append(" ")
                    .append(comanda.getCliente().getApellidoPaterno())
                    .append("\n\n");
        }

        mensaje.append("Detalles:\n");

        for (DetalleComandaDTO d : comanda.getDetalles()) {
            try {
                ProductoDTO producto = coordinador.obtenerProducto(d.getIdProducto());

                mensaje.append("- ")
                        .append(producto.getNombre())
                        .append(" x").append(d.getCantidad())
                        .append(" $").append(d.getPrecioUnitario());

                if (d.getComentario() != null && !d.getComentario().isBlank()) {
                    mensaje.append(" (").append(d.getComentario()).append(")");
                }

                mensaje.append("\n");

            } catch (NegocioException e) {
                mensaje.append("- Producto desconocido\n");
            }
        }

        mensaje.append("\nTotal: $").append(comanda.getTotal());

        return mensaje.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panMenu = new javax.swing.JPanel();
        btnComandas = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnMesas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnCerrarComanda = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panContenedorPedidos = new javax.swing.JPanel();
        jblAviso = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1119, 670));
        setMinimumSize(new java.awt.Dimension(1119, 670));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panEncabezado.setBackground(new java.awt.Color(255, 191, 71));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_negro_pequenio.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Comandas");

        javax.swing.GroupLayout panEncabezadoLayout = new javax.swing.GroupLayout(panEncabezado);
        panEncabezado.setLayout(panEncabezadoLayout);
        panEncabezadoLayout.setHorizontalGroup(
            panEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panEncabezadoLayout.setVerticalGroup(
            panEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panMenu.setBackground(new java.awt.Color(255, 246, 222));

        btnComandas.setBackground(new java.awt.Color(255, 228, 132));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(74, 68, 89));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Comandas");

        javax.swing.GroupLayout btnComandasLayout = new javax.swing.GroupLayout(btnComandas);
        btnComandas.setLayout(btnComandasLayout);
        btnComandasLayout.setHorizontalGroup(
            btnComandasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComandasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnComandasLayout.setVerticalGroup(
            btnComandasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComandasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnMesas.setBackground(new java.awt.Color(255, 246, 222));
        btnMesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMesasMouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(74, 68, 89));
        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(74, 68, 89));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Mesas");

        javax.swing.GroupLayout btnMesasLayout = new javax.swing.GroupLayout(btnMesas);
        btnMesas.setLayout(btnMesasLayout);
        btnMesasLayout.setHorizontalGroup(
            btnMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMesasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnMesasLayout.setVerticalGroup(
            btnMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMesasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCerrarComanda.setBackground(new java.awt.Color(255, 246, 222));
        btnCerrarComanda.setForeground(new java.awt.Color(255, 246, 222));
        btnCerrarComanda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarComandaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrarComandaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrarComandaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCerrarComandaMousePressed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(74, 68, 89));
        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(74, 68, 89));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cerrar Comanda");

        javax.swing.GroupLayout btnCerrarComandaLayout = new javax.swing.GroupLayout(btnCerrarComanda);
        btnCerrarComanda.setLayout(btnCerrarComandaLayout);
        btnCerrarComandaLayout.setHorizontalGroup(
            btnCerrarComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCerrarComandaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnCerrarComandaLayout.setVerticalGroup(
            btnCerrarComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCerrarComandaLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout panMenuLayout = new javax.swing.GroupLayout(panMenu);
        panMenu.setLayout(panMenuLayout);
        panMenuLayout.setHorizontalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnComandas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCerrarComanda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panMenuLayout.setVerticalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMenuLayout.createSequentialGroup()
                .addComponent(btnComandas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCerrarComanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panContenedorPedidos.setBackground(new java.awt.Color(255, 255, 255));

        jblAviso.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jblAviso.setText("Sin comanda registrada");
        panContenedorPedidos.add(jblAviso);

        jScrollPane1.setViewportView(panContenedorPedidos);

        btnCrear.setBackground(new java.awt.Color(181, 245, 255));
        btnCrear.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(74, 68, 89));
        btnCrear.setText("+Crear Comanda");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 728, Short.MAX_VALUE)
                        .addComponent(btnCrear))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(btnCrear)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed

        if (!creada) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea abrir la comanda?",
                    "Confirmar",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (confirmacion == JOptionPane.OK_OPTION) {
                try {
                    ComandaDTO nueva = new ComandaDTO();
                    nueva.setMesa(mesa);

                    comanda = coordinador.registrarComanda(nueva);
                    creada = true;

                    cargarEstado();

                } catch (NegocioException ex) {
                    mostrarMensajeError(ex.getMessage());
                }
            }
            return;
        }

        int opcion = JOptionPane.showOptionDialog(
                this,
                "Seleccionar cliente",
                "Tipo de Cliente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Registrado", "Sin Registro"},
                "Registrado"
        );

        if (opcion == 1) {
            coordinador.mostrarPantallaCrearPedido(mesa, comanda);
            comanda.setCliente(cliente);
        }

        cargarEstado();


    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMesasMouseClicked
        coordinador.mostrarPantallaMesas();
    }//GEN-LAST:event_btnMesasMouseClicked

    private void btnCerrarComandaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarComandaMousePressed

    }//GEN-LAST:event_btnCerrarComandaMousePressed

    private void btnCerrarComandaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarComandaMouseEntered
        btnCerrarComanda.setBackground(Color.decode("#FFE484"));
    }//GEN-LAST:event_btnCerrarComandaMouseEntered

    private void btnCerrarComandaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarComandaMouseExited
        btnCerrarComanda.setBackground(Color.decode("#FFF6DE"));
    }//GEN-LAST:event_btnCerrarComandaMouseExited

    private void btnCerrarComandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarComandaMouseClicked
        try {
            if (comanda == null) {
                mostrarMensajeError("No hay comanda activa.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea cerrar la comanda?",
                    "Confirmar cierre",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (confirmacion != JOptionPane.OK_OPTION) {
                return;
            }

            ComandaDTO actualizada = coordinador.obtenerComanda(comanda.getId());
            actualizada.setEstadoComanda(EstadoComandaDTO.ENTREGADA);
            actualizada = coordinador.actualizarComanda(actualizada);

            coordinador.cambiarEstadoMesa(mesa.getId(), EstadoMesaDTO.DISPONIBLE);

            JOptionPane.showMessageDialog(this, construirMensajeComanda(actualizada));

            comanda = actualizada;
            coordinador.mostrarPantallaMesas();

        } catch (NegocioException ex) {
            mostrarMensajeError(ex.getMessage());
        }
    }//GEN-LAST:event_btnCerrarComandaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnCerrarComanda;
    private javax.swing.JPanel btnComandas;
    private javax.swing.JButton btnCrear;
    private javax.swing.JPanel btnMesas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jblAviso;
    private javax.swing.JPanel panContenedorPedidos;
    private javax.swing.JPanel panEncabezado;
    private javax.swing.JPanel panMenu;
    // End of variables declaration//GEN-END:variables
}
