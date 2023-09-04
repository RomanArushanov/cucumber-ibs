package org.steps;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.Когда;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Owner("Арушанов Роман")
@Severity(SeverityLevel.CRITICAL)
@Tag("DBT_1")
@DisplayName("Добавление продукта в список товаров")
public class AddingExoticProductBDTestSteps {
    private static JdbcTemplate jdbcTemplate;
    private static BasicDataSource dataSource = null;
    @Step
    @Допустим("Соединение с базой данных установленно")
    public static void init() {
        Properties dataBaseProperties = new Properties();

        try {
            InputStream resouerceAsStream = AddingExoticProductBDTestSteps.class.getClassLoader()
                    .getResourceAsStream("database.properties");
            if (resouerceAsStream != null) {
                dataBaseProperties.load(resouerceAsStream);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось загрузить файл database.properties", e);
        }

        dataSource = new BasicDataSource();
        dataSource.setUrl(dataBaseProperties.getProperty("db.url"));
        dataSource.setUsername(dataBaseProperties.getProperty("db.user"));
        dataSource.setPassword(dataBaseProperties.getProperty("db.password"));

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Step("Добавляем запись в базу данных")
    @Когда("Добавляем запись в базу данных")
    public void addProductById(Map<String, String> dataTable) {
        jdbcTemplate.update(
                "INSERT INTO FOOD VALUES (?, ?, ?, ?)",
                Integer.parseInt(dataTable.get("Номер")), dataTable.get("Наименование"),
                dataTable.get("Тип"), Boolean.valueOf(dataTable.get("Экзотичность")));
    }

    @Step("Добавляем запись в базу данных")
    @Когда("Проверяем, что запись появилась в базе данных {string} {string} {string} {string}")
    public void checkProduct(String foodId, String foodName, String foodType, String foodExotic) {
        Product checkResultProduct = new Product(Integer.parseInt(foodId),
                foodName, foodType, Boolean.valueOf(foodExotic));

        Assertions.assertEquals(checkResultProduct, selectProduct(Integer.parseInt(foodId)),
                "Запись с ID " + foodId + " отсутствует");
    }

    @Step("Добавляем запись в базу данных")
    @Когда("Удаляем запись {string}")
    public void deleteProductById(String foodId) {
        jdbcTemplate.update(
                "DELETE FROM FOOD WHERE FOOD_ID = ?;",
                foodId);
    }

    @Step("Добавляем запись в базу данных")
    @Когда("Проверяем, что записи больше нет в таблице  {string} {string} {string} {string}")
    public void checkProductAfter(String foodId, String foodName, String foodType, String foodExotic) {
        Product checkResultProduct = new Product(Integer.parseInt(foodId),
                foodName, foodType, Boolean.valueOf(foodExotic));

        Assertions.assertNotEquals(checkResultProduct, selectProduct(Integer.parseInt(foodId)),
                "Запись с ID " + foodId + " отсутствует");
    }

    public Product selectProduct(int foodId) {
        String sql = "SELECT * FROM FOOD WHERE FOOD_ID = ?";
        RowMapper<Product> rowMapper = (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("FOOD_ID"));
            product.setName(rs.getString("FOOD_NAME"));
            product.setType(rs.getString("FOOD_TYPE"));
            product.setExotic(rs.getBoolean("FOOD_EXOTIC"));
            return product;
        };
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, foodId);
        } catch (Exception ex) {
            return new Product();
        }
    }
}
