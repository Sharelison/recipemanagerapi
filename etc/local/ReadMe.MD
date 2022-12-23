## To run locally

1. Build application with: ```mvn clean package``` 
2. Build image with ```docker build . -t recipemanagerapi:latest```
3. Start with docker-compose ```docker-compose up -d```
4. ```curl localhost:8080/api/v1/recipes```
---

#### To run locally without docker

1. Set the environment variables in .env
2. Run with ```mvn spring-boot:run```
