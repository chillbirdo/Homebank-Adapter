package com.gf.doughflow.workspace;

import com.gf.doughflow.swing.UIHandler;
import com.gf.doughflow.translator.exporter.FileWriter;
import com.gf.doughflow.translator.exporter.XhbExporter;
import com.gf.doughflow.translator.importer.FileReader;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Transaction;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class WorkSpace {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String FILE_ACTUAL = "actual.xhb";
    private final String FOLDER_ACTUAL = "actual";
    private final String FOLDER_BACKUP = "backup";
    private final String FOLDER_IMPORT = "import";
    private final String FOLDER_TEMP = "temp";
    private final String FOLDER_PROCESSED = "processed";

    private final File fileActual;
    private final File folderActual;
    private final File folderWork;
    private final File folderBackup;
    private final File folderImport;
    private final File folderTemp;
    private final Map<Integer, Account> accounts;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");

    public WorkSpace(String folderWork) {
        this.accounts = AccountRegistry.getAccounts();
        this.folderWork = checkExistsAndCreateDir(folderWork);
        this.folderActual = checkExistsAndCreateDir(this.folderWork.getAbsolutePath() + "/" + FOLDER_ACTUAL);
        this.fileActual = checkExistsAndCreateActualFile(folderActual.getAbsolutePath() + "/" + FILE_ACTUAL);
        this.folderBackup = checkExistsAndCreateDir(this.folderWork.getAbsolutePath() + "/" + FOLDER_BACKUP);
        this.folderImport = checkExistsAndCreateDir(this.folderWork.getAbsolutePath() + "/" + FOLDER_IMPORT);
        for (Integer accountId : accounts.keySet()) {
            String importDirAccount = this.folderImport + "/" + accountNameToFileName(accounts.get(accountId).getName());
            String importDirAccountProcessed = importDirAccount + "/" + FOLDER_PROCESSED;
            accounts.get(accountId).setImportDir(importDirAccount);
            checkExistsAndCreateDir(importDirAccount);
            accounts.get(accountId).setImportDirProcessed(importDirAccountProcessed);
            checkExistsAndCreateDir(importDirAccountProcessed);
        }
        this.folderTemp = checkExistsAndCreateDir(this.folderWork.getAbsolutePath() + "/" + FOLDER_TEMP);
        logger.info("Working on workspace at " + folderWork);
    }

    public void importData(UIHandler wdc) throws IOException {
        for (Account account : accounts.values()) {
            long lastmod = 0;
            File latestFile = null;
            logger.info("looking for files to import in '" + account.getImportDir() + "'");
            File[] files = new File(account.getImportDir()).listFiles();
            //find latest file
            for (File f : files) {
                if (f.isDirectory()) {
                    continue;
                }
                if (f.lastModified() > lastmod) {
                    lastmod = f.lastModified();
                    latestFile = f;
                }
            }
            //import latest file
            if (latestFile != null) {
                logger.info("Importing file '" + latestFile.getAbsolutePath() + "' for account '" + account.getName() + "'");
                createBackup("beforeImport");
                List<Transaction> freshTransactions = FileReader.importCsv(account.getImporter(), latestFile);
                int imported = FileWriter.mergeIntoXhb(this.fileActual, freshTransactions);
                String msg = "Imported " + imported + " transactions of account '" + account.getName() + "'";
                if (imported > 0) {
                    logger.info(msg);
                    wdc.showImportedMessage(msg);
                }
            }
            //move all files into processed
            for (File f : files) {
                if (f.isFile()) {
                    try {
                        FileUtils.moveFile(f, new File(account.getImportDirProcessed() + "/" + f.getName() + "_" + sdf.format(new Date())));
                        logger.fine("Moved file '" + f.getAbsolutePath() + "' into '" + account.getImportDirProcessed() + "'");
                    } catch (IOException ex) {
                        logger.warning("Could not move file '" + f.getAbsolutePath() + "' into '" + account.getImportDirProcessed() + "'");
                    }
                }
            }
        }
    }

    public void createBackup(String comment) {
        File destFile = new File(getFolderBackup() + "/backup.xhb_" + sdf.format(new Date()) + ((comment != null) ? "_" + comment : ""));
        try {
            FileUtils.copyFile(getFileActual(), destFile);
        } catch (IOException ex) {
            Logger.getLogger(WorkSpace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String accountNameToFileName(String accountName) {
        return accountName.replaceAll(" ", "_");
    }

    private File checkExistsAndCreateActualFile(String filePath) {
        File actual = new File(filePath);
        if (!actual.exists()) {
            FileWriter fc = new FileWriter(createInitializingExporter(), new ArrayList());
            fc.exportFile(actual);
            System.out.println("created initial xhb file: '" + filePath + "'.");
        } else if (!actual.isFile()) {
            throw new RuntimeException("File '" + actual.getAbsolutePath() + "' is not a file!");
        }
        return actual;
    }

    private XhbExporter createInitializingExporter() {
        XhbExporter xhbExporter = new XhbExporter();
        xhbExporter.setAccounts(accounts.values());
        xhbExporter.setCategories(new ArrayList());
        return xhbExporter;
    }

    private File checkExistsAndCreateDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("could not create directory: '" + dir.getAbsolutePath() + "'.");
            }
            System.out.println("created directory: '" + dirName + "'.");
        } else if (!dir.isDirectory()) {
            throw new RuntimeException(dir.getAbsolutePath() + " is not a directory.");
        }
        return dir;
    }

    public File getFileActual() {
        return fileActual;
    }

    public File getFolderWork() {
        return folderWork;
    }

    public File getFolderActual() {
        return folderActual;
    }

    public File getFolderBackup() {
        return folderBackup;
    }

    public File getFolderImport() {
        return folderImport;
    }

    public File getFolderTemp() {
        return folderTemp;
    }
}
