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
		hunt();
	}
	
	// Search the map for a new asteroid to shoot
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
	
	protected void findAsteroids()
	{
		cruiseControl(10);
	}
	
	protected void attackAsteroid(Asteroid target)
	{
		stop(); // Make sure the asteroid does not go out of range
		float angularDistance = directionTo(target) - rotation;
		if (angularDistance > 5)
			rotateRight();
		else if (angularDistance < -5)
			rotateLeft();
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
		speed = (float) Math.sqrt(velocity[0]*velocity[0] + velocity[1]*velocity[1]);
		
		location[0] += velocity[0]*delta*.01f;
		location[1] += velocity[1]*delta*.01f;
	}
	
	// This method allows the user to set a speed for the ship
	public void cruiseControl(double desiredSpeed)
	{
		if (desiredSpeed <= MAX_SPEED && speed < desiredSpeed)
			accelerate();
		else if (speed > MAX_SPEED || speed > desiredSpeed)
			stop();
		else 
			setLocation();
	}
}