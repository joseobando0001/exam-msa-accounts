FROM gradle:jdk17-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle gradle

COPY src/ src/


RUN gradle build

RUN gradle --no-daemon assemble

FROM amazoncorretto:17.0.14-alpine3.21

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/app.jar"]