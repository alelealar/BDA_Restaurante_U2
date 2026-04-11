package pantallas.moduloComandas.vistas;

import controlador.CoordinadorModuloComandas;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.ProductoDTO;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Kaleb
 */
public class panProductoPedido extends javax.swing.JPanel {

    private DetalleComandaDTO detalle;
    private final CoordinadorModuloComandas coordinador;
    private ProductoDTO producto;

    /**
     * Creates new form panProductoPedido
     *
     * @param detalle
     * @param coordinador
     * @throws excepciones.NegocioException
     */
    public panProductoPedido(DetalleComandaDTO detalle, CoordinadorModuloComandas coordinador) throws NegocioException {
        initComponents();
        this.detalle = detalle;
        this.coordinador = coordinador;
        ProductoDTO producto = coordinador.obtenerProducto(detalle.getIdProducto());
        lblNombre.setText(producto.getNombre() + " X" + detalle.getCantidad() + " $" + detalle.getPrecioUnitario());
        this.producto = producto;
    }

    public void mostrarDialogEditarDetalle(DetalleComandaDTO detalle, String nombreProducto) {

        java.awt.Window ventanaPadre = javax.swing.SwingUtilities.getWindowAncestor(this);

        JDialog dialog = new JDialog(ventanaPadre);
        dialog.setTitle("Editar producto");
        dialog.setModal(true);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel lblNombreProducto = new JLabel("Producto:");
        JLabel lblProducto = new JLabel(nombreProducto);
        lblProducto.setFont(new Font("Verdana", Font.BOLD, 16));

        JLabel lblCantidad = new JLabel("Cantidad:");

        JSpinner spCantidad = new JSpinner(
                new SpinnerNumberModel(detalle.getCantidad(), 1, 999, 1)
        );

        JLabel lblComentario = new JLabel("Comentario:");

        JTextArea txtComentario = new JTextArea(4, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setText(
                detalle.getComentario() == null ? "" : detalle.getComentario()
        );

        JScrollPane scrollComentario = new JScrollPane(txtComentario);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnAceptar = new JButton("Aceptar");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);

        panelPrincipal.add(lblNombre);
        panelPrincipal.add(lblProducto);
        panelPrincipal.add(Box.createVerticalStrut(10));

        panelPrincipal.add(lblCantidad);
        panelPrincipal.add(spCantidad);
        panelPrincipal.add(Box.createVerticalStrut(10));

        panelPrincipal.add(lblComentario);
        panelPrincipal.add(scrollComentario);

        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnAceptar.addActionListener(e -> {

            detalle.setCantidad((Integer) spCantidad.getValue());
            detalle.setComentario(txtComentario.getText().trim());
            try {
                ComandaDTO comanda = coordinador.obtenerComanda(detalle.getIdComanda());

                for (DetalleComandaDTO d : comanda.getDetalles()) {
                    if (d.getId().equals(detalle.getId())) {
                        d.setCantidad(detalle.getCantidad());
                        d.setComentario(detalle.getComentario());
                        break;
                    }
                }

                double totalCalculado = 0.0;
                for (DetalleComandaDTO d : comanda.getDetalles()) {
                    totalCalculado += d.getCantidad() * d.getPrecioUnitario();
                }
                comanda.setTotal(totalCalculado);
                coordinador.actualizarComanda(comanda);
                coordinador.refrescarComandas();

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
            dialog.dispose();

            JOptionPane.showMessageDialog(
                    this,
                    "Producto actualizado correctamente"
            );
        });

        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 217, 165));

        lblNombre.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("jLabel1");

        btnModificar.setBackground(new java.awt.Color(255, 206, 84));
        btnModificar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(0, 0, 0));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 67, 67));
        btnEliminar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(btnModificar)
                .addGap(32, 32, 32)
                .addComponent(btnEliminar)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        mostrarDialogEditarDetalle(detalle, producto.getNombre());

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            ComandaDTO comanda = coordinador.obtenerComanda(detalle.getIdComanda());
            comanda.getDetalles().removeIf(d -> d.getId().equals(detalle.getId()));
            double totalCalculado = 0.0;
            for (DetalleComandaDTO d : comanda.getDetalles()) {
                totalCalculado += d.getCantidad() * d.getPrecioUnitario();
            }
            comanda.setTotal(totalCalculado);
            coordinador.refrescarComandas();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
