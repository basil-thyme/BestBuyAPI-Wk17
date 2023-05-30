package com.bestbuy.crudtest;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBaseStores;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StoresCRUDTest extends TestBaseStores {

    static String name = "PrimUser" + TestUtils.getRandomValue();
    static String type = "BigBox" + TestUtils.getRandomValue();
    static String address = TestUtils.getRandomValue() + " , Random Street";
    static String address2 = "Roaming Street";
    static String City = "London";
    static String state = "England";
    static String zip = "123456";
    static double lat = 54.23569;
    static double lng = -94.563214;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;
    static int allStoresIdSize;


    @Test
    public void test001() {
        // getAll Stores // extract all store id and then size
        Response response = given()
                .when()
                .get("/stores");
        response.then().statusCode(200);

        List<String> sizeOfDataList = response.then().extract().path("data.id");
        int sizeOfData = sizeOfDataList.size();
        System.out.println("The size of the data list is : " + sizeOfData);

        allStoresIdSize = sizeOfData;
        response.prettyPrint();
    }


    @Test
    public void test002() {
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(City);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post("/stores");

        storeId = response.then().contentType(ContentType.JSON).extract().path("id");
        response.then().statusCode(201);

        System.out.println("idNo = " + storeId);
        response.prettyPrint();
    }

    @Test
    public void test003() {
        //getStoreData Of newly generated store
        Response response = given()
                .when()
                .get("/stores");

        List<String> sizeOfDataList = response.then().extract().path("data.id");
        int sizeOofDataAfterPost = sizeOfDataList.size();
        System.out.println("The size of the data list is : " + sizeOofDataAfterPost);

        response.then().statusCode(200);
        int expected = allStoresIdSize;
        //int expected = allStoresIdSize +1;
        int actual = sizeOofDataAfterPost;
        Assert.assertEquals("no of ids did not match.",expected, actual);
        response.prettyPrint();
    }

    @Test
    public void test004() {
        //getStoreData Of newly generated store
        Response response = given()
                .when()
                .get("/stores" + "/" + storeId);
        response.then().statusCode(200);
        response.prettyPrint();
    }


    @Test
    public void test005() {
        //updateStore
        String p1 = "data.findAll{it.id='";
        String p2 = "'}.get(0)";

        name = name + "_updated";
        type = type + "_updated";
        address = address + "updated";
        address2 = address2 + "_updated";

        StorePojo storesPojo = new StorePojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(City);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);

        Response response = given()
                .header("Content-Type", "application/json")
                //   .pathParam("StoreId", storeId)
                .when()
                .body(storesPojo)
                //.put("/stores/8926");
                .put("/stores" + "/" + storeId);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void test006() {
        //asserting all updated fields
        Response response = given()
                .when()
                .get("/stores" + "/" + storeId);
        response.then().statusCode(200);

        String actualName = response.then().contentType(ContentType.JSON).extract().path("name");
        System.out.println("Actual name--->" + actualName);
        String expectedName = name;
        System.out.println("Expected name----> " + expectedName);
        Assert.assertEquals("Updated name is not matching", expectedName, actualName);

        String actualType = response.then().contentType(ContentType.JSON).extract().path("type");
        System.out.println("Actual name--->" + actualType);
        String expectedType = type;
        System.out.println("Expected name----> " + expectedType);
        Assert.assertEquals("Updated type is not matching", expectedType, actualType);

        String actualAddress = response.then().contentType(ContentType.JSON).extract().path("address");
        System.out.println("Actual name--->" + actualAddress);
        String expectedAddress = address;
        System.out.println("Expected name----> " + expectedAddress);
        Assert.assertEquals("Updated address is not matching", expectedAddress, actualAddress);

        String actualAddress2 = response.then().contentType(ContentType.JSON).extract().path("address2");
        System.out.println("Actual name--->" + actualAddress2);
        String expectedAddress2 = address2;
        System.out.println("Expected name----> " + expectedAddress2);
        Assert.assertEquals("Updated address2 is not matching", expectedAddress2, actualAddress2);

        response.prettyPrint();
    }

    @Test
    public void test007() {
        //deleteStore
        Response response = given()
                .when()
                .delete("/stores" + "/" +storeId);
        response.then().statusCode(200);
        response.prettyPrint();
    }



}
