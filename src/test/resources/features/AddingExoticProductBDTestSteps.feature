# language: ru
@BD
Функция: Добавление товара в базу данных

  Предыстория:
    * Соединение с базой данных установленно

  Структура сценария:
    * Добавляем запись в базу данных
      | Номер        | <productId>     |
      | Наименование | <productName>   |
      | Тип          | <productType>   |
      | Экзотичность | <productExotic> |
    * Проверяем, что запись появилась в базе данных "<productId>" "<productName>" "<productType>" "<productExotic>"
    * Удаляем запись "<productId>"
    * Проверяем, что записи больше нет в таблице  "<productId>" "<productName>" "<productType>" "<productExotic>"

    Примеры:
      | productId | productName | productType | productExotic |
      | 100       | Огурец      | VEGETABLE   | false         |
      | 1000      | Apple       | FRUIT       | true          |
      | 10000     | Gurke       | VEGETABLE   | true          |
      | 99        | 胡蘿蔔       | VEGETABLE   | false         |
      | 101       | موز         | FRUIT       | true          |
