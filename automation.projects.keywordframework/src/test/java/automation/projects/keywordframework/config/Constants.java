package automation.projects.keywordframework.config;

import automation.projects.keywordframework.utility.*;;

public class Constants 
{
	public static final String Parent_SourcePath ="C:\\TestWS\\automation.projects.keywordframework\\src\\test\\java\\automation\\projects\\keywordframework\\";
	//public static final String URL = "HTTP://ENTERPRISE.DEMO.ORANGEHRMLIVE.COM/";
	public static final String URL = "http://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
	public static final String Path_TestData = Parent_SourcePath+ "dataEngine/DataEngine.xlsx";
	public static final String File_TestData = "DataEngine.xlsx";
	public static final String Path_JSON_ObjectRepositry=Parent_SourcePath+"config/ObjectRepositoryXYZ.json";
    public static final String Path_ChromeDriver = "C:/Selenium/drivers_ie_chrome/chromedriver.exe";
    public static final String Path_IEDriver = "C:\\FunctionalTestAutomation\\SwSetup\\Selenium\\IEDriver32\\IEDriverServer.exe";
	public static final String Path_BuildExecutionFolder = "C://BuildExecutionLogs//TestWS//";
	public static final String todayDate = GeneralUtils.getCurrentDate();
	public static final String Path_ErrorScreenshots = Path_BuildExecutionFolder + todayDate + "/";
	public static final String Path_TestReportFolder = Path_BuildExecutionFolder + todayDate + "/";
	
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
	//List of Data Engine Excel sheets
	public static final String Sheet_TestCases = "Test Cases";
		
	//List of Data Sheet Column Numbers (Sheet: Test Cases)
	public static final int Col_TestCaseDescription = 1 ;
	public static final int Col_RunMode = 2;
	public static final int Col_Result = 3;
		
	//List of Data Sheet Column Numbers (Sheet: Test Steps)
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID = 1;
	public static final int Col_TestStepDescription = 2 ;
	public static final int Col_PageObject = 3;
	public static final int Col_ActionKeyword = 4;
	public static final int Col_DataSet = 5;
	public static final int Col_TestStepResult = 6;
	public static final int Col_Screenshot = 7;
}
