package com.trivago.stepdefs;

import com.trivago.pages.trivagoPages;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class trivagoStepDef {
	trivagoPages trivagoOb = new trivagoPages();
	@Given("Load Test Data from {string}")
	public void load_Test_Data_from(String testCaseID) {
		trivagoOb.getSheetData(testCaseID);
	}

	@When("use navigates to Trivago Weekend URL")
	public void use_navigates_to_Trivago_Weekend_URL() {
		trivagoOb.launchTrivagoWeekendWebsite();
	}

	@When("user enters required search criteria")
	public void user_enters_required_search_criteria() {
		trivagoOb.enterSearchCriteria();
	}

	@Then("user should see related search results")
	public void user_should_see_related_search_results() {
		trivagoOb.verifySearchResult();
	}

	@When("user clicks on See more stays for the nearest location")
	public void user_clicks_on_See_more_stays_for_the_nearest_location() {
	    trivagoOb.verifyAndClickNearestOption();
	}

	@Then("user should navigate to new tab with all the available stays")
	public void user_should_navigate_to_new_tab_with_all_the_available_stays() {
		trivagoOb.verifyBestStays();
	}

	@When("user clicks on View Deal for the cheapest and recommended option")
	public void user_clicks_on_View_Deal_for_the_cheapest_and_recommended_option() {
		trivagoOb.verifySortedStaysAndClickOnViewDeal();
	}

	@Then("user navigates to respective booking url in a new tab")
	public void user_navigates_to_respective_booking_url_in_a_new_tab() {
		trivagoOb.verifyPartnerWebsiteIsOpnWithViewDeal();
	}

}
