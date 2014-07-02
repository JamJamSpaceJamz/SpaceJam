package ships;

import objects.Asteroid;
import objects.Obj;
import game.Helper;
import game.SimpleTest;
import game.Team;
import game.Constants;

public abstract class AutonomousShip extends Ship
{
	protected final int SEARCH_TIMER = Constants.autoship_search_timer;
	protected int timer = 0;
	protected float dir = 0;
	// Initialize searchDirection with a random direction between -1 and +1
	protected float[] searchDirection = { (float)(2.0*Math.random() - 1.0), (float)(2.0*Math.random() - 1.0) };
	
	public AutonomousShip (float[] spawn, int size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, Team team)
	{
		// Use Ship constructor.
		super(spawn, size, speed, rotateSpd, range, capacity, health, inst, team);
	}
		
	protected void attackTarget(Obj target)
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
	
	// TODO: this method should be combined with asteroidInRange()
	protected boolean enemyInRange() {
		for (Obj obj: objectsInRange) {
			if (obj.getTeam() != this.getTeam() && obj.getTeam() != 0) {
				return true;
			}
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
	
	public void accelerate(boolean acc)
	{
		if (acc)
			accelerate();
		else
			accelerate = acc;
	}
	
	public void accelerate()
	{		
		accelerate = true;
		

		if (speed <= MAX_SPEED)
		{
			acceleration[0] = (float) Helper.cos(rotation)*2; //speed;
			acceleration[1] = (float) Helper.sin(rotation)*2; //speed;
			
			velocity[0] += acceleration[0]*delta*Constants.game_delta_scale;
			velocity[1] += acceleration[1]*delta*Constants.game_delta_scale;
			
			setLocation();
		}
		else
			stop();
	}

	public void rotateLeft(boolean turn)
	{
		if (turn)
			rotateLeft();
		else
			turnLeft = turn;
	}
	
	public void rotateLeft()
	{
		turnLeft = true;
		rotation -= rotateSpd * Constants.game_delta_scale * delta;
		if (rotation < -360)
			rotation += 360;
	}
	
	public void rotateRight(boolean turn)
	{
		if (turn)
			rotateRight();
		else 
			turnRight = turn;
	}
	
	public void rotateRight()
	{
		turnRight = true;
		rotation += rotateSpd * Constants.game_delta_scale * delta;
		if (rotation > 360)
			rotation -= 360;
	}
	
	public void stop(boolean truth)
	{
		if (truth)
			stop();
		else 
			stop = truth;
	}
	
	public void stop()
	{
		stop = true;
		
		velocity[0] -= velocity[0]/10 * delta*.01f;
		velocity[1] -= velocity[1]/10 * delta*.01f;
		
		setLocation();
	}
	
	public void setLocation()
	{
		checkBorders();
		
		speed = (float) Math.sqrt(velocity[0]*velocity[0] + velocity[1]*velocity[1]);
		
		location[0] += velocity[0]*delta*Constants.game_delta_scale;
		location[1] += velocity[1]*delta*Constants.game_delta_scale;
	}
	
	// This method allows the user to set a speed for the ship
	public void cruiseControl(double desiredSpeed)
	{
		if (speed < desiredSpeed)
			accelerate();
		else if (speed > desiredSpeed)
			stop();
		else 
			setLocation();
	}
}