# Запуск
1. Установить Docker
2. Запустить команду docker-compose up

# cURL для теста
1. Для добавления аккаунтов
```
curl --location 'localhost:8081/account' \
--header 'Content-Type: application/json' \
--data '{"name":"Vlad"}'
```
2. Для добавления транзакций
```
curl --location 'localhost:8081/transaction' \
--header 'Content-Type: application/json' \
--data '{
"accountFrom": 1,
"accountTo": 2,
"currency": "USD",
"sum": 9.00,
"expenseCategory": "GOODS"
}
'
```
3. Для добавления лимитов
```
curl --location 'localhost:8081/limit' \
--header 'Content-Type: application/json' \
--data '{
"accountId": 1,
"sum": 10.00,
"currency": "USD",
"expenseCategory": "GOODS"
}
'
```
4. Для вывода транзакций с превышенным лимитом
```
curl --location 'localhost:8081/transaction/exceeded'
```