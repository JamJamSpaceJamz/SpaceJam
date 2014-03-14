package objects;

import ships.AutonomousShip;
import game.SimpleTest;
import game.Team;

public class BlakesAutoShip extends AutonomousShip
{

	public BlakesAutoShip(float[] spawn, int size, int speed, int rotateSpd, float range,
			int capacity, float health, SimpleTest inst, Team team) {
		super(spawn, size, speed, rotateSpd, range, capacity, health, inst, team);
	}
	
	// Destroy asteroids around the map
	protected void hunt()
	{
		// fly to closest asteroid until a certain distance away
		// shoot asteroid. repeat
		if (asteroidInRange())
		{
			Asteroid target = findClosestAsteroid();
			if (target != null)
				attackAsteroid(target);
		} else {
			findAsteroids();
		}
	}
	
	@Override
	public void update(int dt)
	{
		delta = dt;
		
		radar();
		if (isFullCredits)
			returnToBase();
		else		
			hunt();
	}
	
	// strip objectsInRange to just asteroids
	protected Asteroid findClosestAsteroid()
	{
		Asteroid retAst = null;
		float closestDistance = range;
		for (Obj obj: objectsInRange)
		{
			if (obj instanceof Asteroid)
			{
				if (distanceTo(obj) < closestDistance)
				{
					closestDistance = distanceTo(obj);
					retAst = (Asteroid)obj;
				}
			}
		}
		return retAst;
	}
	
	// Search the map for a new asteroid to shoot
		protected void findAsteroids()
		{
			// Fly forward for a set period of time, then turn to a random 
			// direction and try again. In the future, investigate 
			// non-random function to cover the map more efficiently.
			if (timer >= SEARCH_TIMER)
			{
				if (dir - rotation > 3)
				{
					rotateRight();
					stop();
				}
				else if (dir - rotation < -3)
				{
					rotateLeft();
					stop();
				}
				else
				{
					searchDirection[0] = (float)(2.0*Math.random() - 1.0) + this.location[0];
					searchDirection[1] = (float)(2.0*Math.random() - 1.0) + this.location[1];
					dir = directionTo(searchDirection[0], searchDirection[1]);
					timer = 0;
				}
				
			}
			else
			{
				cruiseControl(10);
				++timer;
			}
		}
		
		protected void attackAsteroid(Asteroid target)
		{
			stop(); // Make sure the asteroid does not go out of range
			if (!turnTo(directionTo(target)));
			else
				fire();
		}
		
		protected boolean asteroidInRange()
		{
			for (Obj obj: objectsInRange)
			{
				if (obj instanceof Asteroid)
					return true;
			}
			return false;
		}

}