package ch.protonmail.vladyslavbond.eurobug.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import ch.protonmail.vladyslavbond.eurobug.resources.oauth.ApplicationOAuthService;
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
    @Path("/{api}/create")
    public Response oauth (@PathParam("api") String api)
    {
    	OAuthService service = ApplicationOAuthService.valueOf(api.toUpperCase( ));
    	if (service == null)
    	{
    	    service = ApplicationOAuthService.TWITTER;
    	}
    	Token token = service.getRequestToken( );
        try
        {
            return Response.temporaryRedirect(new URI (service.getAuthorizationUrl(token))).build( );
        } 
        catch (URISyntaxException e1)
        {
            throw new WebApplicationException (e1);
        }
    }
    
    @GET
    @Path("/twitter/callback")
    public Response twitterToken (@QueryParam("oauth_token") String queryToken, @QueryParam("oauth_verifier") String queryVerifier)
    {
        Token requestToken = new Token (queryToken, queryVerifier);
        Verifier verifier = new Verifier (queryVerifier);
        Token accessToken = ApplicationOAuthService.TWITTER.getAccessToken(requestToken, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, ResourceBundle.getBundle("TWITTER").getString("credentials"));
        ApplicationOAuthService.TWITTER.signRequest(accessToken, request);

        org.scribe.model.Response response = request.send();

        return Response.ok(response.getBody()).build();
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