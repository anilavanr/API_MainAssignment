package ToDoList;

import TestPackage.BaseTest;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class NegativeScenarioTest extends BaseTest {
    @Test
    public void negative_register() {

        File json = new File("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Negative.json");
        Response response = given()
                .body(json)
                .when()
                .post("user/register")
                .then()
                .extract()
                .response();
        // verifyAssertCodeAndType(response , 200);
        String result = response.getBody().asString();

        System.out.println(result);


    }

    @Test
    public void negative_login() {

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
        // verifyAssertCodeAndType(response , 200);

    }
    @Test
    public void add_task() throws IOException {

        FileInputStream fis = new FileInputStream("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\utils\\Taskoperation.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet ws = wb.getSheetAt(0);

        Row dataRow = ws.getRow(1);
        Cell cell = dataRow.getCell(1);

        String task_desc = cell.getStringCellValue();
        String task = "{\n" +
                " \"descriptin\":" + task_desc +
                "\n}";

        Response response = given()
                .header("Authorization", "Bearer "+set_token() )
                .body(task)
                .when()
                .post("task")
                .then()
                .log().body()
                .extract()
                .response();




    }


}
