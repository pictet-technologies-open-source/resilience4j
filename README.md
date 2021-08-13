# resilience4j
Resilience4j showcase

## Compile the application

```
$ mvn clean install
```

## Start the application

### Prometheus and Grafana

```
$ cd docker
$ docker-compose up
```

In Grafana you will have to create a new dashboard and import the file docker/grafana.json


### Start the exchange rate webservice

```
$ cd resilience4j-exchange-rate-proxy
$ mvn spring-boot:run
```

### Start the resilient store webservice

```
$ cd resilience4j-store-app
$ mvn spring-boot:run
```

### Start the UI

```
$ cd resilience4j-store-ui
$ npm start
```

## URLs

Web application : http://localhost:4200/#/
Actuator : http://localhost:8080/actuator/
Prometheus : http://localhost:9090/classic/targets#job-store-app
Grafana : http://localhost:3000/?orgId=1
Swagger : http://localhost:8080/swagger-ui/#/currency-controller/getExchangeRatesUsingGET

## Call the application using Apache HTTP Server bechnmarking tool

```
ab -n x -c y 'http://127.0.0.1:4200/items?currency=GBP'
```

where x is the number of call
and y the number of concurrent users  
