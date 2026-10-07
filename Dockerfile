# Etapa 1: Construcción
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargar dependencias (mejora el caché de Docker)
RUN ./mvnw dependency:go-offline

# Copiar el código fuente y compilar
COPY src src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar solo el .jar compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente un puerto, expondremos el estándar por defecto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]