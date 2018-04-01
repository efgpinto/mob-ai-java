FROM mob-ai-java:latest
ARG BOT=Bot
ENV type $BOT

RUN mkdir /robot/
ADD multipaint /robot/multipaint
ADD lib/*.jar /robot/lib/
ADD ${type}.java /robot/${type}.java

ENV CLASSPATH=.:lib/gson-2.6.2.jar

WORKDIR /robot/
RUN javac ${type}.java

ENTRYPOINT java $type
