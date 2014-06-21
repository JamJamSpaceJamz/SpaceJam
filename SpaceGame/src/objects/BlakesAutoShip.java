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
		System.out.println("can I even print???");
		// TODO: This method can be done a lot better..............
		if (enemyInRange()) {
			System.out.println("Found enemy.");
			Obj target = findClosestEnemy();
			if (target != null)
				System.out.println("Target acquired.");
				attack(target);
		} else {		
			// fly to closest asteroid until a certain distance away
			// shoot asteroid. repeat
			if (asteroidInRange())
			{
				Asteroid target = findClosestAsteroid();
				if (target != null)
					attack(target);
			} else {
				findAsteroids();
			}
		}
	}
	
	@Override
	public void update(int dt)
	{
		delta = dt;
		System.out.println("PRINT SOMETHING!!!!");
		radar();
		if (isFullCredits)
			returnToBase();
		else		
			hunt();
	}
	
	// return closest asteroid of those in range
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
	
	// TODO: combine this method with findClosestAsteroid()
	protected Obj findClosestEnemy() {
		Obj retObj = null;
		float closestDistance = range;
		for (Obj obj: objectsInRange) {
			if (obj.getTeam() != this.getTeam()) {
				if (distanceTo(obj) < closestDistance) {
					closestDistance = distanceTo(obj);
					retObj = obj;
				}
			}
		}
		return retObj;
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
		
		protected void attack(Obj target)
		{
			stop(); // Make sure the asteroid does not go out of range
			if (!turnTo(directionTo(target)));
			else
				fire();
		}
		
		protected boolean asteroidInRange()
		{
			System.out.println("In asteroidInRange()");
			for (Obj obj: objectsInRange)
			{
				if (obj instanceof Asteroid)
					return true;
			}
			return false;
		}
		
		// TODO: this method should be combined with asteroidInRange()
		protected boolean enemyInRange() {
			System.out.println("In enemyInRange()");
			for (Obj obj: objectsInRange) {
				System.out.print("obj team = " + obj.getTeam());
				System.out.print(" this team = " + this.getTeam() + "\n");
				if (obj.getTeam() != this.getTeam() && obj.getTeam() != 0) {
					return true;
				}
			}
			return false;
		}
}
