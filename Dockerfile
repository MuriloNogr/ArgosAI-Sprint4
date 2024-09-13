# Etapa 1: Compilar o projeto
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos de configuração Maven e o código-fonte para o container
COPY pom.xml .
COPY src ./src

# Compilar o projeto e gerar o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação
FROM eclipse-temurin:17-jdk-jammy

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado na etapa de build para o container
COPY --from=build /app/target/ArgosAI-Sprint3-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta em que a aplicação irá rodar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]