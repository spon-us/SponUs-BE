FROM eclipse-temurin:17-jdk AS builder
WORKDIR application
ARG JAR_FILE=./api/build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jdk
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# 애플리케이션 JAR 파일이 이미 압축이 해제 되었으므로, JarLauncher를 사용하여 애플리케이션을 시작
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]

