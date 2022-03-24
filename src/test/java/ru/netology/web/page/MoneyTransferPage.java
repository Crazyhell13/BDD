package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private final SelenideElement heading = $(withText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement from = $("[data-test-id=from] input");
    private final SelenideElement button = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public MoneyTransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage transferMoney(int transferAmount, DataHelper.CardNumber cardNumber) {
        amount.setValue(String.valueOf(transferAmount));
        from.setValue(String.valueOf(cardNumber));
        button.click();
        return new DashboardPage();
    }

    public void getError() {
        error.shouldBe(Condition.visible);
    }

    public DashboardPage cancelButton() {
        cancelButton.click();
        return new DashboardPage();
    }
}
