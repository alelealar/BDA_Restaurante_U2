/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallasProducto;

import controlador.Coordinador_ModuloProductos;
import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import dtos.ProductoNuevoDTO;
import enumerators.EstadoProductoDTO;
import enumerators.TipoProductoDTO;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import notificaciones.TipoNotificacion;
import notificaciones.DlgNotificacion2;

/**
 * Clase del frame AgregarProductos.
 * 
 * Aquí están declarados todos los componentes del JFrame junto con el 
 * compartamiento según la acción/evento que se realice con los mismos. 
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class FrmAgregarProducto2 extends javax.swing.JFrame {
    
    /**
     * Cuando la pantalla está en modo 'modificar', este es el producto con el
     * que va trabajando.
     */
    private ProductoDTO producto;
    
    /**
     * Para saber si la pantalla se encuentra en modo modificar.
     */
    private boolean modificar = false;
    
    /**
     * El coordinador de las pantallas del módulo Productos.
     */
    private Coordinador_ModuloProductos coordinador;
    
    /**
     * El modelo de la JList, donde se muestran los detallesProducto.
     */
    private DefaultListModel<String> modelo;
    
    /**
     * Archivo de la imagen que se está actualizando/subiendo como referencia 
     * visual del producto.
     */
    private File archivo;
    
    /**
     * Guarda los resultados que otorgan las operaciones en el coordinador.
     */
    private boolean resultado = false;
    
    /**
     * Variable para saber si existía una imagen cargada y esta se eliminó.
     */
    private boolean imagenEliminada = false;
    
    /**
     * Scroll para mostrar el texto en el apartado de descripción.
     */
    private JScrollPane scrollDescripcion;
    
    /**
     * Los detallesProducto, es la lista que se van compartiendo entre frames y
     * coordinadores. 
     */
    private List<ProductoIngredienteDTO> detallesProducto;
    
    /**
     * Logger para informar sobre cómo van resultando las operaciones realizadas.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmAgregarProducto2.class.getName());

    /**
     * Constructor del frame, recibe el coordinador para que se maneje el mismo
     * en operaciones donde accede a otros módulos.
     */
    public FrmAgregarProducto2(Coordinador_ModuloProductos coordinador) {
        this.coordinador = coordinador;
        initComponents();
        cbTipo.setModel(new DefaultComboBoxModel<>(TipoProductoDTO.values()));
        this.setLocationRelativeTo(null);
        this.modelo = new DefaultListModel<>();
        listaIngredientes.setModel(modelo);
        ocultarBotonEliminarImagen();
    }

    /**
     * Modifica el frame para que tenga la vista de modificar.
     * @param producto Producto con el que se estará trabajando.
     */
    public void setProductoParaModificar(ProductoDTO producto){
        this.producto = producto;
        this.modificar = true;
        cargarDatos();
        btnAgregar.setText("Actualizar");
        lblTitulo.setText("Actualizar Producto");
    }
    
    /**
     * Es el método que se usa desde los coordinadores para ya pasar la lista
     * actualizada y está pueda ser cargada al frame.
     * @param ingredientes Lista ta con registros a mostrar, no puede ser vacía.
     */
    public void setDetallesProducto(List<ProductoIngredienteDTO> ingredientes){
        if(ingredientes == null || ingredientes.isEmpty()){
            DlgNotificacion2.mostrarNotificacion(this, "Tiene que haber ingredientes para el producto.", TipoNotificacion.MENSAJE);
        }
        modelo.clear();
        this.detallesProducto = ingredientes;
        for(ProductoIngredienteDTO dto : detallesProducto){
            modelo.addElement(dto.toString());
        }
        listaIngredientes.revalidate();
        listaIngredientes.repaint();
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Cuando se modifica un producto, este método carga toda su información en 
     * la pantalla y además deshabilita aquellos botones/opciones que no pueden 
     * ser modificadas.
     */
    public void cargarDatos(){
        if(producto == null){
            return;
        }
        txtNombre.setText(producto.getNombre());
        txtNombre.setEditable(false);
        cbTipo.setSelectedItem(producto.getTipo());
        cbTipo.setEnabled(false);
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        this.detallesProducto = producto.getIngredientes();
        for(ProductoIngredienteDTO ingrediente : producto.getIngredientes()){
            modelo.addElement(ingrediente.toString());
        }   
        if (producto.getUrlImagen() != null) {
            ImageIcon icono = new ImageIcon(producto.getUrlImagen());
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            btnSubirImagen.setIcon(new ImageIcon(img));
            mostrarBotonEliminarImagen();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iconLogo = new javax.swing.JLabel();
        lblProductos = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnReportes = new javax.swing.JButton();
        btnRepCom = new javax.swing.JButton();
        btnRepCli = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnClientes = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnProductos = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblIngredientes = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        cbTipo = new javax.swing.JComboBox<>();
        txtPrecio = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        lblSimboloDinero = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaIngredientes = new javax.swing.JList<>();
        lblImagen = new javax.swing.JLabel();
        btnSubirImagen = new javax.swing.JLabel();
        btnAgregarIngrediente = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 191, 71));

        iconLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_blanco_pequeño.png"))); // NOI18N

        lblProductos.setFont(new java.awt.Font("Trebuchet MS", 1, 42)); // NOI18N
        lblProductos.setForeground(new java.awt.Color(255, 255, 255));
        lblProductos.setText("Productos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconLogo)
                .addGap(18, 18, 18)
                .addComponent(lblProductos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(iconLogo)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductos)
                        .addGap(20, 20, 20))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 246, 222));

        btnReportes.setBackground(new java.awt.Color(255, 246, 222));
        btnReportes.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setBorder(null);
        btnReportes.setFocusPainted(false);
        btnReportes.addActionListener(this::btnReportesActionPerformed);

        btnRepCom.setBackground(new java.awt.Color(255, 246, 222));
        btnRepCom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRepCom.setText("• Comandas");
        btnRepCom.setBorder(null);
        btnRepCom.setFocusPainted(false);
        btnRepCom.addActionListener(this::btnRepComActionPerformed);

        btnRepCli.setBackground(new java.awt.Color(255, 246, 222));
        btnRepCli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRepCli.setText("• Clientes");
        btnRepCli.setBorder(null);
        btnRepCli.setFocusPainted(false);
        btnRepCli.addActionListener(this::btnRepCliActionPerformed);

        btnClientes.setBackground(new java.awt.Color(255, 246, 222));
        btnClientes.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setBorder(null);
        btnClientes.setFocusPainted(false);
        btnClientes.setPreferredSize(new java.awt.Dimension(78, 27));
        btnClientes.addActionListener(this::btnClientesActionPerformed);

        btnProductos.setBackground(new java.awt.Color(255, 226, 150));
        btnProductos.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(null);
        btnProductos.setFocusPainted(false);
        btnProductos.addActionListener(this::btnProductosActionPerformed);

        btnInventario.setBackground(new java.awt.Color(255, 246, 222));
        btnInventario.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        btnInventario.setText("Inventario");
        btnInventario.setBorder(null);
        btnInventario.setFocusPainted(false);
        btnInventario.addActionListener(this::btnInventarioActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRepCom)
                .addGap(17, 17, 17))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRepCli, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRepCom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRepCli)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 220, 175));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(545, 531));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Agregar Producto");
        lblTitulo.setPreferredSize(new java.awt.Dimension(205, 31));

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblNombre.setText("Nombre");

        lblDescripcion.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblDescripcion.setText("Descripción");

        lblIngredientes.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblIngredientes.setText("Ingredientes");

        lblPrecio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblPrecio.setText("Precio");

        lblTipo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblTipo.setText("Tipo");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtNombre.setBorder(null);

        cbTipo.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N

        txtPrecio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPrecio.setBorder(null);

        lblSimboloDinero.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblSimboloDinero.setText("$");

        listaIngredientes.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jScrollPane1.setViewportView(listaIngredientes);

        lblImagen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblImagen.setText("Imagen (Opcional)");

        btnSubirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/subir-archivo icon.png"))); // NOI18N
        btnSubirImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubirImagenMouseClicked(evt);
            }
        });

        btnAgregarIngrediente.setBackground(new java.awt.Color(181, 245, 255));
        btnAgregarIngrediente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAgregarIngrediente.setText("+ Añadir Ingredientes");
        btnAgregarIngrediente.setBorderPainted(false);
        btnAgregarIngrediente.setFocusPainted(false);
        btnAgregarIngrediente.setOpaque(true);
        btnAgregarIngrediente.addActionListener(this::btnAgregarIngredienteActionPerformed);

        btnAtras.setBackground(new java.awt.Color(47, 47, 47));
        btnAtras.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atrás");
        btnAtras.setBorderPainted(false);
        btnAtras.setFocusPainted(false);
        btnAtras.setOpaque(true);
        btnAtras.addActionListener(this::btnAtrasActionPerformed);

        btnAgregar.setBackground(new java.awt.Color(47, 47, 47));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setOpaque(true);
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        btnEliminarImagen.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminarImagen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarImagen.setText("Eliminar");
        btnEliminarImagen.setBorderPainted(false);
        btnEliminarImagen.setFocusPainted(false);
        btnEliminarImagen.addActionListener(this::btnEliminarImagenActionPerformed);

        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDescripcion.setBorder(null);
        txtDescripcion.setMaximumSize(new java.awt.Dimension(308, 31));
        txtDescripcion.setMinimumSize(new java.awt.Dimension(308, 31));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jSeparator2)
                            .addComponent(lblNombre)
                            .addComponent(lblDescripcion)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblIngredientes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                                .addComponent(btnAgregarIngrediente))
                            .addComponent(txtNombre)
                            .addComponent(jSeparator1)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblSimboloDinero, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator5))))
                                .addContainerGap())
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(btnSubirImagen)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblTipo)
                                        .addGap(103, 103, 103))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblPrecio)
                                        .addGap(95, 95, 95))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblImagen)
                                        .addGap(47, 47, 47))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnEliminarImagen)
                                        .addGap(28, 28, 28))))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblPrecio)
                                    .addComponent(lblDescripcion))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSimboloDinero)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIngredientes)
                            .addComponent(lblImagen)
                            .addComponent(btnAgregarIngrediente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(btnSubirImagen)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarImagen)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botón que abre el módulo de reportes. 
     */
    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportesActionPerformed

    /**
     * Botón que abre el módulo de reporte -> clientes.
     */
    private void btnRepCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRepCliActionPerformed

    /**
     * Botón que abre el módulo de clientes.
     */
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClientesActionPerformed

    /**
     * Botón que abre el módulo de productos.
     */
    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProductosActionPerformed

    /**
     * Botón que abre el módulo de inventario-ingredientes. 
     */
    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInventarioActionPerformed

    /**
     * Botón que abre el módulo de reportes -> comandas.
     */
    private void btnRepComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRepComActionPerformed

    /**
     * Botón para agregar ingredientes al producto.
     * 
     * Se encarga de usar el coordinador para abrir el catálogo de ingredientes.
     */
    private void btnAgregarIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarIngredienteActionPerformed
        // TODO add your handling code here:
        coordinador.abrirMenuIngredientes();
    }//GEN-LAST:event_btnAgregarIngredienteActionPerformed

    /**
     * Por si el usuario quiere regresar, primero pide confirmación y en base 
     * a su decisión regresa al frame anterior o se queda.
     */
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        if(!txtNombre.getText().isEmpty() || !txtDescripcion.getText().isEmpty() || !txtPrecio.getText().isEmpty() || !modelo.isEmpty() || archivo != null){
            int opcion = DlgNotificacion2.mostrarNotificacion(this, "¿Seguro de que desea regresar? Esto cancelará el proceso y la información se perderá.", TipoNotificacion.CONFIRMACIÓN);
            if(opcion == DlgNotificacion2.RET_CANCELAR){
                return;
            }
        }
     
        coordinador.abrirFrmProductos();
        limpiar();
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    /**
     * Es el botón para cuando se desea guardar el registro/las modificaciones
     * que el usuario haya hecho con el producto. 
     */
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        String nombre;
        String descripcion;
        TipoProductoDTO tipo;
        String precioTemp;
        Double precio;
        String urlImagen;
        
        // nombre
        nombre = txtNombre.getText().trim();

        if (nombre == null || nombre.isEmpty() || nombre.isBlank()){
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Nombre' no puede estar vacío.", TipoNotificacion.MENSAJE);
            return;
        } else if (!nombre.matches("^[\\p{L}\\d., ]+$")) {
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Nombre' solo acepta letras, acentos, espacios y puntos, comas y números.", TipoNotificacion.MENSAJE);
            return;
        }

        // descripción
        descripcion = txtDescripcion.getText().trim();

        if (descripcion == null || descripcion.isEmpty() || descripcion.isBlank()) {
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Descripción' no puede estar vacío.", TipoNotificacion.MENSAJE);
            return;
        } else if (!descripcion.matches("^[\\p{L}\\d., ]+$")) {
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Descripción' solo acepta letras, acentos, espacios y puntos, comas y números.", TipoNotificacion.MENSAJE);
            return;
        }

        // tipo
        tipo = (TipoProductoDTO) cbTipo.getSelectedItem();

        // precio
        precioTemp = txtPrecio.getText().trim();
        if(precioTemp == null || precioTemp.isEmpty() || precioTemp.isBlank()){
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Precio' no puede estar vacío.", TipoNotificacion.MENSAJE);
            return;
        } else if(!precioTemp.matches("^\\d+(\\.\\d+)?$")){
            DlgNotificacion2.mostrarNotificacion(this, "El campo 'Precio' solo acepta números y decimas.", TipoNotificacion.MENSAJE);
            return;
        }
        precio = Double.valueOf(precioTemp);

        // url imagen
        if(archivo != null){
            urlImagen = archivo.getAbsolutePath();
            /*
            esto de abajo era por unos detalles de cuando trabajamos con windows, 
            para cuidar el formato, pero la nt no se ha requerido.
            */
            // urlImagen = archivo.getAbsolutePath().replace("\\", "/");
        } else if(modificar && producto.getUrlImagen() != null && !imagenEliminada){
            urlImagen = producto.getUrlImagen();
        } else {
            urlImagen = null;
        }
        
        String mensaje;
        if(modificar == false){
            mensaje = "¿Seguro de agregar el producto: " + nombre + " - " + tipo + "?";
        } else{
            mensaje = "¿Seguro de actualizar el producto: " + nombre + " - " + tipo + "?";
        }
        
        int opcion = DlgNotificacion2.mostrarNotificacion(this, mensaje, TipoNotificacion.CONFIRMACIÓN);
        if(opcion == DlgNotificacion2.RET_CANCELAR){
            return;
        }
        
        if (modificar) {
            ProductoDTO original = coordinador.consultarProductoPorID(producto.getId());
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            if(detallesProducto != null && !detallesProducto.isEmpty()){
                producto.setIngredientes(detallesProducto);
            }
            producto.setUrlImagen(urlImagen);
            resultado = coordinador.actualizarProducto(producto);   
            if (resultado) {
                DlgNotificacion2.mostrarNotificacion(this, "Producto modificado correctamente", TipoNotificacion.ÉXITO);
                limpiar();
                coordinador.abrirFrmProductos();
                this.dispose();
            }
        } else {
            ProductoNuevoDTO dto = new ProductoNuevoDTO(nombre, tipo, descripcion, precio, EstadoProductoDTO.ACTIVO, urlImagen);
            dto.setIngredientes(detallesProducto);
            resultado = coordinador.agregarProducto(dto);
            if (resultado) {
                DlgNotificacion2.mostrarNotificacion(this, "Producto agregado correctamente", TipoNotificacion.ÉXITO);
                limpiar();
                coordinador.abrirFrmProductos();
                this.dispose();
            }
        }
        
        coordinador.refrescarTablaProductos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    /**
     * Cuando el usuario le da click al label de subir, aquí se maneja el 
     * fileChooser para buscar, guardar y mostrar la imagén seleccionada. 
     */
    private void btnSubirImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirImagenMouseClicked
        // TODO add your handling code here:
        JFileChooser selector = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (PNG, JPG)", "png", "jpg", "jpeg");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            archivo = selector.getSelectedFile();

            ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            btnSubirImagen.setIcon(new ImageIcon(img));
            mostrarBotonEliminarImagen();
        }
    }//GEN-LAST:event_btnSubirImagenMouseClicked

    /**
     * Método de acción del botón de eliminar imagen, por si el usuario sube
     * una imagen y luego se arrepiente, o si está modificando el producto y
     * quiere dejarlo sin imagen.
     */
    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        // TODO add your handling code here:
        int opcion = DlgNotificacion2.mostrarNotificacion(this, "¿Seguro de eliminar la imagen cargada?", TipoNotificacion.CONFIRMACIÓN);
        if(opcion == DlgNotificacion2.RET_CANCELAR){
            return;
        }
        archivo = null;
        imagenEliminada = true;
        btnSubirImagen.setIcon(new ImageIcon(getClass().getResource("/img/subir-archivo icon.png")));
        ocultarBotonEliminarImagen();
    }//GEN-LAST:event_btnEliminarImagenActionPerformed
    
    /**
     * Ya que se agrega/modifica un producto, limpia el frame para dejarlo listo 
     * para la siguiente vez.
     */
    public void limpiar(){
        btnAgregar.setText("Agregar");
        lblTitulo.setText("Agregar Producto");
        producto = null;
        modificar = false;
        resultado = false;
        txtNombre.setText("");
        txtNombre.setEditable(true);
        cbTipo.setEnabled(true);
        txtDescripcion.setText("");
        txtDescripcion.setEditable(true);
        txtDescripcion.setEnabled(true);
        txtPrecio.setText("");
        txtPrecio.setEditable(true);
        archivo = null;
        imagenEliminada = false;
        btnSubirImagen.setIcon(new ImageIcon(getClass().getResource("/img/subir-archivo icon.png")));
        modelo.clear();
        if(detallesProducto != null){
            detallesProducto.clear();
        }
        ocultarBotonEliminarImagen();
        coordinador.limpiarListaIngredientes();
    }
    
    /**
     * Método para modificar el botón de eliminar imagen, que solo es visible si
     * hay una imagen cargada.
     * 
     * Ajusta sus detalles para ocultar el botón sin que se pierda o cambie la
     * forma del resto del frame.
     */
    private void ocultarBotonEliminarImagen() {
        btnEliminarImagen.setForeground(new java.awt.Color(0, 0, 0, 0)); 
        btnEliminarImagen.setBackground(new java.awt.Color(0, 0, 0, 0));
        btnEliminarImagen.setOpaque(false);
        btnEliminarImagen.setContentAreaFilled(false);
        btnEliminarImagen.setText("");
        btnEliminarImagen.setEnabled(false);
    }
    
    /**
     * Método para modificar el botón de eliminar imagen, que solo es visible si
     * hay una imagen cargada.
     * 
     * Ajusta sus detalles para mostrar el botón sin que se pierda o cambie la
     * forma del resto del frame.
     */
    private void mostrarBotonEliminarImagen() {
        btnEliminarImagen.setForeground(new java.awt.Color(255, 255, 255)); 
        btnEliminarImagen.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminarImagen.setOpaque(true);
        btnEliminarImagen.setContentAreaFilled(true);
        btnEliminarImagen.setText("Eliminar");
        btnEliminarImagen.setEnabled(true); 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarIngrediente;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEliminarImagen;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnRepCli;
    private javax.swing.JButton btnRepCom;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel btnSubirImagen;
    private javax.swing.JComboBox<enumerators.TipoProductoDTO> cbTipo;
    private javax.swing.JLabel iconLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblIngredientes;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblSimboloDinero;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> listaIngredientes;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
