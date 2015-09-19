package ch.protonmail.vladyslavbond.eurobug.utils;

final class LongIdentificator<T>
extends NumericIdentificator<T>
{
    /**
     * 
     */
    private static final long serialVersionUID = -5912072234314933985L;

    public LongIdentificator (long id)
    {
        this.id = id;
        this.hashCode = LongIdentificator.class.hashCode( ) + Long.valueOf(this.id).hashCode( );
    }
    
    public LongIdentificator (Long id)
    {
        this.id = id.longValue( );
        this.hashCode = LongIdentificator.class.hashCode( ) + id.hashCode( );
    }

    private final long id;

    @Override
    public int intValue ( )
    {
        return Long.valueOf(this.id).intValue( );
    }

    @Override
    public short shortValue ( )
    {
        return Long.valueOf(this.id).shortValue( );
    }

    @Override
    public long longValue ( )
    {
        return this.id;
    }

    @Override
    public byte byteValue ( )
    {
        return Long.valueOf(this.id).byteValue( );
    }

    @Override
    public float floatValue ( )
    {
        return Long.valueOf(this.id).floatValue();
    }

    @Override
    public double doubleValue ( )
    {
        return Long.valueOf(this.id).doubleValue();
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