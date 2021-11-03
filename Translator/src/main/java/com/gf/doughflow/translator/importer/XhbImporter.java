package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.translator.util.HomebankDate;
import com.gf.doughflow.workspace.AccountRegistry;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XhbImporter implements INonCsvImporter {

    private final String XML_RECORD_NAME = "ope";
    private final String XML_RECORD_ATTR_DATE = "date";
    private final String XML_RECORD_ATTR_AMOUNT = "amount";
    private final String XML_RECORD_ATTR_ACCOUNT = "account";
    private final String XML_RECORD_ATTR_WORDING = "wording";

    @Override
    public Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    @Override
    public Transaction toTransaction(String recordString) throws Exception {
        if (!recordString.startsWith("<" + XML_RECORD_NAME)) {
            return null;
        }
        Element xmlElem;
        xmlElem = parseElement(recordString);
        String date = xmlElem.getAttribute(XML_RECORD_ATTR_DATE);
        String amount = xmlElem.getAttribute(XML_RECORD_ATTR_AMOUNT);
        String account = xmlElem.getAttribute(XML_RECORD_ATTR_ACCOUNT);
        String wording = xmlElem.getAttribute(XML_RECORD_ATTR_WORDING);
        Transaction t = new Transaction();
        t.setDate(HomebankDate.homebankDateToDate(Integer.parseInt(date)));
        t.setValue(Double.parseDouble(amount));
        t.setAccount(AccountRegistry.get(Integer.parseInt(account)));
        t.setDescription(wording);
        return t;
    }

    private Element parseElement(String xmlData) throws XPathExpressionException {
        InputSource source = new InputSource(new StringReader(xmlData));
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node node = (Node) xPath.evaluate("//" + XML_RECORD_NAME, source, XPathConstants.NODE);
        return (Element) node;
    }

}
