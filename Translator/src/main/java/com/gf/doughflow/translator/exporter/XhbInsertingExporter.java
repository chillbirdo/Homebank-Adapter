package com.gf.doughflow.translator.exporter;

import java.io.BufferedReader;
import java.io.FileReader;

public class XhbInsertingExporter extends XhbExporter {

    private final String BEFORE_LINE_PREFIX = "</homebank>";
    
    public String insertFilePath;
    private String contentBefore = null;
    private String contentAfter = null;

    public XhbInsertingExporter(String insertFilePath) {
        super();
        this.insertFilePath = insertFilePath;
    }

    @Override
    public String createHeader() {
        if (contentBefore == null) {
            initContentBeforeAfter();
        }
        return contentBefore;
    }
    
    @Override
    public String createTrailer() {
        if (contentAfter == null) {
            initContentBeforeAfter();
        }
        return contentAfter;
    }   

    public void initContentBeforeAfter() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(insertFilePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith(BEFORE_LINE_PREFIX)) {
                    contentBefore = sb.toString();
                    sb = new StringBuilder();
                }
                sb.append(line).append(System.lineSeparator());
                line = br.readLine();
            }
            if (contentBefore == null) {
                System.out.println("Could not find line starting with " + BEFORE_LINE_PREFIX + ". Appending records..");
                contentBefore = sb.toString();
                contentAfter = "";
            } else {
                contentAfter = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
