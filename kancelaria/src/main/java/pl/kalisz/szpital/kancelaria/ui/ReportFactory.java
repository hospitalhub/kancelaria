package pl.kalisz.szpital.kancelaria.ui;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.server.StreamResource;

import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.TempFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.raport.RaportEtykietyPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportKorespondencjaPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportPocztaPdf;

class ReportFactory {

  private final FileLocationContext fileCtx = new FileLocationContext();

  public StreamResource getReport(ReportType type, ArrayList<Transaction> ts) {
    fileCtx.setFileLocationStrategy(new TempFileLocationStrategy());
    switch (type) {
      case BIEZACE:
        // TODO(AM) przekazywać pdf -> stream resource tworz wewnatrz
        // pdfdocumentwindow
        return new StreamResource(new RaportKorespondencjaPdf(ts), fileCtx.getFile(null).getName());
      case POCZTOWY:
        // table.setFilterFieldValue(Transaction.TYP_WIADOMOSCI, TypWiadomosci.WYCHODZĄCE_POLECONE);
        return new StreamResource(new RaportPocztaPdf(ts), fileCtx.getFile(null).getName());
      case ETYKIETY:
        // TYLKO WYCHODZĄCE
        return new StreamResource(new RaportEtykietyPdf(ts), fileCtx.getFile(null).getName());
    }
    return null;
  }
}
