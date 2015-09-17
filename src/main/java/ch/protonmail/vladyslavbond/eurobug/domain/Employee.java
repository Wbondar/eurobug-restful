package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Set;

public final class Employee
extends Account
{
	Employee (Account account)
	{
		super(account.getId( ), account.getName( ));
	}

	public final Set<Summary> getSummaries ( )
	{
		return Factories.<SummaryFactory>getInstance(SummaryFactory.class).retrieveOwnedBy(this);
	}

	public final Set<Skill> getSkills ( )
	{
		return Factories.<SkillFactory>getInstance(SkillFactory.class).retrievePossessedBy(this);	
	}
}