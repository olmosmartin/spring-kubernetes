ARG MS_NAME=ms-usuarios

FROM amazoncorretto:17-alpine as builder

ARG MS_NAME

WORKDIR /app/$MS_NAME
COPY ./pom.xml /app
COPY ./$MS_NAME/.mvn ./.mvn
COPY ./$MS_NAME/mvnw .
COPY ./$MS_NAME/pom.xml .

#esto solo descarga las dependencias sin ejecutar test y main
#RUN ./mvnw clean package -Dmaven-test.skip -Dmaven-main.skip -Dspring.boot.repackage.skip && rm -r ./target/
#esto hace casi lo mismo q la de arriba
RUN ./mvnw dependency:go-offline
COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTest

FROM amazoncorretto:17-alpine
WORKDIR /app
RUN mkdir ./logs
ARG MS_NAME
ARG TARGET_FOLDER=/app/ms-usuarios/target
COPY --from=builder $TARGET_FOLDER/$MS_NAME-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8001
#creo la variable de ambiente port por defecto en 8001, pero se puede cambiar en el comando del run
#con -e o --env-file y la ruta del archivo
ENV PORT $PORT_APP
EXPOSE $PORT
#entrypoint es mas estricto que cmd y no te deja sobreescribir la linea de entrada por ejemplo
#cuando haces un run y pasas un comando el entrypoint lo va aignorar y usar el comando del entrypiont,
#en cambio cmd lo sobreescribe por el comando q se le pasa
#ENTRYPOINT ["java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]
