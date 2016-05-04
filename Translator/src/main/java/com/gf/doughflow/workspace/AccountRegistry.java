package com.gf.doughflow.workspace;

import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRegistry {
    
    private static final String SEPARATOR = ";";
    private static final String ID_EASYBANK = "EASYBANK";
    
    private static final int ACCOUNT_BANK = 0;
    private static final int ACCOUNT_CURR = 1;
    private static final int ACCOUNT_NAME = 2;
    
    private static Map<Integer, Account> accounts;
    
    public static void init(List<String> accountjsons) {
        accounts = new HashMap<>();
        for (int id = 0; id < accountjsons.size(); id++) {
            String accountjson = accountjsons.get(id);
            String[] accountprop = accountjson.split(SEPARATOR);
            if (accountprop[ACCOUNT_BANK].equals(ID_EASYBANK)) {
                accounts.put(id, new Account(id, accountprop[ACCOUNT_NAME], Currency.valueOf(accountprop[ACCOUNT_CURR]), new EasyBankImporter(id)));
            }
        }
    }
    
    public static Map<Integer, Account> getAccounts() {
        return accounts;
    }
    
    public static Account get(int accountId) {
        return accounts.get(accountId);
    }
}
