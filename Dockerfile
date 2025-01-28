FROM gradle:8.7-jdk21

WORKDIR /app

COPY . .

RUN chmod +x app/gradlew

RUN ./gradlew installDist
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/install/app /app
CMD ["./bin/app"]