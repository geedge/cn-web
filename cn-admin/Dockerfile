FROM java:8
EXPOSE 8080

VOLUME /tmp
ADD cn-admin.jar /app.jar
RUN yum -y install bind-utils \
    && bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
