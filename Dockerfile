#FROM openjdk:8
#
#MAINTAINER octopus
#
#RUN mkdir -p /spider-flow
#
#WORKDIR /spider-flow
#
#EXPOSE 8088
#
#ADD ./spider-flow-web/target/spider-flow.jar ./
#
#CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar spider-flow.jar


FROM openjdk:8u111-jdk-alpine

RUN mkdir -p /spider-flow

WORKDIR /spider-flow

EXPOSE 8088

ADD ./spider-flow-web/target/spider-flow.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar spider-flow.jar
