package internet;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Internet {

	private static WebDriver driver;
	private static StringBuffer script;
	private static JavascriptExecutor jsexcute;
	static {
		System.setProperty("webdriver.firefox.bin", "D:/MyProgram/FF/firefox.exe");
		script = new StringBuffer();
		script.append("document.getElementsByName('DDDDD')[0].value='21150211099';");
		script.append("document.getElementsByName('upass')[0].value='102857';");

	}

	public static void Connect() throws Exception {

		try {
			driver = new FirefoxDriver();
			jsexcute = (JavascriptExecutor) driver;
			driver.get("http://10.81.2.6");
			if (!driver.getTitle().equals("上网注销窗")) {
				jsexcute.executeScript(script.toString());
				driver.findElement(By.name("0MKKey")).click();
			}
		} catch (Exception e) {
			throw new Exception("connect internet fail ");
		}finally{
			if (null != driver){
				driver.close();
			}
			closeBrowser() ;
		}
	}

	public static void disConnect() {
		
		try {
			driver = new FirefoxDriver();
			jsexcute = (JavascriptExecutor) driver;
			driver.get("http://10.81.2.6");
			if (driver.getTitle().equals("上网注销窗")) {
				driver.findElement(By.tagName("input")).click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		} catch (Exception e) {
			
		}finally{
			
			closeBrowser() ;
		}
	}

	public static void closeBrowser() {
		try {
			Runtime.getRuntime().exec("taskkill /f /im firefox.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
