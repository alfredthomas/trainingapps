Feature: Basic Feature

#  Scenario: Click on a spin wheel
#	Then I rotate the device to portrait
#	Then I wait 5 seconds
#	Then I rotate the device to landscape
@upgrade
  Scenario: I open the upgrade menu
	When I swipe in from the right
	Then I should be on the upgrade screen
	
@chart
  Scenario: I swipe through data charts
  	Then I swipe right
  	Then do nothing