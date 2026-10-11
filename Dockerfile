
FROM eclipse-temurin:8-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/smartBusTrackinggg-0.0.1-SNAPSHOT.jar"]
