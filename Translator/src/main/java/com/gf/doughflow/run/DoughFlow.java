package com.gf.doughflow.run;

import com.gf.doughflow.swing.UIHandler;
import com.gf.doughflow.workspace.AccountRegistry;
import com.gf.doughflow.workspace.DFProperties;
import com.gf.doughflow.workspace.WorkSpace;
import java.io.IOException;
import java.util.logging.Logger;

public class DoughFlow {

    private final long FILELISTENER_DELAY_MS = 2000;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void start(String propFilePath) throws IOException {

        DFProperties prop = new DFProperties(propFilePath);
        AccountRegistry.init(prop.readAccounts());
        UIHandler wdc = new UIHandler();
        String workDir = wdc.showDirChooser(prop.getDefaultWorkDir());
        prop.setAndWriteDefaultWorkDir(workDir);
        WorkSpace ws = new WorkSpace(workDir);
        ws.importData(wdc);

        try {
            //start homebank
            Process process = new ProcessBuilder(prop.getHomebankExecuteable(),
                    ws.getFileActual().getAbsolutePath()).start();

            //make local backups
            long lastmodOld = ws.getFileActual().lastModified();
            while (process.isAlive()) { // every 2 seconds check whether the homebank file has changed and make a backup
                Thread.sleep(FILELISTENER_DELAY_MS);
                long lastmod = ws.getFileActual().lastModified();
                if (lastmod > lastmodOld) {
                    ws.createBackup(null);
                    lastmodOld = lastmod;
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.severe(e.getMessage());
        } finally {
            wdc.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String propFile = "./adapter.properties";
        if (args.length > 0) {
            propFile = args[0];
        }
        new DoughFlow().start(propFile);
    }
}
