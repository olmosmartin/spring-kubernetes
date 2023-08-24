FROM amazoncorretto:17-alpine as builder

WORKDIR /app/ms-cursos
COPY ./pom.xml /app
COPY ./ms-cursos/.mvn ./.mvn
COPY ./ms-cursos/mvnw .
COPY ./ms-cursos/pom.xml .

#esto solo descarga las dependencias sin ejecutar test y main
#RUN ./mvnw clean package -Dmaven-test.skip -Dmaven-main.skip -Dspring.boot.repackage.skip && rm -r ./target/
#esto hace casi lo mismo q la de arriba
RUN ./mvnw dependency:go-offline
COPY ./ms-cursos/src ./src

RUN ./mvnw clean package -DskipTest

FROM amazoncorretto:17-alpine
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/ms-cursos/target/ms-cursos-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8002
#creo la variable de ambiente port por defecto en 8001, pero se puede cambiar en el comando del run
#con -e o --env-file y la ruta del archivo
ENV PORT $PORT_APP
EXPOSE $PORT
#entrypoint es mas estricto que cmd y no te deja sobreescribir la linea de entrada por ejemplo
#cuando haces un run y pasas un comando el entrypoint lo va aignorar y usar el comando del entrypiont,
#en cambio cmd lo sobreescribe por el comando q se le pasa
#ENTRYPOINT ["java", "-jar", "ms-cursos-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "ms-cursos-0.0.1-SNAPSHOT.jar"]
