// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;

// Importa el controlador de usuarios y la clase Usuario
import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

// Importa clases de Swing y AWT necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Clase que representa la ventana CRUD de usuarios
public class UsuarioCrudView extends JFrame {

    // Controlador de usuarios para manejar la lógica
    private final UsuarioController controller;

    // Modelo de tabla para mostrar los datos de los usuarios
    private final DefaultTableModel tableModel;

    // Constructor que recibe el controlador
    public UsuarioCrudView(UsuarioController controller) {
        this.controller = controller; // Asigna el controlador recibido

        // Configuración básica de la ventana
        setTitle("Gestión de Usuarios"); // Título de la ventana
        setSize(700, 400); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Al cerrar, solo se cierra esta ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Modelo de tabla con columnas: Nombre, Correo, Rol
        tableModel = new DefaultTableModel(new Object[] { "Nombre", "Correo", "Rol" }, 0);

        // Tabla que mostrará los usuarios
        JTable table = new JTable(tableModel);
        table.setRowHeight(25); // Altura de cada fila
        table.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Fuente de las celdas

        // Panel de búsqueda arriba de la tabla
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Buscar: ");
        JTextField searchField = new JTextField();
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER); // Añade la tabla con scroll al centro
        // Filtrar usuarios mientras se escribe
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }

            private void filtrarUsuarios() {
                String filtro = searchField.getText().toLowerCase();
                tableModel.setRowCount(0);
                for (Usuario u : controller.getUsuarios()) {
                    if (u.getNombre().toLowerCase().contains(filtro) || u.getCorreo().toLowerCase().contains(filtro)) {
                        tableModel.addRow(new Object[] { u.getNombre(), u.getCorreo(), u.getRol() });
                    }
                }
            }
        });

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        ImageIcon crearIcon = null;
        java.io.File crearFile = new java.io.File("imagen/crear.png");
        if (crearFile.exists()) {
            Image img = new ImageIcon(crearFile.getPath()).getImage();
            Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            crearIcon = new ImageIcon(scaledImg);
        }
        JButton addButton = crearIcon != null ? new JButton("Agregar", crearIcon) : new JButton("➕ Agregar"); // Botón
                                                                                                              // para
                                                                                                              // agregar
                                                                                                              // usuario
        ImageIcon editarIcon = null;
        java.io.File editarFile = new java.io.File("imagen/editar.png");
        if (editarFile.exists()) {
            Image img = new ImageIcon(editarFile.getPath()).getImage();
            Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            editarIcon = new ImageIcon(scaledImg);
        }
        JButton editButton = editarIcon != null ? new JButton("Editar", editarIcon) : new JButton("Editar ✏️"); // Botón
                                                                                                                // para
                                                                                                                // editar
                                                                                                                // usuario
        ImageIcon eliminarIcon = null;
        java.io.File eliminarFile = new java.io.File("imagen/eliminar.png");
        if (eliminarFile.exists()) {
            Image img = new ImageIcon(eliminarFile.getPath()).getImage();
            Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            eliminarIcon = new ImageIcon(scaledImg);
        }
        JButton deleteButton = eliminarIcon != null ? new JButton("Eliminar", eliminarIcon)
                : new JButton("🗑 Eliminar"); // Botón para eliminar usuario

        // Acción del botón Agregar
        addButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            String correo = JOptionPane.showInputDialog(this, "Correo:");
            String rol = JOptionPane.showInputDialog(this, "Rol (Administrador/Docente/Estudiante):");
            if (nombre == null || correo == null || rol == null)
                return;
            nombre = nombre.trim();
            correo = correo.trim();
            rol = rol.trim();
            if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ningún campo puede quedar vacío.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!correo.contains("@") || !correo.contains(".")) {
                JOptionPane.showMessageDialog(this, "El correo debe tener un formato válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.addUsuario(new Usuario(nombre, correo, rol));
            cargarUsuarios();
        });
        // Acción del botón Editar
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Usuario usuario = controller.getUsuarios().get(row);
                JTextField nombreField = new JTextField(usuario.getNombre());
                JTextField correoField = new JTextField(usuario.getCorreo());
                JTextField rolField = new JTextField(usuario.getRol());
                Object[] fields = {
                        "Nombre:", nombreField,
                        "Correo:", correoField,
                        "Rol:", rolField
                };
                int result = JOptionPane.showConfirmDialog(this, fields, "Editar Usuario",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String nombre = nombreField.getText().trim();
                    String correo = correoField.getText().trim();
                    String rol = rolField.getText().trim();
                    if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ningún campo puede quedar vacío.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!correo.contains("@") || !correo.contains(".")) {
                        JOptionPane.showMessageDialog(this, "El correo debe tener un formato válido.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.updateUsuario(row, new Usuario(nombre, correo, rol));
                    cargarUsuarios();
                }
            }
        });

        // Acción del botón Eliminar
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow(); // Obtiene la fila seleccionada
            if (row >= 0) { // Verifica que haya una fila seleccionada
                controller.deleteUsuario(row); // Elimina el usuario correspondiente
                cargarUsuarios(); // Recarga la tabla
            }
        });

        // Añade los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH); // Coloca el panel de botones al sur

        // Carga inicialmente los usuarios en la tabla
        cargarUsuarios();
    }

    // Método que carga todos los usuarios del controlador en la tabla
    private void cargarUsuarios() {
        tableModel.setRowCount(0); // Limpia la tabla
        // Si hay texto en el campo de búsqueda, filtra
        JTextField searchField = null;
        for (Component comp : ((JPanel) getContentPane().getComponent(0)).getComponents()) {
            if (comp instanceof JTextField) {
                searchField = (JTextField) comp;
                break;
            }
        }
        String filtro = (searchField != null) ? searchField.getText().toLowerCase() : "";
        for (Usuario u : controller.getUsuarios()) {
            if (filtro.isEmpty() || u.getNombre().toLowerCase().contains(filtro)
                    || u.getCorreo().toLowerCase().contains(filtro)) {
                tableModel.addRow(new Object[] { u.getNombre(), u.getCorreo(), u.getRol() });
            }
        }
    }
}
