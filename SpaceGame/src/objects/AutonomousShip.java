package objects;

import game.Helper;
import game.SimpleTest;

public class AutonomousShip extends Ship
{
	public AutonomousShip (int size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, boolean team)
	{
		// Use Ship constructor.
		super(size, speed, rotateSpd, range, capacity, health, inst, team);
	}
	
	@Override
	public void update(int dt)
	{
		delta = dt;
		
		radar();
		// fly to closest asteroid until a certain distance away
		// shoot asteroid. repeat
		if (asteroidInRange())
		{
			System.out.print("Asteroid in range\t");
			Asteroid target = findClosestAsteroid();
			attackAsteroid(target);
		} else {
			accelerate();
		}
	}
	
	protected void attackAsteroid(Asteroid target)
	{
		stop(); // Make sure the asteroid does not go out of range
		float angularDistance = directionTo(target) - rotation;
		if (angularDistance > 1.5)
		{
			System.out.println("Aiming\t" + angularDistance);
			rotateRight();
		}
		else if (angularDistance < -1.5)
		{
			System.out.println("Aiming\t" + angularDistance);
			rotateLeft();
		}
		else
		{
			System.out.println("Attacking\t" + angularDistance);
			fire();
		}
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
		System.out.print("Target acquired\t\t");
		return retAst;
	}
	
	public void accelerate(boolean acc)
	{
		accelerate();
	}
	
	public void accelerate()
	{		
		if (!this.checkBorders())
		{
			acceleration[0] = (float) Helper.cos(rotation)*2; //speed;
			acceleration[1] = (float) Helper.sin(rotation)*2; //speed;
		
			velocity[0] += acceleration[0]*delta*.01f;
			velocity[1] += acceleration[1]*delta*.01f;
			
			setLocation();
		}
	}

	public void rotateLeft(boolean turn)
	{
		rotateLeft();
	}
	
	public void rotateLeft()
	{
		rotation -= rotateSpd * .01f * delta;
	}
	
	public void rotateRight(boolean turn)
	{
		rotateRight();
	}
	
	public void rotateRight()
	{
		rotation += rotateSpd * .01f * delta;
	}
	
	public void stop(boolean truth)
	{
		stop();
	}
	
	public void stop()
	{
		velocity[0] -= velocity[0]/10 * delta*.01f;
		velocity[1] -= velocity[1]/10 * delta*.01f;
		
		setLocation();
	}
	
	public void setLocation()
	{
		location[0] += velocity[0]*delta*.01f;
		location[1] += velocity[1]*delta*.01f;
	}
}