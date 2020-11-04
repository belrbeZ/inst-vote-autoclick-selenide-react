/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.shared.selenide.SimpleSelenideEventAware
 *  com.belrbez.dev.autovote.util.xpath.SimpleAscXPathBuilder
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.ElementsCollection
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.SelenideElement
 *  org.openqa.selenium.By
 */
package com.belrbez.dev.autovote.service.shared.selenide;

import com.belrbez.dev.autovote.util.xpath.SimpleAscXPathBuilder;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public interface SimpleSelenideEventAware {
    default void clickButton(String caption) {
        this.$withTextAndClick("button", caption);
    }

    default void clickLink(String caption) {
        this.$withTextAndClick("a", caption);
    }

    default SelenideElement $withText(String cssSelector, String text) {
        return Selenide.$$(cssSelector).findBy(Condition.text(text));
    }

    default SelenideElement $$findByClass(String tagName, String className) {
        return Selenide.$$(By.tagName(tagName)).findBy(Condition.cssClass(className));
    }

    default SelenideElement $$findByText(String tagName, String text) {
        return Selenide.$$(By.tagName(tagName)).findBy(Condition.text(text));
    }

    default SelenideElement $$findByText(String text) {
        return this.$$findByText("*", text);
    }

    default SelenideElement $findByName(String name) {
        return Selenide.$(By.name(name));
    }

    default SelenideElement $findByName(String tag, String name) {
        return Selenide.$$(By.tagName(tag)).findBy(Condition.name(name));
    }

    default SelenideElement $withTextAndClick(String cssSelector, String caption) {
        SelenideElement result = Selenide.$$(cssSelector).findBy(Condition.text(caption));
        result.click();
        return result;
    }

    default SimpleAscXPathBuilder<SelenideElement> $find(String tagName) {
        return SimpleAscXPathBuilder.of(tagName, Selenide::$x);
    }

    default SimpleAscXPathBuilder<SelenideElement> $findAny() {
        return SimpleAscXPathBuilder.ofAny(Selenide::$x);
    }

    default /* varargs */ SimpleAscXPathBuilder<SelenideElement> $findAny(String... elements) {
        return SimpleAscXPathBuilder.ofAny(Selenide::$x, (String[]) elements);
    }

    default SimpleAscXPathBuilder<ElementsCollection> $$find(String tagName) {
        return SimpleAscXPathBuilder.of(tagName, Selenide::$$x);
    }

    default SimpleAscXPathBuilder<ElementsCollection> $$findAny() {
        return SimpleAscXPathBuilder.ofAny(Selenide::$$x);
    }

    default /* varargs */ SimpleAscXPathBuilder<ElementsCollection> $$findAny(String... elements) {
        return SimpleAscXPathBuilder.ofAny(Selenide::$$x, (String[]) elements);
    }
}

