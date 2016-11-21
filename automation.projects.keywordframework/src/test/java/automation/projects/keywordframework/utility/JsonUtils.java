package automation.projects.keywordframework.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import automation.projects.keywordframework.config.*;

public class JsonUtils 
{

	public static String readORJson(String ObjectName)
	{
		JSONParser parser = new JSONParser();
		Object obj = null;
		JSONObject jsonObject =null;
		String fileName= Constants.Path_JSON_ObjectRepositry;
		String	objProp = null;
		try
		{
			try 
			{
				obj = parser.parse(new FileReader(fileName));
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			jsonObject = (JSONObject)obj;
			objProp = (String) jsonObject.get(ObjectName);
		}
		catch(ParseException pe)
		{
			System.out.println(pe);
		}
		return objProp;
	}
}