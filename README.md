# Запуск контейнера #

> docker-compose up --build

# Запуск jar файла #

## для mysql ##

>java "-Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app" -jar artifacts/aqa-shop.jar

## для postgresql #

>java "-Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app" -jar artifacts/aqa-shop.jar


# Запуск тестов #

## для mysql ##

>./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" allureServe

## для postgresql ##

>./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app" allureServe


# Генерация отчетов Allure #

## команды для генерации: ## 

> ./gradlew allureReport 
> 
> ./gradlew allureServe

## для завершения работы allureServe ##

> Выполнить команду: Ctrl + С