package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class AccountsResourceTest 
extends ResourceTest 
{

    @Before
	public final void setUpAccountsResourceTest ( )
    {
    	AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
    	accountFactory.create("test", "test");
    }
    
    @Test
    public void testCreate ( ) 
    {
		Form form = new Form( );
		form.param("username", "test");
		form.param("password", "test");
		
        Response response = target("/accounts/create")
    		   .request( ).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        //System.out.println(response);
        assertTrue(response.getStatus( ) >= 200 && response.getStatus( ) < 300);
    }

    @After
    public final void tearDownAccountsResourceTest ( )
    {
    	AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
    	Account account = accountFactory.retrieve("test", "test");
    	accountFactory.destroy(account.getId( ));
    }
}
