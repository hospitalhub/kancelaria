package pl.kalisz.szpital.kancelaria.data.pojo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-09-18T14:25:25.445+0200")
@StaticMetamodel(Dekretacja.class)
public class Dekretacja_ {
	public static volatile SingularAttribute<Dekretacja, Adres> adres;
	public static volatile SingularAttribute<Dekretacja, Boolean> archive;
	public static volatile SingularAttribute<Dekretacja, Date> data;
	public static volatile SingularAttribute<Dekretacja, Integer> id;
	public static volatile SingularAttribute<Dekretacja, String> odbiorca;
	public static volatile SingularAttribute<Dekretacja, String> opis;
	public static volatile SingularAttribute<Dekretacja, Boolean> polecony;
	public static volatile SingularAttribute<Dekretacja, Boolean> przychodzacy;
	public static volatile SingularAttribute<Dekretacja, String> sciezkaDoPliku;
	public static volatile SingularAttribute<Dekretacja, String> sygnatura;
	public static volatile SingularAttribute<Dekretacja, TypPisma> typPisma;
	public static volatile SingularAttribute<Dekretacja, User> user;
	public static volatile SingularAttribute<Dekretacja, Boolean> wewnetrzne;
}
