# Backend Spring Boot - Microservicios de Users y Accounts

## Descripción 📌

Este proyecto consta de dos microservicios **Users** y **Accounts**, desarrollados en **Spring Boot** con WebFlux, que permiten gestionar usuarios y cuentas de manera eficiente. La arquitectura basada en microservicios facilita la escalabilidad y mantenibilidad del sistema, ademas
de que se encuentra creado con API First y OpenApi Generator.
### Microservicios principales:
- **Users**: Se encarga de la gestión de usuarios, incluyendo su creación, actualización y consulta de usuarios como de clientes.
- **Accounts**: Maneja la administración de cuentas, permitiendo la creación, actualización y recuperación de información de cuentas asociadas a los usuarios, al igual que los reportes tanto en PDF como en Excel.

## Demo 🚀

Puedes acceder a la documentación y probar la API mediante el Openapi adjunto de cada uno de los microservicios:
- **Users API**: [View Demo](https://exam-p-949b6094fe5d.herokuapp.com/)
- **Accounts API**: [View Demo](https://exam-msa-accounts-61fa437c4bce.herokuapp.com/)

## Quick start 🚀

Opciones de inicio rápido:
- [Descargar desde Github el MSA- USERS](https://github.com/joseobando0001/exam-msa-users.git)
- [Descargar desde Github el MSA- ACCOUNTS](https://github.com/joseobando0001/exam-msa-accounts.git)

## Comandos de Terminal 🖥️

Este proyecto fue generado con [Spring Initializr](https://start.spring.io/).

### Pasos para ejecutar el proyecto:
1. Asegúrate de tener **Java 17** o superior instalado en tu sistema.
2. Descarga el repositorio y ábrelo en tu IDE preferido (IntelliJ, Eclipse, Spring Tools, VS Code).
3. Verifica que los servicios están corriendo en:
    - **Users**: `http://localhost:8080/api/v1/`
    - **Accounts**: `http://localhost:8081/api/v1/`
4. En cada microservicio adjuntare la colección de Postman tanto local como hacia los microservicios desplegados con CI/CD.

## Tests JUnit 🧪

El proyecto cuenta con pruebas unitarias y de integración usando **JUnit** y **Mockito**:


## Tecnologías utilizadas 🛠️

- **Spring Boot 3.x**
- **Spring Webflux** (para comunicación entre microservicios)
- **Spring Data R2DBC** (persistencia de datos)
- **H2** (base de datos relacional en memoria)
- **Kafka** (mensajería entre microservicios)
- **Swagger/OpenAPI** (documentación de la API)
- **JUnit & Mockito** (pruebas unitarias y de integración)


## Autor ✍️

Este proyecto fue desarrollado por:
- [José Obando](mailto:jose.obando_0001@hotmail.com) - Backend Developer & Tech Lead.

