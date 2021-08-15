[![CircleCI](https://circleci.com/gh/pictet-technologies-open-source/resilience4j/tree/main.svg?style=shield&circle-token=906d4fb4d7f57f26e052dc532dc890b39ccbdad2)](https://circleci.com/gh/pictet-technologies-open-source/resilience4j)
[![CodeFactor](https://www.codefactor.io/repository/github/pictet-technologies-open-source/resilience4j/badge?s=2ef6a9028d54bea95cbdb36e92a5b6bcd21b9f53)](https://www.codefactor.io/repository/github/pictet-technologies-open-source/resilience4j)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Made with Love by Pictet Technologies](https://img.shields.io/badge/Made%20with%20love%20by-Pictet%20Technologies-ff3434.svg)](https://pictet-technologies.com/)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/alexandre-jacquot-34bb7b5)


# resilience4j

This project aims at demonstrating how to use resillience4j in a spring boot app.


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

- Web application : http://localhost:4200/#/
- Actuator : http://localhost:8080/actuator/
- Prometheus : http://localhost:9090/classic/targets#job-store-app
- Grafana : http://localhost:3000/?orgId=1
- Swagger : http://localhost:8080/swagger-ui/#/currency-controller/getExchangeRatesUsingGET

## Call the application using Apache HTTP Server benchmarking tool

```
$ ab -n x -c y 'http://127.0.0.1:4200/items?currency=GBP'
```

where x is the number of calls
and y the number of concurrent users  


## References

- Resilience4j doc : https://resilience4j.readme.io/docs
- Resilience4j repository : https://github.com/resilience4j/resilience4j
- Prometheus : https://prometheus.io/
- Grafana : https://grafana.com/
- Exchange rate free api : https://exchangerate.host/#/
