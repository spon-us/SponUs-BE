# Eclipse Temurin OpenJDK 17 이미지를 사용
FROM eclipse-temurin:17-jdk
ARG JAR_FILE=api/build/libs/*.jar

COPY ${JAR_FILE} app.jar

# Redis 및 supervisord 설치
RUN apt-get update && \
    apt-get install -y redis-server supervisor && \
    rm -rf /var/lib/apt/lists/*

# supervisord 설정 파일 복사
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# 포트 노출
EXPOSE 8080 6379

# supervisord를 사용하여 애플리케이션과 Redis 실행
CMD ["/usr/bin/supervisord"]
