package ships;

import game.SimpleTest;

public class Fighter  extends AutonomousShip
{
	public Fighter(boolean team, SimpleTest inst) 
	{
		// size, speed, rotateSpd, range, capacity, health, inst, team
		super(5, 3, 20, 100, 15, 30, inst, team); 
	}

	@Override
	public void update(int dt) {
	
		
	}
}
