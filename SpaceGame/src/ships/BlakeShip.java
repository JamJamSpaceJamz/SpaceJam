package ships;

import game.SimpleTest;
import game.Team;
	
import objects.Asteroid;
import objects.Obj;
import objects.Credit;
	
	public class BlakeShip extends Fighter {
		public BlakeShip(float[] spawn, Team team, SimpleTest inst) {
			// size, speed, rotateSpd, range, capacity, health, inst, team
			super(spawn, team, inst); 
		}
	
	@Override
	public void update(int dt) {
		delta = dt;
	
		radar();

		if (isFullCredits)
			returnToBase();
		else	
			hunt();
	}
	
	// Destroy asteroids around the map
	protected void hunt() {
		if (enemyInRange()) {
			Obj target = findClosestEnemy();
			if (target != null && !(target instanceof Credit))
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
			} else if (dir - rotation < -3) {
				rotateLeft();
				stop();
			} else {
				searchDirection[0] = (float)(2.0*Math.random() - 1.0) + this.location[0];
				searchDirection[1] = (float)(2.0*Math.random() - 1.0) + this.location[1];
				dir = directionTo(searchDirection[0], searchDirection[1]);
				timer = 0;
			}
	
		} else {
			cruiseControl(10);
			++timer;
		}
	}
	
	protected void attack(Obj target) {
		stop(); // Make sure the asteroid does not go out of range
		if (!turnTo(directionTo(target)));
		else
			fire();
	}
}
	
	
	
