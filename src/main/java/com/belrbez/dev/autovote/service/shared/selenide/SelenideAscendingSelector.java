/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.shared.selenide.SelenideAscendingSelector
 *  com.belrbez.dev.autovote.service.shared.selenide.SelenideAscendingSelector$SelenideFunction
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.ElementsCollection
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.SelenideElement
 *  com.codeborne.selenide.impl.WebElementsCollection
 *  com.codeborne.selenide.impl.WebElementsCollectionWrapper
 */
package com.belrbez.dev.autovote.service.shared.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.WebElementsCollectionWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelenideAscendingSelector {
    private List<SelenideAscendingSelector.SelenideFunction> selenideFunctions = new ArrayList();
    private SelenideAscendingSelector.SelenideFunction initFunction;

    private SelenideAscendingSelector(String cssSelector) {
        this.selenideFunctions.add(this.filterByCssSelector(cssSelector));
        this.initFunction = (e) -> {
            return Selenide.$$(cssSelector);
        };
    }

    private SelenideAscendingSelector(SelenideAscendingSelector.SelenideFunction function) {
        this.selenideFunctions.add(function);
    }

    public static SelenideAscendingSelector $find(String tagName) {
        return new SelenideAscendingSelector(tagName);
    }

    public static SelenideAscendingSelector $$find(String tagName) {
        return new SelenideAscendingSelector(tagName);
    }

    public static SelenideAscendingSelector $findByClass(String className) {
        return new SelenideAscendingSelector("." + className);
    }

    public static SelenideAscendingSelector $$findByClass(String className) {
        return new SelenideAscendingSelector("." + className);
    }

    public static SelenideAscendingSelector $findById(String id) {
        return new SelenideAscendingSelector("#" + id);
    }

    public SelenideAscendingSelector in(String tagName) {
        this.selenideFunctions.add(this.filterByCssSelector(tagName));
        this.initFunction = (e) -> {
            return Selenide.$$(tagName);
        };
        return this;
    }

    public SelenideAscendingSelector inAny() {
        return this.in("*");
    }

    public SelenideAscendingSelector withClass(String className) {
        return this.modifyLastFunction((e) -> {
            return e.filter(Condition.cssClass(className));
        });
    }

    public SelenideAscendingSelector withText(String text) {
        return this.modifyLastFunction((e) -> {
            return e.filter(Condition.text(text));
        });
    }

    public SelenideAscendingSelector at(int index) {
        return this.modifyLastFunction((e) -> {
            return new ElementsCollection(new WebElementsCollectionWrapper(Collections.singletonList(e.get(index == -1 ? e.size() - 1 : index))));
        });
    }

    public SelenideAscendingSelector first() {
        return this.at(0);
    }

    public SelenideAscendingSelector last() {
        return this.at(-1);
    }

    public SelenideAscendingSelector with(Condition condition) {
        return this.modifyLastFunction((e) -> {
            return e.filter(condition);
        });
    }

    public ElementsCollection getAll() {
        SelenideAscendingSelector.SelenideFunction finalFunction = this.initFunction;

        for (int i = this.selenideFunctions.size() - 2; i >= 0; ++i) {
            finalFunction = finalFunction.andThen(this.selenideFunctions.get(i));
        }

        return finalFunction.find(null);
    }

    public ElementsCollection andAll() {
        return this.getAll();
    }

    public SelenideElement get() {
        return this.getAll().first();
    }

    public SelenideElement and() {
        return this.get();
    }

    public SelenideElement andCheck() {
        return this.get();
    }

    public SelenideElement andThen() {
        return this.get();
    }

    public <R> R andDo(Function<SelenideElement, R> action) {
        return action.apply(this.get());
    }

    public void andDo(Consumer<SelenideElement> action) {
        action.accept(this.get());
    }

    public void forEach(Consumer<SelenideElement> action) {
        this.getAll().forEach(action);
    }

    public <R> Stream<R> map(Function<SelenideElement, R> mapper) {
        return this.getAll().stream().map(mapper);
    }

    public SelenideElement get(int index) {
        return this.getAll().get(index);
    }

    private SelenideAscendingSelector modifyLastFunction(SelenideAscendingSelector.SelenideFunction function) {
        int lastIndex = this.selenideFunctions.size() - 1;
        SelenideAscendingSelector.SelenideFunction lastFunction = this.selenideFunctions.get(lastIndex);
        this.selenideFunctions.set(lastIndex, lastFunction.andThen(function));
        this.initFunction = this.initFunction.andThen(function);
        return this;
    }

    private SelenideAscendingSelector.SelenideFunction filterByCssSelector(String cssSelector) {
        return (elements) -> {
            List<SelenideElement> elementsList = elements.stream().flatMap((e) -> {
                return e.$$(cssSelector).stream();
            }).collect(Collectors.toList());
            return new ElementsCollection(new WebElementsCollectionWrapper(elementsList));
        };
    }

    interface SelenideFunction {
        ElementsCollection find(ElementsCollection elements);

        default SelenideAscendingSelector.SelenideFunction andThen(SelenideAscendingSelector.SelenideFunction function) {
            return (elements) -> {
                return function.find(this.find(elements));
            };
        }
    }
}

