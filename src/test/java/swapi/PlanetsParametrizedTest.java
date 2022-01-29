package swapi;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanetsParametrizedTest extends BaseTest {

    private static Stream<Arguments> planetVerificationData() {
        return Stream.of(
                Arguments.of("1", "Tatooine", "arid", "200000"),
                Arguments.of("3", "Yavin IV", "temperate, tropical", "1000"),
                Arguments.of("5", "Dagobah", "murky", "unknown"),
                Arguments.of("7", "Endor", "temperate", "30000000"),
                Arguments.of("9", "Coruscant", "temperate", "1000000000000"));
    }

    @DisplayName("planet validation")
    @ParameterizedTest(name = "Planet id: {0}, planetName: {1}, climate: {2}, population: {3}")
    @MethodSource("planetVerificationData")

    public void planetVerificationData(String id, String planetName, String climate, String population) {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + KIND + "/" + id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(planetName);
        assertThat(json.getString("climate")).isEqualTo(climate);
        assertThat(json.getString("population")).isEqualTo(population);

    }

}
