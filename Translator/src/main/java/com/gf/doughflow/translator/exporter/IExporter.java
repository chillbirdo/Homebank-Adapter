package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Transaction;

public interface IExporter {
    public String createHeader();
    public String export(Transaction transaction);
    public String createTrailer();
}
