package Examples;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * In this class we will be using parameters in our API calls.
 *
 * We are running the test with the DataProviderRunner, so we need to annotate the class with @RunWith.
 */
@RunWith(DataProviderRunner.class)
public class UsingParamsInApiCalls {

    // We are using the DataProviderRunner from the JUnit DataProvider library.
    // We then create an object array with the data we want to use in our tests.
    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][]{
                // Country code, zip code, place name
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };

    }

    // Now we can use the data in our tests.
    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode,
                                                                                                    String zipCode,
                                                                                                    String expectedPlaceName) {
        given().
                pathParam("countryCode", countryCode)
                .pathParam("zipCode", zipCode).
                when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }
}
