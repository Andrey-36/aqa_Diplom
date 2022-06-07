## [Plan.md](documents/Plan.md)
## [Report.md](documents/Report.md)
## [Summary.md](documents/Summary.md)

# Клонировать проект из репозитория #

> репозиторий: https://github.com/Andrey-36/aqa_Diplom.git

# Запустить Docker контейнер #

> docker-compose up --build

# Запустить jar файл #

## команда для запуска MySQL ##

>java "-Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app" -jar artifacts/aqa-shop.jar

## команда для запуска PostgreSQL #

>java "-Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app" -jar artifacts/aqa-shop.jar

# Запустить авто-тесты #

## команда для запуска MySQL ##

>./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" allureServe

## команда для запуска PostgreSQL ##

>./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app" allureServe

# Генерация отчетов Allure #

## команды для генерации Allure: ## 

> ./gradlew allureReport
> 
> ./gradlew allureServe

## команда для завершения работы allureServe ##

> Выполнить: Ctrl + С