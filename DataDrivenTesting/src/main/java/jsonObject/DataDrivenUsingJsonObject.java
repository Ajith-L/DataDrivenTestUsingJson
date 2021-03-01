package jsonObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataDrivenUsingJsonObject {
	@FindBy(xpath="//input[@name='txtUsername']")
	public static WebElement username;
	
	@FindBy(xpath="//input[@name='txtPassword']")
	public static WebElement password;
	
	@FindBy(xpath="//input[@name='Submit']")
	public static WebElement btn;
}
