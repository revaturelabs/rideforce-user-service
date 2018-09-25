FROM openjdk:8-jdk-alpine
COPY target/rideforce-user-service.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/rideforce-user-service.jar"]