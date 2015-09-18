package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.AccountFactory;
import ch.protonmail.vladyslavbond.eurobug.domain.Factories;

@Path("/accounts")
public final class AccountsResource
implements Resource
{
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
    public Response update (@PathParam("account_id") Account account, @FormParam("name") String name)
    {
        AccountFactory accountFactory = Factories.<AccountFactory>getInstance(AccountFactory.class);
        Account updatedAccount = accountFactory.update(account.getId( ), name);
        if (updatedAccount.getName( ).equals(name))
        {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build( );
    }
}