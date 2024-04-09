# Homework For TQS

# Setting up
```
docker-compose up -d (to run in background the mysql database)
docker-compose Down
docker-compose Down -v # down and delete volumes
```

# Running the tests
Integration tests were tested with intellij support, database must be running and default data must be loaded.

```
./mvnw clean test (to run the unit tests)
```

# Running the application
```
./mvnw spring-boot:run
```

# Accessing the application
[Home Page](http://localhost:8080/home.html)

[Reservation Page](http://localhost:8080/reservation.html)


# Demo Videos
[One Drive File](https://uapt33090-my.sharepoint.com/:f:/g/personal/rafael_vilaca_ua_pt/El_4VCv3DTJGqBWccLKZFKABXMIeNonwwT26dvV9EkU2ow?e=05i8MB)
