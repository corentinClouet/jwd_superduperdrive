package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void login() {
		String username = "myUsername";
		String password = "myPassword";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("MyFirstName", "MyLastName", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void loginAndLogout() {
		login();

		driver.get(baseURL + "/logout");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void manageNote() throws InterruptedException {
		String title = "title";
		String description = "description";
		login();

		//add test
		NotePage notePage = new NotePage(driver);
		notePage.addNote(driver, title, description);
		Assertions.assertEquals(title, notePage.getFirstNoteTitle(driver));
		Assertions.assertEquals(description, notePage.getFirstNoteDescription(driver));

		//edit test
		String editedTitle = "editedTitle";
		String editedDescription = "editedDescription";
		notePage.editNote(driver, editedTitle, editedDescription);
		Assertions.assertEquals(editedTitle, notePage.getFirstNoteTitle(driver));
		Assertions.assertEquals(editedDescription, notePage.getFirstNoteDescription(driver));

		//delete test
		notePage.deleteNote(driver);
		Assertions.assertNull(notePage.getFirstNoteTitle(driver));
		Assertions.assertNull(notePage.getFirstNoteDescription(driver));
	}

	@Test
	public void manageCredential() {
		String url = "https://www.google.fr";
		String username = "myUsername";
		String password = "myPassword";
		login();

		//add test
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.addCredential(driver, url, username, password);
		Assertions.assertEquals(url, credentialPage.getFirstCredentialUrl(driver));
		Assertions.assertEquals(username, credentialPage.getFirstCredentialUsername(driver));
		Assertions.assertNotEquals(password, credentialPage.getFirstCredentialEncryptedPassword(driver));

		//edit test
		String editedUrl = "https://www.fnac.fr";
		String editedUsername = "editedUsername";
		String editedPassword = "editedPassword";
		credentialPage.editCredential(driver, editedUrl, editedUsername, editedPassword);
		Assertions.assertEquals(editedUrl, credentialPage.getFirstCredentialUrl(driver));
		Assertions.assertEquals(editedUsername, credentialPage.getFirstCredentialUsername(driver));
		Assertions.assertNotEquals(editedPassword, credentialPage.getFirstCredentialEncryptedPassword(driver));

		//delete test
		credentialPage.deleteCredential(driver);
		Assertions.assertNull(credentialPage.getFirstCredentialUrl(driver));
		Assertions.assertNull(credentialPage.getFirstCredentialUsername(driver));
		Assertions.assertNull(credentialPage.getFirstCredentialEncryptedPassword(driver));
	}
}
