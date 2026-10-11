
FROM eclipse-temurin:8-jdk AS build

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven \
    && mvn clean package -DskipTests

FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=build /app/target/smartBusTrackinggg-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
