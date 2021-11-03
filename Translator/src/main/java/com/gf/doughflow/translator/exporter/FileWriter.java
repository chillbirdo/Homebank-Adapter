package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Transaction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileWriter {

    private final IExporter exporter;
    private final List<Transaction> transactions;

    public FileWriter(IExporter exporter, List<Transaction> transactions){
        this.exporter = exporter;
        this.transactions = transactions;
    }

    public void exportFile(File file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), StandardCharsets.UTF_8));
            String header = exporter.createHeader();
            if (header != null) {
                writer.write(header);
            }
            for (Transaction t : transactions) {
                writer.write(exporter.export(t));
            }
            String trailer = exporter.createTrailer();
            if (trailer != null) {
                writer.write(trailer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public static int mergeIntoXhb(File xhbActualFile, List<Transaction> records){
        XhbInsertingExporter xhbInsertingExporter = new XhbInsertingExporter(xhbActualFile);
        List<Transaction> nonDuplicates = xhbInsertingExporter.filterDuplicates(records);
        FileWriter fc = new FileWriter(xhbInsertingExporter, nonDuplicates);
        fc.exportFile(xhbActualFile);
        return nonDuplicates.size();
    }

}
