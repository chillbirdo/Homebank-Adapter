package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Category;
import com.gf.doughflow.translator.model.Transaction;
import java.util.List;

public class XhbExporter implements IExporter {

    private String hbVersion = "0.59999999999999998";
    private String title = "default";

    private List<Category> categories;
    private List<Account> accounts;

    @Override
    public String createHeader() {
        String header = "<?xml version=\"1.0\"?>\n"
                + "<homebank v=\"" + hbVersion + "\">\n"
                + "<properties title=\"" + title + "\" car_category=\"0\" auto_nbdays=\"0\"/>\n";
        String accHeader = "";
        for( Account acc : accounts){
            accHeader += convertAccount(acc) + "\n";
        }
        String catHeader = "";
        for( Category cat : categories){
            catHeader += convertCategory(cat) + "\n";
        }
        return header + accHeader + catHeader;
    }

    @Override
    public String export(Transaction transaction) {
        return "";
    }

    @Override
    public String createTrailer() {
        return "</homebank>";
    }

    private String convertAccount(Account acc) {
        return "<account key=\"" + acc.getId() + "\" flags=\"0\" pos=\"" + acc.getId() + "\" type=\"1\" name=\"" + acc.getName() + "\" number=\"\" bankname=\"\" initial=\"0\" minimum=\"0\" cheque1=\"0\" cheque2=\"0\"/>";
    }

    private String convertCategory(Category cat) {
        return "";
    }

    public String getHbVersion() {
        return hbVersion;
    }

    public void setHbVersion(String hbVersion) {
        this.hbVersion = hbVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
