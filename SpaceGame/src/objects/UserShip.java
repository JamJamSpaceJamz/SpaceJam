package objects;

import game.Helper;
import game.SimpleTest;
import game.Team;

public class UserShip extends Ship
{
	public UserShip(float[] spawn, int size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, Team team)
	{
		super(spawn, size, speed, rotateSpd, range, capacity, health, inst, team);
	}
	
	// is called once a frame and updates the ship
	// with its new location
	@Override
	public void update(int dt)
	{
		delta = dt;
		radar();
		
		float d = .01f*delta;
		
		if (turnRight)
			rotation += rotateSpd*.01f*delta;
		if (turnLeft)
			rotation -= rotateSpd*.01f*delta;
		
		if (!checkBorders() && accelerate )
		{
			speed = (float)Math.sqrt(velocity[0]*velocity[0]+velocity[1]*velocity[1]);
			
			if (speed < MAX_SPEED)
			{
				acceleration[0] = (float) Helper.cos(rotation)*2; //speed;
				acceleration[1] = (float) Helper.sin(rotation)*2; //speed;
				stop(false);
				velocity[0] += acceleration[0]*delta*.01f;
				velocity[1] += acceleration[1]*delta*.01f;
			} else {
				stop(true);
			}
		}

		for(int i = 0; i < 2; i++)
		{           
			location[i] += velocity[i]*delta*.01f;
			if (stop)
			{
				velocity[i] -= velocity[i]/10 * d;
			}
		}
		
		if (weaponCoolDown > 0)
			--weaponCoolDown;
	}
	
	// changes acceleration to false or true
	// (needed because of the way keypresses work)
	public void accelerate(boolean acc)
	{
		accelerate = acc;
	}
	
	public void rotateLeft(boolean turn)
	{
		turnLeft = turn;    
	}
	
	public void rotateRight(boolean turn)
	{
		turnRight = turn;    
	}

	public void stop(boolean truth)
	{
		stop = truth;
	}
}