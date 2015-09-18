package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

final class SkillFactory
extends Object
implements Factory<Skill>
{
	private static final AtomicInteger counter = new AtomicInteger (1);

	private static final Set<Skill> cache = Collections.<Skill>synchronizedSet(new HashSet<Skill> ( )); 

	public Skill create (String title)
	{
		Skill skill = new Skill (NumericIdentificator.<Skill>valueOf(SkillFactory.counter.getAndIncrement( )), title);
		SkillFactory.cache.add(skill);
		return skill;
	}

	public Skill retrieve (Identificator<Skill> id)
	{
		for (Skill skill : SkillFactory.cache)
		{
			if (skill.getId( ).equals(id))
			{
				return skill;
			}
		}
		return this.getEmpty( );
	}

	public Set<Skill> retrievePossessedBy (Employee possessor)
	{
		return Factories.<SkillPossessionFactory>getInstance(SkillPossessionFactory.class).retrievePossessedBy(possessor);
	}

	public Skill update (Identificator<Skill> id, String newTitle)
	{
		for (Skill skill : SkillFactory.cache)
		{
			if (skill.getId( ).equals(id))
			{
				Skill updatedSkill = new Skill (id, newTitle);
					SkillFactory.cache.remove(skill);
					SkillFactory.cache.add(updatedSkill);
					return updatedSkill;
			}
		}
		return this.getEmpty( );
	}

	public boolean destroy (Identificator<Skill> id)
	{
		for (Skill skill : SkillFactory.cache)
		{
			if (skill.getId( ).equals(id))
			{
				return SkillFactory.cache.remove(skill);
			}
		}
		return false;
	}

	@Override
	public Skill getEmpty ( )
	{
		// TODO
		return null;
	}
}