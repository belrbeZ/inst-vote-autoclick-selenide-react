/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.util.xpath.SimpleAscXPathBuilder
 */
package com.belrbez.dev.autovote.util.xpath;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * Exception performing whole class analysis ignored.
 */
public class SimpleAscXPathBuilder<T> {
    private String xPath = "";
    private String curElement;
    private boolean operatorAdded = false;
    private boolean inCondition = false;
    private Function<String, T> mapper;

    private SimpleAscXPathBuilder(String xPath, Function<String, T> mapper) {
        this.curElement = xPath;
        this.mapper = mapper;
    }

    public static <T> SimpleAscXPathBuilder<T> of(String tagName, Function<String, T> mapper) {
        return new SimpleAscXPathBuilder(tagName, mapper);
    }

    public static <T> SimpleAscXPathBuilder<T> ofAny(Function<String, T> mapper) {
        return SimpleAscXPathBuilder.of("*", mapper);
    }

    public static /* varargs */ <T> SimpleAscXPathBuilder<T> ofAny(Function<String, T> mapper, String... elements) {
        return SimpleAscXPathBuilder.ofAny(mapper).oneOf(elements);
    }

    public SimpleAscXPathBuilder<T> in(String element) {
        this.appendElement();
        this.curElement = element;
        return this;
    }

    public /* varargs */ SimpleAscXPathBuilder<T> inAny(String... elements) {
        return this.inAny().oneOf(elements);
    }

    public SimpleAscXPathBuilder<T> inAny() {
        return this.in("*");
    }

    public SimpleAscXPathBuilder<T> up() {
        return this.in("..");
    }

    public SimpleAscXPathBuilder<T> upOrSelf() {
        this.xPath = String.format("//%s%s", this.curElement, this.xPath);
        this.curElement = "..";
        return this;
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

    public T get() {
        return this.transform();
    }

    public T andThen() {
        return this.transform();
    }

    public T andCheck() {
        return this.transform();
    }

    private /* varargs */ SimpleAscXPathBuilder<T> oneOf(String... tags) {
        String condition = Arrays.stream(tags).map(tag -> "self::" + tag).collect(Collectors.joining(" or "));
        if (tags.length > 0) {
            condition = String.format("(%s)", condition);
        }
        this.addCondition(condition);
        return this;
    }

    public SimpleAscXPathBuilder<T> with(String condition) {
        return this.addCondition(condition);
    }

    public SimpleAscXPathBuilder<T> withIndex(int index) {
        return this.addCondition(String.valueOf(index));
    }

    public SimpleAscXPathBuilder<T> first() {
        return this.withIndex(1);
    }

    public SimpleAscXPathBuilder<T> last() {
        return this.addCondition("last()");
    }

    public SimpleAscXPathBuilder<T> odd() {
        return this.addCondition("position() mod 2 = 1");
    }

    public SimpleAscXPathBuilder<T> even() {
        return this.addCondition("position() mod 2 = 0");
    }

    public SimpleAscXPathBuilder<T> withClass(String className) {
        return this.addCondition(String.format("contains(@class, '%s')", className));
    }

    public SimpleAscXPathBuilder<T> withText(String text) {
        return this.addCondition(String.format("contains(., '%s')", text));
    }

    public SimpleAscXPathBuilder<T> withExactText(String text) {
        return this.addCondition(String.format("normalize-space(text()) = '%s'", text));
    }

    public SimpleAscXPathBuilder<T> withAttr(String attrName, String attrValue) {
        return this.addCondition(String.format("contains(@%s, '%s')", attrName, attrValue));
    }

    public SimpleAscXPathBuilder<T> withPlaceholder(String placeholder) {
        return this.addCondition(String.format("contains(@placeholder, '%s')", placeholder));
    }

    public SimpleAscXPathBuilder<T> and() {
        this.curElement = this.curElement + " and ";
        this.operatorAdded = true;
        return this;
    }

    public SimpleAscXPathBuilder<T> or() {
        this.curElement = this.curElement + " or ";
        this.operatorAdded = true;
        return this;
    }

    public SimpleAscXPathBuilder<T> not(String condition) {
        this.addCondition(String.format("not(%s)", condition));
        return this;
    }

    private SimpleAscXPathBuilder<T> addCondition(String condition) {
        if (!this.inCondition) {
            this.curElement = this.curElement + "[";
            this.inCondition = true;
        }
        this.curElement = this.operatorAdded || this.curElement.charAt(this.curElement.length() - 1) == '[' ? this.curElement + condition : this.curElement + String.format(" and %s", condition);
        this.operatorAdded = false;
        return this;
    }

    private void appendElement() {
        if (!this.curElement.isEmpty()) {
            this.checkElement();
            this.xPath = String.format("//%s%s", this.curElement, this.xPath);
            this.curElement = "";
        }
    }

    private void checkElement() {
        if (this.inCondition) {
            this.curElement = this.curElement + ']';
            this.inCondition = false;
        }
    }
}

