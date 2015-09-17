package ch.protonmail.vladyslavbond.eurobug.resources;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;

public abstract class SummariesResource
implements Resource
{
	public SummariesResource (Account account)
	{
		this.account = account;
	}

	private final Account account;

	protected final Account getAccount ( )
	{
		return this.account;
	}
}