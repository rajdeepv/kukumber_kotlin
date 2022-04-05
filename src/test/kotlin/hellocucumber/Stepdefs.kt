package hellocucumber

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.BeforeAll
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration

val appiumServerMap = mapOf(
    "cucumber-runner-1-thread-1" to "http://127.0.0.1:3000/wd/hub",
    "cucumber-runner-1-thread-2" to "http://127.0.0.1:4723/wd/hub",
    "main" to "http://127.0.0.1:4723/wd/hub"
)

val appiumportMap = mapOf(
    "cucumber-runner-1-thread-1" to "5001",
    "cucumber-runner-1-thread-2" to "6001",
    "main" to "5001"
)

@BeforeAll
fun beforeAll(): Unit {
    println(">> ${Thread.currentThread().name} BeforeAll")
}

class StepDefs {

    init {
        println(">> ${Thread.currentThread().name} initialized StepDefs")
    }

    lateinit var driver: AndroidDriver
    private val URL_STRING = appiumServerMap[Thread.currentThread().name]
    lateinit var capabilities: DesiredCapabilities

    fun setupAppium() {
        capabilities = DesiredCapabilities()
        capabilities.let {
//            it.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554")
            it.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".ui.launcher.DebugLauncherActivity")
            it.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.bumble.app")
            it.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "*")
            it.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            it.setCapability(MobileCapabilityType.AUTOMATION_NAME, "espresso")
            it.setCapability("skipServerInstallation", true)
            it.setCapability(AndroidMobileCapabilityType.NO_SIGN, true)
            it.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0)
            it.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true)
            it.setCapability(AndroidMobileCapabilityType.SKIP_UNLOCK, true)
            it.setCapability(AndroidMobileCapabilityType.INTENT_ACTION, "android.intent.action.MAIN")
            it.setCapability(AndroidMobileCapabilityType.INTENT_CATEGORY, "android.intent.category.LAUNCHER")
            it.setCapability(AndroidMobileCapabilityType.INTENT_FLAGS, "FLAG_ACTIVITY_NEW_TASK")
            it.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, appiumportMap[Thread.currentThread().name])
//            it.setCapability(ESPRESSO_BUILD_CONFIG_OPTION, EspressoBuildConfig("{\"toolsVersions\": {\"composeVersion\": \"1.1.1\", \"compileSdk\":\"31\", \"kotlin\":\"1.6.10\"}}"))
            it.setCapability("tmpDir", Thread.currentThread().name)
            it.setCapability(MobileCapabilityType.FULL_RESET, false)
        }
        val appiumServerUrl = URL(URL_STRING)
        println("Appium server URL: $appiumServerUrl")
        driver = AndroidDriver(appiumServerUrl, capabilities)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
    }

    @Before
    fun beforeHook() {
        println(">> ${Thread.currentThread().name} Before Hook")
        setupAppium()
    }

    @After
    fun afterHook() {
        driver.quit()
    }

    @Given("I am at home screen of Bumble")
    fun i_am_at_home_screen() {
        // do nothing
    }

    @When("I try to register by phone")
    fun i_try_to_register_by_phone() {
        driver.findElement(By.id("com.bumble.app:id/landing_manualLoginButton")).click()
    }

    @Then("I should be able to enter phone number")
    fun i_should_be_able_to_enter_phone_number() {
        Thread.sleep(1)
        val phNumber = driver.findElement(By.id("com.bumble.app:id/reg_input_edittext"))
        Assertions.assertEquals(phNumber.text, "Phone number")
    }

    @When("I try to register by phone number {string}")
    fun i_try_to_register_by_phone_number(string: String?) {
        driver.findElement(By.id("com.bumble.app:id/landing_manualLoginButton")).click()
        driver.findElement(By.id("com.bumble.app:id/reg_input_edittext")).sendKeys(string)
    }

    @Then("I should see phone number as {string}")
    fun i_should_see_phone_number_as(string: String?) {
        val phNumber = driver.findElement(By.id("com.bumble.app:id/reg_input_edittext"))
        Assertions.assertEquals(phNumber.text, string)
    }
}