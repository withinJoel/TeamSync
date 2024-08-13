FROM openjdk:22-jdk
WORKDIR /app
COPY . /app
RUN ./gradlew build
