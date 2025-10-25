#Dockerfile para deploy no render 

# usa imagem maven para compilar o projeto 
FROM maven:3.9.9-eclipse-temurin-21 AS build

# define pasta de trabalho dentro do container
WORKDIR /app

# copia da raiz do meu projeto pra raiz do projeto do docker (copia do windows -> linux)
COPY . .

# dividir a imagem em duas etapas como se fosse CI/CD: 1 - cria o jar 2 - reutiliza o jar 
# compila e gera o JAR (pula testes)
RUN mvn clean package -DskipTests

# apenas o java runtime 
FROM eclipse-temurin:21-jre

# define pasta de trabalho dentro do container
WORKDIR /app 

# copia o jar na etapa anterior
# copia da imagem que eu criei tudo que estiver extensão .jar para o arquivo app.jar 
COPY --from=build /app/target/*.jar app.jar

# informa que a aplicação usa a porta 8080
EXPOSE 8080

# comandos que vão ser executados no final, ou seja, comandos para rodar a aplicação 
ENTRYPOINT ["java", "-jar", "app.jar"]

# a imagem gerada pode ser enviada para o dockerhub e ser usada em qualquer SO;
# pulamos os testes, mas em teoria é pra funcionar em servidores de homologação 

