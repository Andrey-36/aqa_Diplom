# запуск контейнера

> docker-compose up --build

# запуск jar файла

## для mysql

>java "-Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app" -jar artifacts/aqa-shop.jar

## для postgresql

>java "-Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app" -jar artifacts/aqa-shop.jar

# запуск тестов

## для mysql

>./gradlew clean test allureServe "-Ddb.url=jdbc:mysql://localhost:3306/app"

## для postgresql

>./gradlew clean test allureServe "-Ddb.url=jdbc:postgresql://localhost:5432/app"