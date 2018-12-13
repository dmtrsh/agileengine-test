package com.agileengine;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class ElementMatcher {
    private static ElementMatcher ourInstance = new ElementMatcher();

    public Optional<Element> findMatchingElement(Document doc, Attributes attributes) {
        return doc.getAllElements()
                .stream()
                .filter(el -> matches(el, attributes))
                .findFirst();

    }

    private boolean matches(Element element, Attributes attributes){
        return element.attr("title").equals(attributes.get("title"))
                && (!element.classNames().contains("btn-danger") || element.classNames().contains("btn-success"))
                || element.attr("onclick").equals(attributes.get("onclick"));
    }

    public Optional<Element> findElementById(Document doc, String targetElementId) {

        return Optional.ofNullable(doc.getElementById(targetElementId));
    }

    public static ElementMatcher getInstance() {
        return ourInstance;
    }

    private ElementMatcher() {
    }
}
