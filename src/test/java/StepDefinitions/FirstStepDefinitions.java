package StepDefinitions;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FirstStepDefinitions {
	WebDriver driver;
	List<WebElement> webElementList = null;
	HashMap<String, String> objects;
	
	@Before
    public void initializeTest(){
		System.out.println("Starting Test Execution");
		buildObjectRepo();
		//Webdriver manager helps to download drivers using maven dependency 
		WebDriverManager.chromedriver().setup();
	
		//Creating WebDriver object to perform automation on chrome browser
		driver = new ChromeDriver();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                // Code to capture and embed images in test reports (if scenario fails)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }
    
    @Given("^I am on the \"([^\"]*)\" page on URL \"([^\"]*)\"$")
    public void i_am_on_the_page_on_URL(String arg1, String arg2) throws Throwable {
    	driver.get(arg2);
        assertEquals(driver.getTitle(),arg1);
    }

    @When("^I fill in \"([^\"]*)\" with \"([^\"]*)\"$")
    public void i_fill_in_with(String object, String text) throws Throwable {
        webElementList = getObject(object);
        assertEquals(webElementList.size(),1);
        webElementList.get(0).sendKeys(text);
        webElementList.get(0).sendKeys(Keys.TAB);
    }

    @When("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String object) throws Throwable {
        webElementList = getObject(object);
        assertEquals(webElementList.size(),1);
        webElementList.get(0).click();
    }


    @Then("^I should see the \"([^\"]*)\" button$")
    public void i_should_see_the_button(String name) throws Throwable {
        webElementList = getObject(name);
        assertEquals(webElementList.size(),1);
        assertEquals(webElementList.get(0).getAttribute("aria-label"),name);
    }

    @Then("^I should see page having title \"([^\"]*)\"$")
    public void iShouldSeePageHavingTitle(String pageTitle) throws Throwable {
        assertEquals(driver.getTitle(),pageTitle);
    }
    
    public List<WebElement> getObject(String name){
        String[] object = objects.get(name).split("\\|");
        String identifier = object[0];
        String property = object[1];
        List<WebElement> we = null;
        switch (identifier.toLowerCase()){
            case "name":
                we = driver.findElements(By.name(property));
                break;
            case "xpath":
                we = driver.findElements(By.xpath(property));
                break;
            case "css":
                we = driver.findElements(By.cssSelector(property));
                break;
            case "id":
                we = driver.findElements(By.id(property));
                break;
            case "tag":
                we = driver.findElements(By.tagName(property));
                break;
            case "linktext":
                we = driver.findElements(By.linkText(property));
                break;
            case "classname":
                we = driver.findElements(By.className(property));
                break;

        }
        return we;
    }
    
    public void buildObjectRepo() {
    	objects = new HashMap<>();
    	objects.put("Google Search", "xpath|//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]");
    	objects.put("Search","name|q");
    }
}
