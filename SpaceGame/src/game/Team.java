package game;

import org.newdawn.slick.Color;

import ships.AutonomousShip;
import objects.Base;
import objects.Obj;
import ships.Ship;
import ships.UserShip;

public abstract class Team 
{
	protected final int Team;
	protected List<Obj> astList, baseList, bulletList, shipList, turretList;
	protected SimpleTest gameInst;
	
	// Make sure to update this enum if more lists are added to this class.
	public enum objectType { ASTEROID, AUTOSHIP, BASE, BULLET, SHIP, TURRET, USERSHIP }
	
	public Team(int teamNum, SimpleTest gameInst)
	{
		Team = teamNum;
		this.gameInst = gameInst; 
		
		startingUnits();
	}
	
	protected abstract void startingUnits();
	
	protected abstract void addUnit(objectType a, int quantity);
	
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
			case ASTEROID:return astList;
			case AUTOSHIP:return shipList;
			case BASE:	  return baseList;
			case BULLET:  return bulletList;
			case SHIP:	  return shipList;
			case TURRET:  return turretList;
			case USERSHIP:return shipList;
			default:      return null;
		}
	}
	
	public int getTeam()
	{
		return Team;
	}
}
