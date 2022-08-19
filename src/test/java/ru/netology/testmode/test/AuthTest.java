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

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        AuthPage authPage = new AuthPage();
        String expected = "  Личный кабинет";
        var registeredUser = getRegisteredUser("active");

        authPage.authorizeByLogAndPass(registeredUser.getLogin(), registeredUser.getPassword());
        authPage.validateSuccessAuthText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        AuthPage authPage = new AuthPage();
        String expected = "Ошибка! Неверно указан логин или пароль";
        var notRegisteredUser = getUser("active");

        authPage.authorizeByLogAndPass(notRegisteredUser.getLogin(), notRegisteredUser.getPassword());
        authPage.validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        AuthPage authPage = new AuthPage();
        String expected = "Ошибка! Пользователь заблокирован";
        var blockedUser = getRegisteredUser("blocked");

        authPage.authorizeByLogAndPass(blockedUser.getLogin(), blockedUser.getPassword());
        authPage.validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        AuthPage authPage = new AuthPage();
        String expected = "Ошибка! Неверно указан логин или пароль";
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        authPage.authorizeByLogAndPass(wrongLogin, registeredUser.getPassword());
        authPage.validateErrorText(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        AuthPage authPage = new AuthPage();
        String expected = "Ошибка! Неверно указан логин или пароль";
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        authPage.authorizeByLogAndPass(registeredUser.getLogin(), wrongPassword);
        authPage.validateErrorText(expected);
    }

}