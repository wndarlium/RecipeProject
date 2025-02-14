PruebTecnicaKoaliti
PruebTecnicaKoaliti es una aplicación de recetas desarrollada en Android utilizando Jetpack Compose y arquitectura MVVM. La app está orientada a la gestión de recetas, permitiendo a los usuarios autenticarse, agregar, visualizar, filtrar y gestionar recetas de manera intuitiva y moderna. Además, cuenta con funcionalidades adicionales como compartir la app a través de Firebase App Distribution (sin necesidad de integrar otros SDK de Firebase para esta función).

Funcionalidades
Autenticación:

Pantalla de inicio de sesión con credenciales predefinidas.
Mantenimiento de sesión activa incluso al cerrar la app (implementado con DataStore o SharedPreferences).
Gestión de Recetas:

Agregar nuevas recetas con campos:
Título
Descripción
Tiempo de preparación (en minutos)
Preparación (instrucciones detalladas)
Imagen (seleccionable desde la galería)
Ingredientes (lista de ingredientes con cantidad; se pueden añadir o eliminar dinámicamente)
Visualización de un listado de recetas, donde se muestran:
Imagen de la receta (a pantalla completa de ancho en el detalle)
Título (centrado y estilizado con tipografía personalizada)
Tiempo de preparación, descripción y preparación (instrucciones)
Indicador de favorito (estrella)
Detalle de la receta con:
Imagen a pantalla completa
Título, tiempo, descripción y preparación (todos centrados)
Sección de ingredientes en una Card elevada
Opción para marcar como favorita y eliminar la receta
Filtros y Ordenamiento:

Filtro por recetas favoritas.
Ordenamiento por tiempo (ascendente o descendente) con visualización mediante Toast.
Carrito de Compras:

Desde el detalle de receta, se puede agregar la receta al carrito de compras.
El carrito muestra cada receta con su lista de ingredientes, donde cada ingrediente tiene un checkbox para marcarlo (al marcarlo, el texto se tacha).
Validación para evitar duplicados: si ya existe una receta en el carrito, se muestra un AlertDialog.
Distribución:

La aplicación se comparte a través de Firebase App Distribution, sin necesidad de integrar otros servicios de Firebase en el código.
Tecnologías Utilizadas
Android:

Jetpack Compose para la UI.
Arquitectura MVVM.
Room para persistencia de datos (en el caso de recetas, pero el carrito se maneja en memoria).
DataStore o SharedPreferences para el manejo de sesión (según implementación).
Kotlin:

Programación funcional y corutinas para la ejecución asíncrona.
Firebase App Distribution:

Para compartir builds (APK/AAB) con testers sin integrar Firebase en el código de la app.


Notas de Desarrollo
Validaciones:

Se incluyen validaciones en la UI para que los campos no queden vacíos (por ejemplo, en Login y AddRecipeScreen).
La lógica de validación compleja se encuentra en la carpeta util.validators para reutilizarla (como la validación del login).
Navegación:

La navegación se maneja mediante un NavHost centralizado, y cada pantalla se define en el paquete ui.navigation.
Temas:

Se utilizan colores personalizados y tipografías (por ejemplo, PlayfairFamily) para dar un estilo único a la aplicación.

