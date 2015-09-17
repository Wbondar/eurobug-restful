package ch.protonmail.vladyslavbond.eurobug.resources;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.StringIdentificator;

final class Session
extends Object
{
	private final static Set<Session> cache = Collections.<Session>synchronizedSet(new HashSet<Session> ( ));

	public static Session valueOf (String JSESSIONID)
	{
		Identificator<Session> id = StringIdentificator.<Session>valueOf(JSESSIONID);
		for (Session session : Session.cache)
		{
			if (session.getId( ).equals(id))
			{
				return session;
			}
		}
		return null;
	}

	public static Session newInstance (Account account)
	{
		Identificator<Session> id = StringIdentificator.<Session>valueOf(UUID.randomUUID( ).toString( ));
		Session session = new Session (id, account);
		Session.cache.add(session);
		return session;
	}

	public static boolean destroy (Identificator<Session> id)
	{
		for (Session session : Session.cache)
		{
			if (session.getId( ).equals(id))
			{
				return session.destroy( );
			}
		}
		return false;
	}

	private Session (Identificator<Session> id, Account account)
	{
		this.id = id;
		this.account = account;
	}

	private final Identificator<Session> id;

	public final Identificator<Session> getId ( )
	{
		return this.id;
	}

	private final Account account;

	public final Account getAccount ( )
	{
		return this.account;
	}

	public final boolean destroy ( )
	{
		return Session.cache.remove(this);
	}
}