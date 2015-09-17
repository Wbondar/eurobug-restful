package ch.protonmail.vladyslavbond.eurobug.utils;

public final class StringIdentificator<T>
implements Identificator<T>
{
	public static <T> StringIdentificator<T> valueOf (String idValue)
	{
		return new StringIdentificator<T> (idValue);
	}
	
	public StringIdentificator (String id)
	{
		this.id = id;
	}

	private final String id;

	@Override
	public String toString ( )
	{
		return this.id;
	}

	@Override
	public boolean equals (Object o)
	{
		return o.toString( ).equals(this.toString( ));
	}
}