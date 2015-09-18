package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

final class SummaryFactory
extends Object
implements Factory<Summary>
{
	private static final AtomicInteger counter = new AtomicInteger (1);

	private static final Set<Summary> cache = Collections.<Summary>synchronizedSet(new HashSet<Summary> ( )); 

	public Summary create (Employee owner)
	{
		Summary summary = new Summary (NumericIdentificator.<Summary>valueOf(SummaryFactory.counter.getAndIncrement( )), owner);
			SummaryOwnershipFactory summaryOwnershipFactory = Factories.<SummaryOwnershipFactory>getInstance(SummaryOwnershipFactory.class);
			summaryOwnershipFactory.create(owner, summary);
			SummaryFactory.cache.add(summary);
			return summary;
	}

	public Set<Summary> retrieveOwnedBy (Employee employee)
	{
		return Factories.<SummaryOwnershipFactory>getInstance(SummaryOwnershipFactory.class).retrieveOwnedBy(employee);
	}

	public Summary retrieve (Identificator<Summary> id)
	{
		for (Summary summary : SummaryFactory.cache)
		{
			if (summary.getId( ).equals(id))
			{
				return summary;
			}
		}
		return this.getEmpty( );
	}

	public boolean destroy (Identificator<Summary> id)
	{
		for (Summary summary : SummaryFactory.cache)
		{
			if (summary.getId( ).equals(id))
			{
				return SummaryFactory.cache.remove(summary);
			}
		}
		return false;
	}

	@Override
	public Summary getEmpty ( )
	{
		// TODO
		return null;
	}
}