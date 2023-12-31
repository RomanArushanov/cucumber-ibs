# language: ru
@all
Функция: Добавление товара в список товаров

  Предыстория:
    * Пользователь открыл браузер
    * Пользователь открыл страницу "http://localhost:8080/food"


  Структура сценария:
    * Проверил текст заголовка "Список товаров"
    * Нажал кнопку 'Добавить'
    * Проверил заголовок окна добавление продукта "Добавление товара"
    * Ввел название в поле 'Наименование'
      | Наименование | <productName> |
    * Проверил, что название ввелось
      | Наименование | <productName> |
    * Выбрал тип
      | Тип | <productType> |
    * Проверил, что выбор применился
      | Тип | <productType> |
    * Нажал на чек-бок 'Экзотический'
    * Проверил, что чек-бокс 'Экзотический' выбран
    * Нажал кнопку 'Сохранить'
    * Проверил, что в таблице появилась запись с новым продуктом
      | Наименование | <productName>      |
      | Тип          | <productType>      |
      | ТипРу        | <productTypeCheck> |
    * Закрыл страницу

    Примеры:
      | productName | productType | productTypeCheck |
      | Яблоко      | FRUIT       | Фрукт            |
      | Gurke       | VEGETABLE   | Овощ             |
      | 胡蘿蔔       | VEGETABLE   | Овощ             |
      | Абрикос     | FRUIT       | Фрукт            |
      | موز         | FRUIT       | Фрукт            |



