package com.agileengine;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SmartXMLAnalyzer {

    private static Logger LOGGER = LoggerFactory.getLogger(SmartXMLAnalyzer.class);
    private static SmartXMLAnalyzer ourInstance = new SmartXMLAnalyzer();

    private DocumentReader documentReader = DocumentReader.getInstance();
    private ElementMatcher elementMatcher = ElementMatcher.getInstance();

    public static void main(String[] args) {
        SmartXMLAnalyzer smartXMLAnalyzer = getInstance();

        String resourcePathOriginal = args[0];
        String resourcePathTest = args[1];
        String targetElementId = "make-everything-ok-button";

        DocumentReader documentReader = smartXMLAnalyzer.getDocumentReader();
        ElementMatcher elementMatcher = ElementMatcher.getInstance();
        CssPathGenerator cssPathGenerator = CssPathGenerator.getInstance();


        Optional<Document> docOriginalOpt = documentReader.getDocument(new File(resourcePathOriginal));
        Optional<Document> docCaseOpt = documentReader.getDocument(new File(resourcePathTest));

        Optional<Element> buttonOpt = docOriginalOpt.flatMap(document -> elementMatcher.findElementById(document, targetElementId));

        Optional<Attributes> attributesOpt = buttonOpt.map(Element::attributes);

        Optional<Element> matchedButtonOpt = docCaseOpt.flatMap(document ->
                attributesOpt.flatMap(attributes -> elementMatcher.findMatchingElement(document, attributes)));

        List<Element> parentElements = matchedButtonOpt.map(Element::parents)
                .map(o -> o.stream().collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        matchedButtonOpt.map(matchedButton -> cssPathGenerator.getCssPath(parentElements, matchedButton))
                .ifPresent(path -> LOGGER.info("Target element CssPath: [{}]", path));
    }


    public static SmartXMLAnalyzer getInstance() {
        return ourInstance;
    }

    private SmartXMLAnalyzer() {
    }

    public DocumentReader getDocumentReader() {
        return documentReader;
    }

    public void setDocumentReader(DocumentReader documentReader) {
        this.documentReader = documentReader;
    }

    public ElementMatcher getElementMatcher() {
        return elementMatcher;
    }

    public void setElementMatcher(ElementMatcher elementMatcher) {
        this.elementMatcher = elementMatcher;
    }
}