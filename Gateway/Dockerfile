FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN MAVEN_OPTS="-Dhttps.protocols=TLSv1.2" mvn clean package -DskipTests || \
    (sleep 5 && MAVEN_OPTS="-Dhttps.protocols=TLSv1.2" mvn clean package -DskipTests) || \
    (sleep 5 && MAVEN_OPTS="-Dhttps.protocols=TLSv1.2" mvn clean package -DskipTests)

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
