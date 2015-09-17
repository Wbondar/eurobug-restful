package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

abstract class ResourceTest
extends Object
{
	private static final String HOST_URL = "http://localhost:8080";

	public ResourceTest ( )
	{
		this.client = ClientBuilder.newBuilder( )
			.build( );
	}

	private final Client client;

	protected WebTarget target (String pathToResourceRelative)
	{
		return client.target(HOST_URL + pathToResourceRelative);
	}
}