package ch.protonmail.vladyslavbond.eurobug.resources;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.protonmail.vladyslavbond.eurobug.domain.*;

@ApplicationPath("/")
public final class Application
extends javax.ws.rs.core.Application
implements Resource
{
	public Application ( )
	{
		Set<Class<?>> classes = new HashSet<Class<?>> ( );
		classes.add(AccountsResource.class);
		classes.add(Application.class);
		classes.add(ExternalSummariesResource.class);
		classes.add(InternalSummariesResource.class);
		classes.add(PDFSummariesResource.class);
		classes.add(SessionsResource.class);
		classes.add(SummariesResource.class);
		this.classes = Collections.<Class<?>>unmodifiableSet(classes);

		Set<Object> singletons = new HashSet<Object> ( );
		singletons.add(new AccountsResource ( ));
		singletons.add(new SessionsResource ( ));
		this.singletons = Collections.<Object>unmodifiableSet(singletons);
	}
	
	private final Set<Class<?>> classes;
	
	@Override
	public Set<Class<?>> getClasses ( )
	{
		return this.classes;
	}

	private final Set<Object> singletons;
	
	@Override 
	public Set<Object> getSingletons ( )
	{
		return this.singletons;
	}

	@Path("/{account_id}/summaries")
	@Produces(MediaType.TEXT_HTML)
	public SummariesResource summaries (@CookieParam("JSESSIONID") Session session)
	{
		Account account = session.getAccount( );
		if (account != null)
		{
			return new InternalSummariesResource(account);
		}
		throw new WebApplicationException(new RuntimeException ("Client is yet to pass authentication to the system."), Response.Status.NOT_FOUND);
	}
}