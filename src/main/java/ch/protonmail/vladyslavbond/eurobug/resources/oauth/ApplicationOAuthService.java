package ch.protonmail.vladyslavbond.eurobug.resources.oauth;

import java.util.ResourceBundle;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.GoogleApi;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public enum ApplicationOAuthService
implements OAuthService
{
      TWITTER  (TwitterApi.class, ResourceBundle.getBundle("TWITTER"))
    /*, FACEBOOK (FacebookApi.class, ResourceBundle.getBundle("FACEBOOK"))
    , GOOGLE   (GoogleApi.class, ResourceBundle.getBundle("GOOGLE"))
    , VKONTAKTE       (VkontakteApi.class, ResourceBundle.getBundle("VKONTAKTE")) */
    ;
    
    private ApplicationOAuthService (Class<? extends Api> typeOfAPI, String key, String secret, String callback)
    {
        this.service = new ServiceBuilder ( )
                .provider(typeOfAPI)
                .apiKey(key)
                .apiSecret(secret)
                .callback(callback)
                .build( );
    }
    
    private ApplicationOAuthService (Class<? extends Api> typeOfAPI, ResourceBundle bundle)
    {
        this
        (
             typeOfAPI
            ,bundle.getString("key")
            ,bundle.getString("secret")
            ,bundle.getString("callback")
        );
    }
    
    private final OAuthService service;

    @Override
    public Token getAccessToken(Token arg0, Verifier arg1)
    {
        return service.getAccessToken(arg0, arg1);
    }

    @Override
    public String getAuthorizationUrl(Token arg0)
    {
        return service.getAuthorizationUrl(arg0);
    }

    @Override
    public Token getRequestToken ( )
    {
        return service.getRequestToken( );
    }

    @Override
    public String getVersion()
    {
        return service.getVersion( );
    }

    @Override
    public void signRequest(Token arg0, OAuthRequest arg1)
    {
        service.signRequest(arg0, arg1);
    }
}
