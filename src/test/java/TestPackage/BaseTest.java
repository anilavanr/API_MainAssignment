package TestPackage;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import utils.ApplicationProperties;


import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BaseTest {



    @BeforeTest
    public void request_specification() {


        String baseurl = ApplicationProperties.INSTANCE.getbaseUrl();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseurl).
                addHeader("Content-Type", "application/json")
                .setContentType(ContentType.JSON);
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());

    }


    public void verifyAssertCodeAndType(Response response, int code) {
        assertThat(response.statusCode(), is(equalTo(code)));


    }

    public String set_token(){

        String token = ApplicationProperties.INSTANCE.get_token();
        return token;
    }



    public void verifycountwithlimit(JSONObject object, int i) {

        assertThat(object.get("count"),is(equalTo(i)));
    }


}