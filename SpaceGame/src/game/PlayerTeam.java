package game;

import objects.AutonomousShip;
import objects.Base;
import objects.Obj;
import objects.Ship;
import objects.UserShip;

import org.newdawn.slick.Color;

public class PlayerTeam extends Team
{
	public PlayerTeam(int teamNum, SimpleTest gameInst) 
	{  
		super(teamNum, gameInst); 
	}

	@Override
	protected void startingUnits() 
	{
		addUnit(objectType.BASE);
		
		// Add three miner ships. Two autonomous; One user controlled.
		final int AUTOSHIPS = 2, USERSHIPS = 1;
		addUnit(objectType.AUTOSHIP, AUTOSHIPS);
		addUnit(objectType.USERSHIP, USERSHIPS);	
	}
	
	@Override // Add a generic objects that will have a known spawn location.
	protected void addUnit(objectType a, int quantity) 
	{
		switch(a)
		{
			case SHIP:
			case AUTOSHIP:
				addAutoShip(quantity);
				break;
			case USERSHIP:
				addUserShip(quantity);
				break;
			case BASE:
				addBase();
				break;
			case BULLET:
				addBullet(quantity);
		}
	}
	
	// Add objects that have a spawn location that is dependent upon another object
	protected void addUnit(objectType a, int quantity, Obj thing)
	{
		
	}
	
	private void addBullet(int quantity)
	{
		// TODO: David, figure this out.
	}
	
	private void addAutoShip(int quantity)
	{
		final int SHIP_SPEED = 3, SHIP_SIZE = 5;
		final int SHIP_ROTATION = 20, SHIP_CAPACITY = 15; // TODO: Set initial rotation based on spawn location.
		final float SHIP_RANGE = 100, SHIP_HEALTH = 30;
		float[] shipSpawn = {200f, 200f}; // TODO: Add method to set unique spawn locations for each ship.
		for (int i=0; i<quantity; i++)
		{
			Ship ship = new AutonomousShip(shipSpawn, SHIP_SIZE, SHIP_SPEED, SHIP_ROTATION,
										   SHIP_RANGE, SHIP_CAPACITY, SHIP_HEALTH, gameInst, this);
			shipList.add(ship);
		}
	}
	
	private void addUserShip(int quantity)
	{
		final int SHIP_SPEED = 3, SHIP_SIZE = 5;
		final int SHIP_ROTATION = 20, SHIP_CAPACITY = 15; // TODO: Set initial rotation based on spawn location.
		final float SHIP_RANGE = 100, SHIP_HEALTH = 30;
		float[] shipSpawn = {200f, 200f}; // TODO: Add method to set unique spawn locations for each ship.
		for (int i=0; i<quantity; i++)
		{
			Ship ship = new UserShip(shipSpawn, SHIP_SIZE, SHIP_SPEED, SHIP_ROTATION,
										   SHIP_RANGE, SHIP_CAPACITY, SHIP_HEALTH, gameInst, this);
			shipList.add(ship);
		}
	}
	
	private void addBase()
	{
		// Add a base. All initial values could/should be tweaked.
		final float[] BASESPAWN = {200f, 200f}; // TODO: Add method to set base location based on team number.
		final float BASESIZE = 30;
		final Color BASECOLOR = Color.green;
		Base mainBase = new Base(BASESIZE, BASESPAWN, BASECOLOR, gameInst, this);
		baseList.add(mainBase);
	}
}

