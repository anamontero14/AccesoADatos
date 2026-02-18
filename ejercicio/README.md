# API REST Gestión de Alumnos

API REST para gestionar alumnos desarrollada con Spring Boot.

## Tecnologías utilizadas
- Java 23
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- MySQL
- Swagger/OpenAPI

## Requisitos previos
- Java 17 o superior
- MySQL instalado y corriendo
- Maven

## Configuración de la base de datos
Crear la base de datos en MySQL:
```sql
CREATE DATABASE alumnos_db;
```

Configurar las credenciales en `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/alumnos_db
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
```

## Cómo ejecutar el proyecto
1. Clonar el repositorio
2. Configurar la base de datos
3. Ejecutar la clase `EjercicioApplication.java`
4. La API estará disponible en `http://localhost:8080`

## Credenciales de prueba
- Usuario: `admin`
- Contraseña: `1234`

## Documentación
Swagger UI disponible en: `http://localhost:8080/swagger-ui/index.html`

## Endpoints disponibles
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /alumnos | Lista todos los alumnos |
| GET | /alumnos/{id} | Busca alumno por ID |
| POST | /alumnos | Crea un nuevo alumno |
| PUT | /alumnos/{id} | Actualiza un alumno |
| DELETE | /alumnos/{id} | Elimina un alumno |