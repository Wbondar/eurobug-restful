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
        return Long.valueOf(this.id).equals(o);
    }

    @Override
    public int hashCode ( )
    {
        return Long.valueOf(this.id).hashCode( );
    }
}