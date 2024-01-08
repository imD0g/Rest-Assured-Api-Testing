package Examples;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * In this class we will be looking at what we have learned and utilising all the concepts.
 *
 * We will be including the following into our example:
 * - RequestSpecification
 * - ResponseSpecification
 * - Storing data from API calls
 * - Using methods to make API calls easier
 */
public class Z_ExampleApiCalls {

    private static RequestSpecification requestSpec;

    private static ResponseSpecification responseSpec;

    private final Response response = getResponseBodyCountryZipcode("us", "90210");

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    @BeforeClass
    public static void createResponseSpecification() {
        responseSpec = new ResponseSpecBuilder().
                expectContentType("application/json").
                build();
    }

    @Test
    public void assertPostCode() {
        String postCode = getPostCodeFromResponseBody(response);
        Assert.assertEquals("90210", postCode);
    }

    @Test
    public void assertCountry() {
        String country = getCountryFromResponseBody(response);
        Assert.assertEquals("United States", country);
    }

    @Test
    public void assertCountryAbbreviation() {
        String countryAbbreviation = getCountryAbbreviationFromResponseBody(response);
        Assert.assertEquals("US", countryAbbreviation);
    }

    @Test
    public void assertPlaces() {
        JsonArray places = getPlacesArrayFromResponseBody(response);

        // Assert not null
        Assert.assertNotNull(places);

        // Assert the json array contents
        Assert.assertEquals(1, places.size());
        Assert.assertEquals("Beverly Hills", places.get(0)
                                                   .getAsJsonObject()
                                                   .get("place name")
                                                   .getAsString());
        Assert.assertEquals("34.0901", places.get(0)
                                             .getAsJsonObject()
                                             .get("latitude")
                                             .getAsString());
        Assert.assertEquals("-118.4065", places.get(0)
                                               .getAsJsonObject()
                                               .get("longitude")
                                               .getAsString());
        Assert.assertEquals("California", places.get(0)
                                                .getAsJsonObject()
                                                .get("state")
                                                .getAsString());
        Assert.assertEquals("CA", places.get(0)
                                        .getAsJsonObject()
                                        .get("state abbreviation")
                                        .getAsString());
    }

    @Test
    public void assertPlaceName() {
        String placeName = getPlaceNameFromResponseBody(response);
        Assert.assertEquals("Beverly Hills", placeName);
    }

    @Test
    public void assertLongitude() {
        String longitude = getLongitudeFromResponseBody(response);
        Assert.assertEquals("-118.4065", longitude);
    }

    @Test
    public void assertLatitude() {
        String latitude = getLatitudeFromResponseBody(response);
        Assert.assertEquals("34.0901", latitude);
    }

    @Test
    public void assertState() {
        String state = getStateFromResponseBody(response);
        Assert.assertEquals("California", state);
    }

    @Test
    public void assertStateAbbreviation() {
        String stateAbbreviation = getStateAbbreviationFromResponseBody(response);
        Assert.assertEquals("CA", stateAbbreviation);
    }

    /**
     * This method will make a GET request to the specified country and zip code.
     *
     * @param country - The country parameter for the API call.
     * @param zipCode - The zip code parameter for the API call.
     * @return response - The response from the API call.
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
     * This method returns the country post code from the response body.
     *
     * @param response - The response from the API call.
     * @return postCode - The post code from the response body.
     */
    public String getPostCodeFromResponseBody(Response response) {
        try {
            return response.path("'post code'");
        } catch (Exception e) {
            return "No post code found in response body.";
        }
    }

    /**
     * This method returns the country from the response body.
     *
     * @param response - The response from the API call.
     * @return country - The country from the response body.
     */
    public String getCountryFromResponseBody(Response response) {
        try {
            return response.path("'country'");
        } catch (Exception e) {
            return "No country found in response body.";
        }
    }

    /**
     * This method returns the country abbreviation from the response body.
     *
     * @param response - The response from the API call.
     * @return countryAbbreviation - The country abbreviation from the response body.
     */
    public String getCountryAbbreviationFromResponseBody(Response response) {
        try {
            return response.path("'country abbreviation'");
        } catch (Exception e) {
            return "No country abbreviation found in response body.";
        }
    }

    /**
     * This method returns the "places" array from the response body.
     *
     * @param response - The response from the API call.
     * @return placesArray - The "places" array from the response body.
     */
    public JsonArray getPlacesArrayFromResponseBody(Response response) {
        try {
            String responseBody = response.getBody()
                                          .asString();
            JsonObject jsonObject = JsonParser.parseString(responseBody)
                                              .getAsJsonObject();

            if (jsonObject.has("places") && jsonObject.get("places")
                                                      .isJsonArray()) {
                return jsonObject.getAsJsonArray("places");
            } else {
                return new JsonArray(); // Return an empty array if no "places" array is found
            }
        } catch (Exception e) {
            return new JsonArray(); // Return an empty array in case of any exception
        }
    }

    /**
     * This method returns the place name from the response body.
     *
     * @param response - The response from the API call.
     * @return placeName - The place name from the response body.
     */
    public String getPlaceNameFromResponseBody(Response response) {
        try {
            return response.path("places[0].'place name'");
        } catch (Exception e) {
            return "No place name found in response body.";
        }
    }

    /**
     * This method returns the longitude from the response body.
     *
     * @param response - The response from the API call.
     * @return longitude - The longitude from the response body.
     */
    public String getLongitudeFromResponseBody(Response response) {
        try {
            return response.path("places[0].longitude");
        } catch (Exception e) {
            return "No longitude found in response body.";
        }
    }

    /**
     * This method returns the latitude from the response body.
     *
     * @param response - The response from the API call.
     * @return latitude - The latitude from the response body.
     */
    public String getLatitudeFromResponseBody(Response response) {
        try {
            return response.path("places[0].latitude");
        } catch (Exception e) {
            return "No latitude found in response body.";
        }
    }

    /**
     * This method returns the state from the response body.
     *
     * @param response - The response from the API call.
     * @return state - The state from the response body.
     */
    public String getStateFromResponseBody(Response response) {
        try {
            return response.path("places[0].state");
        } catch (Exception e) {
            return "No state found in response body.";
        }
    }

    /**
     * This method returns the state abbreviation from the response body.
     *
     * @param response - The response from the API call.
     * @return stateAbbreviation - The state abbreviation from the response body.
     */
    public String getStateAbbreviationFromResponseBody(Response response) {
        try {
            return response.path("places[0].'state abbreviation'");
        } catch (Exception e) {
            return "No state abbreviation found in response body.";
        }
    }
}
