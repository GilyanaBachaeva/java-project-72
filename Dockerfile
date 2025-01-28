FROM gradle:8.7-jdk21

WORKDIR /app

COPY /app .

RUN chmod +x app/gradlew

RUN gradle installDist

CMD ./build/install/app/bin/app