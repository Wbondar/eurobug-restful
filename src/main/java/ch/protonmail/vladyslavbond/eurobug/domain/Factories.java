package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Factories
extends Object
{
	private static final Map<Object, Object> cache = Collections.<Object, Object>synchronizedMap(new HashMap<Object, Object> ( ));

	public static <T extends Factory<?>> T getInstance (Class<T> typeOfFactory)
	{
		try
		{
			T factory = typeOfFactory.cast(Factories.cache.get(typeOfFactory));
			if (factory == null)
			{
				factory = typeOfFactory.newInstance( );
				Factories.cache.put(typeOfFactory, factory);
			}
			return factory;
		} catch (Exception e) {
			throw new RuntimeException ("Failed to instantiate domain factory.", e);
		}
	}
}