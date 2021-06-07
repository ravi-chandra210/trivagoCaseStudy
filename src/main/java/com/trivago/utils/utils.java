package com.trivago.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.jsoup.helper.HttpConnection.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import jxl.Workbook;
//import jxl.common.Logger;
import jxl.read.biff.BiffException;

public class utils {

	/**
	 * @author : Srinivasa Ravi Chandra Muktavarapu
	 * @category : Trivago case Study
	 * @Task : Automation of given Scenario
	 * @date : 02/06/2021
	 */
	static Logger log1 = Logger.getLogger(utils.class.getName());
	
	public static ArrayList<Map<String, String>> excelRows= null;
	public static Map<String, String> data = null;
	public static String tcName=null;
	public static int rowVal;
	public static int cellVal;
	public static String val=null;
	public static int dataIndex = 0;
	org.apache.logging.log4j.Logger log = LogManager.getLogger(utils.class);

	/******************* Methods to read excel sheet and store data in Hashmaps ***************/
	public static void getTestData(String testName) throws IOException {

		try {
			tcName = testName;
			excelRows = new ArrayList<Map<String, String>>();
			if (dataIndex == -1) {
				return;
			}
			String dir = System.getProperty("user.dir") + "\\TestData\\TestCasesNew.xls";
			System.out.println("This is the path of the test data file : "+dir);
			Workbook wb = Workbook.getWorkbook((new File(dir)));
			jxl.Sheet dataSheet = wb.getSheet("Test Data");
			data = new HashMap<String, String>();
			System.out.println(testName);
			dataIndex = dataSheet.findCell(testName).getRow();
			for (int i = 0; i < dataSheet.getColumns(); i++) {
				String key = dataSheet.getCell(i, 0).getContents();
				String value = dataSheet.getCell(i, dataIndex).getContents();
				data.put(key, value);
			}
			excelRows.add(data);
			System.out.println(data);
			System.out.println("getTestData method completed...");
		} catch (IOException | BiffException e) {
			e.printStackTrace();
		}
	}

	public static String storeData(String key) {
		return data.get(key);		
	}

