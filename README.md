# API Testing w/ Rest Assured

This project demonstrates some real world examples of using Rest Assured to verify APIs

## Features

- **Request and Response Specifications**: Utilize RestAssured's capabilities to define common request and response specifications.
- **Structured Test Cases**: Well-organized test cases focusing on specific aspects of the API response.
- **Modular Approach**: Separate concerns by encapsulating API calls and response parsing in dedicated methods.
- **Data Extraction**: Extract valuable information from API responses for further validation.
- **JUnit Assertions**: Leverage the JUnit framework for clear and concise assertions.

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/awesome-api-testing.git
   ```

2.    Open in your IDE:
    Import the project into your favorite Java IDE to explore and run the tests.

3.    Set Up Dependencies:
    Ensure you have all the necessary dependencies, including RestAssured and Gson.

4.    Run the Tests:
    Execute the provided JUnit tests to see the magic happen.

## Usage

```java

// Example of making an API call and asserting the postal code
@Test
public void assertPostCode() {
    String postCode = getPostCodeFromResponseBody(getResponseBodyCountryZipcode("us", "90210"));
    Assert.assertEquals("90210", postCode);
}
```

## Contributing

If you find any issues or have ideas for improvements, feel free to contribute! Follow these steps:

    Fork the repository
    Create your feature branch (git checkout -b feature/AmazingFeature)
    Commit your changes (git commit -m 'Add some amazing feature')
    Push to the branch (git push origin feature/AmazingFeature)
    Open a pull request

Happy testing! 🚀
