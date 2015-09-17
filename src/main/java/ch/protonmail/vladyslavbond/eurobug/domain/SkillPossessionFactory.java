package ch.protonmail.vladyslavbond.eurobug.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

final class SkillPossessionFactory
extends Object
implements Factory<SkillPossession>
{
	private static final Set<SkillPossession> cache = Collections.<SkillPossession>synchronizedSet(new HashSet<SkillPossession> ( )); 

	public SkillPossession create (Employee possessor, Skill skill)
	{
		NumericIdentificator<SkillPossession> id = NumericIdentificator.<SkillPossession>valueOf(possessor.getId( ).toString( ).concat(skill.getId( ).toString( )));
		SkillPossession skillPossession = new SkillPossession (id, possessor, skill);
		SkillPossessionFactory.cache.add(skillPossession);
		return skillPossession;
	}

	public Set<Skill> retrievePossessedBy (Employee possessor)
	{
		Set<Skill> found = new HashSet<Skill> ( );
		for (SkillPossession skillPossession : SkillPossessionFactory.cache)
		{
			if (skillPossession.getPossessor( ).getId( ).equals(possessor.getId( )))
			{
				found.add(skillPossession.getSkill( ));
			}
		}
		return Collections.<Skill>unmodifiableSet(found);
	}

	public boolean destroy (Identificator<SkillPossession> id)
	{
		for (SkillPossession skillPossession : SkillPossessionFactory.cache)
		{
			if (skillPossession.getId( ).equals(id))
			{
				return SkillPossessionFactory.cache.remove(skillPossession);
			}
		}
		return false;
	}

	@Override
	public SkillPossession getEmpty ( )
	{
		// TODO
		return null;
	}
}