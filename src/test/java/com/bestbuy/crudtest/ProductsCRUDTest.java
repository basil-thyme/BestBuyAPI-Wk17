package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBaseProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBaseProducts {
    static String name = "My Electronics " + TestUtils.getRandomName();
    static String type = "Gadgets" + TestUtils.getRandomName();
    static Double price = 555.99;
    static Integer shipping = 5;
    static String upc = "039393943943";
    static String description = "Electronics Gadgets under one roof " + TestUtils.getRandomValue();
    static String manufacturer = "Google";
    static String model = TestUtils.getRandomName();
    static String url = "https://www.bestbuy.com/site/apple-macbook-pro-13-display-with-touch-bar-intel-core-i5-16gb-memory-512gb-ssd-space-gray/6287726.p?skuId=6287726";
    static String image = "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6287/6287726_sd.jpg;maxHeight=640;maxWidth=550";
    static int productId;

    @Test
    public void test001() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .when()
                .post("/products");
        productId = response.then().contentType(ContentType.JSON).extract().path("id");
        response.then().statusCode(201);

        System.out.println("idNo = " + productId);
        response.prettyPrint();

    }

    @Test
    public void test002() {
        Response response = given()
                .when()
                .get("/products" + "/" + productId);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void test003() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name + "_Updated");
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .patch("/products" + "/" + productId);
        response.prettyPrint();
        response.then().log().all().statusCode(200);
    }

    @Test
    public void test004() {
        Response response = given()
                .when()
                .get("/products" + "/" + productId);
        response.then().statusCode(200);
        response.prettyPrint();
        String actualName = response.then().extract().body().path("name");
        Assert.assertEquals("Name is not updated", name + "_Updated", actualName);
        System.out.println("ActualName is: " + actualName);
        System.out.println("ExpectedName is: " + name + "_Updated");

    }

    @Test
    public void test005() {
        Response response = given()
                .when()
                .delete("/products" + "/" + productId);
        response.prettyPrint();
        response.then().log().all().statusCode(200);

    }


}
