* running in local development mode

* run these two command in terminal;
  docker run --detach -it --name rabbitmq-server -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:4-management
  docker run -p 6379:6379 --name redis-server -d redis:7

* add his line to the environment variables if you are running with an IDE;
    - SPRING_PROFILES_ACTIVE=dev

* run the application in Terminal with Maven;

- Linux / macOS
  SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run

- Windows (Command Prompt)
  set SPRING_PROFILES_ACTIVE=dev
  mvn spring-boot:run

- Windows (PowerShell)
  $env:SPRING_PROFILES_ACTIVE="dev"
  mvn spring-boot:run

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

* running with docker compose

* create docker image command in terminal;
  mvn compile jib:dockerBuild

* run application in terminal;
  docker compose up

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

* Testing services with Postman;

- import "exchange-app.postman_collection.json" in root folder to postman
- You need a JWT token to run backend services. You can use "login" endpoint with existing user or also can create a new user with "register" endpoint.
- Services need a Bearer JWT token in header.
- You should run first "notify/stream" for server send event notifications. The notifications are pushed from server to postman asynchronously.
- "account", "account/balance", "account/deposit", "account/deposit" and "exchange" endpoints can be tested with postman.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

* CurrencyUtilTests JUnit and ExchangeServiceTest Mockito tests are provided.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
