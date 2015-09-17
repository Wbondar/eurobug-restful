package ch.protonmail.vladyslavbond.eurobug.domain;

interface Factory<T> 
{
	public abstract T getEmpty ( );
}