package TestPackage;


import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.ApplicationProperties;


import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BaseTest {

    public static String current_path = System.getProperty("user.dir");
    public static ExtentReports extent = new ExtentReports();
    // public static ExtentSparkReporter htmlReporter = new ExtentSparkReporter(current_path+"/src/test/java/base/report.html");
    public static ExtentSparkReporter htmlReporter = new ExtentSparkReporter(current_path+"/src/test/java/Reports/report.html");
    static String id;

    static String email;
    //Logger log = LogManager.getLogManager("");


    @BeforeTest
    public void request_specification() {

        // RestAssured.baseURI = baseurl;
        String baseurl = ApplicationProperties.INSTANCE.getbaseUrl();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseurl).
                addHeader("Content-Type", "application/json")
                .setContentType(ContentType.JSON);
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());

    }


    public void verifyAssertCodeAndType(Response response, int code) {
        assertThat(response.statusCode(), is(equalTo(code)));
        // assertThat(response.contentType(), containsString(String.valueOf(ContentType.JSON)));

    }

    public String set_token(){

        String token = ApplicationProperties.INSTANCE.gettoken();
        return token;
    }

    public void store_data(String id,String email){

        BaseTest.id = id;
        BaseTest.email = email;
    }


    public String get_id(){
        return id;
    }

    public String get_email(){
        return email;
    }

    public void verifycountwithlimit(JSONObject object, int i) {

        assertThat(object.get("count"),is(equalTo(i)));
    }

    @AfterTest
    public void close() {

        extent.flush();
    }
}