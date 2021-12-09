import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CurrencyConvertionTest {

    private static Response response;

    @BeforeAll
    public static void setup() {
        response = given().get(Consts.URL+Consts.key);
    }

    //Security

    @Test
    public void validKeyTest() {
        response.then().statusCode(200);
    }

    @Test
    public void invalidKeyTest() {
        response=given().get(Consts.URL+"?access_key=blla");
        System.out.print(response.asString());
        response.then().statusCode(101);
    }
    //this test failed because I got 200 code instead of 101.

    @Test
    public void missingKeyTest(){
        response=given().get(Consts.URL+"?access_key=");
        System.out.print(response.asString());
        response.then().statusCode(101);

    }

    //Currency conversion
    @Test
    public void convertUSDtoCADTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"cad");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", contains("true"));
    }

    @Test
    public void convertUSDtoEURTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"eur");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", contains("true"));
    }

    @Test
    public void convertUSDtoNISTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"nis");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", contains("true"));
    }

    @Test
    public void convertUSDtoRUBTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"rub");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", contains("true"));
    }

    //number of currencies
    @Test
    public void currenciesTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"cad,eur,nis,rub");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", contains("true"));
    }

    @Test
    public void invalidCurrencyTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"can");
        System.out.println(response.asString());
        response.then().statusCode(402);
        response.then().body("error.info", contains("You have provided one or more invalid Currency Codes"));
    }

    @Test
    public void oneInvalidCurrencyTest(){
        response=given().get(Consts.URL+Consts.key+Consts.currency+"can,eur,nis,rub");
        System.out.println(response.asString());
        response.then().statusCode(402);
        response.then().body("error.info", contains("You have provided one or more invalid Currency Codes"));
    }

    //historical

    @Test
    public void historicalConversionToAllTest(){
        response=given().get(Consts.historicalURL+Consts.historical+Consts.key+Consts.date+"2020-01-01");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("historical", contains("true"));
    }

    @Test
    public void historicalConversionToCadTest(){
        response=given().get(Consts.historicalURL+Consts.historical+Consts.key+Consts.date+"2020-01-01"+Consts.currency+"cad");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("historical", contains("true"));
    }

    @Test
    public void incorrectHistoricalDateTest(){
        response=given().get(Consts.historicalURL+Consts.historical+Consts.key+Consts.date+"2025-01-01");
        System.out.println(response.asString());
        response.then().body("success", contains("false"));
        response.then().body("error.code", equalTo(302));
    }

    @Test
    public void noHistoricalDateTest(){
        response=given().get(Consts.historicalURL+Consts.historical+Consts.key+Consts.date+" ");
        System.out.println(response.asString());
        response.then().body("success", contains("false"));
        response.then().body("error.code", equalTo(301));
    }










    }
