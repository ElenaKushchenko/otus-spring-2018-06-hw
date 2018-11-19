FROM maven:3.6.0 AS build

ENV PROJECT_DIR="/opt/library-app"
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
ADD ./src $PROJECT_DIR/src

RUN mvn clean package


FROM java:8 AS runtime

ENV PROJECT_DIR="/opt/library-app"
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/hw17.jar $PROJECT_DIR/

EXPOSE 8877

CMD ["java", "-jar", "/opt/library-app/hw17.jar"]
