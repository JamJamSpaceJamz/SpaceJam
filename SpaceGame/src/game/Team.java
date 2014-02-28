package game;

import org.newdawn.slick.Color;

import objects.AutonomousShip;
import objects.Base;
import objects.Obj;
import objects.Ship;
import objects.UserShip;

public class Team 
{
	private final int Team;
	private List<Obj> bulletList, shipList, baseList, turretList;
	private SimpleTest gameInst;
	
	// Make sure to update this enum if more lists are added to this class.
	public enum objectType { BULLETS, SHIPS, BASES, TURRETS }
	
	public Team(int teamNum, SimpleTest gameInst)
	{
		Team = teamNum;
		this.gameInst = gameInst; 
		
		startingUnits();
		
	}
	
	private void startingUnits()
	{
		// Add a base. All initial values could/should be tweaked.
		final float[] BASESPAWN = {200f, 200f}; // TODO: Add method to set base location based on team number.
		final float BASESIZE = 30;
		final Color BASECOLOR = Color.green;
		Base mainBase = new Base(BASESIZE, BASESPAWN, BASECOLOR, gameInst, Team);
		baseList.add(mainBase);
		
		// Add three miner ships. Two autonomous; One user controlled.
		final int AUTOSHIPS = 2, USERSHIPS = 1, SHIP_SPEED = 3, SHIP_SIZE = 5;
		final int SHIP_ROTATION = 20, SHIP_CAPACITY = 15; // TODO: Set initial rotation based on spawn location.
		final float SHIP_RANGE = 100, SHIP_HEALTH = 30;
		float[] shipSpawn = {200f, 200f}; // TODO: Add method to set unique spawn locations for each ship.
		for (int i=0; i<AUTOSHIPS; i++)
		{
			Ship ship = new AutonomousShip(shipSpawn, SHIP_SIZE, SHIP_SPEED, SHIP_ROTATION,
										   SHIP_RANGE, SHIP_CAPACITY, SHIP_HEALTH, gameInst, Team);
			shipList.add(ship);
		}
		for (int i=0; i<USERSHIPS; i++)
		{
			Ship ship = new UserShip(shipSpawn, SHIP_SIZE, SHIP_SPEED, SHIP_ROTATION, 
					  				 SHIP_RANGE, SHIP_CAPACITY, SHIP_HEALTH, gameInst, Team);
			shipList.add(ship);
		}
		

	}
	
	public void addUnit(objectType a)
	{
		
	}
	
	public void removeUnit(objectType a)
	{
		
	}
	
	// Make sure to update this method if more lists are added to this class.
	public List<Obj> getList(objectType a)
	{
		switch(a)
		{
			case BULLETS: return bulletList;
			case SHIPS:	  return shipList;
			case BASES:	  return baseList;
			case TURRETS: return turretList;
			default:      return null;
		}
	}
	
	public int getTeam()
	{
		return Team;
	}
}
