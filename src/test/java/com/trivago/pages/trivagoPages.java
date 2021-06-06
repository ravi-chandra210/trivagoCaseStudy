package com.trivago.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.trivago.utils.utils;


public class trivagoPages {
	
	/**
	 * @author : Srinivasa Ravi Chandra Muktavarapu
	 * @category : Trivago case Study
	 * @Task : Automation of given Scenario
	 * @date : 04/06/2021
	 */
	protected static RemoteWebDriver driver;

	/************************************************ Static variables to store Excel sheet data **************************************/
	static String testCaseID; 
	static String browser;
	static String url;
	static String currency;
	static String location;
	static String radius;
	static String toDate;
	static String fromDate;
	static String adultGuestCount;
	static String childGuestCount;
	static String roomCount;
	static String booking_Partner;

	/************************************************** Trivago Page Elements ******************************************************/

	String logo = "//div[@class='Logo_logo__3wiNX']";
	String currDrpButton = "(//div[@class = 'ControlButton_button__r3jp5' and @role='button'])[1]";
	String SelectCurrncy = "(//li[@data-qa='currency-option'])[1]";
	String searchLocation = "(//input[contains(@class, 'DestinationControl_input__23k7O') and @type='search'])[1]";
	String appearedLocation = "//button[contains(@class,'Button_button__1uuDa')]//span[@class ='DestinationItem_label__1nzfS']";
	String searchRadius = "(//span[@class='IconLabel_label__rUNtX IconLabel_truncateLabel__PDMe-'])[6]";
	String radiusValue = "//span[@class='ControlSelectItem_label__IbRcQ']";
	String searchFrmDate = "(//div[@class='ControlButton_button__r3jp5' and @role='button'])[4]";
	String calendar ="//time[@class='Day_label__MJnuS']";
	String searchToDate = "(//div[@class='ControlButton_button__r3jp5' and @role='button'])[5]";
	String searchGuest = "(//div[@class = 'ControlButton_button__r3jp5' and @role='button'])[6]";
	String addAdult = "(//input[@class='NumberInput_value__1i8N5'])[1]";
	String addChild = "(//input[@class= 'NumberInput_value__1i8N5'])[2]";
	String addRoom = "(//input[@class= 'NumberInput_value__1i8N5'])[3]";
	String applyButton = "//button[contains(@class, '4Hjca') and contains(text(),'Apply')]";
	String searchRsltHdr = "//h4[@class='Header_title__2OWux']";
	String src_HdrContainer = "//div[@class = 'Header_container__3buHG']";
	String srcHeading = "//h3[@class = 'Title_title__21wSL' ]";
	String search_Tiles = "//ul[@class='DealTilesDesktop_tiles__27xGH']";
	String searchRsltDistance = "//div[@class = 'Header_distance__2cEOq']";
	String searchRsltSubHdr = "//div[@class = 'Header_subline__3EWj1']";
	String seeMoreStaysLink = "//a[@title = 'See more stays']";
	String sortedStays = "//div[@class = 'item__flex-column']";
	String sorted_Hdr = "//span[@class='item-link name__copytext']";
	String sortedPrices = "//strong[@class='accommodation-list__price--e1070']";
	String sortedRatings = "//span[contains(@class , 'item-components__pillValue--eaee3')]";
	String viewDealButton = "//span[contains(text() , 'View Deal')]";
	String sortBy = "//select[@id='mf-select-sortby']";
	String viewDealSearch = "//button[@data-qa='search-button']";
	String sortedStayPageHdrSec = "//div[@id='js-fullscreen-hero']";
	String bookingPartner = "//span[@class='accommodation-list__partner--ce464']";

	/********************************************** Methods ******************************************************/

	/*..... Required class objects .....*/
	utils utilsOb = new utils();
	org.apache.logging.log4j.Logger log = LogManager.getLogger(trivagoPages.class);


