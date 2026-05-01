What I learn in this project

- Production-Ready Patient Management System with Microservices
- Java, Spring boot & docker microservices
- Postgres Database
- Load Balancers & API Gateways to route requests
- Event driven communication - Kafka
- Real time communication - rest & grpc
- Authenticate Users & Secure APIs using bearer tokens
- Integration test
- Deploying to AWS using localstack and infrastructure as code

Introduction API Gateways

- An API Gateway acts like as a single entry point for clients to interact with multiple microservices
- Routes requests to the microservices, hiding the internal addresses from client
- Handles concerns like authentication, authorization, logging, monitoring, rate limiting and caching centrally that are common to all microservices

![img.png](images/img.png)

If not use API Gateway
![img_1.png](images/img_1.png)

If use API Gateway
![img.png](images/img_2.png)

Auth Service
![image.png](images/img_3.png)

Testing
![image.png](images/img_4.png)

opencode -s ses_227c0a716ffe226Pd5Qu4dp9OC
  