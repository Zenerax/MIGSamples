package mig_tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class MIGTests {

	public WebDriver driver;
	
	//Return WebElement by locator once the element is located on the page 
	// with explicit timeout in seconds provided by the caller of the method.
	public WebElement getWebElementWhenVisible(By locator, int timeout) {
		 
		WebElement element = null;
		 
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		 
		//Set explicit wait until the web element is located on he web page.
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		 
		return element;
	}
	
	//Return WebElement by locator once the element is located on the page 
	// with explicit timeout in seconds provided by the caller of the method.
	public List<WebElement> getWebElementsWhenVisible(By locator, int timeout) {
		 
		List<WebElement> elements = null;
		 
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		 
		//Set explicit wait until the web element is located on he web page.
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
		elements = driver.findElements(locator);
		 
		return elements;
	}
		
	public void clickWhenReady(By locator, int timeout) {
	 
		WebElement element = null;
		
		// WebDriverWait: specialized fluent wait with timeout in seconds
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		 
		//Set explicit wait until the web element is located on he web page.
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		 
		element.click();
	  }
		
		
	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
		driver = new ChromeDriver();
 	  }
		
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	  }
	
	@DataProvider (name="RBASearchCriteria")
	String[][] createRitchieBrosAuctionSearchData() {
        
	  //Generate the combination of industry and category
	  //More combination of data for data driven testing can be added below.
      return new String[][] {
              {"Construction","Excavators", "2014", "2017"}
      };
	}  

	@Test
	public void verifyGooglePage() {
		
		driver.get("https://www.google.com");
		
		//Wait until the search button is loaded with explicit wait time of 10 seconds
		WebElement searchButton = getWebElementWhenVisible(By.name("btnK"), 10);

		clickWhenReady(By.name("btnK"), 10);
		
		//Test the expected URL is loaded and the search button is displayed on the page
		Assert.assertTrue(driver.getCurrentUrl().toLowerCase().equals("https://www.google.com/") && searchButton.isDisplayed());
	}
	  

	  
	@Test
	public void verifyMIWSearchOnGoogle() {
		
		driver.get("https://www.google.com");
		WebElement searchTextField;
		List<WebElement> searchResults;
		
		//Wait until the search text input field is visible with explicit wait time of 10 seconds
		searchTextField = getWebElementWhenVisible(By.id("lst-ib"), 10);
		
		searchTextField.sendKeys("mobile integration workgroup");

		//Click the Google Search button when the button is ready explicit wait time of 10 seconds 
		clickWhenReady(By.name("btnG"), 10);
		
		searchResults = getWebElementsWhenVisible(By.className("r"), 10);
		
		//Get the href value from the first search result item.
		String urlLink = searchResults.get(0).findElement(By.tagName("a")).getAttribute("href");
		
		//Test the expected URL is loaded found from the first search result item.
		Assert.assertEquals(urlLink, "http://mobileintegration-group.com/");
	}
	  
	@Test (dataProvider="RBASearchCriteria")
	//This case is a good candidate for data driven testing. 
	//I created the test case expandable for more combination of data driven testing
	//The test data can be store in XLSX or CSV file, but for simplicity of the purpose of this MIW interview process, 
	//I added a hard coded data set within the same java file.
	//All available search criteria elements and their handling can be added to make this test case more complete, but not added for this round of interview question for now.
	public void verifyRitchieBrosAuctioneersSearch_Caterpilar(String industry, String category, String startYear, String endYear) {
			
		driver.get("https://www.rbauction.com");
		
		//Click the More Search Options Search button when the button is ready explicit wait time of 10 seconds 
		clickWhenReady(By.className("more-search"), 10);
		
		//Select the requested industry value from the Industry drop down list
		if (!industry.isEmpty()) {
			WebElement selectAdvIndustry = getWebElementWhenVisible(By.id("adv-industry"), 10);
			Select industryDL = new Select(selectAdvIndustry);
			List<WebElement> industryOpstions = industryDL.getOptions();
		    
			for (WebElement option : industryOpstions) {
		        if (option.getText().toLowerCase().equals(industry.toLowerCase())) {
		            option.click();
		            break;
		        }
		    }
		}
		
		//Select the requested category value from the Category drop down list
		if (!category.isEmpty()) {
			WebElement selectCategory = getWebElementWhenVisible(By.id("adv-category"), 10);
			
			Select categoryDL = new Select(selectCategory);
			List<WebElement> categoryOpstions = categoryDL.getOptions();
			
			for (WebElement option : categoryOpstions) {
		        if (option.getText().toLowerCase().equals(category.toLowerCase())) {
		            option.click();
		            break;
		        }
		    }
		}
		
		//Enter the start year for the search
		if (!startYear.isEmpty()) {
			WebElement startYearInput = getWebElementWhenVisible(By.name("txtSelectManuYearStart"), 10);
				startYearInput.sendKeys(startYear);
		    }
	  
			//Enter the start year for the search
		if (!endYear.isEmpty()) {
			WebElement endYearInput = getWebElementWhenVisible(By.name("txtSelectManuYearEnd"), 10);
			endYearInput.sendKeys(endYear);
		}
					
		//Start advanced search by clicking "Search Equipment" button, which is identified with class name "qa-auto-adv-search-submit"
		clickWhenReady(By.className("qa-auto-adv-search-submit"), 10);
		
		//If Feedback dialog is present,dismiss the dialog to proceed with the rest of the testing.
		try {
			if (getWebElementWhenVisible(By.className("fsrDialogs"), 5).isDisplayed()) {
				clickWhenReady(By.className("fsrDeclineButton"), 5);
			}					
		}
		catch (NoSuchElementException e) {
			System.out.println("The Feedback dialog is not present at this time of test run.  Continue to the rest of testing procedures.");
		}
		catch (TimeoutException e) {
			System.out.println("The Feedback dialog is not present at this time of test run.  Continue to the rest of testing procedures.");
		}
		
		List<WebElement> searchResults = getWebElementsWhenVisible(By.className("search-results-item"), 3);
		
		//Get the Details text from the first search result item
		String shortDetails = searchResults.get(0).findElement(By.className("list-only")).getText();
		
		
		System.out.println("==================================================================================");
		System.out.println("Answer for the Interview Questipm Q3 =============================================");
		System.out.println("==================================================================================");
		System.out.println(shortDetails);
		System.out.println("==================================================================================");
		
		//Verify if the details has any non-empty string value
		Assert.assertTrue(!shortDetails.isEmpty());
	}
	  
}
