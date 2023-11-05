FROM gradle:7.2-jdk11

WORKDIR /bookstore-ingress
COPY . .
RUN gradle clean build

CMD gradle bootRun
