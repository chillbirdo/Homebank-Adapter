package com.gf.doughflow.run;

import com.gf.doughflow.workspace.WorkSpace;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class DoughFlow {

    public static void main(String args[]) throws IOException, InterruptedException {

        String workDir = ".";
        if (args.length > 0) {
            workDir = args[0];
        }
        WorkSpace ws = new WorkSpace(workDir);

        //start homebank
        Process process = new ProcessBuilder("homebank",
                ws.getActualFile().getAbsolutePath()).start();

        //make local copies
        long lastmodOld = ws.getActualFile().lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmssSSS");
        while(process.isAlive()){
            Thread.sleep(2000l);
            long lastmod = ws.getActualFile().lastModified();
            if( lastmod > lastmodOld){
                File destFile = new File(ws.getBackupDir() + "/backup.xhb_" + sdf.format(new Date()));
                FileUtils.copyFile(ws.getActualFile(), destFile);
            } 
        }
    }
}
