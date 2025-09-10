#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#COPY target/petition.jar app.jar
##VOLUME /tmp
##COPY *.jar Petition.jar
#ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
## Replace with a non-root user to avoid running the container with excessive privileges
#RUN addgroup -S appgroup && adduser -S appuser -G appgroup
#USER appuser
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar Petition.jar" ]
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ../../applications/app-service/build/libs/Petition.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
