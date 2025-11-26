## Orders Example. 

An example of an ordering API where a SAGA orchestrator pattern is applied.
Two endpoints are exposed, one for placing orders and another for order enquiries.

## Execution instructions

First, launch the payments and shipping services:

`java -jar payment-0.1.jar`  This service is listen in 8081 port.

`java -jar shipment-0.1.jar` This service is listen in 8082 port.

## Application Data

There are two users defined in this example:

```
User: jjb
balance: 30.0$
```

```
User: other
balance: 200.0$
```

And exist these products and stock:

```
id: 1
stock: 3
```

```
id: 2
stock: 5
```

Data is stored in a cache memory in each service. If you need reset data, restart the service.

## Endpoints available and Model

To create a new order example:

```
curl --location 'http://localhost:8080/orders/add' \
--header 'Content-Type: application/json' \
--data '{
    "userId": "jjb",
    "product": {
        "id": "1",
        "price": 19.95
    }
}'

Response:

{
    "orderId": "85494aa9-cf82-4461-b82c-e158d1c98759",
    "status": "SHIPPED"
}

```
To find a order:


```
curl --location 'http://localhost:8080/orders/jjb/85494aa9-cf82-4461-b82c-e158d1c98759'

Response:

{
    "orderId": "85494aa9-cf82-4461-b82c-e158d1c98759",
    "userId": "jjb",
    "product": {
        "id": "1",
        "price": 1.95
    },
    "status": "SHIPPED"
}

```



## With Micronaut 4.10.2

- [User Guide](https://docs.micronaut.io/4.10.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)
## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)


