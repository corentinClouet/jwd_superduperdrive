package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredential;

    @FindBy(id = "btn-add-credential")
    private WebElement addButton;

    @FindBy(id = "btn-edit-credential")
    private List<WebElement> editButton;

    @FindBy(id = "btn-delete-credential")
    private List<WebElement> deleteButton;

    @FindBy(id = "credential-url")
    private List<WebElement> urlList;

    @FindBy(id = "credential-username")
    private List<WebElement> usernameList;

    @FindBy(id = "credential-password")
    private List<WebElement> passwordList;

    @FindBy(id = "credential-id-input")
    private WebElement idInput;

    @FindBy(id = "credential-url-input")
    private WebElement urlInput;

    @FindBy(id = "credential-username-input")
    private WebElement usernameInput;

    @FindBy(id = "credential-password-input")
    private WebElement passwordInput;

    @FindBy(id = "credential-submit")
    private WebElement credentialSubmit;

    @FindBy(id = "credential-submit-modal")
    private WebElement credentialSubmitModal;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Get first credential URL
     *
     * @param driver WebDriver
     * @return url
     */
    public String getFirstCredentialUrl(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        if (urlList.isEmpty()) {
            return null;
        }
        return urlList.get(0).getText();
    }

    /**
     * Get first credential username
     *
     * @param driver WebDriver
     * @return username
     */
    public String getFirstCredentialUsername(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        if (usernameList.isEmpty()) {
            return null;
        }
        return usernameList.get(0).getText();
    }

    /**
     * Get first credential password
     *
     * @param driver WebDriver
     * @return encrypted password
     */
    public String getFirstCredentialEncryptedPassword(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        if (passwordList.isEmpty()) {
            return null;
        }
        return passwordList.get(0).getText();
    }

    /**
     * Add credential
     *
     * @param driver   WebDriver
     * @param url      URL
     * @param username Username
     * @param password Password (origin)
     */
    public void addCredential(WebDriver driver, String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlInput)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitModal)).click();
        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    /**
     * Edit credential
     *
     * @param driver   WebDriver
     * @param url      URL
     * @param username Username
     * @param password Password (origin)
     */
    public void editCredential(WebDriver driver, String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0)))).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlInput));
        urlInput.clear();
        urlInput.sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitModal)).click();
        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    /**
     * Delete crendential
     *
     * @param driver WebDriver
     */
    public void deleteCredential(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredential);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0))).click();
        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

}
