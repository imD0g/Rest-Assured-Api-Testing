package Examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * In this class we will be looking at how we can store data from API calls as variables.
 * <p>
 * For example we can store an authentication token from a login call and then use it in subsequent calls.
 * This could be used as a precondition for our tests.
 * <p>
 * We can also store data from the response body and use it in the future.
 */
public class D_StoringDataFromApiCalls {

    private static RequestSpecification requestSpec;

    private static ResponseSpecification responseSpec;

    // Create the RequestSpecification
    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    @BeforeClass
    public static void createResponseSpecification() {
        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType("application/json").
                build();
    }

    @Test
    public void storingDataExample__checkPlaceNameInResponseBody_expectSpecifiedPlaceName() {

        // Create our variable and assign the desired value to it.
        String placeName =

                given().
                        spec(requestSpec).
                        when().
                        get("us/90210").
                        then().
                        spec(responseSpec).
                        and().
                        extract().
                        path("places[0].'place name'");

        // Assert that the stored variable is equal to the expected value.
        Assert.assertEquals(placeName, "Beverly Hills");
    }

    /**
     * This method will extract the place name from the response body.
     *
     * @param responseBody - The response body from the API call.
     * @return placeName - The place name from the response body.
     */
    public String extractPlaceNameFromResponseBody(Response responseBody) {
        // Return the place name from the response body.
        return responseBody.path("places[0].'place name'");
    }

    /**
     * We can also make calls of the same type easier by creating a method for them.
     */
    public Response getResponseBodyCountryZipcode(String country, String zipCode) {
        return given().
                spec(requestSpec).
                when().
                get(country + "/" + zipCode).
                then().
                spec(responseSpec).
                and().
                extract().
                response();
    }

    /**
     * This test will utilise the extractPlaceNameFromResponseBody method to extract the place name from the response body.
     */
    @Test
    public void storingDataExample__UtilisingMethods() {
        // Create a variable to store the response.
        Response response = getResponseBodyCountryZipcode("us", "90210");

        // Create a variable to store the place name.
        String placeName = extractPlaceNameFromResponseBody(response);

        // Assert that the stored variable is equal to the expected value.
        Assert.assertEquals(placeName, "Beverly Hills");
    }
}
