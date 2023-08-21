FROM amazoncorretto:17-alpine as builder

WORKDIR /app/ms-usuarios
COPY ./pom.xml /app
COPY ./ms-usuarios/.mvn ./.mvn
COPY ./ms-usuarios/mvnw .
COPY ./ms-usuarios/pom.xml .

#esto solo descarga las dependencias sin ejecutar test y main
#RUN ./mvnw clean package -Dmaven-test.skip -Dmaven-main.skip -Dspring.boot.repackage.skip && rm -r ./target/
#esto hace casi lo mismo q la de arriba
RUN ./mvnw dependency:go-offline
COPY ./ms-usuarios/src ./src

RUN ./mvnw clean package -DskipTest

FROM amazoncorretto:17-alpine
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/ms-usuarios/target/ms-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
#entrypoint es mas estricto que cmd y no te deja sobreescribir la linea de entrada por ejemplo
#cuando haces un run y pasas un comando el entrypoint lo va aignorar y usar el comando del entrypiont,
#en cambio cmd lo sobreescribe por el comando q se le pasa
#ENTRYPOINT ["java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]
