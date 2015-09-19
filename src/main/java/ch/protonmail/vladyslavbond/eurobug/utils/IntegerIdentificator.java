package ch.protonmail.vladyslavbond.eurobug.utils;

final class IntegerIdentificator<T>
extends NumericIdentificator<T>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6716472901843123392L;

	public IntegerIdentificator (int id)
    {
        this.id = id;
        this.hashCode = IntegerIdentificator.class.hashCode( ) + id;
    }
	
	public IntegerIdentificator (Integer id)
	{
	    this.id = id.intValue( );
	    this.hashCode = IntegerIdentificator.class.hashCode( ) + id.intValue( );
	}

    private final int id;

    @Override
    public int intValue ( )
    {
        return this.id;
    }

    @Override
    public short shortValue ( )
    {
        return (short)this.id;
    }

    @Override
    public long longValue ( )
    {
        return (long)this.id;
    }

    @Override
    public byte byteValue ( )
    {
        return Integer.valueOf(this.id).byteValue( );
    }

    @Override
    public float floatValue ( )
    {
        return (float)this.id;
    }

    @Override
    public double doubleValue ( )
    {
        return (double)this.id;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (o == this)
        {
            return true;
        }
        if (o instanceof LongIdentificator)
        {
            return (o.hashCode( ) == this.hashCode( ));
        }
        return false;
    }
    
    transient private final int hashCode;

    @Override
    public int hashCode ( )
    {
        return this.hashCode;
    }
}