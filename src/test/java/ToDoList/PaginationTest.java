package ToDoList;

import TestPackage.BaseTest;
import io.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PaginationTest extends BaseTest {

    @Test
    public void limit_pagination() {

        call_pagination(2);
        call_pagination(5);
        call_pagination(10);

    }

    @Test
    public void call_pagination(int i) {

        Response response = given()
                .header("Authorization", "Bearer " + set_token())
                .param("limit", i)
                .when()
                .get("task")
                .then()
                .log().all()
                .extract()
                .response();

        verifyAssertCodeAndType(response, 200);
        JSONObject object = new JSONObject(response.asString());
        verifycountwithlimit(object,i);

    }


    @Test
    public void inputValidation() {

        try (InputStream input = new FileInputStream("src/main/resources/token.properties")) {

            Properties properties = new Properties();
            properties.load(input);
            Response response = given()
                    .header("Authorization", "Bearer " + set_token())
                    .when()
                    .get("task")
                    .then()
                    .log().body()
                    .extract()
                    .response();

            JSONObject obj = new JSONObject(response.asString());
            JSONArray data = obj.getJSONArray("data");

            FileInputStream fis = new FileInputStream("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Taskoperation.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheet("Sheet1");


            for(int i=0;i<data.length();i++){

                assertThat(data.getJSONObject(i).get("owner"),is(equalTo(properties.getProperty("id"))));

                for (int count = 1; count <= ws.getLastRowNum(); count++) {

                    XSSFRow dataRow = ws.getRow(count);
                    XSSFCell cell = dataRow.getCell(1);

                    String Taskoperation = cell.getStringCellValue();
                    assertThat(data.getJSONObject(i).get("description"),is(equalTo(Taskoperation)));
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}