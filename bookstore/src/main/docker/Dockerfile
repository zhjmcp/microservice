#FROM 192.168.118.130:5000/zhaojigang/jdk8:c7_j8
FROM openjdk

ADD bookstore-0.0.1-SNAPSHOT.jar  app.jar

ENV JAVA_HOME /app/jdk1.8.0_151

ENV PATH $PATH:$JAVA_HOME/bin

CMD ["java","-jar","app.jar"]