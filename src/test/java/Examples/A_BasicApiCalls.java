package Examples;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;


/**
 * This class contains examples of basic API calls using Rest Assured.
 * The Rest Assured library uses a BDD-style approach to writing tests.
 * We can use the given(), when() and then() methods to construct our tests.
 */
public class A_BasicApiCalls {

    /**
     * This test case will perform a GET request to the /us/90210 endpoint.
     * It will then check whether the response has HTTP status code 200 (OK).
     */
    @Test
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .assertThat()
                .statusCode(200);
    }

    /**
     * This test case will perform a GET request to the /us/90210 endpoint.
     * It will check whether the Content-Type header is set to application/json.
     */
    @Test
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    /**
     * This test case will perform a GET request to the /us/90210 endpoint.
     * It will then log the request and response details.
     */
    @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails() {
        given()
                .log()
                .all()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .body();
    }
}
