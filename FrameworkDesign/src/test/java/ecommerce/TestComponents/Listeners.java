package ecommerce.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{

	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();//Thread safe to avoid concurrency sync issues
	
	ExtentReports extent = ExtentReporterNG.getReportObject();
	@Override
	public void onTestStart(ITestResult result)
	{
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);//Unique thread id(ErrorValidationsTest)->test inside a map
	}
	
	@Override 
	public void onTestSuccess(ITestResult result)
	{
		extentTest.get().log(Status.PASS, "Test Passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result)
	{
		//test.log(Status.FAIL, "Test Failed");
		extentTest.get().fail(result.getThrowable());
		
		try
		{
		driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
				.get(result.getInstance());
		
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Taking screenshot and attaching to report
		String filePath = null;
		try {
			filePath = getScreenShot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}
	
	@Override
	public void onFinish(ITestContext context)
	{
		extent.flush();
	}
}
