package json;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jsonObject.DataDrivenUsingJsonObject;

public class DataDrivenJson {
	WebDriver driver;

	@DataProvider(name = "connection")
	String[] json() throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		FileReader reader = new FileReader("logindata.json");
		Object obj = parser.parse(reader);
		JSONObject jobj = (JSONObject) obj;

		JSONArray arr = (JSONArray) jobj.get("data");

		String array[] = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			JSONObject ser = (JSONObject) arr.get(i);
			String name = (String) ser.get("Username");
			String pwd = (String) ser.get("Password");

			array[i] = name + "," + pwd;
		}
		return array;
	}

	@Test(dataProvider = "connection")
	public void logindata(String data) throws IOException {
		FileReader reader = new FileReader("config.properties");
		Properties properties = new Properties();
		properties.load(reader);
		String name = (String) properties.get("Broswer");
		String dri = (String) properties.get("driverloc");

		if (name.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", dri);
			driver = new ChromeDriver();
			driver.get("https://opensource-demo.orangehrmlive.com/");
		} else if (name.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.get("https://opensource-demo.orangehrmlive.com/");

		}
		String users[] = data.split(",");
		PageFactory.initElements(driver, DataDrivenUsingJsonObject.class);
		DataDrivenUsingJsonObject.username.sendKeys(users[0]);
		DataDrivenUsingJsonObject.password.sendKeys(users[1]);
		DataDrivenUsingJsonObject.btn.click();
    
		driver.close();
	
	}
		
	
}
