package ch.protonmail.vladyslavbond.eurobug.utils;

public abstract class NumericIdentificator<T>
extends Number
implements Identificator<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7325800050492399786L;

	public static final <T> NumericIdentificator<T> valueOf (String id)
	{
		return new IntegerIdentificator<T>(Integer.valueOf(id));
	}

	public static final <T> NumericIdentificator<T> valueOf (int id)
	{
		return new IntegerIdentificator<T> (id);
	}
}