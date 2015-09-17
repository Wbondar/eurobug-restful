package ch.protonmail.vladyslavbond.eurobug.domain;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

public final class Skill
extends Object
{
	public static Skill valueOf (String idValue)
	{
		SkillFactory skillFactory = Factories.<SkillFactory>getInstance(SkillFactory.class);
		Identificator<Skill> id = NumericIdentificator.<Skill>valueOf(idValue);
		Skill skill = skillFactory.retrieve(id);
		if (skill != null)
		{
			return skill;
		}
		return skillFactory.getEmpty( );
	}

	Skill (Identificator<Skill> id, String title)
	{
		this.id = id;
		this.title = title;
	}

	private final Identificator<Skill> id;

	public final Identificator<Skill> getId ( )
	{
		return this.id;
	}

	private final String title;

	public final String getTitle ( )
	{
		return this.title;
	}
}