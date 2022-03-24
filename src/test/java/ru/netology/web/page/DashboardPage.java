package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement firstCard = $$(".list__item").first();
    private final SelenideElement secondCard = $$(".list__item").last();
    private final SelenideElement reload = $("[data-test-id=action-reload]");
    private final SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();
    private final SelenideElement secondCardButton = $$("[data-test-id=action-deposit]").last();
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public MoneyTransferPage firstCardButton(){
        firstCardButton.click();
        return new  MoneyTransferPage();
    }

    public MoneyTransferPage secondCardButton(){
        secondCardButton.click();
        return new  MoneyTransferPage();
    }

    public int getFirstCardBalance() {
        val text = firstCard.text();
        return extractBalanceCard1(text);
    }

    private int extractBalanceCard1(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getSecondCardBalance() {
        val text = secondCard.text();
        return extractBalanceCard2(text);
    }

    private int extractBalanceCard2(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
