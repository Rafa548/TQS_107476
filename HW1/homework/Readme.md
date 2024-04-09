# CMS-CCT
Homework For TQS
# Setting up
```
docker-compose up -d (to run in background the mysql database)
docker-compose Down
docker-compose Down -v # down and delete volumes
```

# Running the tests
```
./mvnw clean test
```

# Running the application
```
./mvnw spring-boot:run
```

# Accessing the application
```
http://localhost:8080/home.html (to access the home page)
http://localhost:8080/reservation.html (to access the reservation page)
```