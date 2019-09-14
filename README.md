# Docker Compose with Spring Boot, Hibernate OGM, MongoDB

## What you'll build
- A simple Spring Boot application with MongoDB running inside Docker containers.
- Simple RESTful service for working with shop items.

## What you'll need
- Docker CE

## Stack
- Docker
- Java
- Spring Boot
- MongoDB
- Hibernate OGM
- Maven

## Run
- Clone repo `git clone https://github.com/DimaRyazanov/shopitem`
- Run command `docker-compose up`

## Commands
- Create new items
`curl -X POST http://localhost:8080/api/item/ -d '{"name":"your name", "description":"you desc", "options":{ "key_1":"value_1", ... , "key_n":"value_n" }}' -H "Content-Type: application/json"`
- Get item by id
`curl -X GET http://localhost:8080/api/item/{you_id}`
- Get items by name (Strict compliance)
`curl -X GET http://localhost:8080/api/item/filter?name={you_name}`
- Get items by key and value
`curl -X GET http://localhost:8080/api/item/filter/option?key={you_key}&name={you_value}`

