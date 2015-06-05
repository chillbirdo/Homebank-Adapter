package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Transaction;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileCreator {

    private final IExporter exporter;
    private final List<Transaction> transactions;
    
    public FileCreator( IExporter exporter, List<Transaction> transactions){
        this.exporter = exporter;
        this.transactions = transactions;
    }
    
    public void exportFile(String filename) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "UTF-8"));
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
}
