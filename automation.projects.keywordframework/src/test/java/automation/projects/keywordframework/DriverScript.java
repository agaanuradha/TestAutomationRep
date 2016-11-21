package automation.projects.keywordframework;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.log4j.xml.DOMConfigurator;
import automation.projects.keywordframework.config.*;
import automation.projects.keywordframework.utility.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import automation.projects.keywordframework.*;

public class DriverScript 
{
	public static ActionKeywords actionKeywords;
	public static Method method[];
	public static boolean bResult;
	public static Assertions assertions;
	public static Method method_assert[];
	public static boolean bAssertResult;
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sData;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sTestCaseDescription;
	public static String sDescription;
	public static String screenshotLink;
	public static String screenshotPath = null;
	private static ExtentReports extent;
	private static ExtentTest Test;

	
	public DriverScript() throws NoSuchMethodException, SecurityException
	{	   
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getDeclaredMethods();
		assertions = new Assertions();
		method_assert = assertions.getClass().getDeclaredMethods();
	}


	@BeforeTest
	public void beforeTest()
	{
		String Path_DataEngine = Constants.Path_TestData;   
		DOMConfigurator.configure("log4j.xml");
		//System.setProperty("org.uncommons.reportng.escape-output", "false");
		try 
		{
			ExcelUtils.setExcelFile(Path_DataEngine);
			extent = new ExtentReports(Constants.Path_TestReportFolder + "Report.html", true, NetworkMode.OFFLINE);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	/*@SuppressWarnings("static-access")
	@AfterMethod
	public void afterMethod()
	{
		actionKeywords.driver.quit();
	}*/

	
	@Test
	public void mainDriverScript()
	{
		try 
		{
			//new DriverScript();  
			DriverScript startEngine = new DriverScript();
			startEngine.execute_TestCase();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void execute_TestCase() throws Exception 
	{
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

		for(int iTestcase=1; iTestcase<=iTotalTestCases; iTestcase++)
		{
			bResult = true;
			bAssertResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			sTestCaseDescription  = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseDescription,Constants.Sheet_TestCases);
			
			if (sRunMode.equals("Yes"))
			{
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
				iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
				Log.startTestCase(sTestCaseID);
				bResult=true;
				
				Test = extent.startTest(sTestCaseID, sTestCaseDescription);
				extent.endTest(Test);
				
				for (; iTestStep<=iTestLastStep; iTestStep++)
				{
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
					sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
					sDescription=ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepDescription, sTestCaseID);
					
					//Test = extent.
					//Test = extent.startTest(sTestCaseID, sDescription);
					//extent.endTest(Test);
					
					System.out.println("sActionKeyword = "+sActionKeyword);

					if(sActionKeyword.equals("N/A"))
					{
						sActionKeyword = "";
					}

					if(sPageObject.equals("N/A"))
					{
						sPageObject = "";
					}

					if(sData.equals("N/A"))
					{
						sData = "";
					}

					if(sActionKeyword.contains("assert_"))
					{
						System.out.println("if(sActionKeyword.contains(assert_))");
						execute_Assertions();
					}
					else
					{
						execute_Actions();
					}

					if(bResult==false)
					{
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						
					/*	screenshotPath = ExcelUtils.setScreenshotinExcel(iTestStep, Constants.Col_Screenshot, sTestCaseID);
						screenshotPath = screenshotPath.replace("//", "/");
						screenshotLink = "file:///"+ screenshotPath;*/
						
						//log-pass status
						Test.log(LogStatus.FAIL,sDescription, "Fail");
						
						// report with snapshot
						String img = Test.addScreenCapture(screenshotPath);
						Test.log(LogStatus.INFO, "Snapshot Attached"+img);
					    
						// writing everything to document
						extent.flush();
						
						break;
					}
				}
				

				if(bResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);	
					
					//log-pass status
					Test.log(LogStatus.PASS, sDescription,"Pass");
					
					// report with snapshot
					String img = Test.addScreenCapture(screenshotPath);
					Test.log(LogStatus.INFO, "Snapshot Attached"+img);
				    
					// writing everything to document
					extent.flush();
				}
			}
		}
		
	}

	private static void execute_Actions() throws Exception 
	{
		for(int i = 0;i < method.length;i++)
		{
			if(method[i].getName().equals(sActionKeyword))
			{
				method[i].invoke(sActionKeyword,sPageObject,sData);

				if(bResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					screenshotPath = ExcelUtils.setScreenshotinExcel(iTestStep, Constants.Col_Screenshot, sTestCaseID);
					screenshotPath = screenshotPath.replace("//", "/");
					screenshotLink = "file:///"+ screenshotPath;
					
					//log-pass status
					Test.log(LogStatus.PASS,sDescription, "Pass");
					
					// report with snapshot
					String img = Test.addScreenCapture(screenshotPath);
					Test.log(LogStatus.INFO, "Snapshot Attached"+img);
				    
					// writing everything to document
					extent.flush();
					
					break;
				}
				else
				{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					screenshotPath = ExcelUtils.setScreenshotinExcel(iTestStep, Constants.Col_Screenshot, sTestCaseID);
					screenshotPath = screenshotPath.replace("//", "/");
					screenshotLink = "file:///"+ screenshotPath;
					//ActionKeywords.closeBrowser();
					
					//log-pass status
					Test.log(LogStatus.FAIL,sDescription, "Fail");
					
					// report with snapshot
					String img = Test.addScreenCapture(screenshotPath);
					Test.log(LogStatus.INFO, "Snapshot Attached"+img);
				    
					// writing everything to document
					extent.flush();
					
					break;
				}
			}
		}
	}

	private static void execute_Assertions() throws Exception 
	{
		for(int i = 0;i < method_assert.length;i++)
		{
			if(method_assert[i].getName().equals(sActionKeyword))
			{
				method_assert[i].invoke(sActionKeyword,sPageObject,sData);
				if(bAssertResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					screenshotPath = ExcelUtils.setScreenshotinExcel(iTestStep, Constants.Col_Screenshot, sTestCaseID);
					screenshotPath = screenshotPath.replace("//", "/");
					screenshotLink = "file:///"+ screenshotPath;
					
					//log-pass status
					Test.log(LogStatus.PASS, "Passssss");
					
					// report with snapshot
					String img = Test.addScreenCapture(screenshotPath);
					Test.log(LogStatus.INFO, "Snapshot Attached"+img);
				    
					// writing everything to document
					extent.flush();
					
					break;
				}
				else
				{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					screenshotPath = ExcelUtils.setScreenshotinExcel(iTestStep, Constants.Col_Screenshot, sTestCaseID);
					screenshotPath = screenshotPath.replace("//", "/");
					screenshotLink = "file:///"+ screenshotPath;
					ActionKeywords.closeBrowser();
					
					//log-pass status
					Test.log(LogStatus.PASS, "Passssss");
					
					// report with snapshot
					String img = Test.addScreenCapture(screenshotPath);
					Test.log(LogStatus.INFO, "Snapshot Attached"+img);
				    
					// writing everything to document
					extent.flush();
					
					break;
				}
			}
		}
	}
}