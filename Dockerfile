FROM gradle:jdk10 as builder
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
RUN gradle build

FROM openjdk:8
VOLUME /tmp

EXPOSE 8080
COPY --from=builder /home/gradle/build/libs/datastore-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-jar","datastore-0.0.1-SNAPSHOT.jar"]