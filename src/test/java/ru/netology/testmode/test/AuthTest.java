package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.page.AuthPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {
    AuthPage authPage = new AuthPage();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        String expected = "  Личный кабинет";
        var registeredUser = getRegisteredUser("active");

        authPage
                .authorizeByLogAndPass(registeredUser.getLogin(), registeredUser.getPassword());
        $x("//*[@id='root']//h2").shouldBe(Condition.text(expected));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        String expected = "Ошибка! Неверно указан логин или пароль";
        var notRegisteredUser = getUser("active");

        authPage
                .failedAuthorizeByLogAndPass(notRegisteredUser.getLogin(), notRegisteredUser.getPassword())
                .validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        String expected = "Ошибка! Пользователь заблокирован";
        var blockedUser = getRegisteredUser("blocked");

        authPage.failedAuthorizeByLogAndPass(blockedUser.getLogin(), blockedUser.getPassword())
                .validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        String expected = "Ошибка! Неверно указан логин или пароль";
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        authPage
                .failedAuthorizeByLogAndPass(wrongLogin, registeredUser.getPassword())
                .validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        String expected = "Ошибка! Неверно указан логин или пароль";
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        authPage
                .failedAuthorizeByLogAndPass(registeredUser.getLogin(), wrongPassword)
                .validateErrorText(expected);
    }

}