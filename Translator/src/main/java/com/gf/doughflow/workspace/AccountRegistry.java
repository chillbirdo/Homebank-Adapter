package com.gf.doughflow.workspace;

import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.importer.IImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AccountRegistry {

    private static final String SEPARATOR = ";";
    private static final String ID_EASYBANK = "EASYBANK";

    private static final int ACCOUNT_BANK = 0;
    private static final int ACCOUNT_CURR = 1;
    private static final int ACCOUNT_NAME = 2;

    private static Map<Integer, Account> accounts;

    public static void init(Map<Integer, String> accountInfoStrMap) {
        accounts = new HashMap<>();
        for (Entry<Integer, String> e : accountInfoStrMap.entrySet()) {
            String accountInfoStr = e.getValue();
            Integer accountId = e.getKey();
            String[] accountprop = accountInfoStr.split(SEPARATOR);
            IImporter importer = null;
            switch (accountprop[ACCOUNT_BANK]) {
                case ID_EASYBANK:
                    importer = new EasyBankImporter(accountId);
                    break;
                default:
                    throw new RuntimeException("Invalid Bank Id: " + accountprop[ACCOUNT_BANK]);
            }
            accounts.put(accountId, new Account(accountId, accountprop[ACCOUNT_NAME], Currency.valueOf(accountprop[ACCOUNT_CURR]), importer));
        }
    }

    public static Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public static Account get(int accountId) {
        return accounts.get(accountId);
    }
}
