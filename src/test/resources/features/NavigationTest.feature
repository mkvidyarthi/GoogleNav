Feature: Automate Google Navigation

Scenario: Navigate to specified coordinates and print the route title, distance in miles, and the travel time to a file titled “routes.txt”.

Given Launch Chrome and maximize the window
When Navigate to Google Maps
And Search for San Francisco, California
Then Verify the coordinates for San Francisco are 37.7577627,-122.4726194
Then Search for driving directions from Chico, California to San Francisco, California
Then Verify two or more routes are displayed in the list and print in text file
