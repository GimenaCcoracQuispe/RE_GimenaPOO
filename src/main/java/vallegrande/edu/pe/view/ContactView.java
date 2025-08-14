package vallegrande.edu.pe.view;

import java.awt.image.BufferedImage;
import vallegrande.edu.pe.controller.ContactController;
import vallegrande.edu.pe.model.Contact;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vista principal con Swing que muestra la lista de contactos y botones.
 * Ventana maximizada, botones con estilo moderno y colores.
 */
public class ContactView extends JFrame {
    private final ContactController controller;
    private DefaultTableModel tableModel;
    private JTable table;

    // Toast panel para notificaciones visuales
    private JPanel toastPanel;
    private JLabel toastLabel;
    private Timer toastTimer;

    // Colores principales del tema
    private final Color PRIMARY = new Color(33, 150, 243); // Azul moderno
    private final Color PRIMARY_DARK = new Color(25, 118, 210);
    private final Color ERROR = new Color(220, 53, 69);
    private final Color SUCCESS = new Color(40, 167, 69);
    private final Color BG = new Color(245, 247, 251); // Fondo claro
    private final Color PANEL_BG = Color.WHITE;
    private final Color TABLE_HEADER_BG = new Color(240, 244, 250);

    public ContactView(ContactController controller) {
        super("Agenda MVC Swing - Vallegrande");
        this.controller = controller;
        initUI();
        loadContacts();
        showWelcomeToast();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Fuente base moderna
        Font baseFont = new Font("Segoe UI", Font.PLAIN, 17);

        // Panel principal con margen y fondo
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        contentPanel.setBackground(BG);
        setContentPane(contentPanel);

        // Panel superior con mensaje de bienvenida
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel welcome = new JLabel("Bienvenido a tu Agenda de Contactos", JLabel.LEFT);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(PRIMARY_DARK);
        topPanel.add(welcome, BorderLayout.WEST);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Panel central con borde suave y sombra
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(PANEL_BG);
        centerPanel.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 4, 4, new Color(230, 236, 245)),
                new EmptyBorder(20, 20, 20, 20)));
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Tabla con modelo y estilo
        tableModel = new DefaultTableModel(new String[] { "ID", "Nombre", "Email", "Teléfono" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(baseFont);
        table.setRowHeight(32);
        table.setForeground(new Color(33, 33, 33));
        table.setBackground(PANEL_BG);
        table.setSelectionBackground(new Color(232, 244, 253));
        table.setSelectionForeground(PRIMARY_DARK);

        // Encabezado de tabla personalizado
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 19));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(PRIMARY_DARK);
        header.setBorder(new MatteBorder(0, 0, 2, 0, PRIMARY));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones con layout y fondo
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 8));
        buttonsPanel.setOpaque(false);

        // Botón "Agregar" con icono y estilo
        JButton addBtn = createIconButton("Agregar", PRIMARY, "plus");
        // Botón "Eliminar" con icono y estilo
        JButton deleteBtn = createIconButton("Eliminar", ERROR, "trash");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(deleteBtn);

        centerPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Toast panel para notificaciones
        toastPanel = new JPanel();
        toastPanel.setOpaque(false);
        toastPanel.setLayout(new GridBagLayout());
        toastLabel = new JLabel();
        toastLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        toastLabel.setOpaque(true);
        toastLabel.setVisible(false);
        toastLabel.setBorder(new EmptyBorder(12, 30, 12, 30));
        toastLabel.setForeground(Color.WHITE);
        toastLabel.setBackground(SUCCESS);
        toastLabel.setBorder(new RoundedBorder(18));
        toastPanel.add(toastLabel);
        contentPanel.add(toastPanel, BorderLayout.SOUTH);

        // Eventos botones
        addBtn.addActionListener(e -> showAddContactDialog());
        deleteBtn.addActionListener(e -> deleteSelectedContact());
    }

    /**
     * Crea un botón moderno con icono SVG embebido, color y bordes redondeados.
     */
    private JButton createIconButton(String text, Color baseColor, String iconType) {
        JButton button = new JButton(text, getIcon(iconType, 22, Color.WHITE));
        button.setFont(new Font("Segoe UI", Font.BOLD, 17));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setMargin(new Insets(10, 26, 10, 26));
        button.setIconTextGap(12);

        // Sombra ligera
        button.setUI(new ShadowButtonUI());

        // Hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }

    /**
     * Devuelve un icono SVG simple embebido (plus o trash).
     */
    private Icon getIcon(String type, int size, Color color) {
        // Solo dos tipos: plus y trash
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        if ("plus".equals(type)) {
            int s = size / 2;
            g.fillRoundRect(s - 2, 5, 4, size - 10, 3, 3);
            g.fillRoundRect(5, s - 2, size - 10, 4, 3, 3);
        } else if ("trash".equals(type)) {
            g.fillRect(size / 4, size / 2, size / 2, size / 4);
            g.fillRect(size / 3, size / 4, size / 3, size / 2);
            g.fillRect(size / 4, size / 4, size / 2, 4);
            g.fillRect(size / 2 - 2, size / 4 - 4, 4, 4);
        }
        g.dispose();
        return new ImageIcon(img);
    }

    private void loadContacts() {
        tableModel.setRowCount(0);
        List<Contact> contacts = controller.list();
        for (Contact c : contacts) {
            tableModel.addRow(new Object[] { c.id(), c.name(), c.email(), c.phone() });
        }
    }

    private void showAddContactDialog() {
        AddContactDialog dialog = new AddContactDialog(this, controller);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            loadContacts();
            showToast("Contacto agregado exitosamente.", SUCCESS);
        }
    }

    private void deleteSelectedContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showToast("Seleccione un contacto para eliminar.", ERROR);
            return;
        }
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar este contacto?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.delete(id);
            loadContacts();
            showToast("Contacto eliminado.", PRIMARY_DARK);
        }
    }

    /**
     * Muestra un toast visual en la parte inferior.
     */
    private void showToast(String message, Color bg) {
        toastLabel.setText(message);
        toastLabel.setBackground(bg);
        toastLabel.setVisible(true);
        if (toastTimer != null && toastTimer.isRunning())
            toastTimer.stop();
        toastTimer = new Timer(2100, e -> toastLabel.setVisible(false));
        toastTimer.setRepeats(false);
        toastTimer.start();
    }

    /**
     * Mensaje de bienvenida inicial.
     */
    private void showWelcomeToast() {
        showToast("¡Bienvenido! Usa los botones para agregar o eliminar contactos.", PRIMARY);
    }

    /**
     * Borde redondeado para botones y paneles.
     */
    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(220, 230, 245));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(6, 14, 6, 14);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            return getBorderInsets(c);
        }
    }

    /**
     * UI personalizada para sombra en botones.
     */
    static class ShadowButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = c.getWidth(), h = c.getHeight();
            g2.setColor(new Color(33, 150, 243, 30));
            g2.fillRoundRect(2, h - 8, w - 4, 8, 16, 16);
            g2.dispose();
            super.paint(g, c);
        }
    }
}