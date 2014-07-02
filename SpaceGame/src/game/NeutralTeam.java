package game;

import objects.Asteroid;
import objects.Credit;
import objects.Obj;
import game.Team.objectType;
import game.Constants;

public class NeutralTeam extends Team
{	
	public NeutralTeam(int teamNum, SimpleTest gameInst)
	{
		super(teamNum, gameInst);
	}
	
	protected void startingUnits()
	{
		final int NUMBER_ASTEROIDS = Constants.game_init_asteroids;
		this.astList    = new List<Obj>();
		this.creditList = new List<Obj>();
		
		addUnit(objectType.ASTEROID, NUMBER_ASTEROIDS);
	}
	
	protected void addUnit(objectType a, int quantity, Obj thing)
	{
		switch(a)
		{
			case ASTEROID:
				addAsteroid(quantity);
				break;
			case CREDIT:
				addCredit(thing);
				break;
		}
	}
	
	private void addAsteroid(int quantity)
	{
		// Generate all asteroids
		for (int i = 0; i < quantity; i++)
		{
			float[] loc = new float[2];
			loc[0] = (float) (Math.random()* gameInst.container.getWidth());
			loc[1] = (float) (Math.random()* gameInst.container.getHeight());

			
			float ast_size = (float) (Math.random() * 10 + 4) * Constants.scale;
			Asteroid ast = new Asteroid(loc, ast_size, gameInst, this);
			this.getList(objectType.ASTEROID).add(ast);
			this.updateAllUnits(ast);
		}	
	}

	private void addCredit(Obj thing)
	{
		if (thing instanceof Credit)
		{
			this.getList(objectType.CREDIT).add(thing);
			this.updateAllUnits(thing);
		}
	}
}
