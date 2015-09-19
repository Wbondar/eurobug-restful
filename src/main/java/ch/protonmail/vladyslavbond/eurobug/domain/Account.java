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
		this(NumericIdentificator.<Account>valueOf(0L), "nominevacans");
	}

	Account (Identificator<Account> id, String name)
	{
        this.id   = id;
        this.name = name;
        this.hashCode = Account.class.hashCode( ) + id.hashCode( ) + name.hashCode( );
	}

	private final Identificator<Account> id;

	public final Identificator<Account> getId ( )
	{
		return this.id;
	}

	private final String name;

	@XmlAttribute(name = "name")
	public final String getName ( )
	{
		return this.name;
	}
	
	@Override
	public boolean equals (Object o)
	{
	    if (o == null)
	    {
	        return false;
	    }
	    if (o == this)
	    {
	        return true;
	    }
	    if (o instanceof Account)
	    {
	        return o.hashCode( ) == this.hashCode( );
	    }
	    return false;
	}
	
	transient private final int hashCode;
	
	@Override
	public int hashCode ( )
	{
	    return hashCode;
	}
}