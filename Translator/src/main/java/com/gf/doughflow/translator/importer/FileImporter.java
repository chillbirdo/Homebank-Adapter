package com.gf.doughflow.translator.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gf.doughflow.translator.model.Transaction;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileImporter {

    public static List<Transaction> importRecordPerLine(IImporter importer, File file) {
        List<Transaction> ret = new LinkedList<>();
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));
            String line = br.readLine();

            while (line != null) {
                System.out.println("LINE: " + line);
                Transaction t;
                try {
                    t = importer.toTransaction(line);
                    ret.add(t);
                } catch (Exception ex) {
                    Logger.getLogger(FileImporter.class.getName()).log(Level.SEVERE, "Could not import Record. ['" + line + "']", ex);
                }
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
