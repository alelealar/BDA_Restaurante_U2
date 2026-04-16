package pantallas.moduloComandas.vistas;

import controlador.CoordinadorModuloComandas;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
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
import javax.swing.SwingUtilities;

/**
 * Panel que representa un producto dentro de una comanda. Permite modificar o
 * eliminar el producto.
 */
public class panProductoPedido extends javax.swing.JPanel {

    private final DetalleComandaDTO detalle;
    private final CoordinadorModuloComandas coordinador;
    private final ProductoDTO producto;
    private final ComandaDTO comanda;

    /**
     * Constructor del panel.
     *
     * @param detalle
     * @param coordinador
     * @param comanda
     * @throws excepciones.NegocioException
     */
    public panProductoPedido(DetalleComandaDTO detalle,
            CoordinadorModuloComandas coordinador,
            ComandaDTO comanda) throws NegocioException {

        initComponents();

        this.detalle = detalle;
        this.coordinador = coordinador;
        this.comanda = comanda;

        this.producto = coordinador.obtenerProducto(detalle.getIdProducto());

        actualizarTexto();
    }

    /**
     * Actualiza el texto del label principal.
     */
    private void actualizarTexto() {
        lblNombre.setText(
                producto.getNombre()
                + " X" + detalle.getCantidad()
                + " $" + detalle.getPrecioUnitario()
        );
    }

    /**
     * Muestra el diálogo para editar el detalle.
     */
    public void mostrarDialogEditarDetalle(DetalleComandaDTO detalle, String nombreProducto) {

        JDialog dialog = crearDialogo();

        JSpinner spCantidad = new JSpinner(
                new SpinnerNumberModel(detalle.getCantidad(), 1, 999, 1)
        );

        JTextArea txtComentario = new JTextArea(4, 20);
        txtComentario.setText(detalle.getComentario() == null ? "" : detalle.getComentario());
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnAceptar.addActionListener(e -> editarDetalle(dialog, spCantidad, txtComentario));

        dialog.add(crearPanelContenido(nombreProducto, spCantidad, txtComentario), BorderLayout.CENTER);
        dialog.add(crearPanelBotones(btnAceptar, btnCancelar), BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    /**
     * Lógica para editar el detalle.
     */
    private void editarDetalle(JDialog dialog, JSpinner spCantidad, JTextArea txtComentario) {

        int cantidadAnterior = detalle.getCantidad();
        int cantidadNueva = (Integer) spCantidad.getValue();

        detalle.setCantidad(cantidadNueva);
        detalle.setComentario(txtComentario.getText().trim());

        try {
            ComandaDTO comandaActual = coordinador.obtenerComanda(detalle.getIdComanda());

            actualizarDetalleEnLista(comandaActual);

            comandaActual.setTotal(calcularTotal(comandaActual.getDetalles()));

            int diferencia = cantidadNueva - cantidadAnterior;

            if (diferencia != 0) {
                //Si la diferencia es mayor a 0 se resta, si es menor se suma.
                TipoMovimiento movimiento = diferencia > 0 ? TipoMovimiento.SALIDA : TipoMovimiento.ENTRADA;

                //obtenemos el valor absoluto de la diferencia para evitar negativos
                int cantidadMovimiento = Math.abs(diferencia);

                for (ProductoIngredienteDTO pi : producto.getIngredientes()) {

                    int totalIngrediente = pi.getCantidad() * cantidadMovimiento;

                    coordinador.actualizarIngredientes(pi.getIngrediente().getId(), totalIngrediente, movimiento);
                }
            }

            coordinador.actualizarComanda(comandaActual);
            coordinador.refrescarComandas();

        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
            dialog.dispose();
            return;
        }

        dialog.dispose();
        JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
        actualizarTexto();
    }

    /**
     * Elimina el producto de la comanda.
     */
    private void eliminarProducto() {
        try {
            ComandaDTO comandaActual = coordinador.obtenerComanda(detalle.getIdComanda());

            DetalleComandaDTO detalleEliminar = null;

            for (DetalleComandaDTO d : comandaActual.getDetalles()) {
                if (d.getId() != null && d.getId().equals(detalle.getId())) {
                    detalleEliminar = d;
                    break;
                }
            }

            if (detalleEliminar == null) {
                return;
            }

            regresarIngredientes(detalleEliminar);

            comandaActual.getDetalles().remove(detalleEliminar);

            comandaActual.setTotal(calcularTotal(comandaActual.getDetalles()));

            coordinador.actualizarComanda(comandaActual);
            coordinador.refrescarComandas();

        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Logica para regresar ingredientes por detalle
     *
     * @param detalleEliminar
     * @throws NegocioException
     */
    private void regresarIngredientes(DetalleComandaDTO detalleEliminar) throws NegocioException {

        ProductoDTO producto = coordinador.obtenerProducto(detalleEliminar.getIdProducto());

        for (ProductoIngredienteDTO pi : producto.getIngredientes()) {

            int cantidadRegresar = pi.getCantidad() * detalleEliminar.getCantidad();

            coordinador.actualizarIngredientes(
                    pi.getIngrediente().getId(),
                    cantidadRegresar,
                    TipoMovimiento.ENTRADA
            );
        }
    }

    /**
     * Actualiza ingredientes al eliminar producto.
     */
    private void actualizarIngredientesEntrada() throws NegocioException {

        for (ProductoIngredienteDTO pi : producto.getIngredientes()) {

            int cantidadTotal = pi.getCantidad() * detalle.getCantidad();

            coordinador.actualizarIngredientes(
                    pi.getIngrediente().getId(),
                    cantidadTotal,
                    TipoMovimiento.ENTRADA
            );
        }
    }

    /**
     * Actualiza el detalle dentro de la comanda.
     */
    private void actualizarDetalleEnLista(ComandaDTO comanda) {
        for (DetalleComandaDTO d : comanda.getDetalles()) {
            if (d.getId().equals(detalle.getId())) {
                d.setCantidad(detalle.getCantidad());
                d.setComentario(detalle.getComentario());
                break;
            }
        }
    }

    /**
     * Calcula el total de la comanda.
     */
    private double calcularTotal(List<DetalleComandaDTO> detalles) {
        double total = 0.0;

        for (DetalleComandaDTO d : detalles) {
            total += d.getCantidad() * d.getPrecioUnitario();
        }

        return total;
    }

    /**
     * Crea el diálogo base.
     */
    private JDialog crearDialogo() {

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setTitle("Editar producto");
        dialog.setModal(true);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout(10, 10));
        return dialog;
    }

    /**
     * Panel de contenido del diálogo.
     */
    private JPanel crearPanelContenido(String nombreProducto, JSpinner spCantidad, JTextArea txtComentario) {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblProducto = new JLabel(nombreProducto);
        lblProducto.setFont(new Font("Verdana", Font.BOLD, 16));

        panel.add(new JLabel("Producto:"));
        panel.add(lblProducto);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Cantidad:"));
        panel.add(spCantidad);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Comentario:"));
        panel.add(new JScrollPane(txtComentario));

        return panel;
    }

    /**
     * Panel de botones.
     */
    private JPanel crearPanelBotones(JButton aceptar, JButton cancelar) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(cancelar);
        panel.add(aceptar);
        return panel;
    }

    /**
     * Muestra mensaje de error.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.WARNING_MESSAGE);
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
        btnModificar.setContentAreaFilled(false);
        btnModificar.setOpaque(true);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 67, 67));
        btnEliminar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setOpaque(true);
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
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea elimnar el pedido?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta == JOptionPane.OK_OPTION) {
            eliminarProducto();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
