package ch.protonmail.vladyslavbond.eurobug.utils;

@Deprecated
public final class Username
implements CharSequence
{
	public static Username valueOf (String username)
	{
		return new Username (username.replaceAll("\\s+","").toLowerCase( ));
	}

	private Username (String username)
	{
		this.username = username;
	}

	private final String username;

	@Override
	public char charAt (int index)
	{
		return this.username.charAt(index);
	}

	@Override
	public int length ( )
	{
		return this.username.length( );
	}

	@Override
	public CharSequence subSequence (int start, int end)
	{
		return this.username.subSequence(start, end);
	}

	@Override
	public String toString ( )
	{
		return this.username;
	}

	@Override
	public boolean equals (Object o)
	{
		return this.username.equals(o.toString( ));
	}
}