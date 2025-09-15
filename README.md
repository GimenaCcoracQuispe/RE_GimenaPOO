# MiAppDesktop
## Mejoras aplicadas

- **Interfaz visual profesional:**
	- Colores institucionales y consistentes.
	- Logotipo institucional en la parte superior (se carga desde `imagen/logo.png`).
	- Botones de menú con iconos representativos y fuentes legibles.
	- Iconos personalizados en los botones de Agregar (`imagen/crear.png`), Editar (`imagen/editar.png`) y Eliminar (`imagen/eliminar.png`).

- **Edición de usuarios:**
	- Botón "Editar" con icono de lápiz.
	- Al seleccionar una fila y pulsar "Editar", se abre un cuadro de diálogo para modificar nombre, correo y rol.
	- Validaciones: ningún campo puede quedar vacío y el correo debe tener formato válido (contiene "@" y ".").
	- Al guardar, la tabla se actualiza automáticamente.

- **Búsqueda de usuarios:**
	- Campo de texto arriba de la tabla para filtrar usuarios por nombre o correo en tiempo real.
	- La tabla muestra solo los registros coincidentes mientras escribes.

- **Persistencia básica:**
	- Los usuarios se guardan automáticamente en el archivo `usuarios.csv` en la raíz del proyecto.
	- Al iniciar la aplicación, los usuarios se cargan desde ese archivo.
	- Los cambios (agregar, editar, eliminar) se guardan automáticamente.

## Cómo usar

1. **Edición de usuarios:**
	 - Selecciona una fila en la tabla de usuarios.
	 - Haz clic en el botón "Editar" (icono de lápiz).
	 - Modifica los datos y confirma. Si hay errores de validación, se mostrarán mensajes amigables.

2. **Búsqueda:**
	 - Escribe en el campo "Buscar" arriba de la tabla para filtrar usuarios por nombre o correo.
	 - La tabla se actualiza automáticamente mostrando solo los usuarios que coinciden.

3. **Persistencia:**
	 - Los usuarios se guardan en `usuarios.csv`.
	 - Al iniciar la app, se cargan automáticamente.
	 - No se pierden los datos al cerrar la aplicación.

## Cambios

- Java 8+
- Las imágenes de iconos deben estar en la carpeta `imagen/` dentro del proyecto:
	- `logo.png` (logotipo)
	- `crear.png` (icono agregar)
	- `editar.png` (icono editar)
	- `eliminar.png` (icono eliminar)
<img width="681" height="389" alt="Captura de pantalla 2025-09-15 105646" src="https://github.com/user-attachments/assets/85d7bfe6-071c-4e4f-af09-3d6b0813e431" />

## Subir a GitHub

1. Realiza commit de todos los cambios.
2. Sube el proyecto a tu repositorio en GitHub.
3. Incluye este README.md actualizado.

---

¡Proyecto listo para presentación y uso institucional!
