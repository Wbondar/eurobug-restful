package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.core.Response;

/*import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class SessionsResourceTest 
extends Object
/*extends JerseyTest*/
{
    private static final String correctData = "{\"id\":2367864038,\"screen_name\":\"vladyslavbond\"}";
    private final SessionsResource resource;
    private final AccountFactory accountFactory;
    
    public SessionsResourceTest ( )
    {
        this.resource = new SessionsResource ( );
        this.accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
    }
    
    /*@Override
    protected Application configure ( ) 
    {
        return new ResourceConfig(SessionsResource.class);
    }*/

    @Before
	public final void setUpSessionsResourceTest ( )
    {
        Account account = accountFactory.create(1, 2367864038L, "vladyslavbond");
        assertEquals(account, accountFactory.retrieve(1, 2367864038L));
    }
    
    private final Integer idOfProvider = 1;
    private final Long idOfAccount = 2367864038L;
    private final String screenName = "vladyslavbond";
    
    @Test
    public void testCreation ( )
    {
        Account account = resource.create(idOfProvider, idOfAccount, screenName);
        assertTrue(account != null);
        assertFalse(account.equals(accountFactory.getEmpty( )));
        assertFalse(account == accountFactory.getEmpty( ));
    }
    
    @Test
    public void testAbstractProcessing ( )
    {
        for (int i = 0; i < 10; i++)
        {
            Account account = this.resource.process(idOfProvider, idOfAccount, screenName);
            assertTrue(account != null);
            assertFalse(account.equals(accountFactory.getEmpty( )));
            assertFalse(account == accountFactory.getEmpty( ));
            Account retrievedAccount = accountFactory.retrieve(account.getId( ));
            assertEquals(account, retrievedAccount);
            retrievedAccount = accountFactory.retrieve(idOfProvider, idOfAccount);
            assertEquals(account, retrievedAccount);
        }
    }
    
    @Test
    public void testProcessingWithTwitter ( ) 
    {
        for (int i = 0; i < 10; i++)
        {
            Account account = this.resource.processTwitter(correctData);
            assertTrue(account != null);
            assertFalse(account.equals(accountFactory.getEmpty( )));
            assertFalse(account == accountFactory.getEmpty( ));
            Account retrievedAccount = accountFactory.retrieve(account.getId( ));
            assertEquals(account, retrievedAccount);
            retrievedAccount = accountFactory.retrieve(idOfProvider, idOfAccount);
            assertEquals(account, retrievedAccount);
        }
    }

    @After
    public final void tearDownSessionsResourceTest ( )
    {
        accountFactory.destroy(NumericIdentificator.<Account>valueOf(12367864038L));
    }
}
