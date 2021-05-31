FROM openjdk:8

COPY ./target/castexski-client-1.0-SNAPSHOT.jar ./castexski-client.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "castexski-client.jar"]

CMD ["localhost", "8080"]
