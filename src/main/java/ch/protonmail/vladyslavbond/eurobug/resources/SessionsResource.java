package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import ch.protonmail.vladyslavbond.eurobug.utils.Password;
import ch.protonmail.vladyslavbond.eurobug.utils.Username;

@Path("/sessions")
public final class SessionsResource
implements Resource
{
    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public Response create (@FormParam("username") Username username, @FormParam("password") String password)
    {
    	AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        Account account = accountFactory.retrieve(username, password);
        if (account != null)
        {
            Session session = Session.newInstance(account);
            NewCookie cookie = new NewCookie ("JSESSIONID", session.getId( ).toString( ));
            return Response.ok(account).cookie(cookie).build( );
        }
        return Response.status(Status.BAD_REQUEST).entity(accountFactory.getEmpty( )).build( );
    }

    @GET 
    @Path("/create")
    public String create ( )
    {
        return "<form method='POST' action='/sessions/create'><input type='text' name='username' /><input type='password' name='password' /><input type='submit' /></form>";
    }

    @POST
    @Path("/destroy")
    @Produces(MediaType.TEXT_HTML)
    public Response destroy (@CookieParam("JSESSIONID") Session session)
    {
    	// TODO
    	session.destroy( );
        return null;
    }
}