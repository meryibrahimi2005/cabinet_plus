# Utiliser Java 21
FROM eclipse-temurin:21-jdk

# Définir le répertoire de travail
WORKDIR /app

# Copier tout le projet
COPY . /app

# Installer Maven
RUN apt-get update && apt-get install -y maven

# Build avec Maven en générant un fat JAR
RUN mvn clean package -DskipTests \
    && cp target/cabinetPlus-1.0-SNAPSHOT-shaded.jar /app/app.jar

# Commande pour lancer le fat JAR
CMD ["java", "-jar", "app.jar"]
