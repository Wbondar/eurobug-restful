package ch.protonmail.vladyslavbond.eurobug.domain;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;

final class SkillPossession
extends Object
{
	SkillPossession (Identificator<SkillPossession> id, Employee possessor, Skill skill)
	{
		this.id        = id;
		this.possessor = possessor;
		this.skill     = skill;
	}

	private final Identificator<SkillPossession> id;

	public Identificator<SkillPossession> getId ( )
	{
		return this.id;
	}

	private final Employee possessor;

	public final Employee getPossessor ( )
	{
		return this.possessor;
	}

	private final Skill skill;

	public final Skill getSkill ( )
	{
		return this.skill;
	}
}