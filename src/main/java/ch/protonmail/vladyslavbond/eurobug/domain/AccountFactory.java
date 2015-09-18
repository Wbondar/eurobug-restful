package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

public final class AccountFactory
extends Object
implements Factory<Account>
{
	private static final Set<Account> cache = Collections.<Account>synchronizedSet(new HashSet<Account> ( )); 
	
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

	public Account update (Identificator<Account> id, String newName)
	{
		for (Account account : AccountFactory.cache)
		{
			if (account.getId( ).equals(id))
			{
				Account updatedAccount = new Account (id, newName);
				AccountFactory.cache.remove(account);
				AccountFactory.cache.add(updatedAccount);
				return updatedAccount;
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

    public Account create(Integer idOfService, Long idOfAccountUnderService,
            String name)
    {
        Account account = this.retrieve(idOfService, idOfAccountUnderService);
        if (account == null)
        {
            Identificator<Account> idOfNativeAccount = NumericIdentificator.<Account>valueOf(idOfService.toString( ).concat(idOfAccountUnderService.toString( )));
            account = new Account (idOfNativeAccount, name);
            AccountFactory.cache.add(account);
        }
        return account;
    }

    public Account retrieve(Integer idOfService, Long idOfAccountUnderService)
    {
        Identificator<Account> idOfNativeAccount = NumericIdentificator.<Account>valueOf(idOfService.toString( ).concat(idOfAccountUnderService.toString( )));
        return this.retrieve(idOfNativeAccount);
    }
}