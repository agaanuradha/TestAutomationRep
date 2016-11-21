package automation.projects.keywordframework.utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.testng.Reporter;

import automation.projects.keywordframework.utility.*;


public class ReportUtils 
{
	public static void setScreenshotinReport() throws Exception 
	{
		try 
		{
			String ErrorScreenshot1 = GeneralUtils.getScreenshot();
			File file = new File(ErrorScreenshot1);

			System.setProperty("org.uncommons.reportng.escape-output", "false");
			String absolute = file.getAbsolutePath();
			int beginIndex = absolute.indexOf(".");
			String relative = absolute.substring(beginIndex).replace(".\\", "");
			String screenShot = relative.replace('\\', '/');

			Reporter.log("<a href=\"" + screenShot + "\"><p align=\"left\">Error screenshot at " + new Date() + "</p>");
			Reporter.log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile() + "\" alt=\"screenshot at "
					+ new Date() + "\"/></p></a><br />");

		}
		catch (IOException e) 
		{
			Log.info("Not able to take error screenshot " + e.getMessage());
		}
	}
}
