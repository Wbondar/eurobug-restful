package ch.protonmail.vladyslavbond.eurobug.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
		this(0, "nominevacans");
	}

	private Account (int id, String name)
	{
		this.id   = id;
		this.name = name;
	}

	Account (Identificator<Account> id, Username name)
	{
		this(((NumericIdentificator<Account>)id).intValue( ), name.toString( ));
	}

	Account (Identificator<Account> id, String name)
	{
		this(id, Username.valueOf(name));
	}

	@XmlAttribute(name = "id")
	private final int id;

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