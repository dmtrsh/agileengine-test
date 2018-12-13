package com.agileengine;

import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CssPathGenerator {
    private static CssPathGenerator ourInstance = new CssPathGenerator();
    private static final Function<Element, String> elementToCssPath = e -> {
        String tag = e.tagName();
        String classes = String.join(".", e.classNames());
        return tag + (classes.length() > 0 ? "." + classes : "");
    };

    public String getCssPath(List<Element> elements, Element element){
        Collections.reverse(elements);

        String parentPath = elements.stream()
                .map(elementToCssPath)
                .collect(Collectors.joining(">"));

        return parentPath + ">" + Optional.of(element).map(elementToCssPath).orElse("");
    }

    public static CssPathGenerator getInstance() {
        return ourInstance;
    }

    private CssPathGenerator() {
    }
}
