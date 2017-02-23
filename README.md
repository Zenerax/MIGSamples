# MIGSamples
MIG Interview Question Codes

Aaron, 

I added the source code and the compiled test on this GIT

I used recent build of Java EE, Selenium and Test NG builds for the test automation.  I added additional details you requested below.

How to Build:
  1. Please sync the test code and add necessary Selenium and TestNG libraries to the project.
  2. Add ChromeDriver.exe file at the root of the project folder.
  3. If you have problems building the test project, please consider running the test using a command line batch script such as:
  
        set projectPath=<Full path to project root>
        set workspace=<Full path to your workspace>
        
        cd %projectPath%
        set classpath=%projectPath%\bin;<Full path to Selenium and TestNG libraries>\*;
        java org.testng.TestNG %projectPath%\src\mig_tests\TestGroups.xml
        pause
  
  4.  QA Test Plan:
    Use Chrome for this round of testing.
    Parameterized testing for the Brower drivers will help building up cross browser testing for more test coverage.
    Explicit wait is added to lookup Web elements to avoid errors from unexpeted timing issues.
    For the web site with random user feedback dialog, the diaglog is detected and dismissed dynamically to continue testing.
    
    4.1.  The web Google web site http://www.google.com is opens and shows key web element and the browser URL shows.
          PASS: The actual URL matches https://www.google.com/ and the Search button is displayed
          FAIL: If the URL does not match the expected URL or the Search button is not present, consider test failed.
          
    4.2. The Google web site is opened and search is initiated for "Mobile Ingetration Group" and the top non-ad search result 
         contains the result item with URL http://mobileintegration-group.com/.
         PASS: The actual URL from the first non-ad search result item matches the expected url http://mobileintegration-group.com/
         FAIL: The actual URL from the first non-ad search result item does not match the expected url.
         
    4.3. Test the search result with advanced search conditions return non-empty string for the details of the item returned fromt the  
         searh.
         PASS: The first search result item contains non-empty string.
         FAIL: The first search result item contains empty string.
   
   5. I created a few extra functions to locate web elements and click action on them with explicit wait to avoid unwanted test problems with web page loading timing issue.  Also I added @BeforeTest and @AfterTest functions for test setup and clean up.
         
  
