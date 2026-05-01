FROM maven:3.9.9-eclipse-temurin-21 as builder

WORKDIR /app

COPY pom.xml .

RUN sed -i 's|</mirrors>|  <mirror>\n      <id>aliyun</id>\n      <mirrorOf>*</mirrorOf>\n      <url>https://maven.aliyun.com/repository/public</url>\n    </mirror>\n</mirrors>|' /usr/share/maven/conf/settings.xml

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

#FROM openjdk:21-jdk as runner
FROM eclipse-temurin:21-jre as runner

WORKDIR /app

COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4004

ENTRYPOINT ["java", "-jar", "app.jar"]