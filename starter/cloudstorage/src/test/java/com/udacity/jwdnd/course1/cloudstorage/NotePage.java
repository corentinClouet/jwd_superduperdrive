package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNote;

    @FindBy(id = "btn-add-note")
    private WebElement addButton;

    @FindBy(id = "btn-edit-note")
    private List<WebElement> editButton;

    @FindBy(id = "btn-delete-note")
    private List<WebElement> deleteButton;

    @FindBy(id = "note-title")
    private List<WebElement> titleList;

    @FindBy(id = "note-description")
    private List<WebElement> descriptionList;

    @FindBy(id = "note-title-input")
    private WebElement inputTitle;

    @FindBy(id = "note-description-input")
    private WebElement inputDescription;

    @FindBy(id = "note-submit")
    private WebElement submitButton;

    @FindBy(id = "note-modal-submit")
    private WebElement submitModalButton;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Get first note title
     *
     * @param driver WebDriver
     * @return title note
     */
    public String getFirstNoteTitle(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNote);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        if (titleList.isEmpty()) {
            return null;
        }
        return titleList.get(0).getText();
    }

    /**
     * Get first note description
     *
     * @param driver WebDriver
     * @return description
     */
    public String getFirstNoteDescription(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNote);
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        if (descriptionList.isEmpty()) {
            return null;
        }
        return descriptionList.get(0).getText();
    }

    /**
     * Add new note
     *
     * @param driver      WebDriver
     * @param title       title to use
     * @param description description to use
     */
    public void addNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNote);
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputTitle)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(inputDescription)).sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(submitModalButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(navNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    /**
     * Edit first note
     *
     * @param driver      WebDriver
     * @param title       title to use
     * @param description description to use
     */
    public void editNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNote);
        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0)))).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputTitle));
        inputTitle.clear();
        inputTitle.sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(inputDescription));
        inputDescription.clear();
        inputDescription.sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(submitModalButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(navNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    /**
     * Delete first note
     *
     * @param driver WebDriver
     */
    public void deleteNote(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNote);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0))).click();
        wait.until(ExpectedConditions.elementToBeClickable(navNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

}
