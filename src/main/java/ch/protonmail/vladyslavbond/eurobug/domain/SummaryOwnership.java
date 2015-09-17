package ch.protonmail.vladyslavbond.eurobug.domain;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;

final class SummaryOwnership
extends Object
{
	SummaryOwnership (Identificator<SummaryOwnership> id, Employee owner, Summary owned)
	{
		this.id    = id;
		this.owner = owner;
		this.owned = owned;
	}

	private final Employee owner;

	public final Employee getOwner ( )
	{
		return this.owner;
	}

	private final Summary owned;

	public final Summary getOwned ( )
	{
		return this.owned;
	}

	private final Identificator<SummaryOwnership> id;

	public final Identificator<SummaryOwnership> getId ( )
	{
		return this.id;
	}
}