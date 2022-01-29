package swapi;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicPlanetsTest extends BaseTest {

    @Test
    public void numberOfPlanets() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + KIND)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("results.name");

        assertEquals(10, names.size());
    }

    @Test
    public void checkInformationForThirdPlanet() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + KIND + "/3")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Yavin IV");
        assertThat(json.getString("rotation_period")).isEqualTo("24");
        assertThat(json.getString("diameter")).isEqualTo("10200");
        assertThat(json.getString("climate")).isEqualTo("temperate, tropical");
        assertThat(json.getString("terrain")).isEqualTo("jungle, rainforests");
        assertThat(json.getString("population")).isEqualTo("1000");
    }

}
