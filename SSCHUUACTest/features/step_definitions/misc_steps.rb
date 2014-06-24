When(/^I swipe in from the right$/) do
  performAction('drag',1,99,50,50,5)
end

Then(/^I should be on the upgrade screen$/) do
  drawer_screen = page(UpgradeScreen).await
end
