package ru.netology.testmode.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {
    private final SelenideElement loginField = $("[name='login']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement loginBtn = $("[data-test-id='action-login']");
    private final SelenideElement errorText =
            $x("//*[@data-test-id='error-notification']//*[@class='notification__content']");
    private final SelenideElement successAuthText = $x("//*[@id='root']//h2");

    public void authorizeByLogAndPass(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginBtn.click();
    }

    public void validateSuccessAuthText(String expectedText) {
        successAuthText.shouldBe(Condition.text(expectedText));
    }

    public void validateErrorText(String expectedText) {
        errorText.shouldBe(Condition.text(expectedText));
    }
}
