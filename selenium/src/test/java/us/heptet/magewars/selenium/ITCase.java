package us.heptet.magewars.selenium;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.google.common.base.*;
import com.google.common.base.Objects;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;


import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.*;
import org.slf4j.bridge.SLF4JBridgeHandler;
import us.heptet.magewars.test.ExternalTestCase;

import javax.annotation.Nullable;

import static junit.framework.TestCase.fail;
//import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

@RunWith(JUnitPlatform.class)
public class ITCase {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ITCase.class);

    private static final String XPATHEXP_CHILDREN = "*";
    private static final String FILENAME_INDEXHTML = "index.html";

    private static final String GWTID_USERNAMEBOX = "gwt-debug-usernamebox";
    private static final String GWTID_PASSWORDBOX = "gwt-debug-passwordbox";
    private static final String GWTID_LOGINBTN = "gwt-debug-loginbtn";
    private static final String GWTID_CREATEGAMEBUTTON = "gwt-debug-createGameButton";
    private static final String GWTID_GAMENAMEBOX = "gwt-debug-gameNameBox";
    private static final String GWTID_OKBUTTON = "gwt-debug-okButton";
    private static final String GWTID_GAMEDETAILSTABLE = "gwt-debug-gameDetailsTable";
    private static final String GWTID_JOINGAMEBUTTON = "gwt-debug-joinGameButton";
    private static final String GWTID_SIGNUPBUTTON = "gwt-debug-signupButton";
    private static final String GWTID_SIGNUPLINK = "gwt-debug-signupLink";
    private static final String GWTID_PASSWORD1BOX = "gwt-debug-password1Box";
    private static final String GWTID_PASSWORD2BOX = "gwt-debug-password2Box";
    private static final String GWTID_EMAILBOX = "gwt-debug-emailBox";
    private static final String GWTID_SIGNUPUSERNAMEBOX = "gwt-debug-usernameBox";
    private static final String CLASSNAME_SPELLBOOKTREE = "spellbooktree";

    private static final String COOKIENAME_JSESSIONID = "JSESSIONID";
    private static final String INPUTSTRING_GAMENAME = "selenium game";
    private static final String INPUTSTRING_USEREMAIL = "seltest@heptet.us";
    private static final String USERNAME_PREFIX = "seltest";
    private static final String INPUTSTRING_PASSWORD = "xyz";

    private static final String HEADERTEXT_GAMENAME = "Game Name";
    private static final String HEADERTEXT_GAMEUSER = "User";
    private static final String HEADERTEXT_GAMESTATUS = "Status";
    private static final String HEADERTEXT_GAMEACTIONS = "gameActions";

    private static final String TAGNAME_TBODY = "tbody";
    private static final String TAGNAME_TR = "tr";
    private static final String TAGNAME_TD = "td";
    private static final String TAGNAME_THEAD = "thead";

    private static final String CSSSELECTOR_TDTH = "td,th";
    private static final String CSSSELECTOR_ALL = "*";

    private static final String HTMLATTNAME_CLASS = "class";
    private static final String HTMLATTNAME_ARIAEXPANDED = "aria-expanded";

    private static final String ARIAEXPANDED_FALSE = "false";

    Random random = new Random();

    String moduleName = "magewars-webapp";
    String moduleVersion = "0.20-SNAPSHOT";

    static String propertyProjectPart = "magewars";

    static String reportDirProperty = propertyProjectPart + ".selenium.reportDir";
    static String reportDir = System.getProperty(reportDirProperty, "selenium-report");

    // this is set to webapp because it's the "Webapp" that we are testing, even though the test module is selenium
    String propertyModulePart = "webapp";
    String propertyPrefix = propertyProjectPart + "." + propertyModulePart + ".";
    String hostProperty = propertyPrefix + "host";
    String portProperty = propertyPrefix + "port";
    String contextPathProperty = propertyPrefix + "contextPath";
    String hostPageProperty = propertyPrefix + "hostPage";
    String webAppUrlProperty = propertyPrefix + "webAppUrl";

    String useFirefoxProperty = "useFirefox";


    private String contextPath = "/" + moduleName + "-" + moduleVersion;
    private String host = "localhost";
    private String hostPage = "/WebApp.html";
    private int port = 5060;
    private String webAppUrl;

    private List<WebDriver> drivers;
    private static WebDriver driver = null;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    String jsessionid;
    String smallPage;
    private String userName;
    private boolean fInit = false;
    private boolean fInitEachTest = true;
    private static boolean quitDriversOnExit = true;
    private boolean useFirefoxDriver = false;
    private boolean useHtmlUnitDriver = true;
    private boolean useRemoteDriver = false;
    private URI uri;
    private PhantomJSDriver phantomJSDriver;
    private static long startupTime;
    private static Path reportDirPath;
    private SuperDriverManager superDriverManager = new SuperDriverManager();
    private Driver whichDriver;
    private ManagedDriver<? extends WebDriver> managedDriver;

    public class Property {
        boolean hasProperty;
    }

    static List<Path> screenshots = new LinkedList<>();

    static boolean enableReport = true;

    @BeforeAll
    public static void beforeClass() {
        logger.trace("Before class");

        logger.debug("Report directory is {}", reportDir);
        logger.debug("Report directory property is {}", reportDirProperty);

        reportDirPath = Paths.get(reportDir);
        try {
            Files.createDirectory(reportDirPath);
        } catch (IOException x) {
            logger.error("Unable to create directory {}: {}", reportDirPath.toAbsolutePath(), x.toString());
            logger.warn("Report disabled.");
            enableReport = false;
        }

        startupTime = System.currentTimeMillis();
    }

    @AfterAll
    public static void afterClass() {
        if (quitDriversOnExit) {
            if (driver != null) {
                driver.quit();
            }
        }

        Path path = reportDirPath.resolve(FILENAME_INDEXHTML);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("utf-8"), StandardOpenOption.CREATE_NEW);

            writer.write("<html><head><title>Selenium Report</title></head><body>\n");
            screenshots.forEach((p) -> {
                try {
                    writer.write("<p><u>" + p.getFileName() + "</u></p><p><img src=\"" + p.getFileName().toString() + "\"></p>");
                } catch (IOException x) {
                }
            });
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        try {
            if (!fInit || fInitEachTest) {
                constructUrl();

                if (driver != null) {
                    driver.quit();
                }

                whichDriver = Driver.NONE;

                if(useRemoteDriver)
                {
                    whichDriver = Driver.REMOTE;
                } else {
                    useFirefoxDriver = Boolean.parseBoolean(getProperty(useFirefoxProperty, "false", null));
                    if (useFirefoxDriver) {
                        whichDriver = Driver.FIREFOX;
                    } else if (useHtmlUnitDriver) {
                        whichDriver = Driver.HTMLUNIT;
                    }
                }

                if(whichDriver == Driver.NONE)
                {
                    logger.error("no driver selected.");
                    throw new RuntimeException("No driver selected.");
                }
                managedDriver = superDriverManager.createManagedDriver(whichDriver);
                if(managedDriver == null)
                {
                    logger.error("null driver.");
                    throw new RuntimeException("null driver");
                }
                driver = managedDriver.getInstance();

/*
                if (useFirefoxDriver) {
                    logger.info("using firefox driver");
                    FirefoxProfile ffProfile;
                    //JavaScriptError.addExtension(ffProfile);

                    try {
                        ffProfile = new FirefoxProfile();
                        driver = new FirefoxDriver(ffProfile);
                    } catch (Exception ex) {
                        logger.error("Unable to create FirefoxDriver: {}", ex);
                    }
                    logger.info("driver is {}", driver);
                } else if (useHtmlUnitDriver) {
                    driver = new HtmlUnitDriver(true);
                } else {
*/

                driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

                fInit = true;
            }
        } catch (Exception ex) {
            logger.debug("Unable to complete setUp method: {}", ex);
            logger.error("Unable to complete setUp method: {}", ex);

        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (fInitEachTest) {
            if (driver != null) {
                driver.quit();
            }
        }

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private void constructUrl() throws URISyntaxException {
        logger.debug("Attempting to construct URL.");
        host = getProperty(hostProperty, host, null);
        port = Integer.parseInt(getProperty(portProperty, String.valueOf(port), null));
        contextPath = getProperty(contextPathProperty, contextPath, null);
        hostPage = getProperty(hostPageProperty, hostPage, null);
        webAppUrl = "http://" + host + (port == 80 ? "" : (":" + String.valueOf(port))) + contextPath +
                hostPage;
        Property hasWebAppProperty = new Property();
        webAppUrl = getProperty(webAppUrlProperty, webAppUrl, hasWebAppProperty);
        logger.info("Web App Url is {}", webAppUrl);

        getProperty("logAll", null, null);

        uri = new URI(webAppUrl);
        if (hasWebAppProperty.hasProperty) {
            port = uri.getPort();
            logger.debug("setting port to {}", port);
        }

        smallPage = uri.resolve("smallPage").toASCIIString();
    }

    private String getProperty(String property, String defaultValue, Property hasProperty) {
        logger.trace("Retrieving property {}", property);
        String value = System.getProperty(property);

        if (value == null || value.isEmpty()) {
            if (hasProperty != null) {
                hasProperty.hasProperty = false;
            }
            if (defaultValue != null) {
                logger.debug("Property {} unset, using default of '{}'", property, defaultValue);
                value = defaultValue;
            } else {
                logger.debug("Property {} unset, no default provided.", property);
            }
        } else {
            if (hasProperty != null) {
                hasProperty.hasProperty = true;
            }
            logger.debug("Property {} set, value is '{}', ", property, value);
        }
        return value;
    }

    static int signupIteration = 0;

    public void signup(WebDriver webDriver, SignupInfo signupInfo) throws Exception {
        // we may not want to just nuke cookies on the supplied webdriver
        String testMethod = "signup";

        logger.info("here 1");

        webDriver.manage().deleteAllCookies();
        logger.info("here 2");
        webDriver.get(webAppUrl);
        logger.info("here 3");

        WebElement until = (new WebDriverWait(webDriver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_SIGNUPLINK)));
        logger.info("until = {}, {}", until, until.getTagName());
        if(until == null)
        {
            logger.error("Waited for presence of sign up link but could not find it.");
        }

        signupIteration++;

        //makeScreenshot(testMethod, signupIteration, "load");

        logger.info("here 4");

        WebElement element;
        try {
            element = webDriver.findElement(By.id(GWTID_SIGNUPLINK));
        } catch (Exception x) {
            logger.error("Unable to find sign up button by id {}: ", GWTID_SIGNUPLINK, x.getMessage());
            throw x;
        }
        logger.info("here 5");

        element.click();

        (new WebDriverWait(webDriver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_SIGNUPUSERNAMEBOX)));
        logger.info("here 6");

        //makeScreenshot(testMethod, signupIteration, "signupLinkClicked");

        if (signupInfo.getUsername() == null) {
            int randUserId = random.nextInt(100000);
            String myUserName = USERNAME_PREFIX + randUserId;
            signupInfo.setUsername(myUserName);
        }
        if (signupInfo.getPassword() == null) {
            signupInfo.setPassword(INPUTSTRING_PASSWORD);
        }

        webDriver.findElement(By.id(GWTID_SIGNUPUSERNAMEBOX)).sendKeys(signupInfo.getUsername());
        logger.info("here 7");
        webDriver.findElement(By.id(GWTID_PASSWORD1BOX)).sendKeys(signupInfo.getPassword());
        logger.info("here 8");
        webDriver.findElement(By.id(GWTID_PASSWORD2BOX)).sendKeys(signupInfo.getPassword());
        logger.info("here 9");
        webDriver.findElement(By.id(GWTID_EMAILBOX)).sendKeys(INPUTSTRING_USEREMAIL);
        logger.info("here 10");

        //makeScreenshot(testMethod, signupIteration, "formFilled");

        webDriver.findElement(By.id(GWTID_SIGNUPBUTTON)).click();
        logger.info("here 11");

       logger.debug("Sent request to sign up as user {} with password {}", signupInfo.getUsername(), signupInfo.getPassword());

        // need to wait for success
        // we wait for the create game button
        WebElement dyn = (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_CREATEGAMEBUTTON)));
        if(dyn == null)
        {
            logger.warn("Element not found! GWT CREATE GAME BUTTON");
        }
        //Thread.sleep(500);
        //makeScreenshot(testMethod, signupIteration, "signupClicked");

        jsessionid = webDriver.manage().getCookieNamed(COOKIENAME_JSESSIONID).getValue();
        logger.info("Signed up user {} with password {}, JSESSIONID={}", signupInfo.getUsername(), signupInfo.getPassword(), jsessionid);
    }


    @Test
    @ExternalTestCase("MWT-5")
    public void testSignup() throws Exception {
        SignupInfo signupInfo = new SignupInfo();
        signup(driver, signupInfo);

        // store username for later
        userName = signupInfo.getUsername();

        //final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
        //assertTrue(jsErrors.isEmpty(), "JS errors occured: " + jsErrors);
    }

    @Test//(dependsOnMethods = {"testSignup"})
    @ExternalTestCase("MWT-8")
    public void testLogin() throws Exception {
        String testMethod = "testLogin";

        SignupInfo signupInfo = new SignupInfo();
        signup(driver, signupInfo);

        // store username for later
        userName = signupInfo.getUsername();

        doLogin(driver, signupInfo);

        jsessionid = driver.manage().getCookieNamed(COOKIENAME_JSESSIONID).getValue();

        //final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
        //assertTrue(jsErrors.isEmpty(), "JS errors occured: " + jsErrors);
    }

    @Test
    @Disabled
    public void testLoginWithTwitter() throws Exception {
        String testMethod = "testLoginWithTwitter";

        driver.manage().deleteAllCookies();
        logger.info("here 2");
        driver.get(webAppUrl);
        logger.info("here 3");

        WebElement until = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_SIGNUPLINK)));

        WebElement element = driver.findElement(By.xpath("//p/a"));
        element.click();

        until = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("allow")));

        WebElement username_or_email = driver.findElement(By.id("username_or_email"));
        if(username_or_email != null) {
            username_or_email.sendKeys("sereneseattlite");

            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("4wezm9bexstw");
        }

        WebElement allow = driver.findElement(By.id("allow"));
        allow.click();

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_GAMEDETAILSTABLE)));
    }

    // use invocationCount 11 to trigger a bug
    @Test//(/*groups={"broken"}, */dependsOnMethods = {"testSignup"}, invocationCount = 1, skipFailedInvocations = true)
    @ExternalTestCase("MWT-9")
    public void testCreateGame() throws Exception {
        String testMethod = "testCreateGame";
        SignupInfo signupInfo = new SignupInfo();
        signup(driver, signupInfo);

        // store username for later
        userName = signupInfo.getUsername();

        doLogin(driver, signupInfo);

        makeScreenshot(testMethod, "load");

        doCreateGame(driver);

        //System.out.println(driver.getPageSource());
        //final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
        //assertTrue(jsErrors.isEmpty(), "JS errors occured: " + jsErrors);
    }

    private GameInfo doCreateGame(WebDriver webDriver) throws IOException {
        String testMethod = "doCreateGame";
        DiscoveredGames discoveredGames = new DiscoveredGames().discoverGames(webDriver);

        WebElement createGameButton = webDriver.findElement(By.id(GWTID_CREATEGAMEBUTTON));
        makeScreenshot(testMethod, "foundCreateGameButton");

        createGameButton.click();
        makeScreenshot(testMethod, "clickedCreateGameButton");
        webDriver.findElement(By.id(GWTID_GAMENAMEBOX)).sendKeys(INPUTSTRING_GAMENAME);
        makeScreenshot(testMethod, "enteredGameName");

        webDriver.findElement(By.id(GWTID_OKBUTTON)).click();
        makeScreenshot(testMethod, "clickedOkButton");

        GameInfo game = (new WebDriverWait(webDriver, 30)).until(discoveredGames.gameIsAdded());

        makeScreenshot(testMethod, "foundGameList");

        logger.error("I found {}", game.gameName);
        return game;
    }

    @Test//(dependsOnMethods = "testCreateGame")
    public void testJoinGame() throws Exception {
        String testMethod = "testJoinGame";

        SignupInfo signupInfo = new SignupInfo();
        signup(driver, signupInfo);

/*
        HtmlUnitDriver d = (HtmlUnitDriver)driver;
        String script = "{ window.__gwt_bookmarklet_params = {'server_url':'http://localhost:8333/'}; var s = document.createElement('script'); s.src = 'http://localhost:8333/dev_mode_on.js'; void(document.getElementsByTagName('head')[0].appendChild(s));}";
        d.executeScript(script);
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.module_webapp"))).click();
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//text()[contains(., 'Compiling webapp')]")));
*/

        (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_GAMEDETAILSTABLE)));
        WebElement tbody2 = driver.findElement(By.xpath("//*[@id='" + GWTID_GAMEDETAILSTABLE + "']/tbody[2]"));
        if(tbody2.isDisplayed())
        {
            try {
                logger.info("waiting for games table to load...");
                Boolean until = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                    @Nullable
                    @Override
                    public Boolean apply(@Nullable WebDriver input) {
                        return tbody2.isDisplayed() == false;
                    }
                });
                //Boolean until = (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='" + GWTID_GAMEDETAILSTABLE + "']/tbody[2]")));
                logger.info("until = {}", until);
            } catch(Exception ex)
            {
                logger.warn("{}", ex);
            }
        } else {
            logger.info("no need to wait for games table to load, 'loading' tbody is hidden");
        }

        DiscoveredGames games1 = new DiscoveredGames().discoverGames(driver);

        ManagedDriver driver2 = superDriverManager.createManagedDriver(whichDriver);

        SignupInfo user2 = new SignupInfo();
        signup(driver2.getInstance(), user2);

