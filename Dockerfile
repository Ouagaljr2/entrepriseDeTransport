# Utilisation de l'image Java officielle
FROM openjdk:17-jdk-slim

# Copie du fichier JAR dans le conteneur
COPY target/entrepriseDeTransport-0.0.1-SNAPSHOT.jar /app.jar

# Copier le script SQL d'initialisation
COPY sql/init.sql /docker-entrypoint-initdb.d/init.sql


# Expose le port
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
