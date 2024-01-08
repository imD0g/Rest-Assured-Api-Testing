package Examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * In this class we will be looking at how we can use the RequestSpecification and ResponseSpecification classes.
 *
 * These will allow us to define preconditions that we can use in our tests, for example:
 *
 * RequestSpecification:
 * - Base URI
 * - Authentication
 * - Headers
 *
 * ResponseSpecification:
 * - Status code
 * - Content type
 * - Headers
 *
 * This allows us to reuse the same preconditions in multiple tests, which makes our test suite more maintainable.
 */
public class C_ResponseAndRequestSpecApiCalls {

    /**
     * RequestSpecification:
     */

    // Create an instance of RequestSpecification that we can use in our tests.
    private static RequestSpecification requestSpec;

    // Create the RequestSpecification
    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    // Now we can use the RequestSpecification in our tests.
    // We just need to define the endpoint and parameters.
    @Test
    public void requestSpecExample__checkStatusCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().
                assertThat().
                statusCode(200);
    }

    /**
     * ResponseSpecification:
     */

    // Create an instance of ResponseSpecification that we can use in our tests.
    private static ResponseSpecification responseSpec;

    // Create the ResponseSpecification
    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType("application/json").
                build();
    }

    // Now we can use the ResponseSpecification in our tests.
    @Test
    public void responseSpecExample_checkStatusCode_expect200AndJson() {
        given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("'country abbreviation'", equalTo("US"));
    }
}
