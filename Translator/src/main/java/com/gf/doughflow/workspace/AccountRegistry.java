package com.gf.doughflow.workspace;

import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import java.util.HashMap;
import java.util.Map;

public class AccountRegistry {

    private static Map<Integer, Account> accounts;
    
    static{
        accounts = new HashMap<>();
        accounts.put(1, new Account(1, "easy giro", Currency.EUR, new EasyBankImporter(1)));
        accounts.put(2, new Account(2, "easy spar", Currency.EUR, new EasyBankImporter(2)));
    }

    public static Map<Integer, Account> getAccounts() {
        return accounts;
    }
    
    public static Account get(int accountId){
        return accounts.get(accountId);
    }
}
