/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.util.xpath.XPathCondition
 */
package com.belrbez.dev.autovote.util.xpath;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Exception performing whole class analysis ignored.
 */
public class XPathCondition {
    private String condition;
    private boolean operatorAdded = false;

    private XPathCondition(String condition) {
        this.condition = condition;
    }

    public static XPathCondition with() {
        return new XPathCondition("");
    }

    public static XPathCondition which(XPathCondition condition) {
        return new XPathCondition(String.format("(%s)", condition));
    }

    public static XPathCondition who() {
        return XPathCondition.with();
    }

    public static XPathCondition which() {
        return XPathCondition.with();
    }

    public static XPathCondition has() {
        return XPathCondition.with();
    }

    public static XPathCondition have() {
        return XPathCondition.with();
    }

    public static XPathCondition is() {
        return XPathCondition.with();
    }

    public static XPathCondition are() {
        return XPathCondition.with();
    }

    public static XPathCondition empty() {
        return XPathCondition.with();
    }

    public static /* varargs */ XPathCondition join(XPathCondition... conditions) {
        return XPathCondition.join(XPathCondition.empty(), (XPathCondition[]) conditions);
    }

    public static /* varargs */ XPathCondition join(XPathCondition firstCondition, XPathCondition... conditions) {
        Arrays.stream(conditions).forEach(c -> firstCondition.addCondition(c.toString()));
        return firstCondition;
    }

    public static /* varargs */ XPathCondition oneOf(String firstTag, String... tags) {
        Stream.Builder<String> streamBuilder = Stream.<String>builder().add(firstTag);
        Arrays.stream(tags).forEach(streamBuilder::add);
        String condition = streamBuilder.build().map(tag -> "self::" + tag).collect(Collectors.joining(" or "));
        condition = String.format("(%s)", condition);
        return new XPathCondition(condition);
    }

    public XPathCondition condition(String condition) {
        return this.addCondition(condition);
    }

    public XPathCondition index(int index) {
        return this.addCondition(String.valueOf(index));
    }

    public XPathCondition position(int index) {
        return this.index(index);
    }

    public XPathCondition first() {
        return this.index(1);
    }

    public XPathCondition last() {
        return this.addCondition("last()");
    }

    public XPathCondition odd() {
        return this.addCondition("position() mod 2 = 1");
    }

    public XPathCondition oddIndex() {
        return this.odd();
    }

    public XPathCondition oddPosition() {
        return this.odd();
    }

    public XPathCondition even() {
        return this.addCondition("position() mod 2 = 0");
    }

    public XPathCondition evenIndex() {
        return this.even();
    }

    public XPathCondition evenPosition() {
        return this.even();
    }

    public XPathCondition className(String className) {
        return this.addCondition(String.format("contains(@class, '%s')", className));
    }

    public XPathCondition classes(String classes) {
        return this.className(classes);
    }

    public XPathCondition exactClassName(String className) {
        return this.addCondition(String.format("@class = '%s'", className));
    }

    public XPathCondition text(String text) {
        return this.addCondition(String.format("contains(., '%s')", text));
    }

    public XPathCondition exactText(String text) {
        return this.addCondition(String.format("normalize-space(text()) = '%s'", text));
    }

    public XPathCondition attr(String attrName, String attrValue) {
        return this.addCondition(String.format("contains(@%s, '%s')", attrName, attrValue));
    }

    public XPathCondition exactAttr(String attrName, String attrValue) {
        return this.addCondition(String.format("@%s = '%s'", attrName, attrValue));
    }

    public XPathCondition placeholder(String placeholder) {
        return this.addCondition(String.format("contains(@placeholder, '%s')", placeholder));
    }

    public XPathCondition exactPlaceholder(String placeholder) {
        return this.addCondition(String.format("@placeholder = '%s'", placeholder));
    }

    public /* varargs */ XPathCondition and(XPathCondition... conditions) {
        this.condition = this.condition + " and ";
        this.operatorAdded = true;
        if (conditions.length > 0) {
            this.condition = this.condition + "(";
        }
        Arrays.asList(conditions).forEach(c -> this.addCondition(c.toString()));
        if (conditions.length > 0) {
            this.condition = this.condition + ")";
            this.operatorAdded = false;
        }
        return this;
    }

    public /* varargs */ XPathCondition or(XPathCondition... conditions) {
        this.condition = this.condition + " or ";
        this.operatorAdded = true;
        if (conditions.length > 0) {
            this.condition = this.condition + "(";
        }
        this.condition = this.condition + Arrays.stream(conditions).map(XPathCondition::toString).collect(Collectors.joining(" or "));
        if (conditions.length > 0) {
            this.operatorAdded = false;
            this.condition = this.condition + ")";
        }
        return this;
    }

    public /* varargs */ XPathCondition not(XPathCondition... conditions) {
        String toInverse = Arrays.stream(conditions).map(XPathCondition::toString).collect(Collectors.joining(" and "));
        this.condition = String.format("not(%s)", toInverse);
        return this;
    }

    public XPathCondition wrap() {
        this.condition = String.format("(%s)", this.condition);
        return this;
    }

    public XPathCondition wrapped() {
        return this.wrap();
    }

    public XPathCondition inBrackets() {
        return this.wrap();
    }

    private XPathCondition addCondition(String condition) {
        this.condition = this.operatorAdded || this.condition.isEmpty() ? this.condition + condition : this.condition + String.format(" and %s", condition);
        this.operatorAdded = false;
        return this;
    }

    public String toString() {
        return this.condition;
    }
}

