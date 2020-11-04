/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.util.xpath.XPathBuilder
 *  com.belrbez.dev.autovote.util.xpath.XPathCondition
 *  org.junit.Assert
 */
package com.belrbez.dev.autovote.util.xpath;

import org.junit.Assert;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * Exception performing whole class analysis ignored.
 */
public class XPathBuilder<T> {
    private String xPath = "";
    private String curElement;
    private boolean desc;
    private boolean fromRoot = false;
    private Function<String, T> mapper;

    private /* varargs */ XPathBuilder(String xPath, Function<String, T> mapper, boolean desc, XPathCondition... conditions) {
        this.curElement = xPath;
        this.desc = desc;
        this.mapper = mapper;
        this.applyConditions(conditions);
    }

    private /* varargs */ XPathBuilder(String xPath, boolean desc, XPathCondition... conditions) {
        this(xPath, s -> null, desc, conditions);
    }

    public static /* varargs */ <T> XPathBuilder<T> from(String tagName, Function<String, T> mapper, XPathCondition... conditions) {
        return new XPathBuilder("//" + tagName, mapper, true, conditions);
    }

    public static /* varargs */ XPathBuilder from(String tagName, XPathCondition... conditions) {
        return XPathBuilder.from(tagName, s -> s, (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder fromElement(String element, XPathCondition... conditions) {
        return XPathBuilder.from(element, (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder fromAny(XPathCondition... conditions) {
        return XPathBuilder.from("*", (XPathCondition[]) conditions);
    }

    public static /* varargs */ <T> XPathBuilder<T> fromAny(Function<String, T> mapper, XPathCondition... conditions) {
        return XPathBuilder.from("*", mapper, (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder fromAnyElement(XPathCondition... conditions) {
        return XPathBuilder.fromAny((XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder fromRoot(String element, XPathCondition... conditions) {
        return new XPathBuilder("/" + element, true, conditions);
    }

    public static /* varargs */ XPathBuilder fromRootElement(String element, XPathCondition... conditions) {
        return XPathBuilder.fromRoot(element, (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder anyFromRoot(XPathCondition... conditions) {
        return XPathBuilder.fromRoot("*", (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder anyElementFromRoot(XPathCondition... conditions) {
        return XPathBuilder.anyFromRoot((XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder of(String tagName, XPathCondition... conditions) {
        return new XPathBuilder(tagName, false, conditions);
    }

    public static /* varargs */ <T> XPathBuilder<T> of(String tagName, Function<String, T> mapper, XPathCondition... conditions) {
        return new XPathBuilder(tagName, mapper, false, conditions);
    }

    public static /* varargs */ XPathBuilder ofElement(String element, XPathCondition... conditions) {
        return XPathBuilder.of(element, (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathBuilder ofAny(XPathCondition... conditions) {
        return XPathBuilder.of("*", (XPathCondition[]) conditions);
    }

    public static /* varargs */ <T> XPathBuilder<T> ofAny(Function<String, T> mapper, XPathCondition... conditions) {
        return XPathBuilder.of("*", mapper, (XPathCondition[]) conditions);
    }

    public static /* varargs */ <T> XPathBuilder<T> ofAny(List<String> elements, Function<String, T> mapper, XPathCondition... conditions) {
        return XPathBuilder.ofAny(mapper, (XPathCondition[]) conditions).which(XPathBuilder.anyTag(elements));
    }

    public static /* varargs */ <T> XPathBuilder<T> ofAny(Function<String, T> mapper, String firstElement, String... elements) {
        return XPathBuilder.ofAny(mapper, (XPathCondition[]) new XPathCondition[0]).which(XPathCondition.oneOf(firstElement, (String[]) elements));
    }

    public static /* varargs */ XPathBuilder ofAnyElement(XPathCondition... conditions) {
        return XPathBuilder.ofAny((XPathCondition[]) conditions);
    }

    private static XPathCondition anyTag(List<String> elements) {
        Assert.assertTrue("Should be at least one element", elements.size() > 0);
        String[] elementsArray = (String[]) elements.subList(1, elements.size()).toArray();
        return XPathCondition.oneOf(elements.get(0), (String[]) elementsArray);
    }

    public /* varargs */ XPathBuilder<T> and(String element, XPathCondition... conditions) {
        this.checkBuilderIsDescending();
        this.xPath = this.xPath + this.curElement + "//";
        this.curElement = element;
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> andAny(XPathCondition... conditions) {
        return this.and("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> andThen(String element, XPathCondition... conditions) {
        return this.and(element, conditions);
    }

    public /* varargs */ XPathBuilder<T> andThenAny(XPathCondition... conditions) {
        return this.and("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> next(String element, XPathCondition... conditions) {
        this.checkBuilderIsDescending();
        this.xPath = this.xPath + this.curElement + "/";
        this.curElement = element;
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> andNext(String element, XPathCondition... conditions) {
        return this.next(element, conditions);
    }

    public /* varargs */ XPathBuilder<T> nextAny(XPathCondition... conditions) {
        return this.next("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> andNextAny(XPathCondition... conditions) {
        return this.next("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> in(String element, XPathCondition... conditions) {
        this.checkBuilderIsAscending();
        this.xPath = String.format("//%s%s", this.curElement, this.xPath);
        this.curElement = element;
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> inside(String element, XPathCondition... conditions) {
        return this.in(element, conditions);
    }

    public /* varargs */ XPathBuilder<T> inAny(List<String> elements, XPathCondition... conditions) {
        XPathCondition anyTagCondition = XPathBuilder.anyTag(elements);
        XPathCondition condition = XPathCondition.join(anyTagCondition, (XPathCondition[]) conditions);
        return this.inAny(condition);
    }

    public /* varargs */ XPathBuilder<T> inAny(String firstElement, String... elements) {
        XPathCondition anyTagCondition = XPathCondition.oneOf(firstElement, (String[]) elements);
        return this.inAny(anyTagCondition);
    }

    public /* varargs */ XPathBuilder<T> inAny(XPathCondition... conditions) {
        return this.in("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> insideAny(XPathCondition... conditions) {
        return this.in("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> up(XPathCondition... conditions) {
        this.xPath = this.desc ? this.xPath + this.curElement + "/" : String.format("/%s%s", this.curElement, this.xPath);
        this.curElement = "..";
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> upOrSelf(XPathCondition... conditions) {
        this.xPath = this.desc ? this.xPath + this.curElement + "//" : String.format("//%s%s", this.curElement, this.xPath);
        this.curElement = "..";
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> exactIn(String element, XPathCondition... conditions) {
        this.checkBuilderIsAscending();
        this.xPath = String.format("/%s%s", this.curElement, this.xPath);
        this.curElement = element;
        this.applyConditions(conditions);
        return this;
    }

    public /* varargs */ XPathBuilder<T> exactInside(String element, XPathCondition... conditions) {
        return this.exactIn(element, conditions);
    }

    public /* varargs */ XPathBuilder<T> exactInsideAny(XPathCondition... conditions) {
        return this.exactIn("*", conditions);
    }

    public /* varargs */ XPathBuilder<T> inRootElement(String element, XPathCondition... conditions) {
        this.fromRoot = true;
        return this.in(element, conditions);
    }

    public /* varargs */ XPathBuilder<T> insideRootElement(String element, XPathCondition... conditions) {
        return this.inRootElement(element, conditions);
    }

    public String build() {
        this.appendElement();
        return this.xPath;
    }

    public String xpath() {
        return this.build();
    }

    public T transform() {
        return this.mapper.apply(this.build());
    }

    public T map() {
        return this.transform();
    }

    public T and() {
        return this.transform();
    }

    public T get() {
        return this.transform();
    }

    public T andThen() {
        return this.transform();
    }

    public void andThen(Consumer<T> action) {
        action.accept(this.transform());
    }

    public <R> R andThen(Function<T, R> action) {
        return action.apply(this.transform());
    }

    public T andCheck() {
        return this.transform();
    }

    public void andCheck(Consumer<T> action) {
        this.andThen(action);
    }

    private void appendElement() {
        if (!this.curElement.isEmpty()) {
            this.xPath = this.desc ? this.xPath + this.curElement : String.format("%s%s%s", this.fromRoot ? "/" : "//", this.curElement, this.xPath);
            this.curElement = "";
        }
    }

    public /* varargs */ XPathBuilder<T> which(XPathCondition... conditions) {
        return this.applyConditions(conditions);
    }

    public /* varargs */ XPathBuilder<T> that(XPathCondition... conditions) {
        return this.which(conditions);
    }

    public /* varargs */ XPathBuilder<T> who(XPathCondition... conditions) {
        return this.which(conditions);
    }

    private /* varargs */ XPathBuilder<T> applyConditions(XPathCondition... conditions) {
        if (conditions.length > 0) {
            XPathCondition existingCondition = this.extractAndRemoveCondition();
            XPathCondition condition = XPathCondition.join(existingCondition, (XPathCondition[]) conditions);
            this.curElement = this.curElement + String.format("[%s]", condition.toString());
        }
        return this;
    }

    private XPathCondition extractAndRemoveCondition() {
        int startOfCondition = this.curElement.indexOf("[");
        if (startOfCondition != -1) {
            String conditionStr = this.curElement.substring(startOfCondition + 1, this.curElement.length() - 1);
            XPathCondition condition = XPathCondition.empty().condition(conditionStr);
            this.curElement = this.curElement.substring(0, startOfCondition);
            return condition;
        }
        return XPathCondition.empty();
    }

    private void checkBuilderIsDescending() {
        Assert.assertTrue("Ascending builder can't use descending operations", this.desc);
    }

    private void checkBuilderIsAscending() {
        Assert.assertTrue("Descending builder can't use ascending operations", !this.desc);
    }
}

