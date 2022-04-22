package com.nexign;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@DisplayName("Класс с тестами для работы с Allure Reports")
public class IssuesSearchTest {
    //Vars
    String  repository = "alissok/qa-guru-lesson-5-junit",
            url = "https://github.com",
            issueName = "I am test issue";
    SelenideElement repinput = $(".header-search-input");

    @BeforeAll
    static void addLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    void searchIssueWithLogger() {
        open(url);

        repinput.click();
        repinput.setValue(repository);
        repinput.submit();

        $(By.linkText(repository)).click();
        $(By.partialLinkText("Issues")).click();
        $(withText(issueName)).should(Condition.visible);
    }

    @Test
    void searchIssueWithLambdaSteps() {
        step("Открываем главную страницу GitHub", () -> {
            open(url);
        });
        step("Ищем репозиторий " + repository + " по ссылке", () -> {
            repinput.click();
            repinput.setValue(repository);
            repinput.submit();
        });
        step("Переходим по ссылке на искомый репозиторий " + repository, () -> {
            $(By.linkText(repository)).click();
        });
        step("Переходим в раздел Issues", () -> {
            $(By.partialLinkText("Issues")).click();
        });
        step("Проверяем существование Issue с названием " + issueName, () -> {
            $(withText(issueName)).should(Condition.visible);
        });
    }

    @Test
    void searchIssueStepsAnnotaton() {
        WebSteps steps = new WebSteps();

        steps.openMainPage(url);
        steps.searchForRepository(repository);
        steps.clickOnRepositoryLink(repository);
        steps.openIssuesDir();
        steps.checkIssueWithSpecificName(issueName);
    }
}