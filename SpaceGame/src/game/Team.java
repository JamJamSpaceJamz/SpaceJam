package game;

import org.newdawn.slick.Color;

import game.Constants;

import objects.Base;
import objects.Obj;

import ships.AutonomousShip;
import ships.Ship;
import ships.UserShip;

public abstract class Team 
{
	protected final int team;
	protected SimpleTest gameInst;
	protected List<Obj> allUnits, astList, baseList, bulletList, creditList, shipList, turretList;
	protected int credits;
	
	// Make sure to update this enum if more lists are added to this class.
	public enum objectType { ASTEROID, AUTOSHIP, BASE, BULLET, CREDIT, SHIP, TURRET, USERSHIP }
	
	public Team(int teamNum, SimpleTest gameInst)
	{
		// start with the ability to buy ship
		credits = Constants.team_initial_credits;
		team = teamNum;
		this.gameInst = gameInst; 
		this.allUnits = new List<Obj>();
		
		startingUnits();
	}
	
	protected abstract void startingUnits();
	
	protected abstract void addUnit(objectType a, int quantity, Obj thing);
	
	protected void updateAllUnits(Obj thing)
	{
		allUnits.add(thing);
	}
	
	public void addUnit(objectType a, Obj thing)
	{
		addUnit(a, 1, thing);
	}
	
	public void addUnit(objectType a)
	{
		addUnit(a, 1, null);
	}
	
	public void addUnit(objectType a, int quantity)
	{
		addUnit(a, quantity, null);
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
			case CREDIT:  return creditList;
			case SHIP:	  return shipList;
			case TURRET:  return turretList;
			case USERSHIP:return shipList;
			default:      return null;
		}
	}
	
	public List<Obj> getAllUnits()
	{
		return allUnits;
	}
	
	public int getTeam()
	{
		return team;
	}	
}
