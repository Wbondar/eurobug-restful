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

import org.json.JSONObject;
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
    public SessionsResource ( ) {}
    
    @GET
    @Path("/{api}/create")
    public Response oauth (@PathParam("api") String api)
    {
        OAuthService service = ApplicationOAuthService.valueOf(api.toUpperCase( ));
        if (service == null)
        {
            return Response.status(Status.BAD_REQUEST).entity("Unknown OAuth API provider.").build( );
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
    
    final Account processTwitter (String data)
    {
        JSONObject json = new JSONObject (data);
        Long id = json.getLong("id");
        if (id == null || id <= 0)
        {
            throw new RuntimeException ("Identitifcator is missing.");
        }
        AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        Account account = accountFactory.retrieve(1, id);
        if (account == null || account.equals(accountFactory.getEmpty( )))
        {
            String screenName = json.getString("screen_name");
            if (screenName == null || screenName.isEmpty( ))
            {
                throw new RuntimeException ("Screen name is missing.");
            }
            account = accountFactory.create(1, id, screenName);
            return account;
        }
        throw new RuntimeException ("Account is mising.");
    }
    
    @GET
    @Path("/twitter/callback")
    @Produces({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML})
    public Response twitterToken (@QueryParam("oauth_token") String queryToken, @QueryParam("oauth_verifier") String queryVerifier)
    {
        try
        {
            Token requestToken = new Token (queryToken, queryVerifier);
            Verifier verifier = new Verifier (queryVerifier);
            Token accessToken = ApplicationOAuthService.TWITTER.getAccessToken(requestToken, verifier);

            OAuthRequest request = new OAuthRequest(Verb.GET, ResourceBundle.getBundle("TWITTER").getString("credentials"));
            ApplicationOAuthService.TWITTER.signRequest(accessToken, request);

            org.scribe.model.Response response = request.send();
            Account account = this.processTwitter(response.getBody( ));
            if (account != null)
            {
                Session session = Session.newInstance(account);
                NewCookie cookie = new NewCookie ("JSESSIONID", session.getId( ).toString( ));
                return Response.ok( ).cookie(cookie).entity(account).build();   
            }
            return Response.serverError( ).entity("Account is missing.").build( );
        } catch (Exception e) {
            return Response.serverError( ).entity(e.getMessage( )).build( );
        }
    }

    @POST
    @Path("/destroy")
    @Produces({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML})
    public Response destroy (@CookieParam("JSESSIONID") Session session)
    {
        // TODO
        session.destroy( );
        return null;
    }
}