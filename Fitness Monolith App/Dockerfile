FROM eclipse-temurin:25-jre

WORKDIR /app

# COPY *.jar file in target folder , Copy it into the container and rename it as app.jar.
COPY target/*.jar app.jar

EXPOSE 8080

# RUN this command when container start
ENTRYPOINT ["java","-jar","app.jar"]

#   ["java","-jar","app.jar"] → tells Docker to launch the JVM (java) and run the JAR file (app.jar) inside the container.
