package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ch.protonmail.vladyslavbond.eurobug.utils.Password;
import ch.protonmail.vladyslavbond.eurobug.utils.Username;

public final class Passwords
{
	private static final Map<Username, Password> pairs = Collections.<Username, Password>synchronizedMap(new HashMap<Username, Password> ( ));

	public static Password save (Username username, String plainText)
	{
		return Passwords.save(username, new Password (plainText));
	}
	
	public static Password save (String username, String plainText)
	{
		return Passwords.save(Username.valueOf(username), new Password(plainText));
	}
	
	public static Password save (String username, Password hash)
	{
		return Passwords.save(Username.valueOf(username), hash);
	}

	public static Password save (Username username, Password hash)
	{
		Passwords.pairs.put(username, hash);
		return Passwords.pairs.get(username);
	}

	public static boolean verify (Username username, String plainText)
	{
		Password password = Passwords.pairs.get(username);
		if (password != null)
		{
			return password.equals(plainText);
		}
		return false;
	}

	private Passwords ( ) {}
}