/*
        HtmlUnitDriver d2 = (HtmlUnitDriver)driver2.getInstance();
        String script2 = "{ window.__gwt_bookmarklet_params = {'server_url':'http://localhost:8333/'}; var s = document.createElement('script'); s.src = 'http://localhost:8333/dev_mode_on.js'; void(document.getElementsByTagName('head')[0].appendChild(s));}";
        d2.executeScript(script2);
        (new WebDriverWait(driver2.getInstance(), 60)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.module_webapp"))).click();
        (new WebDriverWait(driver2.getInstance(), 60)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//text()[contains(., 'Compiling webapp')]")));
*/

        (new WebDriverWait(driver2.getInstance(), 3)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_GAMEDETAILSTABLE)));
        try {
            (new WebDriverWait(driver2.getInstance(), 3)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='" + GWTID_GAMEDETAILSTABLE + "']/tbody[2]")));
        } catch(Exception ex)
        {
            logger.warn("{}", ex);
        }

        DiscoveredGames games2 = new DiscoveredGames().discoverGames(driver2.getInstance());

        //doLogin(driver2.getInstance(), signupInfo);

        GameInfo game = doCreateGame(driver2.getInstance());

        GameInfo game1 = (new WebDriverWait(driver, 3)).until(games1.gameIsAdded());

        // button index 0 is join (only button)
        game.buttons.get(0).click();

        WebElement joinGameButton = (new WebDriverWait(driver2.getInstance(), 5)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_JOINGAMEBUTTON)));

        assert joinGameButton.isDisplayed();
        assert joinGameButton.isEnabled();

        joinGameButton.click();

        SpellbookTree spellBookTree = (new WebDriverWait(driver2.getInstance(), 5)).until(new SpellbookTree(driver).isReady());
        spellBookTree.parse();

        SpellbookTree.Mage mage1 = spellBookTree.mages.get(0);
        mage1.spellbooks.get(0).select();

        game1.buttons.get(0).click();

        WebElement joinGameButton1 = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_JOINGAMEBUTTON)));
        logger.info("clicking join button {}", joinGameButton1);
        joinGameButton1.click();

        // we should do expected wait here
        Thread.sleep(300);

        SpellbookTree spellBookTree2 = new SpellbookTree(driver);
        SpellbookTree.Mage mage2 = null;
        for (SpellbookTree.Mage mage : spellBookTree2.mages) {
            if (!mage.name.equals(mage1.name)) {
                mage2 = mage;
                break;
            }
        }
        if (mage2 != null) {
            mage2.spellbooks.get(0).select();
        }

        driver.findElement(By.id("gwt-debug-startGameButton")).click();
        Thread.sleep(5000);

        //System.out.println(driver.getPageSource());
    }

    private void doLogin(WebDriver driver, SignupInfo signupInfo) {
        driver.manage().deleteAllCookies();
        logger.debug("Navigating to url {}", webAppUrl);
        driver.get(webAppUrl);

        logger.debug("waiting for element #" + GWTID_LOGINBTN);
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_LOGINBTN)));

        logger.debug("entering username");
        driver.findElement(By.id(GWTID_USERNAMEBOX)).sendKeys(signupInfo.getUsername());
        logger.debug("entering password");
        driver.findElement(By.id(GWTID_PASSWORDBOX)).sendKeys(signupInfo.getPassword());
        logger.debug("clicking login button");
        driver.findElement(By.id(GWTID_LOGINBTN)).click();

        try {
            logger.debug("waiting for element #" + GWTID_CREATEGAMEBUTTON);
            WebElement dyn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(GWTID_CREATEGAMEBUTTON)));

        } catch(TimeoutException ex)
        {
            logger.warn("unable to find element #{}: {}", GWTID_CREATEGAMEBUTTON, ex.toString());
            List<WebElement> elements = driver.findElements(By.xpath("//*[starts-with(attribute::id, 'gwt-debug-')]"));
            for(WebElement webElement:elements)
            {
                logger.info("{}", webElement.getAttribute("id"));
            }
            throw ex;
        }
        logger.debug("login complete.");


    }

    private void dumpElement(WebElement node, int depth) {
        long when = System.currentTimeMillis();

        logger.info("[{}] Element tag: {}", depth, node.getTagName());
        for (WebElement e : node.findElements(By.xpath(XPATHEXP_CHILDREN))) {
            dumpElement(e, depth + 1);
        }
    }

    private void makeScreenshot(Object... args) throws IOException {
        if (phantomJSDriver != null) {
            byte[] screenshotAs = phantomJSDriver.getScreenshotAs(OutputType.BYTES);
            String string = "";
            for (int i = 0; i < args.length; i++) {
                string += "-" + args[i].toString();
            }
            //string = string.substring(1);

            long diff = System.currentTimeMillis() - startupTime;
            String path = Long.toString(startupTime) + "-" + Long.toString(diff) + string + ".png";
            Path path1 = reportDirPath.resolve(path);
            screenshots.add(path1);

            OutputStream outputStream = Files.newOutputStream(path1, StandardOpenOption.CREATE_NEW);
            outputStream.write(screenshotAs);
            outputStream.close();
            logger.info("Screenshot is {}", path1.toAbsolutePath().toString());
        }
    }

    //@Test(dependsOnMethods = "testCreateGame")
    public void testTest() throws Exception {
        driver.manage().deleteAllCookies();
        driver.get("http://localhost:5060/magewars-webapp/#home");
        driver.findElement(By.id(GWTID_USERNAMEBOX)).clear();
        driver.findElement(By.id(GWTID_USERNAMEBOX)).sendKeys("kay");
        driver.findElement(By.id(GWTID_PASSWORDBOX)).clear();
        driver.findElement(By.id(GWTID_PASSWORDBOX)).sendKeys("poo");
        driver.findElement(By.id(GWTID_LOGINBTN)).click();
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
        driver.findElement(By.id(GWTID_JOINGAMEBUTTON)).click();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    private class GameInfo {
        String gameName;
        String gameUser;
        String gameStatus;
        boolean newGame;
        List<WebElement> buttons;

        public GameInfo(String gameName, String gameUser, String gameStatus) {
            this.gameName = gameName;
            this.gameUser = gameUser;
            this.gameStatus = gameStatus;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameInfo gameInfo = (GameInfo) o;
            return com.google.common.base.Objects.equal(gameName, gameInfo.gameName) &&
                    Objects.equal(gameUser, gameInfo.gameUser) &&
                    Objects.equal(gameStatus, gameInfo.gameStatus);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(gameName, gameUser, gameStatus);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("gameName", gameName)
                    .add("gameUser", gameUser)
                    .add("gameStatus", gameStatus)
                    .toString();
        }
    }

    private class DiscoveredGames {
        private WebElement table;
        private WebDriver myDriver;
        private WebElement tbody;
        private List<WebElement> rows;
        private List<GameInfo> games = new ArrayList<>();
        private final Map<String, Integer> headerMap = new HashMap<>();
        private Map<GameInfo, GameInfo> gameMap = new HashMap<>();

        public WebElement getTable() {
            return table;
        }

        public List<GameInfo> getGames() {
            return games;
        }

        public WebElement getTableBody() {
            return tbody;
        }

        public List<WebElement> getRows() {
            return rows;
        }

        public DiscoveredGames discoverGames(WebDriver driver) {
            table = driver.findElement(By.id(GWTID_GAMEDETAILSTABLE));
            myDriver = driver;

            readHeaders();

            tbody = table.findElement(By.tagName(TAGNAME_TBODY));
            return readRows();
        }

        private void readHeaders() {
            WebElement thead = table.findElement(By.tagName(TAGNAME_THEAD));
            List<WebElement> headerRows = thead.findElements(By.tagName(TAGNAME_TR));
            List<String> headerNames = new ArrayList<>();
            for (WebElement headerRow : headerRows) {
                // Right now we are doing simplistic table parsing. we don't process colspan attributes
                List<WebElement> headerColumns = headerRow.findElements(By.cssSelector(CSSSELECTOR_TDTH));
                int headerIndex = 0;
                for (WebElement headerColumn : headerColumns) {
                    String headerText = headerColumn.getText();
                    if (headerText.isEmpty()) {
                        WebElement subElem = headerColumn.findElement(By.cssSelector(CSSSELECTOR_ALL));
                        String aClass = subElem.getAttribute(HTMLATTNAME_CLASS);
                        headerText = aClass;
                    }
                    logger.debug("header column: {}.", headerText);
                    headerNames.add(headerText);
                    headerMap.put(headerText, headerIndex);
                    headerIndex++;
                }
                break;
            }
        }

        private DiscoveredGames readRows() {
            // if we get an exception in here due to DOM changes,
            // we need to restore the old list of games
            List<GameInfo> oldGames = games;
            games = new ArrayList<>();
            Map<GameInfo, GameInfo> oldMap = gameMap;
            gameMap = new HashMap<>();

            try {
                table = myDriver.findElement(By.id(GWTID_GAMEDETAILSTABLE));
                tbody = table.findElement(By.tagName(TAGNAME_TBODY));
                rows = tbody.findElements(By.tagName(TAGNAME_TR));

                int rowIndex = 0;
                for (WebElement row : rows) {
                    List<WebElement> columns = row.findElements(By.tagName(TAGNAME_TD));
                    GameInfo gameInfo = new GameInfo(columns.get(headerMap.get(HEADERTEXT_GAMENAME)).getText(),
                            columns.get(headerMap.get(HEADERTEXT_GAMEUSER)).getText(),
                            columns.get(headerMap.get(HEADERTEXT_GAMESTATUS)).getText());

                    WebElement actions = columns.get(headerMap.get(HEADERTEXT_GAMEACTIONS));
                    List<WebElement> buttons = actions.findElements(By.tagName("button"));
                    gameInfo.buttons = buttons;

                    // sure it's possible that two games have all the same information
                    if (!oldMap.containsKey(gameInfo)) {
                        gameInfo.newGame = true;
                    }
                    gameMap.put(gameInfo, gameInfo);
                    games.add(gameInfo);

                    logger.info("Row {}: Game name is {}", rowIndex, gameInfo.gameName);
                    rowIndex++;
                }
                return this;
            } catch(Exception ex)
            {
                games = oldGames;
                gameMap = oldMap;
            }
            return this;
        }

        ExpectedCondition<GameInfo> gameIsAdded() {
            return new ExpectedCondition<GameInfo>() {
                @Nullable
                @Override
                public GameInfo apply(@Nullable WebDriver input) {
                    if(input != myDriver)
                    {
                        throw new RuntimeException("Input webdriver differs from original webdriver.");
                    }

                    List<GameInfo> oldGames = new ArrayList<>(games);
                    try {
                        readRows();
                    }
                    catch(Exception ex) // won't get exceptions now
                    {
                        logger.warn("{}", ex.getMessage());
                        return null;
                    }
                    // right now the list of games only increases ...
                    if (games.size() > oldGames.size()) {
                        return games.get(oldGames.size());
                    }
                    return null;
                }
            };
        }
    }

    private class SpellbookTree {

        private  WebElement spellbookTree;

        public ExpectedCondition<SpellbookTree> isReady() {
            return new ExpectedCondition<SpellbookTree>() {
                @Nullable
                @Override
                public SpellbookTree apply(@Nullable WebDriver input) {
                    spellbookTree = input.findElement(By.className(CLASSNAME_SPELLBOOKTREE));
                    if (spellbookTree != null) {
                        return SpellbookTree.this;
                    }
                    return null;
                }
            };
        }

        class Spellbook {
            WebElement selectElement;
            String name;

            public void select() {
                selectElement.click();
            }
        }

        class Mage {
            String name;
            List<Spellbook> spellbooks = new ArrayList<>();
        }

        List<Mage> mages = new ArrayList<>();

        WebDriver driver;

        // FIXME this no longer handles closed trees
        public SpellbookTree(WebDriver driver) {
            this.driver = driver;
        }

        public SpellbookTree parse()
        {
//            spellbookTree = driver.findElement(By.className(CLASSNAME_SPELLBOOKTREE));
            List<WebElement> nodes = spellbookTree.findElements(By.xpath(".//div[@aria-level='1' and @role='treeitem']"));
            for (WebElement node : nodes) {
                Mage mage = new Mage();
                WebElement img = node.findElement(By.xpath("//img"));
                WebElement label = node.findElement(By.xpath("div/div/div[2]"));
                mage.name = label.getText();
                List<WebElement> subItems = node.findElements(By.xpath(".//div[@aria-level='2' and @role='treeitem']"));
                for (WebElement subItem : subItems) {
                    Spellbook spellbook = new Spellbook();
                    spellbook.selectElement = subItem.findElement(By.xpath(".//*[@onclick]"));
                    spellbook.name = subItem.getTagName();
                    mage.spellbooks.add(spellbook);
                }
                mages.add(mage);
            }
            return this;
        }
    }
}
