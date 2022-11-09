package ToDoList;

import TestPackage.BaseTest;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AddTask_Test extends BaseTest {


    @Test
    public void add_task() throws IOException {

        FileInputStream fis = new FileInputStream("C:\\Users\\anilavanr\\API_MainAssignment\\src\\test\\java\\TestPackage\\Taskoperation.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet ws = wb.getSheetAt(0);



        for (int count = 1; count <= ws.getLastRowNum(); count++) {


            Row dataRow = ws.getRow(count);
            Cell cell = dataRow.getCell(1);

            String task_desc = cell.getStringCellValue();
            String task = "{\n" +
                    " \"description\":" + task_desc +
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
        //   verifyAssertCodeAndType(response, 201);


    }

}

