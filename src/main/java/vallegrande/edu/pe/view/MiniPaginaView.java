// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;

// Importa el controlador de usuarios para manejar la lógica
import vallegrande.edu.pe.controller.UsuarioController;

// Importa clases de Swing y AWT necesarias para la interfaz gráfica
import javax.swing.*;
import java.awt.*;

// Clase que representa la ventana principal tipo "mini página web" del instituto
public class MiniPaginaView extends JFrame {

    // Controlador de usuarios para gestionar acciones relacionadas con usuarios
    private final UsuarioController controller;

    // Constructor que recibe el controlador de usuarios
    public MiniPaginaView(UsuarioController controller) {
        this.controller = controller; // Asigna el controlador recibido
        initUI(); // Inicializa la interfaz gráfica
    }

    // Método que construye toda la interfaz de usuario
    // ...existing code...
    private void initUI() {
        setTitle("Instituto Valle Grande - Portal Principal");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===============================
        // Encabezado tipo banner con logotipo
        // ===============================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 70, 140));
        header.setPreferredSize(new Dimension(getWidth(), 90));

        // Logotipo usando ruta relativa desde la carpeta del proyecto
        ImageIcon logoIcon;
        java.io.File logoFile = new java.io.File("imagen/logo.png");
        if (logoFile.exists()) {
            Image img = new ImageIcon(logoFile.getPath()).getImage();
            Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImg);
        } else {
            logoIcon = (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");
        }
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(90, 90));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        header.add(logoLabel, BorderLayout.WEST);

        JLabel title = new JLabel("Bienvenido al Portal del Instituto Valle Grande", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // ===============================
        // Panel central
        // ===============================
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.WHITE);

        // -------------------------------
        // Texto informativo
        // -------------------------------
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        JLabel infoLabel = new JLabel("<html><center><h2 style='color:#00468c;'>Gestión Académica y Administrativa</h2>"
                +
                "<span style='font-size:15px;'>Administre usuarios, docentes y estudiantes desde un mismo lugar<br>" +
                "Sistema institucional moderno y seguro</span></center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(infoLabel);

        // -------------------------------
        // Panel de botones tipo menú
        // -------------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(245, 245, 245));

        // Botón Administrar Usuarios
        JButton btnUsuarios = crearBotonMenu("Administrar Usuarios", new Color(0, 120, 215),
                UIManager.getIcon("FileView.fileIcon"));
        btnUsuarios.addActionListener(e -> new UsuarioCrudView(controller).setVisible(true));

        // Botón Administrar Docentes
        JButton btnDocentes = crearBotonMenu("Administrar Docentes", new Color(34, 167, 240),
                UIManager.getIcon("FileView.directoryIcon"));

        // Botón Administrar Estudiantes
        JButton btnEstudiantes = crearBotonMenu("Administrar Estudiantes", new Color(34, 153, 84),
                UIManager.getIcon("FileView.computerIcon"));

        menuPanel.add(btnUsuarios);
        menuPanel.add(btnDocentes);
        menuPanel.add(btnEstudiantes);

        centerPanel.add(infoPanel);
        centerPanel.add(menuPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ===============================
        // Footer
        // ===============================
        JPanel footer = new JPanel();
        footer.setBackground(new Color(230, 230, 230));
        JLabel lblFooter = new JLabel("© 2025 Instituto Valle Grande - Todos los derechos reservados");
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.add(lblFooter);
        add(footer, BorderLayout.SOUTH);
    }

    // Método auxiliar para crear botones tipo menú con icono y estilo
    private JButton crearBotonMenu(String texto, Color colorFondo, Icon icono) {
        JButton btn = new JButton(texto, icono);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorFondo);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(270, 60));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(16);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 140), 2),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        return btn;
    }
}
// ...existing code...
