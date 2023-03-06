# Excelenium (Excel + Selenium)

## Why did I build this tool 
Selenium Java API gives a tremendous power and ease of usage in hands of developers who want to automate browser navigations through their java programs. However, when it comes to use this for testing automation, testers tend to shy away from this tool as it is required to write some amount of java code to achieve this automation. Keeping all this in mind, Excelenium tries to make it simpler for testers by hiding all selenium code in a jar and using excel file to read test cases to be executed. Only thing now one has to do is to prepare excel file, mention all steps in an easy to understand command syntax and pass this to Excelenium. Rest will be taken care by Excelenium.


## Required Settings:

1. Place the mvn jar file in required app folder.
2. Place the resources folder ouside the app folder.
3. update the mail properties.
4. Test cases can be updated in Main.xlsx

If this is giving cert error, check your settings.xml file in the <user>/.m2 folder and follow instructions in this link
https://stackoverflow.com/questions/25911623/problems-using-maven-and-ssl-behind-proxy

## To run the application.

java -jar [jar file name] Main.xlsx
