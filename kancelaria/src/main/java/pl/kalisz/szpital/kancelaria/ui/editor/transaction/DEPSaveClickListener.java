package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Deprecated
final class DEPSaveClickListener extends TransactionFormButtonListener {

  public DEPSaveClickListener(TransactionWindow window) {
    super(window);
  }

  @Override
  public void buttonClick(ClickEvent event) {
//    DbHelper dbHelper = ((DashboardUI)UI.getCurrent()).getDbHelper();
//    window.getTransaction().setOpis("ZXCV");
//    dbHelper.getContainer(DbHelper.TRANSACTION_CONTAINER).addEntity(window.getTransaction());
    window.close();
  }
  
  /*
  if (typWiadomosci.getValue() == null) {
    Notification.show(Strings.NIE_WYBRANO_TYPU_ZDARZENIA, Strings.WYBIERZ_TYP,
        Notification.Type.WARNING_MESSAGE);
  } else if (!walidacjaKomorkaOrg()) {
    Notification.show(Strings.NIEPRAWIDLOWA_WARTOSC, Strings.POPRAW_KOMORKI_ORG,
        Notification.Type.WARNING_MESSAGE);
  } else if (transaction.getAdres() == null) {
    Notification.show(Strings.BRAK_ADRESU, Strings.WYBIERZ_ADRES,
        Notification.Type.WARNING_MESSAGE);
  } else {
    // context.setFileLocationStrategy(new FilepathLocationStrategy());
    // if (pdfUploadFile == null && !context.exists(transaction)) {
    System.out.println("BRAK??? " + transaction.getPlikSciezka());
    ConfirmDialog.show(UI.getCurrent(), Strings.BRAK_SKANU, Strings.CZY_ZAPISAC_BEZ_SKANU,
        "Tak", "Nie", new ConfirmDialog.Listener() {
      @Override
      public void onClose(final ConfirmDialog dialog) {
        if (dialog.isConfirmed()) {
          // zapisz(transaction);
        }
      }
    });
    // } else {
    // logger.debug("zapisz start");
    // zapisz(transaction);


  public void setLastSavedTransaction(final Transaction transaction) {
    ((DashboardUI) UI.getCurrent()).setLastTransaction(transaction);
  }
 // TODO(AM) testy do zrobienia - 1 utworzenie zdarzenia 2 skopiowanie zdarzenia z wszystkim 3
  // edycja
  // TODO(AM) 4 przeniesienie do kosza 5 przeniesienie do innego typu wiadomosci (zewn -> wewn)
  // TODO(AM) rzeczy do sprawdzenia: utworzenie, sygnatura, zmieniona tresc

  private void zapisz(final Transaction transaction) {
//    TypWiadomosci typWiadomosci = TypWiadomosci.getByTytul(getTyp());
    boolean isNewTransaction = transaction.getId() == null;
    if (isNewTransaction) {
      Date data = new Date(System.currentTimeMillis());
      transaction.setData(data);
//      transaction.setTypWiadomosci(typWiadomosci);
    }
//    transaction.setTypPisma(getTypPisma());
//    transaction.setOdbiorca(getKomorka());
//    transaction.setOpis(getOpis());
//    transaction.setTypWiadomosci(typWiadomosci);
    transaction.setSygnatura(null);
    DashboardUI dashboardUI = ((DashboardUI) UI.getCurrent());
    @SuppressWarnings("unchecked")
    JPAContainer<Transaction> container =
        dashboardUI.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);
    try {
      Integer id = (Integer) container.addEntity(transaction);
      transaction.setId(id);
      // BUG: ksiegowosc i typ pisma
      String name = ((DashboardUI) UI.getCurrent()).getUser().getLogin();
      if (name.equals("ksiegowosc") && transaction.getTypPisma().getNazwa().equals("PISMO")) {
        TypPisma tpFaktura = new TypPisma("FAKTURA");
        container.getContainerProperty(transaction.getId(), "typPisma").setValue(tpFaktura);
      }
      // BUG1END
      zapiszPlik(transaction);
      container.commit();
      // Notification.show("Numer dokumentu: " + transaction.getId().toString(),
      // Notification.Type.ERROR_MESSAGE);
      TransactionNumberNotificationWindow sub =
          new TransactionNumberNotificationWindow(transaction.getId());
      UI.getCurrent().addWindow(sub);
      close();
      setLastSavedTransaction(transaction);
      // Broadcaster
      // .broadcast(name + " dodaje " + transaction.getTypPisma() + " nr " + transaction.getId());
    } catch (Exception e) {
      showErr();
    }
  }

  private String zapiszPlik(final Transaction t) {
    // dodano plik
    if (pdfUploadFile != null && pdfUploadFile.exists()) {
      System.out.println("File: " + pdfUploadFile);
      try {
        context.setFileLocationStrategy(new NumerIdFileLocation());
        final File newFile = context.getFile(t.getId());
        System.out.println(newFile.getAbsolutePath());
        new Thread() {
          public void run() {
            pdfUploadFile.renameTo(newFile);
          };
        }.start();
        return newFile.getAbsolutePath();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
    if (t.getPlikSciezka() != null && !t.getPlikSciezka().equals("")) {
      // skopiowano zdarzenie wraz z plikiem
      return t.getPlikSciezka();
    }
    return null;
  }


  *
  *
  */
}