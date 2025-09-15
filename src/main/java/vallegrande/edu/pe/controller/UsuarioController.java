// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.controller;

// Importa la clase Usuario del paquete model
import vallegrande.edu.pe.model.Usuario;

// Importa las clases necesarias para usar listas dinámicas
import java.util.ArrayList;
import java.util.List;

// Definición de la clase UsuarioController que maneja la lógica de usuarios
public class UsuarioController {
    private static final String ARCHIVO = "usuarios.csv";

    // Lista privada que almacena todos los usuarios
    private final List<Usuario> usuarios;

    // Constructor de la clase
    public UsuarioController() {
        usuarios = new ArrayList<>();
        cargarDesdeArchivo();
        if (usuarios.isEmpty()) {
            // Si el archivo está vacío o no existe, agrega datos de ejemplo
            usuarios.add(new Usuario("Valery Chumpitaz", "valery@correo.com", "Administrador"));
            usuarios.add(new Usuario("Juan Pérez", "juan@correo.com", "Docente"));
            usuarios.add(new Usuario("María López", "maria@correo.com", "Estudiante"));
            guardarEnArchivo();
        }
    }

    // Método que retorna la lista completa de usuarios
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Método para agregar un nuevo usuario a la lista
    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarEnArchivo();
    }

    // Método para eliminar un usuario de la lista según su índice
    public void deleteUsuario(int index) {
        if (index >= 0 && index < usuarios.size()) {
            usuarios.remove(index);
            guardarEnArchivo();
        }
    }

    // Método para actualizar un usuario existente en la lista según su índice
    public void updateUsuario(int index, Usuario usuario) {
        if (index >= 0 && index < usuarios.size()) {
            usuarios.set(index, usuario);
            guardarEnArchivo();
        }
    }

    // Guarda la lista de usuarios en un archivo CSV
    private void guardarEnArchivo() {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(ARCHIVO)) {
            for (Usuario u : usuarios) {
                pw.println(u.getNombre().replace(",", " ") + "," + u.getCorreo().replace(",", " ") + "," + u.getRol().replace(",", " "));
            }
        } catch (Exception e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // Carga la lista de usuarios desde un archivo CSV
    private void cargarDesdeArchivo() {
        java.io.File file = new java.io.File(ARCHIVO);
        if (!file.exists()) return;
        try (java.util.Scanner sc = new java.util.Scanner(file)) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    usuarios.add(new Usuario(partes[0], partes[1], partes[2]));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
}


