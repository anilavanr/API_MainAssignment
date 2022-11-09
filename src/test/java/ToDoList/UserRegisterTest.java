package ToDoList;

import TestPackage.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import static TestPackage.BaseTest.extent;
import static TestPackage.BaseTest.htmlReporter;
import static io.restassured.RestAssured.given;

public class UserRegisterTest extends BaseTest {

    String id;
    String email;
    @Test
    public void user_register() {

        extent.attachReporter(htmlReporter);
        ExtentTest test = extent.createTest("Test No.3","Cart Page Functionality Checked");

        try (OutputStream output = new FileOutputStream("C:\\Users\\anilavanr\\API_MainAssignment\\src\\main\\resources\\token.properties")) {

            File json = new File("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Register.json");
            Properties prop = new Properties();
            Response response = given()
                    .body(json)
                    .when()
                    .post("user/register")
                    .then()
                    .log().body()
                    .extract()
                    .response();

            verifyAssertCodeAndType(response , 201);
            test.pass("Registration Successful");
            JsonPath obj = new JsonPath(response.asString());
            String email = obj.get("user.email");
            String id = obj.get("user._id");
            String token = obj.get("token");
            prop.setProperty("Token",token);
            prop.setProperty("email",email);
            prop.setProperty("id",id);
            prop.store(output, null);


        } catch (IOException io) {
            io.printStackTrace();
        }


    }

}