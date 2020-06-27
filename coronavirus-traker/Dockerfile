FROM openjdk:12-alpine

COPY target/coronavirus-tracker.war /coronavirus.war

EXPOSE 8085

CMD ["java","-jar","/coronavirus.war"]