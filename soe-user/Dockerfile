FROM adoptopenjdk/openjdk11:jre11u-nightly
VOLUME /tmp
EXPOSE 8001
COPY target/soe-user.jar soe-user.jar
ENTRYPOINT ["java","-jar","/soe-user.jar"]
MAINTAINER LYW <1716224950@qq.com>