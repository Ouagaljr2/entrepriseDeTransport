# Utilisation de l'image Java officielle
FROM openjdk:17-jdk-slim

# Copie du fichier JAR dans le conteneur
COPY target/entrepriseDeTransport-0.0.1-SNAPSHOT.jar /app.jar

# Expose le port
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