	/*..... Browser System properties .....*/
	static {
		System.setProperty("webdriver.chrome.driver", "./software/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "./software/geckodriver.exe");
	}

	/*..... This Method will read test data from provided excel sheet .....*/
	public void getSheetData(String testName){
		try {
			utils.getTestData(testName);
			testCaseID=utils.storeData("Test Case Name");
			System.out.println("Executing testCaseId...." + testCaseID );
			browser = utils.storeData("BROWSER");
			url = utils.storeData("WEEKEND_URL");
			currency = utils.storeData("CURRENCY");
			location = utils.storeData("LOCATION");
			radius = utils.storeData("RADIUS");
			fromDate = utils.storeData("FROM_DATE");
			toDate = utils.storeData("TO_DATE");
			adultGuestCount = utils.storeData("ADULT_GUESTS");
			childGuestCount = utils.storeData("CHILDREN_GUESTS");
			roomCount = utils.storeData("ROOMS");
		} catch (IOException e) {
			log.error("Error occured while reading the test data from excel sheet");
		}
	}

	/*.....This Method will Launch browser and launching trivago weekend URl. 
	 * Browser name and Website URl is passed from Test data excel sheet .....*/
	public WebDriver launchTrivagoWeekendWebsite(){
		try {
			if(browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}else if(browser.equalsIgnoreCase("Firefox")) {
				driver = new FirefoxDriver();                      
			}
			utilsOb.waitForElm(1);
			driver.get(url);		
			driver.manage().window().maximize();
			utilsOb.waitForElm(3);
			utilsOb.waitForGivenElem(driver, logo);
		} catch (Exception e) {
			log.error("Error occured while launching the trivago website in desired browser");
		}
		return driver;
	}

	/*..... This Method will enter values in search criteria for trivago weekend portal. 
	 * Search criteria is passed from Test data excel sheet .....*/
	public void enterSearchCriteria() {
		utilsOb.waitAndClick(driver,1,currDrpButton);
		utilsOb.selectElemByText(driver, SelectCurrncy, currency);
		utilsOb.sendInput(driver, searchLocation, location);
		utilsOb.waitAndClick(driver,1,appearedLocation);
		utilsOb.waitAndClick(driver,2,searchRadius);
		utilsOb.selectElemByText(driver, radiusValue, radius);
		utilsOb.waitAndClick(driver,2, searchFrmDate);
		utilsOb.selectElemByDateValue(driver, calendar, fromDate);
		utilsOb.waitAndClick(driver,2, searchToDate);
		utilsOb.selectElemByDateValue(driver, calendar, toDate);
		utilsOb.waitAndClick(driver,1, searchGuest);
		utilsOb.sendInput(driver, addAdult, adultGuestCount);
		utilsOb.sendInput(driver, addChild, childGuestCount);
		utilsOb.sendInput(driver, addRoom, roomCount);
		utilsOb.waitAndClick(driver,2, applyButton);
	}

	/*..... This Method will verify the stays shown for nearest search destination results .....*/
	public void verifyBestStays() {
		utilsOb.switchToWindow(driver, 1);
		utilsOb.waitAndClick(driver, 1, viewDealSearch);
		utilsOb.selectDropDownByVisibleText(driver, sortBy, "Price & Recommended");
		utilsOb.waitForElm(2);
	}

	/*..... This Method will choose the cheapest stay option shown for nearest search destination results and click on view deals button.....*/
	public void verifySortedStaysAndClickOnViewDeal() {
		String key = null;
		String value = null;
		int val =0;
		try {
			List <WebElement> sortedStay_box = driver.findElements(By.xpath(sortedStays));
			List <WebElement> sorted_Prices = driver.findElements(By.xpath(sortedPrices));
			Map<String, Integer> searchData = new HashMap<String, Integer>();
			if(sortedStay_box.size()>0 && sorted_Prices.size()>0) {
				for(int i=0;i<sortedStay_box.size();i++){
					key = sortedStay_box.get(i).getText();
					value = utilsOb.trimString(sorted_Prices.get(i).getText());
					value = utilsOb.brkString(value, 1);
					val = utilsOb.convertStringInt(value);
					searchData.put(key, val);
				}	 
				log.info("Search result is found as : " + searchData);
				String  cheapestPlace = utilsOb.getMinValKeyofMap(searchData);
				cheapestPlace = utilsOb.brkStringByText(cheapestPlace, "to");
				System.out.println("***" + cheapestPlace + "***");
				log.info("Best place to visit on desired weekend is :" + cheapestPlace);
				for(int k=0;k<sortedStay_box.size();k++){
					WebElement currElem = sortedStay_box.get(k);
					String curPlace = currElem.getText();
					//System.out.println(curPlace);
					if(curPlace.contains(cheapestPlace)){
						int currentThread = k+1;
						currElem.findElement(By.xpath("("+viewDealButton+")["+currentThread+"]")).click();
						booking_Partner = currElem.findElement(By.xpath("("+bookingPartner+")["+currentThread+"]")).getText();
					}
				}
			}else {
				utilsOb.verifyGivenWebElement(utilsOb.selectElement(driver, sortedStayPageHdrSec));
			}			
		}catch(Exception e) {
			log.error("Error occured while verifying the sorted stays");
		}
	}

	/*..... This Method will verify the header for the see more options page for nearest shown destination .....*/
	public void verifySearchResult() {
		utilsOb.verifyGivenWebElement(utilsOb.selectElement(driver, src_HdrContainer) );
	}

	/*..... This Method will verify the user is navigated to partners website successfully 
	 * when clicked on view deal button and close the browser.....*/
	public void verifyPartnerWebsiteIsOpnWithViewDeal() {
		utilsOb.verifyPageTitle(driver, booking_Partner);
		closeBrowser();
	}

	/*..... This Method will close all the active session of browser .....*/
	public void closeBrowser() {
		driver.quit();
	}

	/*..... This Method will choose the nearest shown destination and click on see more options link .....*/
	@SuppressWarnings("null")
	public void verifyAndClickNearestOption() {
		String key = null;
		String value = null;
		int val =0;
		try {
			List <WebElement> placesHdr = driver.findElements(By.xpath(searchRsltHdr));
			List <WebElement> src_PlHdrs = driver.findElements(By.xpath(src_HdrContainer));
			List <WebElement> placeDist = driver.findElements(By.xpath(searchRsltDistance));
			Map<String, Integer> searchData = new HashMap<String, Integer>();
			if(placesHdr.size()>0 && placeDist.size()>0) {
				for(int i=0;i<placesHdr.size();i++){
					key = placesHdr.get(i).getText();
					value = utilsOb.trimString(placeDist.get(i).getText());
					val = utilsOb.convertStringInt(value);
					searchData.put(key, val);
				}	 
				log.info("Search result is found as : " + searchData);
				String  nearestPlace = utilsOb.getMinValKeyofMap(searchData);
				log.info("Nearest place to visit on desired weekend is :" + nearestPlace);
				for(int k=0;k<src_PlHdrs.size();k++){
					WebElement currElem = src_PlHdrs.get(k);
					String curPlace = currElem.getText();
					if(curPlace.contains(nearestPlace)) {
						int currThrd = k+1;
						currElem.findElement(By.xpath("("+seeMoreStaysLink+")["+currThrd+"]")).click();
					}else {
			
					}
				}
			}else {
				utilsOb.verifyGivenWebElement(utilsOb.selectElement(driver, search_Tiles));

			}			
		}catch(Exception e) {
			log.error("Error occured while verifying the search results");
		}
	}
}
