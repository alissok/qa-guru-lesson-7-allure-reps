package com.nexign;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebSteps {

    @Step("Открываем главную страницу GitHub")
    public void openMainPage(String url) {
        open(url);
    }

    @Step("Ищем репозиторий {repository} по ссылке")
    public void searchForRepository(String repository) {
        $(".header-search-input").click();
        $(".header-search-input").setValue(repository);
        $(".header-search-input").submit();
    }

    @Step("Переходим по ссылке на искомый репозиторий")
    public void clickOnRepositoryLink(String repository) {
        $(By.linkText(repository)).click();
    }

    @Step("Переходим в раздел Issues")
    public void openIssuesDir() {
        $(By.partialLinkText("Issues")).click();
    }
    @Step("Проверяем существование Issue с названием {issueName}")
    public void checkIssueWithSpecificName (String issueName) {
        $(withText(issueName)).should(Condition.visible);
        Allure.getLifecycle().addAttachment(
                "Исходники",
                "text/html",
                "html",
                 WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)
        );
        takeScreenshot();
    }
    @Attachment(value = "Скриншот для всяких ребят", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
