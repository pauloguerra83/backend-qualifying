FROM adoptopenjdk/openjdk11:alpine-jre

# adiciona o jar independente do nome da aplicacao modificada
ARG JAR_FILE=target/*.jar

#expoe a porta de uso
EXPOSE 8080

WORKDIR /opt/app

# copia *..jar para /opt/app/app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]