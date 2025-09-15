package vallegrande.edu.pe;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.view.MiniPaginaView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioController controller = new UsuarioController();
            new MiniPaginaView(controller).setVisible(true);
        });
    }
}
