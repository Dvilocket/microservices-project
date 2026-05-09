# Microservices Project

Proyecto desarrollado con arquitectura de microservicios usando Spring Boot, Eureka, API Gateway, PostgreSQL y Docker.

---

# Arquitectura

El sistema está compuesto por los siguientes microservicios:

* Eureka Server → Registro y descubrimiento de servicios
* Gateway Service → API Gateway
* Vehicle Service → Gestión de vehículos
* Operation Service → Gestión de operaciones
* PostgreSQL → Base de datos

---

# Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Cloud Eureka
* Spring Cloud Gateway
* Spring Data JPA
* PostgreSQL
* Maven
* Docker
* Docker Compose

---

# Requisitos

Antes de ejecutar el proyecto, instalar:

* Java 17
* Maven
* Docker Desktop

---

# Clonar proyecto

```bash id="k0lj73"
git clone https://github.com/Dvilocket/microservices-project.git
cd microservices-project
```

---

# Compilar microservicios

Ejecutar en cada microservicio:

```bash id="1o0d0v"
mvn clean package -DskipTests
```
si no te sirve el anterior comando, usa este desde el IDE

.\mvnw.cmd clean package -DskipTests

Esto generará los archivos `.jar` necesarios para Docker.

---

# Ejecutar proyecto

Desde la raíz del proyecto ejecutar:

```bash id="lqsmf6"
docker compose up --build
```

---

# Servicios disponibles

| Servicio          | Puerto |
| ----------------- | ------ |
| Eureka Server     | 8761   |
| Gateway Service   | 8080   |
| Vehicle Service   | 8081   |
| Operation Service | 8083   |
| PostgreSQL        | 5432   |

---

# Eureka Dashboard

Abrir en navegador:

```txt id="wms4uh"
http://localhost:8761
```

---

# Base de datos

PostgreSQL se ejecuta mediante Docker utilizando volumen persistente.

Para reiniciar completamente la base de datos:

```bash id="h4rn1t"
docker compose down -v
```

# Nota

importante si se descarga el codigo fuente, importar los modulos para que el IDE los reconozca
1.Se le da en la opción File
2.Se le da en la opción project Structure
3.Se busca la opción de Modules
4.En el icono de una cruz o un mas, se le da en agregar y se busca el archivo POM de cada servicio

<img width="1918" height="1007" alt="image" src="https://github.com/user-attachments/assets/51d12fdf-cb3b-46cb-831c-486c4a01de54" />




---

# Autor

Miguel Angel Ramirez Echeverry
