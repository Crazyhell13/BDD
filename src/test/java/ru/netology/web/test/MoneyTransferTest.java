package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        val loginPage = open("http://localhost:9999/", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Transfer from first card to second, happy path")
    void shouldTransferMoneyBetweenOwnCards1() {
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = DataHelper.getAmountOfMoney(secondCardBalance);
        transferPage.transferMoney(sum, DataHelper.getSecondCardNumber());
        val expectedFirstCardBalance = firstCardBalance + sum;
        val expectedSecondCardBalance = secondCardBalance - sum;
        Assertions.assertEquals(expectedFirstCardBalance, dashboardPage.getFirstCardBalance());
        Assertions.assertEquals(expectedSecondCardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Transfer from second card to first, happy path")
    void shouldTransferMoneyBetweenOwnCards2() {
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCardButton();
        val sum = DataHelper.getAmountOfMoney(firstCardBalance);
        transferPage.transferMoney(sum, DataHelper.getFirstCardNumber());
        val expectedFirstCardBalance = firstCardBalance - sum;
        val expectedSecondCardBalance = secondCardBalance + sum;
        Assertions.assertEquals(expectedFirstCardBalance, dashboardPage.getFirstCardBalance());
        Assertions.assertEquals(expectedSecondCardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Transfer to the same card")
    void shouldTransferMoneyBetweenOwnCards3(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = DataHelper.getAmountOfMoney(firstCardBalance);
        transferPage.transferMoney(sum, DataHelper.getFirstCardNumber());
        transferPage.getError();
    }

    @Test
    @DisplayName("Not filled card number")
    void shouldTransferMoneyBetweenOwnCards4(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = DataHelper.getAmountOfMoney(secondCardBalance);
        transferPage.transferMoney(sum, DataHelper.getEmptyCardNumber());
        transferPage.getError();
    }

    @Test
    @DisplayName("Invalid card Number")
    void shouldTransferMoneyBetweenOwnCards5(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = DataHelper.getAmountOfMoney(secondCardBalance);
        transferPage.transferMoney(sum, DataHelper.getInvalidCardNumber());
        transferPage.getError();
    }

    @Test
    @DisplayName("Transfer sum equals zero")
    void shouldTransferMoneyBetweenOwnCards6(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = 0;
        transferPage.transferMoney(sum,DataHelper.getSecondCardNumber());
        transferPage.getError();
    }

    @Test
    @DisplayName("Transfer above the limit")
    void shouldTransferMoneyBetweenOwnCards7(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val sum = secondCardBalance + 100;
        transferPage.transferMoney(sum, DataHelper.getSecondCardNumber());
        transferPage.getError();
    }

    @Test
    @DisplayName("Button 'Отмена' returns to the previous page")
    void shouldTransferMoneyBetweenOwnCards8(){
        val dashboardPage = new DashboardPage();
        val firstCardBalance = dashboardPage.getFirstCardBalance();
        val secondCardBalance = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        transferPage.cancelButton();
    }
}
