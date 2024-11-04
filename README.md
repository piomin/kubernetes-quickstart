# Demo Java Apps for Kubernetes [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

In this repository, you will find the simple Java apps to run on Kubernetes for a particular scenario.
Currently, there are apps for:
- Kafka (`kafka` directory)

In order to easily run the apps directly from the source you need to install `skaffold`.

## Articles

I'm using those apps in the several articles on my blog. Here's the list:
1. How to run and manage Kafka on Kubernetes with the Strimzi operator. We run two simple apps for sending messages (`kafka/producer`) and receiving them for Kafka topic (`kafka/consumer`). A detailed guide may be found in the following article: [Apache Kafka on Kubernetes with Strimzi](https://piotrminkowski.com/2023/11/06/apache-kafka-on-kubernetes-with-strimzi/)
2. How to use JKube to generate Kubernetes resources and build images. A detailed guide may be found in the following article: [Spring Boot on Kubernetes with Eclipse JKube](https://piotrminkowski.com/2024/10/03/spring-boot-on-kubernetes-with-eclipse-jkube/)


## Usage

In order to run the example apps you need to have:
- JDK17+
- Maven
- [Skaffold](https://skaffold.dev/)
- Kubernetes cluster

### Apache Kafka

Go to the `kafka` directory. It contains to sample apps: `producer` and `consumer`.

To run them on Kubernetes just execute:
```shell
skaffold run --tail 
```

You can also automatically create and configure Kafka based on Strimzi by activating the `kafka-strimzi` profile:
```shell
skaffold run --tail -p kafka-strimzi 
```
However, first you need to install Strimzi, e.g. using its Helm chart:
```shell
helm repo add strimzi https://strimzi.io/charts
helm install strimzi-kafka-operator strimzi/strimzi-kafka-operator 
```