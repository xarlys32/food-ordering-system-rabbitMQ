# ddd-template

Template base en Java + Spring Boot para modelar un modulo `Order` con enfoque **DDD + CQRS + Ports & Adapters (Hexagonal)**.

## Objetivo

Este proyecto muestra una estructura limpia para separar:

- reglas de negocio (`domain`)
- casos de uso (`application`)
- adaptadores tecnicos (`infrastructure`)
- piezas reutilizables (`shared`)

La idea principal es que el dominio no dependa de frameworks ni detalles de infraestructura.

## Stack tecnico

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Gradle
- Docker Compose

## Estructura del proyecto

```text
ddd-template/
|- build.gradle
|- compose.yaml
|- src/
|  |- main/
|  |  |- java/com/example/dddtemplate/
|  |  |  |- order/
|  |  |  |  |- application/
|  |  |  |  |  |- command/
|  |  |  |  |  |- query/
|  |  |  |  |  |- dto/
|  |  |  |  |  |- mapper/
|  |  |  |  |- domain/
|  |  |  |  |  |- model/
|  |  |  |  |  |- event/
|  |  |  |  |  |- exception/
|  |  |  |  |  |- port/out/
|  |  |  |  |  |- service/
|  |  |  |  |- infrastructure/
|  |  |  |     |- api/
|  |  |  |     |- persistence/
|  |  |  |     |- messaging/
|  |  |  |- shared/
|  |  |     |- application/cqrs/
|  |  |     |- domain/
|  |  |     |- infrastructure/cqrs/
|  |  |- resources/
|  |     |- application.properties
|  |- test/
```

## Como leer cada capa

### `order/domain`

Nucleo del negocio.

- `model/Order`: Aggregate Root con invariantes y transiciones de estado.
- `model/valueobject`: `OrderId`, `OrderStatus`.
- `event/OrderCreatedEvent`: evento de dominio al crear un pedido.
- `exception/*`: errores de negocio (`OrderDomainException`, `OrderNotFoundException`).
- `port/out/*`: contratos que el dominio/aplicacion necesitan para salir (repositorio, publicador de eventos).
- `service/OrderDomainService`: logica de negocio que no encaja en una sola entidad.

### `order/application`

Orquesta casos de uso y aplica CQRS.

- `command/CreateOrderCommand` + `handler/CreateOrderCommandHandler`: caso de uso de escritura.
- `query/GetOrderQuery` + `handler/GetOrderQueryHandler`: caso de uso de lectura.
- `dto/response/OrderResponse`: salida para capa API.
- `mapper/OrderMapper`: traduccion entre dominio y DTO.

### `order/infrastructure`

Implementa adaptadores concretos (entrada y salida).

- `api/controller/OrderController`: expone REST (`/api/v1/orders`).
- `api/dto/*`: DTOs HTTP (`CreateOrderRequest`, `OrderApiResponse`).
- `persistence/*`: JPA + mapper + adapter para PostgreSQL.
- `messaging/producer/OrderEventKafkaProducer`: publica eventos en Kafka.
- `messaging/consumer/OrderEventKafkaConsumer`: consume eventos de Kafka.

### `shared`

Bloques comunes reutilizables.

- `shared/application/cqrs/*`: interfaces `Command`, `Query`, `CommandBus`, `QueryBus`, handlers.
- `shared/infrastructure/cqrs/*`: implementaciones Spring (`SpringCommandBus`, `SpringQueryBus`).
- `shared/domain/*`: bases para `AggregateRoot`, `DomainEvent`, `ValueObject`.

## Flujo funcional (alto nivel)

### Crear pedido

1. `OrderController` recibe `POST /api/v1/orders`.
2. Construye `CreateOrderCommand` y lo envia al `CommandBus`.
3. `CreateOrderCommandHandler` crea `Order` y valida reglas de dominio.
4. El pedido se guarda via `OrderRepositoryPort` (implementado por `OrderPersistenceAdapter`).
5. Se publican eventos de dominio via `OrderEventPublisherPort` (implementado por `OrderEventKafkaProducer`).
6. Se devuelve `OrderResponse` al cliente.

### Consultar pedido

1. `OrderController` recibe `GET /api/v1/orders/{orderId}`.
2. Construye `GetOrderQuery` y lo envia al `QueryBus`.
3. `GetOrderQueryHandler` consulta via `OrderRepositoryPort`.
4. Si no existe, lanza `OrderNotFoundException`.
5. Si existe, mapea a `OrderResponse` y responde HTTP 200.

## Ejecucion local

### Prerrequisitos

- Docker Desktop (o Docker Engine + Compose)
- Java 17

### 1) Levantar dependencias (PostgreSQL + Kafka)

```powershell
docker compose up -d
```

Esto inicia:

- PostgreSQL en `localhost:5432`
- Zookeeper en `localhost:2181`
- Kafka en `localhost:9092`

### 2) Ejecutar la aplicacion

```powershell
.\gradlew.bat bootRun
```

### 3) Ejecutar tests

```powershell
.\gradlew.bat test
```

## Endpoints principales

- `POST /api/v1/orders`
- `GET /api/v1/orders/{orderId}`

### Ejemplo rapido (PowerShell)

```powershell
$body = @{ customerId = "CUST-001"; totalAmount = 150.75 } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/v1/orders" -ContentType "application/json" -Body $body
```

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/v1/orders/{orderId}"
```

## Notas

- `application.properties` ya apunta por defecto a PostgreSQL y Kafka en localhost.
- `spring.jpa.hibernate.ddl-auto=validate` espera que el esquema exista y sea compatible.
- El topic por defecto para creacion de pedidos es `order.created`.

