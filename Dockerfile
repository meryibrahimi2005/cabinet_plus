FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
CMD ["java", "--module-path", "/root/.m2/repository/org/openjfx/javafx-controls/21.0.2/:/root/.m2/repository/org/openjfx/javafx-fxml/21.0.2/:/root/.m2/repository/org/openjfx/javafx-base/21.0.2/:/root/.m2/repository/org/openjfx/javafx-graphics/21.0.2/", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/cabinetPlus-1.0-SNAPSHOT.jar"]