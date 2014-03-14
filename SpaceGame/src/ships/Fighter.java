package ships;

import game.SimpleTest;
import game.Team;

public class Fighter  extends AutonomousShip
{
	public Fighter(float[] spawn, Team team, SimpleTest inst) 
	{
		// size, speed, rotateSpd, range, capacity, health, inst, team
		super(spawn, 5, 3, 20, 100, 15, 30, inst, team); 
	}

	@Override
	public void update(int dt) {
	
		
	}
}
