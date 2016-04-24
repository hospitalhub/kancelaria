package pl.kalisz.szpital.kancelaria.data.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.vaadin.appfoundation.authentication.data.User;

import pl.kalisz.szpital.db.kancelaria.AdresBuilder;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.TransactionBuilder;
import pl.kalisz.szpital.db.kancelaria.TransactionFactory;
import pl.kalisz.szpital.db.kancelaria.TypPisma;
import pl.kalisz.szpital.utils.Strings;

/**
 * The Class TransactionFactoryTest.
 */
public class TransactionFactoryTest {

	/** The u. */
	private User u = new User();

	/** The last. */
	private Transaction last = new TransactionBuilder(1L).adres(
			new AdresBuilder().adres("ul", "00-001", "Kasina Wielka").build())
			.build();

	/**
	 * Builds the test.
	 */
	@Test
	public void newTransactionTest() {
		TransactionFactory factory = new TransactionFactory(u, last);
		try {
			Transaction t = factory.getTransaction(Strings.NEW_TRANSACTION);
			Assert.assertEquals(t.getTypPisma(), new TypPisma(
					Strings.DEFAULT_TYPPISMA));
			Assert.assertEquals(t.getOdbiorca(), Strings.EMPTY_STRING);
			Assert.assertEquals(t.getOpis(), Strings.EMPTY_STRING);
			Assert.assertEquals(t.getPlikSciezka(), null);
			Assert.assertEquals(t.getAdres(), null);
			Assert.assertEquals(t.getSygnatura(), null);
			Assert.assertEquals(t.getPolecony(), null);
			Assert.assertEquals(t.getArchive(), null);
			Assert.assertEquals(t.getId(), null);
			Assert.assertEquals(t.getUser(), u);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
