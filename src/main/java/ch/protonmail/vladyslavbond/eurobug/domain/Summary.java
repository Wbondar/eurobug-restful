package ch.protonmail.vladyslavbond.eurobug.domain;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

public final class Summary
{
	public static Summary valueOf (String idValue)
	{
		SummaryFactory summaryFactory = Factories.<SummaryFactory>getInstance(SummaryFactory.class);
		Identificator<Summary> id = NumericIdentificator.<Summary>valueOf(idValue);
		Summary summary = summaryFactory.retrieve(id);
		if (summary != null)
		{
			return summary;
		}
		return summaryFactory.getEmpty( );
	}

	Summary (Identificator<Summary> id, Employee owner)
	{
		this.id = id;
		this.owner = owner;
	}

	private final Identificator<Summary> id;

	public final Identificator<Summary> getId ( )
	{
		return this.id;
	}

	private final Employee owner;

	public final Employee getOwner ( )
	{
		return this.owner;
	}
}