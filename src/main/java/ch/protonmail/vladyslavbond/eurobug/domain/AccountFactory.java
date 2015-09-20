package ch.protonmail.vladyslavbond.eurobug.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.eurobug.datasource.Storage;
import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

public final class AccountFactory
extends Object
implements Factory<Account>
{
	private static final Set<Account> cache = Collections.<Account>synchronizedSet(new HashSet<Account> ( )); 
	
	private static void store (Account account)
	{
        try
        (
                Connection connection = Storage.DEFAULT.getConnection();
        )
        {
            PreparedStatement statement = connection.prepareCall("SELECT * FROM account_create(?, ?);");
            statement.setLong(1, ((NumericIdentificator<Account>)account.getId( )).longValue( ));
            statement.setString(2, account.getName( ));
            statement.executeQuery( );
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to store an account in the database.", e);
        }
	}
	
	private static Account fetch (long id)
	{
	    try
	    (
	            Connection connection = Storage.DEFAULT.getConnection();
	    )
	    {
	        PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM account WHERE id = ?;");
	        statement.setLong(1, id);
	        ResultSet resultSet = statement.executeQuery( );
	        boolean hasNext = resultSet.next( );
	        if (!hasNext)
	        {
	            return null;
	        }
	        String name = resultSet.getString(2);
	        return new Account (NumericIdentificator.<Account>valueOf(id), name);
	    } catch (SQLException e) {
	        throw new RuntimeException ("Failed to fetch an account from the database.", e);
	    }
	}
	
	private static Account fetch (Identificator<Account> id)
	{
	    return fetch(((NumericIdentificator<Account>)id).longValue( ));
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
		Account account = fetch (id);
		if (account != null)
		{
		    return account;
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
	
	public Account create (Identificator<Account> id, String name)
	{
	    Account account = Account.EMPTY;
	    try
	    {
	        account = new Account (id, name);
	        store(account);
	    } catch (Exception e) {
	        throw e;
	    }
        AccountFactory.cache.add(account);
        return account;
	}

    public Account create(Integer idOfService, Long idOfAccountUnderService,
            String name)
    {
        Account account = this.retrieve(idOfService, idOfAccountUnderService);
        if (account == null || account.equals(this.getEmpty( )))
        {
            Identificator<Account> idOfNativeAccount = NumericIdentificator.<Account>valueOf(idOfService.toString( ).concat(idOfAccountUnderService.toString( )));
            return create (idOfNativeAccount, name);
        }
        return account;
    }

    public Account retrieve(Integer idOfService, Long idOfAccountUnderService)
    {
        Identificator<Account> idOfNativeAccount = NumericIdentificator.<Account>valueOf(idOfService.toString( ).concat(idOfAccountUnderService.toString( )));
        return this.retrieve(idOfNativeAccount);
    }
}