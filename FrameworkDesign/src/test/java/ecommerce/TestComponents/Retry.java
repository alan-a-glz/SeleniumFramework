package ecommerce.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	int count = 0;
	int maxTry = 1;//Retry once again
	
	@Override
	public boolean retry(ITestResult result) {//To rerun flaky failed tests
		// TODO Auto-generated method stub
		
		if(count<maxTry)
		{
			count++;
			return true;
		}
		return false;
	}

}
