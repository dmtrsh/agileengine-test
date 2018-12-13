package com.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class DocumentReader {
    private static Logger LOGGER = LoggerFactory.getLogger(SmartXMLAnalyzer.class);

    private static String CHARSET_NAME = "utf8";
    private static DocumentReader ourInstance = new DocumentReader();


    public  Optional<Document> getDocument(File htmlFile){
        try {
            return Optional.ofNullable(Jsoup.parse(htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath()));
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    public static DocumentReader getInstance() {
        return ourInstance;
    }

    private DocumentReader() {
    }
}
