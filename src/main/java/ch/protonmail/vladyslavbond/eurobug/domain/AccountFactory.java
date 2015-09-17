package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;
import ch.protonmail.vladyslavbond.eurobug.utils.Password;
import ch.protonmail.vladyslavbond.eurobug.utils.Username;

public final class AccountFactory
extends Object
implements Factory<Account>
{
	private static final AtomicInteger counter = new AtomicInteger (1);

	private static final Set<Account> cache = Collections.<Account>synchronizedSet(new HashSet<Account> ( )); 

	public Account create (Username username, String password)
	{
		Account account = new Account (NumericIdentificator.<Account>valueOf(AccountFactory.counter.getAndIncrement( )), username);
		Passwords.save(username, password);
		AccountFactory.cache.add(account);
		return account;
	}

	public Account create (String name, String password)
	{
		Username username = Username.valueOf(name);
		return this.create(username, password);
	}
	
	public Account retrieve (Identificator<Account> id)
	{
		for (Account account : AccountFactory.cache)
		{
			if (account.getId( ).equals(id))
			{
				return account;
			}
		}
		return this.getEmpty( );
	}

	public Account retrieve (Username username, String password)
	{
		if (Passwords.verify(username, password))
		{
			for (Account account : AccountFactory.cache)
			{
				if (account.getName( ).equals(username))
				{
					return account;
				}
			}
		}
		return this.getEmpty( );
	}

	public Account update (Identificator<Account> id, String newName)
	{
		for (Account account : AccountFactory.cache)
		{
			if (account.getId( ).equals(id))
			{
				Account updatedAccount = new Account (id, newName);
				if (updatedAccount != null)
				{
					AccountFactory.cache.remove(account);
					AccountFactory.cache.add(updatedAccount);
					return updatedAccount;
				}
				return this.getEmpty( );
			}
		}
		return this.getEmpty( );
	}

	public Account update (Identificator<Account> id, Password newPassword)
	{
		for (Account account : AccountFactory.cache)
		{
			if (account.getId( ).equals(id))
			{
				Passwords.save(account.getName( ), newPassword);
				return account;
			}
		}
		return this.getEmpty( );
	}

	public boolean destroy (Identificator<Account> id)
	{
		for (Account account : AccountFactory.cache)
		{
			if (account.getId( ).equals(id))
			{
				return AccountFactory.cache.remove(account);
			}
		}
		return false;
	}

	@Override
	public Account getEmpty ( )
	{
		return Account.EMPTY;
	}

	public Account retrieve(String name, String password) 
	{
		return this.retrieve(Username.valueOf(name), password);
	}
}