package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Category;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.translator.util.DescriptionConverter;
import com.gf.doughflow.translator.util.HomebankDate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class XhbExporter implements IExporter {

    private String hbVersion = "0.59999999999999998";
    private String title = "default";
    private final DecimalFormat df = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US));

    private List<Category> categories;
    private Collection<Account> accounts;

    @Override
    public String createHeader() {
        String header = "<?xml version=\"1.0\"?>" + System.lineSeparator()
                + "<homebank v=\"" + hbVersion + "\">" + System.lineSeparator()
                + "<properties title=\"" + title + "\" car_category=\"0\" auto_nbdays=\"0\"/>" + System.lineSeparator();
        StringBuilder accHeader = new StringBuilder();
        for (Account acc : accounts) {
            accHeader.append(convertAccount(acc)).append(System.lineSeparator());
        }
        StringBuilder catHeader = new StringBuilder();
        for (Category cat : categories) {
            catHeader.append(convertCategory(cat)).append(System.lineSeparator());
        }
        return header + accHeader + catHeader;
    }

    @Override
    public String export(Transaction transaction) {
        return "<ope date=\"" + HomebankDate.dateToHomebankDate(transaction.getDate()) + "\" amount=\"" + convertValue(transaction.getValue()) + "\" account=\"" + transaction.getAccount().getId()
                + "\" wording=\"" + replaceProblematicCharacters(DescriptionConverter.replaceGermanLetters(transaction.getDescription().toLowerCase()))
                + "\" dst_account=\"0\" payee=\"0\" category=\"0\" info=\"\" tags=\"\" kxfer=\"0\" />" + System.lineSeparator();
    }

    @Override
    public String createTrailer() {
        return "</homebank>";
    }

    private String convertAccount(Account acc) {
        return "<account key=\"" + acc.getId() + "\" flags=\"0\" pos=\"" + acc.getId() + "\" type=\"1\" name=\"" + acc.getName() + "\" number=\"\" bankname=\"\" initial=\"0\" minimum=\"0\" cheque1=\"0\" cheque2=\"0\"/>";
    }

    private String replaceProblematicCharacters( String description){
        return description.replaceAll("&", "&amp;");
    }

    private String convertCategory(Category cat) {
        return "";
    }

    private String convertValue(Double value) {
        return df.format(value);
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

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