	/*.....All required Selenium webdriver methods are overridden here in utils class in order to achieve a effective automation script .....*/
	public void waitForElm(int wt) {
		try {
			int wait = wt*1000;
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}
	
	public static void logRequestAndResponseDetails(Response response, Logger log) {
		log.info("Status Code--->" + response.statusCode());
		log.info("Response--->" +response.toString());
		log.info("Response Headers--->" + response.headers());
		
	}

	public void verifyGivenWebElement(WebElement elem) {
		try {
			waitForElm(3);
			if(elem.isDisplayed()==true) {
				log1.info("Webelement is displayed on webpage");
			}else {
				System.out.println("WebElement user is trying looking for is not displayed on web page " );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void verifyPageTitle(WebDriver driver, String PageTitle) {
		waitForElm(2);
		switchActiveWindow(driver);
		String pT = getPageTitle(driver);
		if(pT.contains(PageTitle)) {
			log1.info("User is on correct webpage");
		}else {
			System.out.println("User is navigated to some other page which is : " + pT);
		}
	}

	public String brkString(String s, int ind) {
		String str = s.substring(ind);
		return str;
	}
	
	public String brkStringByText(String s, String ind) {
		String [] strArray = s.split(ind);
		String str = strArray[0];
		return str;
	}

	public void clickElemWithJS(WebDriver driver, WebElement elem) {
		try {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", elem);
		}catch(Exception e) {
			log1.error("Error occurred when trying to click on the element with JaVaScript" + e);
		}
	}

	public void scrollPageDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");
	}

	public void clearElm(WebDriver driver, String xpath) {
		try {
			waitForGivenElem(driver, xpath);
			WebElement elem = selectElement(driver, xpath);
			if(elem.isDisplayed()==true) {
				elem.clear();
			}
		} catch (Exception e) {
			log1.error("Error occurred while clearing Input filed");
		}
	}

	public void selectDrpDwnValue(WebDriver driver, String xpath, String value) {
		try {
			waitForGivenElem(driver, xpath);
			WebElement elem = selectElement(driver, xpath);
			Select dropdomain = new Select(elem);
			dropdomain.selectByValue(value);
		} catch (Exception e) {
			log1.error("Error occurred while selecting dropdown value");
		}
	}

	public void sendInput(WebDriver driver, String xpath, String inputValue) {
		try {
			WebElement elem = selectElement(driver, xpath);
			waitForElm(1);
			clearWebField(elem);
			elem.sendKeys(inputValue);
		} catch (Exception e) {
			log1.error("Error occurred while sending input value");
		}
	}
	
	public void selectDropDownByVisibleText (WebDriver driver, String xpath, String Text) {
		Select dropdown = new Select(selectElement(driver, xpath));
		dropdown.selectByVisibleText(Text);
	}

	public void takeScrShot(WebDriver driver) throws IOException {
		try {
			TakesScreenshot p1 = (TakesScreenshot)driver;
			File actual = p1.getScreenshotAs(OutputType.FILE);
			File expected = new File("./screenshots/" + getCurrentTime() +".jpeg");
			FileUtils.copyFile(actual, expected);
		} catch (Exception e) {
			log1.error("Unable to take screenshot of required screen.");
		} 
	}

	public String getCurrentTime() {
		String time = null;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			time = dtf.format(now);
		} catch (Exception e) {
			log1.error("Error occurred while capturing the current time");
		}
		return time;
	}

	public WebElement selectElement(WebDriver driver, String xPath) {
		WebElement element = null;
		try {
			element = driver.findElement(By.xpath(xPath));
		} catch (Exception e) {

			log1.error("Error occurred while finding the element");
		}
		return element;	
	}

	public void switchToiframe(WebDriver driver) {
		try {
			WebElement iframe1 = driver.findElement(By.id("the_message_iframe"));
			driver.switchTo().frame(iframe1);
		} catch (Exception e) {
			log1.error("Iframe is not displayed");
		}	
	}

	public void openNewWindow(WebDriver driver , String url) {
		((JavascriptExecutor)driver).executeScript("window.open()");
		switchToWindow(driver, 1);
		waitForElm(2);
		driver.get(url);
	}

	public void waitForGivenElem(WebDriver driver , String xpath) {
		try {
			WebDriverWait w = new WebDriverWait(driver,10);
			w.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			log1.error("Error Occurred while waiting for the element");
		}
	}

	public void waitAndClick(WebDriver driver ,int wait, String xpath) {
		try {
			waitForElm(wait);
			//WebDriverWait w = new WebDriverWait(driver,10);
			//w.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			log1.error("Error Occurred while waiting for the element to be clickable");
		}
	}

	public void clearWebField(WebElement element){
		while(!element.getAttribute("value").equals("")){
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public void selectElemByText(WebDriver driver , String xpath, String elmTxt) {
		try {
			List <WebElement> elms = driver.findElements(By.xpath(xpath));
			WebElement elm = null;
			for(int i=0;i< elms.size();i++) {
				elm = elms.get(i);
				String elmText = elm.getText();
				if(elmText.contains(elmTxt)) {
					elm.click();
				}
			}
			log1.info(elmTxt +" Element is selected successfully ");
		} catch (Exception e) {
			log1.error("Error Occurred while extracting the "+ elmTxt + " element text");
		}
	}

	public void selectFirstElemInList(WebDriver driver , String xpath) {
		try {
			List <WebElement> elms = driver.findElements(By.xpath(xpath));
			for(int i=0;i< elms.size();i++) {
				elms.get(0).click();
			}			
		} catch (Exception e) {
			log1.error("Error Occurred while waiting for the element to be clickable");
		}
	}

	public void selectElemByDateValue(WebDriver driver , String xpath, String date) {
		try {
			List <WebElement> elms = driver.findElements(By.xpath(xpath));
			WebElement elm = null;
			for(int i=0;i< elms.size();i++) {
				elm = elms.get(i);
				String elmText = elm.getAttribute("datetime");
				if(elmText.equals(date)) {
					elm.click();
				} 
			}			
		} catch (Exception e) {
			log1.error("Error Occurred while selecting calendar date");
		}
	}

	public void switchActiveWindow(WebDriver driver) {
		driver.switchTo().activeElement();	   
	}

	public void switchToWindow(WebDriver driver , int window) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if(window<tabs.size()) {
			driver.switchTo().window(tabs.get(window));
		}else {
			log1.error("Desired window is not opened");
		}
	}

	public String getElemText(WebDriver driver , String xpath) {
		String txt = null;
		try {
			waitForGivenElem(driver, xpath);
			WebElement elem = selectElement(driver, xpath);
			if(elem.isDisplayed()==true) {
				txt = elem.getText();
			}
		} catch (Exception e) {
			log1.error("Element for which user wants to get the text is not displayed");
		}
		return txt;
	}

	public String getPageTitle(WebDriver driver) {
		waitForElm(1);
		return driver.getTitle();
	}

	public String trimString(String str) {
		String[] strAr = str.split(" ");
		return strAr[0];
	}

	public int convertStringInt(String str) {
		int num = 0;
		try{
			num = Integer.parseInt(str);
		}
		catch (NumberFormatException ex){
			ex.printStackTrace();
		}
		return num;
	}

	public int getMinValofList(List<Integer> lst) {
		int minList = 0;
		if(lst.size()!=0) {
			minList = Collections.min(lst);
		}else {
			System.out.println("Passed List is empty");
		}
		return minList;	
	}

	public String getMinValKeyofMap(Map<String ,Integer> mp) {
		String minValKey = null;
		if(mp.size()!=0) {
			Integer minValue = Collections.min(mp.values());
			
			for (Map.Entry<String, Integer> entry: mp.entrySet())
	        {
	            if (minValue.equals(entry.getValue())) {
	            	minValKey = entry.getKey();
	            }
	        }
		}else {
			System.out.println("Passed List is empty");
		}
		return minValKey;	
	}
}
