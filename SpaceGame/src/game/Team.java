package game;

import org.newdawn.slick.Color;

import objects.AutonomousShip;
import objects.Base;
import objects.Obj;
import objects.Ship;
import objects.UserShip;

public abstract class Team 
{
	private final int Team;
	private List<Obj> astList, baseList, bulletList, shipList, turretList;
	private SimpleTest gameInst;
	
	// Make sure to update this enum if more lists are added to this class.
	public enum objectType { ASTERIOD, AUTOSHIP, BASE, BULLET, SHIP, TURRET, USERSHIP }
	
	public Team(int teamNum, SimpleTest gameInst)
	{
		Team = teamNum;
		this.gameInst = gameInst; 
		
		startingUnits();
		
	}
	
	private abstract void startingUnits()
	{
//		addUnit(objectType.BASE);
//		
//		// Add three miner ships. Two autonomous; One user controlled.
//		final int AUTOSHIPS = 2, USERSHIPS = 1;
//		addUnit(objectType.AUTOSHIP, AUTOSHIPS);
//		addUnit(objectType.USERSHIP, USERSHIPS);	
	}
	
	public void addUnit(objectType a, int quantity)
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
		}
	}
	
	public void addUnit(objectType a)
	{
		addUnit(a, 1);
	}
	
	public void removeUnit(Obj a)
	{
		a.remove();
	}
	
	// Make sure to update this method if more lists are added to this class.
	public List<Obj> getList(objectType a)
	{
		switch(a)
		{
			case BULLET:  return bulletList;
			case SHIP:	  return shipList;
			case BASE:	  return baseList;
			case TURRET:  return turretList;
			default:      return null;
		}
	}
	
	public int getTeam()
	{
		return Team;
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
	
	private void addAutoShip()
	{
		addAutoShip(1);
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
	
	private void addUserShip()
	{
		addUserShip(1);
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
