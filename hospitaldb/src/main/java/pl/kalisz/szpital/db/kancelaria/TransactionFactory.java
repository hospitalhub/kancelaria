package pl.kalisz.szpital.db.kancelaria;

import pl.kalisz.szpital.utils.Strings;

/**
 * A factory for creating Transaction objects.
 */
public final class TransactionFactory {

	/** The last transaction. */
	private Transaction lastTransaction;

	/** The default transaction. */
	private Transaction defaultTransaction;

	/** The builder. */
	private TransactionBuilder builder = new TransactionBuilder();

	/**
	 * Sets the last transaction.
	 * 
	 * @param lastTransaction
	 *            the new last transaction
	 */
	private void setLastTransaction(Transaction lastTransaction) {
		if (lastTransaction == null) {
			this.lastTransaction = defaultTransaction;
		} else {
			this.lastTransaction = lastTransaction;
		}
	}

	private void setDefaultTransaction(User u) {
		defaultTransaction = builder.defaultTransaction(u).build();
	}

	public TransactionFactory(User u, Transaction lastTransaction) {
		setDefaultTransaction(u);
		setLastTransaction(lastTransaction);
	}

	/**
	 * Gets the filepath.
	 * 
	 * @return the filepath
	 */
	private String getFilepath() {
		return lastTransaction.getPlikSciezka();
	}

	/**
	 * Gets the transaction.
	 * 
	 * @param type
	 *            the type
	 * @return the transaction
	 * @throws Exception
	 *             the exception
	 */
	public Transaction getTransaction(final String type) throws Exception {
		if (Strings.NEW_TRANSACTION.equals(type)) {
			return defaultTransaction;
		} else if (Strings.COPY_LAST.equals(type)) {
			return new TransactionBuilder(lastTransaction).id(null)
					.plikSciezka(null).build();
		} else if (Strings.COPY_LAST_WITH_SCAN.equals(type)) {
			return new TransactionBuilder(lastTransaction).id(null)
					.plikSciezka(getFilepath()).build();
		} else if (Strings.COPY_ADDRESS.equals(type)) {
			return new TransactionBuilder(defaultTransaction).adres(
					lastTransaction.getAdres()).build();
		} else if (Strings.COPY_TYPE.equals(type)) {
			return new TransactionBuilder(defaultTransaction).typPisma(
					lastTransaction.getTypPisma()).build();
		} else {
			throw new Exception(
					"Missing Transaction Type in Transaction Factory: " + type);
		}
	}
}
