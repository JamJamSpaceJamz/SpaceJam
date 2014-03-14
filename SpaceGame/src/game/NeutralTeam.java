package game;

import game.Team.objectType;

public class NeutralTeam extends Team
{	
	public NeutralTeam(int teamNum, SimpleTest gameInst)
	{
		super(teamNum, gameInst);
	}
	
	protected void startingUnits()
	{
		final int NUMBER_ASTEROIDS = 25;
		addUnit(objectType.ASTEROID, NUMBER_ASTEROIDS);
	}
	
	protected void addUnit(objectType a, int quantity)
	{
		switch(a)
		{
			case ASTEROID:
				
		}
	}
}
