package com.gf.doughflow.workspace;

import com.gf.doughflow.translator.exporter.FileExporter;
import com.gf.doughflow.translator.exporter.XhbExporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import sun.java2d.pipe.hw.AccelDeviceEventListener;

public class WorkSpace {

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String FILE_ACTUAL = "actual.xhb";
    private final String FOLDER_ACTUAL = "actual";
    private final String FOLDER_BACKUP = "backup";
    private final String FOLDER_IMPORT = "import";
    private final String FOLDER_TEMP = "temp";

    private final File actualFile;
    private final File workDir;
    private final File actualDir;
    private final File backupDir;
    private final File importDir;
    private final File tempDir;

    public WorkSpace(String wd) {
        this.workDir = checkExistsAndCreateDir(wd);
        this.actualDir = checkExistsAndCreateDir(workDir.getAbsolutePath() + "/" + FOLDER_ACTUAL);
        this.actualFile = checkExistsAndCreateActualFile(actualDir.getAbsolutePath() + "/" + FILE_ACTUAL);
        this.backupDir = checkExistsAndCreateDir(workDir.getAbsolutePath() + "/" + FOLDER_BACKUP);
        this.importDir = checkExistsAndCreateDir(workDir.getAbsolutePath() + "/" + FOLDER_IMPORT);
        this.tempDir = checkExistsAndCreateDir(workDir.getAbsolutePath() + "/" + FOLDER_TEMP);

    }

    private File checkExistsAndCreateActualFile(String filePath) {
        File actual = new File(filePath);
        if (!actual.exists()) {
            FileExporter.exportFile(new ArrayList(), createInitializingExporter(), filePath);
            System.out.println("created initial xhb file: '" + filePath + "'.");            
        } else if (!actual.isFile()) {
            throw new RuntimeException("File '" + actual.getAbsolutePath() + "' is not a file!");
        }
        return actual;
    }

    private XhbExporter createInitializingExporter() {
        XhbExporter xhbExporter = new XhbExporter();
        List<Account> accounts = new ArrayList<Account>(2);
        Account easygiro = new Account(1, "easy giro", Currency.EUR);
        Account easyspar = new Account(2, "easy spar", Currency.EUR);
        accounts.add(easygiro);
        accounts.add(easyspar);
        xhbExporter.setAccounts(accounts);
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

    public File getActualFile() {
        return actualFile;
    }

    public File getWorkDir() {
        return workDir;
    }

    public File getActualDir() {
        return actualDir;
    }

    public File getBackupDir() {
        return backupDir;
    }

    public File getImportDir() {
        return importDir;
    }

    public File getTempDir() {
        return tempDir;
    }
    
    
}
