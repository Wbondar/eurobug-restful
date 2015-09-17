package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

final class SummaryOwnershipFactory
extends Object
implements Factory<SummaryOwnership>
{
	private static final Set<SummaryOwnership> cache = Collections.<SummaryOwnership>synchronizedSet(new HashSet<SummaryOwnership> ( )); 

	public SummaryOwnership create (Employee owner, Summary owned)
	{
		NumericIdentificator<SummaryOwnership> id = NumericIdentificator.<SummaryOwnership>valueOf(owner.getId( ).toString( ).concat(owned.getId( ).toString( )));
		SummaryOwnership summaryOwnership = new SummaryOwnership (id, owner, owned);
		SummaryOwnershipFactory.cache.add(summaryOwnership);
		return summaryOwnership;
	}

	public Set<Summary> retrieveOwnedBy (Employee owner)
	{
		Set<Summary> found = new HashSet<Summary> ( );
		for (SummaryOwnership summaryOwnership : SummaryOwnershipFactory.cache)
		{
			if (summaryOwnership.getOwner( ).getId( ).equals(owner.getId( )))
			{
				found.add(summaryOwnership.getOwned( ));
			}
		}
		return Collections.<Summary>unmodifiableSet(found);
	}

	public boolean destroy (Identificator<SummaryOwnership> id)
	{
		for (SummaryOwnership summaryOwnership : SummaryOwnershipFactory.cache)
		{
			if (summaryOwnership.getId( ).equals(id))
			{
				return SummaryOwnershipFactory.cache.remove(summaryOwnership);
			}
		}
		return false;
	}

	@Override
	public SummaryOwnership getEmpty ( )
	{
		// TODO
		return null;
	}
}