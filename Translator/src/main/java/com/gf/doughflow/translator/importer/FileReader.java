package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;
import com.opencsv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader {

    public static List<Transaction> importCsv(BaseCsvImporter importer, File file) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(file), importer.getCharset());
        List<Transaction> transactionList = new LinkedList<>();
        RFC4180Parser parser = new RFC4180ParserBuilder()
                .withSeparator(importer.getCsvImporterProperties().getSeparator().toCharArray()[0])
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(importer.getCsvImporterProperties().getHeaderLineAmount())
                .withCSVParser(parser)
                .build();
        String[] line = null;
        boolean hasNext;
        int lineCounter = 0;
        do {
            hasNext = false;
            Transaction transaction;
            try {
                line = csvReader.readNext();
                transaction = importer.toTransaction(line);
                if (transaction != null) {
                    transactionList.add(transaction);
                }
                hasNext = csvReader.peek() != null;
            } catch (Exception ex) {
                Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
                        "invalid record in file '" + file.getAbsolutePath() + " in line " + lineCounter,
                        ex);
            }
            lineCounter++;
        } while (hasNext);
        reader.close();
        csvReader.close();
        return transactionList;
    }

    public static List<Transaction> importNonCsv(INonCsvImporter importer, File file) {
        List<Transaction> ret = new LinkedList<>();
        try {
            InputStream in = new FileInputStream(file.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(in, importer.getCharset() != null ? importer.getCharset() : StandardCharsets.UTF_8));
            String line = br.readLine();
            while (line != null) {
                Transaction t;
                try {
                    t = importer.toTransaction(line);
                    if (t != null) {
                        ret.add(t);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, "Could not import Record. ['" + line + "']", ex);
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
