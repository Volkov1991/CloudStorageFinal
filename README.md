## Описание проекта

Создано приложение в виде REST-сервиса, которое предоставляет интерфейс для загрузки файлов и отображения списка уже загруженных файлов пользователя. Готовое веб-приложение [(FRONT)](https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-frontend) подключается к этому сервису без необходимости дополнительных доработок. Сервис поддерживает следующие функции:

* Просмотр списка файлов
* Добавление новых файлов
* Удаление файлов
* Переименование файлов
* Авторизация пользователей.

При авторизации система проверяет введенные логин и пароль, сверяя их с данными в базе. Пароль хранится в зашифрованном виде. Если авторизация прошла успешно, генерируется JWT-токен, который предоставляет доступ к работе с файлами в течение определенного времени. Стоит отметить, что после успешной авторизации все запросы с FRONT отправляются с header "auth-token", а в качестве значения содержится токен. При выходе пользователя из системы (logout) токен становится недействительным, и пользователь перенаправляется на страницу входа.

Подключение к API осуществляется по порту 8080.

## Описание реализации
* Приложение разработано с использованием Spring Boot;
* Использована база данных PostgreSQL;
* Использован сборщик пакетов Maven;
* Использована система управления миграциями Liquibase;
* Для запуска используется docker, docker-compose;
* Код покрыт unit тестами с использованием mockito;
* Добавлены интеграционные тесты с использованием testcontainers;
* JJWT
