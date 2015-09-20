package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;
import static org.junit.Assert.assertTrue;

public final class AccountsResourceTest 
extends ResourceTest 
{

    @Before
	public final void setUpAccountsResourceTest ( )
    {
    	AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
    	accountFactory.create(1, 2367864038L, "vladyslavbond");
    }
    
    @Test
    public void testRetrieve ( ) 
    {	
        Response response = target("/accounts/12367864038")
    		   .request( ).get( );

        //System.out.println(response);
        assertTrue(response.getStatus( ) >= 200 && response.getStatus( ) < 300);
    }

    @After
    public final void tearDownAccountsResourceTest ( )
    {
    	AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
    	accountFactory.destroy(NumericIdentificator.<Account>valueOf(12367864038L));
    }
}
