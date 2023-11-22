package com.edw.controller;

import com.edw.config.WiremockConfig;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

/**
 * <pre>
 *     com.edw.controller.IndexControllerTest
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 22 Nov 2023 12:13
 */
@QuarkusTest
@QuarkusTestResource(WiremockConfig.class)
public class IndexControllerTest {

    @Test
    public void testIndex() {
        given()
            .when()
                .get("/?name=edwin")
            .then()
                .statusCode(200)
                .body("hello", isA(String.class))
                .body("hello", equalTo("edwin"))
            .log().all();
    }

    @Test
    public void testCall() {
        given()
            .when()
                .get("/call")
                .then()
            .statusCode(200)
                .body("hello", isA(String.class))
                .body("hello", equalTo("mock"))
            .log().all();
    }

    @Test
    public void testApiPost() {
        given()
            .when()
                .get("/post?id=1")
            .then()
                .statusCode(200)
                .body("id", isA(Integer.class))
                .body("id", equalTo(1))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
            .log().all();

        given()
            .when()
                .get("/post?id=2")
            .then()
                .statusCode(200)
                .body("id", isA(Integer.class))
                .body("id", equalTo(2))
                .body("title", equalTo("post 2"))
            .log().all();
    }
}
