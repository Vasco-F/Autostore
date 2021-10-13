FROM tomcat:9.0-jdk16
ADD autostore/target/autostore-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY wait-for-it.sh .
RUN apt-get update
RUN apt-get install -y openssl
EXPOSE 8080
CMD ["chmod", "+x", "wait-for-it.sh"]
CMD ["./wait-for-it.sh", "mysql-storage:3306"]
CMD ["catalina.sh","run"]
