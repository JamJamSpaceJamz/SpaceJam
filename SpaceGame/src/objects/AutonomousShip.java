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
		float d = .01f*delta;
		
		// Put autonomous code here!
		
		accelerate(true);
	}
	
	public void accelerate(boolean acc)
	{
		accelerate = acc;
		if (!this.checkBorders() && accelerate )
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
		
	}
	
	public void rotateRight(boolean turn)
	{
		
	}
	
	public void stop(boolean truth)
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