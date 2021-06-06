Feature: Click on View Deal

Scenario Outline: User should click on desired View Deal.
Given Load Test Data from "<Test_ID>"
When use navigates to Trivago Weekend URL
And user enters required search criteria
Then user should see related search results
When user clicks on See more stays for the nearest location
Then user should navigate to new tab with all the available stays
When user clicks on View Deal for the cheapest and recommended option
Then user navigates to respective booking url in a new tab

Examples:
			|Test_ID|
			|TC_01|
			#|TC_02|