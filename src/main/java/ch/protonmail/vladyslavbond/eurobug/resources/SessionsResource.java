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
    
    final Account create (Integer idOfProvider, Long idOfAccount, String screenName)
    {
        AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        if (idOfProvider == null || idOfProvider <= 0)
        {
            throw new RuntimeException ("Id of provider is missing.");
        }
        if (idOfAccount == null || idOfAccount <= 0)
        {
            throw new RuntimeException ("Id of account is missing.");
        }
        if (screenName == null || screenName.isEmpty( ))
        {
            throw new RuntimeException ("Name of account is missing.");
        }
        return accountFactory.create(idOfProvider, idOfAccount, screenName);   
    }
    
    final Account retrieve (Integer idOfProvider, Long idOfAccount)
    {
        AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        if (idOfProvider == null || idOfProvider <= 0)
        {
            throw new RuntimeException ("Id of provider is missing.");
        }
        if (idOfAccount == null || idOfAccount <= 0)
        {
            throw new RuntimeException ("Id of account is missing.");
        }
        return accountFactory.retrieve(idOfProvider, idOfAccount);
    }
    
    final Account process (Integer idOfProvider, Long idOfAccount, String screenName)
    {
        Account account = this.retrieve(idOfProvider, idOfAccount);
        if (account != null)
        {
            return account;
        }
        account = this.create(idOfProvider, idOfAccount, screenName);
        if (account != null)
        {
            return account;
        }
        throw new RuntimeException ("Account is missing.");
    }
    
    final Account processTwitter (String data)
    {
        JSONObject json = new JSONObject (data);
        Integer idOfProvider = ApplicationOAuthService.TWITTER.getId( );
        Long id = json.getLong("id");
        String screenName = json.getString("screen_name");
        return this.process(idOfProvider, id, screenName);
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