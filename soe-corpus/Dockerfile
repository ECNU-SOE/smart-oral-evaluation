FROM adoptopenjdk/openjdk11:jre11u-nightly
VOLUME /tmp
EXPOSE 8002
COPY target/soe-corpus.jar soe-corpus.jar
ENTRYPOINT ["java","-jar","/soe-corpus.jar"]
MAINTAINER LYW <1716224950@qq.com>