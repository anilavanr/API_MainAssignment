package ToDoList;

import TestPackage.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


import static io.restassured.RestAssured.given;

public class UserRegisterTest extends BaseTest {

    @Test
    public void register_userdata() {



        try (OutputStream output = new FileOutputStream("C:\\Users\\anilavanr\\API_MainAssignment\\src\\main\\resources\\token.properties")) {

            File json = new File("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Register.json");
            Properties properties = new Properties();
            Response response = given()
                    .body(json)
                    .when()
                    .post("user/register")
                    .then()
                    .log().body()
                    .extract()
                    .response();

            verifyAssertCodeAndType(response , 201);
            JsonPath path = new JsonPath(response.asString());
            String email = path.get("user.email");
            String id = path.get("user._id");
            String token = path.get("token");
            properties.setProperty("Token",token);
            properties.setProperty("email",email);
            properties.setProperty("id",id);
            properties.store(output, null);


        } catch (IOException io) {
            io.printStackTrace();
        }


    }

}