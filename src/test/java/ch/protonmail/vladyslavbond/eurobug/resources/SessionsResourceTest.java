package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.core.Response;

/*import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public final class SessionsResourceTest 
extends ResourceTest
/*extends JerseyTest*/
{
    /*@Override
    protected Application configure ( ) 
    {
        return new ResourceConfig(SessionsResource.class);
    }*/

    @Before
	public final void setUpSessionsResourceTest ( )
    {
    }
    
    @Test
    public void testCreate ( ) 
    {
        Response response = target("/sessions/twitter/create")
    		   .request( ).get( );
        assertFalse(response.getCookies( ).get("JSESSIONID").getValue( ).isEmpty( ));
    }

    @After
    public final void tearDownSessionsResourceTest ( )
    {
    }
}
