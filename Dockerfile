FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn

RUN chmod +x mvnw

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -B

COPY src src

RUN --mount=type=cache,target=/root/.m2 \
    --mount=type=cache,target=/app/target \
    ./mvnw package -DskipTests -B && \
    cp target/*.jar /app/app.jar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/app.jar app.jar
COPY .env .
COPY images images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
