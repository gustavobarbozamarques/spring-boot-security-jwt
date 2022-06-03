FROM gradle:7.4.2-jdk17 as builder
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN gradle build

FROM openjdk:17-slim
ENV VERSION=0.0.1-SNAPSHOT
ENV JWT_ACCESS_TOKEN_EXPIRATION_MINUTES=
ENV JWT_ACCESS_TOKEN_SECRET=
ENV JWT_REFRESH_TOKEN_EXPIRATION_MINUTES=
ENV JWT_REFRESH_TOKEN_SECRET=
CMD mkdir /app
COPY --from=builder /home/src/build/libs/spring-boot-security-jwt-${VERSION}.jar /app.jar
EXPOSE 8080
ENTRYPOINT java -jar /app.jar