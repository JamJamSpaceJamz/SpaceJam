package ships;

import game.SimpleTest;
import game.Team;

public class Miner extends AutonomousShip
{
	public Miner(float[] spawn, Team team, SimpleTest inst) 
	{
		// size, speed, rotateSpd, range, capacity, health, inst, team
		super(spawn, 5, 3, 20, 100, 15, 30, inst, team); 
	}

	@Override
	public void update(int dt) {
	
		
	}

}
