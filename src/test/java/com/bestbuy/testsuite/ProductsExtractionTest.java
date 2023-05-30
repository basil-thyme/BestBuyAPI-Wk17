package com.bestbuy.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductsExtractionTest {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/products")
                .then().statusCode(200);
    }

    // 21) Extract the value of limit
    @Test
    public void test001() {
        int limit = response.extract().path("limit");
        System.out.println("The value of limit is : " + limit);

    }

    // 22) Extract the total
    @Test
    public void test002() {
        int total = response.extract().path("total");
        System.out.println("The value of total is : " + total);

    }

    // 23) Extract the name of 5th product
    @Test
    public void test003() {
        String name = response.extract().path("data[4].name");
        System.out.println("The name of 5th product : " + name);

    }

    // 24) Extract the names of all the products
    @Test
    public void test004() {
        List<String> namesOfAllProducts = response.extract().path("data.name");
        System.out.println("Names of all the products : " + namesOfAllProducts);
    }

    // 25) Extract the productId of all the products
    @Test
    public void test005() {
        List<Integer> allProductId = response.extract().path("data.id");
        System.out.println("productId of all the products : " + allProductId);
    }

    // 26) Print the size of the data list
    @Test
    public void test006() {
        List<Integer> sizeofData = response.extract().path("data.id");
        int size = sizeofData.size();
        System.out.println("The size of the data list: " + size);
    }

    // 27) Get all the value of the product where product name = Energizer - MAX Batteries AA (4-Pack)
    @Test
    public void test007() {
        List<HashMap<String, ?>> values = response.extract().path("data.findAll{it.name == 'Energizer - MAX Batteries AA (4-Pack)'}"); //return list of maps
        System.out.println("The values for product name 'Energizer - MAX Batteries AA (4-Pack)' are: " + values);

    }

    // 28) Get the model of the product where product name = Energizer - N Cell E90 Batteries (2-Pack)
    @Test
    public void test008() {
        List<Double> model = response.extract().path("data.findAll{it.name == 'Energizer - N Cell E90 Batteries (2-Pack)'}.model");
        System.out.println("The model of product name 'Energizer - N Cell E90 Batteries (2-Pack)' is : " + model);

    }

    // 29) Get all the categories of 8th products
    @Test
    public void test009() {
        List<HashMap<String, ?>> allCategories = response.extract().path("data[7].categories");
        System.out.println(" All the categories of 8th products is : " + allCategories);

    }

    // 30) Get categories of the store where product id = 150115
    @Test
    public void test010() {
        List<HashMap<String, ?>> categories = response.extract().path("data.findAll{it.id == 150115}.categories");
        System.out.println(" Categories of the store where product id = 150115 is: " + categories);

    }

    // 31) Get all the descriptions of all the products
    @Test
    public void test011() {
        List<String> descriptionsOfProducts = response.extract().path("data.description");
        System.out.println("All the descriptions of all the products is: " + descriptionsOfProducts);
    }

    // 32) Get id of all the all categories of all the products
    @Test
    public void test012() {
        List<HashMap<String, ?>> allIdAndIdOfCategories = response.extract().path("data.categories.id");
        System.out.println("Id of all the all categories of all the products is: " + allIdAndIdOfCategories);

    }

    // 33) Find the product names Where type = HardGood
    @Test
    public void test013() {
        List<String> productName = response.extract().path("data.findAll{it.type == 'HardGood'}.name");
        System.out.println("The product names Where type = HardGood is: " + productName);

    }

    // 34) Find the Total number of categories for the product where product name = Duracell - AA 1.5V CopperTop Batteries (4-Pack)
    @Test
    public void test014() {
        List<HashMap<String, ?>> totalNumberOfCategories = response.extract().path("data.findAll{it.name == 'Duracell - AA 1.5V CopperTop Batteries (4-Pack)'}.categories");
        int size = totalNumberOfCategories.size();
        System.out.println("The Total number of categories for the product where product name = Duracell - AA 1.5V CopperTop Batteries (4-Pack) is: " + totalNumberOfCategories);
        System.out.println("The Total number of categories for the product where product name = Duracell - AA 1.5V CopperTop Batteries (4-Pack) is: " + size);

    }

    // 35) Find the createdAt for all products whose price < 5.49
    @Test
    public void test015() {
        List<String> createdAt = response.extract().path("data.findAll{it.price < 5.49 }.createdAt");
        System.out.println("CreatedAt for all products whose price < 5.49 is: " + createdAt);

    }

    // 36)Find the name of all categories Where product name = “Energizer - MAX Batteries AA (4-Pack)”
    @Test
    public void test016() {
        List<HashMap<String, ?>> nameOfCategories = response.extract().path("data.findAll{it.name == 'Energizer - MAX Batteries AA (4-Pack)'}.categories.name");
        System.out.println("The name of all categories Where product name = “Energizer - MAX Batteries AA (4-Pack) is: " + nameOfCategories);

    }

    // 37)Find the manufacturer of all the products
    @Test
    public void test017() {
        List<String> manufacturer = response.extract().path("data.manufacturer");
        System.out.println("The manufacturer of all the products are:  " + manufacturer);

    }

    // 38)Find the image of products whose manufacturer is = Energizer
    @Test
    public void test018() {
        List<String> imageOfProducts = response.extract().path("data.findAll{it.manufacturer == 'Energizer'}.image");
        System.out.println("The manufacturer of all the products are:  " + imageOfProducts);

    }

    // 39)Find the createdAt for all categories products whose price > 5.99
    @Test
    public void test019() {
        List<String> createdAt = response.extract().path("data.findAll{it.price > 5.99 }.categories.createdAt");
        System.out.println("CreatedAt for all categories products whose price > 5.99 is : " + createdAt);
    }

    // 40)Find the uri of all the products
    @Test
    public void test020() {
        List<String> uriOfProducts = response.extract().path("data.url");
        System.out.println("The uri of all the products: " + uriOfProducts);


    }
}
