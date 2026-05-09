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

---

# Autor

Miguel Angel Ramirez Echeverry
