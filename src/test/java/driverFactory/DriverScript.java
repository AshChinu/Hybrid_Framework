package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	public static WebDriver driver;
	String inputpath = "D:\\ERP_Project\\ERP_Hybrid_Framework\\FileInput\\DataEngine.xlsx";
	String outputpath = "D:\\ERP_Project\\ERP_Hybrid_Framework\\FileOutput\\HybridResults.xlsx";
	public void startTest() throws Throwable
	{
		String Modulestatus = "";
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
		{
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule = xl.getCellData("MasterTestCases", i, 1);
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locater_Type = xl.getCellData(TCModule, j, 2);
					String Locater_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
						}
						else if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
						}
						else if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locater_Type, Locater_Value, Test_Data);
						}
						else if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locater_Type, Locater_Value, Test_Data);
						}
						else if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locater_Type, Locater_Value);
						}
						else if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, Test_Data);
						}
						else if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
						}
						else if(Object_Type.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
						}
						else if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver, Test_Data);
						}
						else if(Object_Type.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.captureData(driver, Locater_Type, Locater_Value);
						}
						else if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
						}
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						Modulestatus="True";
					}catch(Throwable t)
					{
						System.out.println(t.getMessage());
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						Modulestatus="False";
					}
					if(Modulestatus.equalsIgnoreCase("True"))
					{
						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
					}
					else
					{
						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
					}
				}
			}
			else
			{
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}

	}
}
