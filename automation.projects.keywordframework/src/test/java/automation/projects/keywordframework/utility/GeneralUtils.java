package automation.projects.keywordframework.utility;



import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import automation.projects.keywordframework.config.*;


import automation.projects.keywordframework.utility.*;

public class GeneralUtils 
{
	private static int captureNum = 0;

	public static String getScreenshot() throws Exception
	{
		String ErrorScreenshot = null;
		
		try 
		{
			captureNum = captureNum + 1;
			ErrorScreenshot = Constants.Path_ErrorScreenshots+ "Error_"+ captureNum  + ".png";
			System.out.println(ActionKeywords.driver.getTitle());
			File srcFile = ((TakesScreenshot)ActionKeywords.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(ErrorScreenshot));
		}
		catch (IOException e)
		{
			//System.out.println("getScreenshot = "+e.printStackTrace());
			Log.info("Not able to take error screenshot " + e.getMessage());
		}
		
		return ErrorScreenshot;
	}

	public static String getCurrentDate()
	{
		String runDate= null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		runDate = dateFormat.format(date).toString();
		return runDate;
	}

}
