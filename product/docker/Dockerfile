FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY ../pom.xml .
COPY ../src src/
RUN ["mvn", "--no-transfer-progress", "package"]

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/*.jar product.jar
CMD ["java", "-jar", "product.jar"]
