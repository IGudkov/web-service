
# SWAGGER
В проект добавлена зависимость на openapi, что позволяет
получать описание API микросервиса в формате .json по адресу
`http://<server.url>/v3/api-docs`
При необходимости получения описания в формате .yaml выполнить запрос на адрес
`http://<server.url>/v3/api-docs.yaml`
Описание в формате yml можно посмотреть в
`web-service-api-specs\src\main\resources\openapi\specs.yaml`
Локально можно перейти в swagger по адресу `http://localhost:8081/swagger-ui.html`

# LIQUIBASE
В `task5_web_service\web-service-api\src\main\resources\changelog` добавлены скрипты
по созданию таблиц + добавлению тестовых данных, необходимых для тестирования

# DOCKER
Создание контейнеров выполняется из [compose.yml](compose.yml)

# Модульные тесты
Тесты реализованы через запуск контейнера с postgres:14.9-alpine3.18
Если Docker не установлен, то тесты будут пропущены
Входные данные по созданию ЭП и ПР берутся из файлов .json, расположенных в
`web-service-api/src/main/resources/json`

# Тестирование через DOCKER + SWAGGER
Результаты тестирования расположены в папке [test_result](test_result)
