# ------------------------------
# Stage 1: Build usando Maven + JDK 17
# ------------------------------
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copiar pom.xml e src
COPY pom.xml .
COPY src ./src

# Build da aplicação, pulando os testes
RUN mvn clean package -DskipTests

# ------------------------------
# Stage 2: Imagem final só com JDK
# ------------------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o jar do stage de build
COPY --from=build /app/target/*.jar app.jar

# Expor porta 8080
EXPOSE 8080

# Comando padrão para rodar a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
