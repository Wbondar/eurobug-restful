package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;
import ch.protonmail.vladyslavbond.eurobug.utils.Password;
import ch.protonmail.vladyslavbond.eurobug.utils.Username;

@Path("/accounts")
public final class AccountsResource
implements Resource
{
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create (@FormParam("username") Username username, @FormParam("password") String password)
    {
        AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        Account account = accountFactory.create(username, password);
        if (account != null)
        {
            return Response.status(Status.CREATED).entity(account).build( );
        }
        return Response.status(Status.BAD_REQUEST).build( );
    }
    
    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public String create ( )
    {
    	return "<form method='POST' action='/accounts/create'><input type='text' name='username' /><input type='password' name='password' /><input type='submit' /></form>"; 
    }

    @GET
    @Path("/{account_id : \\d+}")
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieve (@PathParam("account_id") Account account)
    {
        if (account != null)
        {
        	return Response.ok(account).build( );
        }
        return Response.status(Status.NOT_FOUND).build( );
    }	

    @PUT
    @Path("/{account_id : \\d+}/update")
    @Produces(MediaType.TEXT_HTML)
    public Response update (@PathParam("account_id") Account id, @FormParam("password_old") Password oldOne, @FormParam("password_new") Password newOne)
    {
        // TDOO
        return this.retrieve(id);
    }
}