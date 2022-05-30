package com.gf.doughflow.workspace;

import com.gf.doughflow.translator.importer.*;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class AccountRegistry {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String SEPARATOR = ";";
    private static final String ID_EASYBANK = "EASYBANK";
    private static final String ID_N26 = "N26";
    private static final String ID_FLATEX = "FLATEX";
    private static final String ID_DKB = "DKB";
    private static final String ID_DKB_CREDIT = "DKB_CREDIT";
    private static final String ID_REVOLUT = "REVOLUT";
    private static final String ID_OTHER_EXPENSES = "OTHER_EXPENSES";

    private static final int ACCOUNT_BANK = 0;
    private static final int ACCOUNT_CURR = 1;
    private static final int ACCOUNT_NAME = 2;
    private static final int IGNORE_BEFORE_DATE = 3;

    private static final String DATE_FORMAT = "dd/MM/yy";
    private static Map<Integer, Account> accounts;

    public static void init(Map<Integer, String> accountInfoStrMap) {
        accounts = new HashMap<>();
        for (Entry<Integer, String> e : accountInfoStrMap.entrySet()) {
            String accountInfoStr = e.getValue();
            Integer accountId = e.getKey();
            String[] accountprop = accountInfoStr.split(SEPARATOR);
            BaseCsvImporter importer;
            switch (accountprop[ACCOUNT_BANK]) {
                case ID_EASYBANK:
                    importer = new EasybankCsvImporter(accountId);
                    break;
                case ID_N26:
                    importer = new N26CsvImporter(accountId);
                    break;
                case ID_DKB:
                    importer = new DKBCsvImporter(accountId);
                    break;
                case ID_DKB_CREDIT:
                    importer = new DKBCreditcardCsvImporter(accountId);
                    break;
                case ID_REVOLUT:
                    importer = new RevolutCsvImporter(accountId);
                    break;
                case ID_FLATEX:
                    importer = new FlatexCsvImporter(accountId);
                    break;
                case ID_OTHER_EXPENSES:
                    importer = null;
                    break;
                default:
                    throw new RuntimeException("Invalid Bank Id: " + accountprop[ACCOUNT_BANK]);
            }
            Date ignoreBeforeDate = null;
            try {
                ignoreBeforeDate = new SimpleDateFormat(DATE_FORMAT).parse(accountprop[IGNORE_BEFORE_DATE]);
                logger.fine("Account with Id '" + accountId + "' ignores transactions before date: " + accountprop[IGNORE_BEFORE_DATE]);
            } catch (ParseException ex) {
            }
            accounts.put(accountId, new Account(accountId, accountprop[ACCOUNT_NAME], Currency.valueOf(accountprop[ACCOUNT_CURR]), ignoreBeforeDate, importer));
        }
    }

    public static Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public static Account get(int accountId) {
        return accounts.get(accountId);
    }

}
