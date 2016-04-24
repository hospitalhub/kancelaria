package pl.kalisz.szpital.kancelaria.data.pojo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-20T17:01:35.072+0100")
@StaticMetamodel(Transaction.class)
public class Transaction_ {
	public static volatile SingularAttribute<Transaction, Adres> adres;
	public static volatile SingularAttribute<Transaction, Boolean> archive;
	public static volatile SingularAttribute<Transaction, Date> data;
	public static volatile SingularAttribute<Transaction, Integer> id;
	public static volatile SingularAttribute<Transaction, String> odbiorca;
	public static volatile SingularAttribute<Transaction, String> opis;
	public static volatile SingularAttribute<Transaction, Boolean> polecony;
	public static volatile SingularAttribute<Transaction, Boolean> przychodzacy;
	public static volatile SingularAttribute<Transaction, String> sciezkaDoPliku;
	public static volatile SingularAttribute<Transaction, String> sygnatura;
	public static volatile SingularAttribute<Transaction, TypPisma> typPisma;
	public static volatile SingularAttribute<Transaction, User> user;
	public static volatile SingularAttribute<Transaction, Boolean> wewnetrzne;
}
