package utilities;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Screenshot {
	public static void takeScreenshot(WebDriver driver,String filename) throws Exception{
		
	TakesScreenshot tks = (TakesScreenshot) driver;
	
	File src = tks.getScreenshotAs(OutputType.FILE);
	File dest = new File("./screenshots/"+filename+".png");
	FileHandler.copy(src, dest);

}
}
