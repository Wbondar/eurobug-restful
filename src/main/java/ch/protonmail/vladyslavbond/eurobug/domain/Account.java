package ch.protonmail.vladyslavbond.eurobug.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ch.protonmail.vladyslavbond.eurobug.utils.*;

@XmlRootElement(name = "account")
public class Account
{
	static final Account EMPTY = new Account ( );

	public static Account valueOf (String idValue)
	{
		AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
		Identificator<Account> id = NumericIdentificator.<Account>valueOf(idValue);
		Account account = accountFactory.retrieve(id);
		if (account != null)
		{
			return account;
		}
		return accountFactory.getEmpty( );
	}

	private Account ( )
	{
		this(0L, "nominevacans");
	}

	private Account (long id, String name)
	{
		this.id   = id;
		this.name = name;
	}

	Account (Identificator<Account> id, String name)
	{
		this(((NumericIdentificator<Account>)id).longValue( ), name);
	}

	@XmlAttribute(name = "id")
	private final long id;

	public final Identificator<Account> getId ( )
	{
		return (Identificator<Account>)NumericIdentificator.<Account>valueOf(this.id);
	}

	private final String name;

	@XmlAttribute(name = "name")
	public final String getName ( )
	{
		return this.name;
	}
}