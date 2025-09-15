FROM maven:3.9-eclipse-temurin-24
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN cp target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080