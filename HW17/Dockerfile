FROM java:8

ENV PROJECT_DIR="/opt/library-app"
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./target/hw17.jar $PROJECT_DIR/hw17.jar

EXPOSE 8877

CMD ["java", "-jar", "/opt/library-app/hw17.jar"]
