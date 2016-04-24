package pl.kalisz.szpital.kancelaria.data.pojo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-20T17:01:35.092+0100")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Role> roles;
}
