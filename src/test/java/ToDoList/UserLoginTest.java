package ToDoList;



import TestPackage.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserLoginTest extends BaseTest {

    UserRegisterTest Test_Registration;

    {
        Test_Registration = new UserRegisterTest();
    }

    @Test
    public void user_registerLogin(){

        try (InputStream input = new FileInputStream("src/main/resources/token.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            File json = new File("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Login.json");
            Response response = given()
                    /*.header("Authorization","Bearer "+set_token())*/
                    .body(json)
                    .when()
                    .post("user/login")
                    .then()
                    .log().body()
                    .extract()
                    .response();
            verifyAssertCodeAndType(response , 200);
            JsonPath obj = new JsonPath(response.asString());


            String email=obj.get("user.email");
            String getemail=properties.getProperty("email");
            System.out.println(getemail);
            assertThat(email,is(equalTo(getemail)));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}