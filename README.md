Aplicación de Registro de Usuarios
Esta aplicación expone una API RESTful para la creación, búsqueda, actualización y eliminación de usuarios. Los usuarios pueden registrarse con información como nombre, correo electrónico, contraseña y teléfonos asociados.

Requisitos
Asegúrate de tener instalados los siguientes requisitos:

Java 8 o superior
Gradle (para el proceso de construcción)
Banco de datos en memoria (H2 Database)
Framework Spring
Hibernate (para la persistencia)
Servidor Tomcat o Jetty Embedded


Ejecución
Sigue estos pasos para ejecutar la aplicación:

Clona este repositorio en tu máquina local:
git clone https://github.com/tu-usuario/tu-repositorio.git


Accede al directorio de la aplicación:
cd tu-repositorio


Construye la aplicación con Gradle:
./gradlew build

Ejecuta la aplicación:
./gradlew bootRun

La aplicación estará disponible en http://localhost:8080.

Endpoints
A continuación, se presentan los endpoints disponibles:

POST /api/usuarios/registro: Registra un nuevo usuario.
GET /api/usuarios/{id}: Obtiene un usuario por su ID.
GET /api/usuarios/todos: Obtiene todos los usuarios registrados.
PUT /api/usuarios/{id}: Actualiza los detalles de un usuario existente.
DELETE /api/usuarios/{id}: Elimina un usuario por su ID.

Formato de Entrada
El formato JSON esperado para registrar un usuario es:

{
"nombre": "Nombre del Usuario",
"correo": "correo@ejemplo.com",
"contraseña": "contraseña123",
"phones": [
{
"number": "1234567",
"citycode": "1",
"countrycode": "57"
}
]
}

Formato de Respuesta de Error
Los errores se devuelven en el siguiente formato JSON:

{
"mensaje": "mensaje de error"
}

Notas Adicionales

La aplicación utiliza una base de datos H2 en memoria.
Se han implementado reglas de validación para el formato del correo y la contraseña.
Para obtener un token de acceso JWT, consulta la documentación de autenticación y autorización.
Las respuestas exitosas incluyen el código de estado HTTP correspondiente y los datos del usuario registrado o actualizado.
