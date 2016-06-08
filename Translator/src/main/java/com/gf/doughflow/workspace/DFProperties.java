package com.gf.doughflow.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class DFProperties extends Properties {

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String WORKDIR_DEFAULT = "workdir_default";
    private final String WORKDIR_DEFAULT_DEFAULT = "./ws_private";
    private final String EXEC_HOMEBANK = "exec_homebank";
    private final String ACCOUNT_PREFIX = "account_id";
    

    File propFile;
    String workDir;
    String homebankExecuteable;

    public DFProperties(String propFilePath) {
        propFile = new File(propFilePath);
        try {
            InputStream in = new FileInputStream(propFile);
            init(in);
            in.close();
        } catch (Exception e) {
            logger.severe("Error when reading properties file '" + propFilePath + "'. Exiting...\n" + e.getMessage());
        }
    }

    public void setAndWriteDefaultWorkDir(String wd) {
        setProperty(WORKDIR_DEFAULT, wd);
        try {
            writeToFile();
        } catch (Exception ex) {
            logger.warning("Could not write properties file: " + propFile.getAbsolutePath() + "\n" + ex.getMessage());
        }
    }

    private void writeToFile() throws Exception {
        OutputStream output = new FileOutputStream(propFile);
        store(output, null);
        output.close();
    }

    private void init(InputStream stream) throws IOException {
        super.load(stream);
        workDir = readWorkDir();
        homebankExecuteable = readHomebank();
    }

    private String readWorkDir() {
        String wd = getProperty(WORKDIR_DEFAULT);
        if (wd == null || wd.isEmpty()) {
            return WORKDIR_DEFAULT_DEFAULT;
        }
        return wd;
    }

    private String readHomebank() {
        String homebank = getProperty(EXEC_HOMEBANK);
        if (homebank == null || homebank.isEmpty()) {
            throw new RuntimeException("Path to homebank is missing. Please adapt the properties");
        }
        return homebank;
    }

    public Map<Integer,String> readAccounts() {
        if( getProperty(ACCOUNT_PREFIX + 0) != null){
            throw new RuntimeException("Account with ID 0 detected. Accounts should start with ID 1.");
        }
        Map<Integer,String> accountMap = new HashMap(10);
        int id = 1;
        while(getProperty(ACCOUNT_PREFIX + id) != null){
            accountMap.put(id, getProperty( ACCOUNT_PREFIX + id));
            id++;
        }
        if(accountMap.isEmpty()){
            throw new RuntimeException("No accounts have been configured.");
        }
        return accountMap;
    }
    
    public String getDefaultWorkDir() {
        return workDir;
    }

    public File getPropFile() {
        return propFile;
    }

    public void setPropFile(File propFile) {
        this.propFile = propFile;
    }

    public String getHomebankExecuteable() {
        return homebankExecuteable;
    }

    public void setHomebankExecuteable(String homebankExecuteable) {
        this.homebankExecuteable = homebankExecuteable;
    }
    
}
