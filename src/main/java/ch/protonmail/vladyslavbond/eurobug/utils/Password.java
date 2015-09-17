package ch.protonmail.vladyslavbond.eurobug.utils;

import org.mindrot.jbcrypt.*;

public final class Password
implements CharSequence
{
	public Password (String plainText)
	{
		this.hash = BCrypt.hashpw(plainText, BCrypt.gensalt( ));
	}

	private final String hash;

	@Override
	public char charAt (int index)
	{
		return this.hash.charAt(index);
	}

	@Override
	public int length ( )
	{
		return this.hash.length( );
	}

	@Override
	public CharSequence subSequence (int start, int end)
	{
		return this.hash.subSequence(start, end);
	}

	@Override
	public String toString ( )
	{
		// TODO: It must return hashed password.
		return this.hash;
	}

	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o instanceof String)
		{
			return BCrypt.checkpw(o.toString( ), this.hash);
		}
		return false;
	}
}