package game;

import ships.AutonomousShip;
import objects.Base;
import objects.Bullet;
import objects.Obj;
import ships.Ship;
import objects.Turret;
import ships.UserShip;
import ships.Fighter;
import ships.BlakeShip;

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
		this.baseList   = new List<Obj>();
		this.shipList   = new List<Obj>();
		this.bulletList = new List<Obj>();
		this.turretList = new List<Obj>();
		
		addUnit(objectType.BASE);
		
		// Add three miner ships. Two autonomous; One user controlled.
		final int AUTOSHIPS = 5, USERSHIPS = 0;
		addUnit(objectType.AUTOSHIP, AUTOSHIPS);
		addUnit(objectType.USERSHIP, USERSHIPS);	
	}
	
	@Override 
	protected void addUnit(objectType a, int quantity, Obj thing)
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
				addBullet(thing);
				break;
			case TURRET:
				addTurret(thing);
				break;
		}
	}
	
	private void addTurret(Obj thing)
	{
		if (thing instanceof Turret)
		{
			this.getList(objectType.TURRET).add(thing);
			this.updateAllUnits(thing);
		}
	}
	
	private void addBullet(Obj thing)
	{
		if (thing instanceof Bullet)
		{
			this.getList(objectType.BULLET).add(thing);
			this.updateAllUnits(thing);
		}
	}
	
	private void addAutoShip(int quantity)
	{
//		final int SHIP_SPEED = 3, SHIP_SIZE = 5;
//		final int SHIP_ROTATION = 20, SHIP_CAPACITY = 15; // TODO: Set initial rotation based on spawn location.
//		final float SHIP_RANGE = 100, SHIP_HEALTH = 30;
		for (int i=0; i<quantity; i++)
		{
			float[] shipSpawn = genShipSpawnLocation();
			
			Ship ship = new BlakeShip(shipSpawn, this, gameInst);
			this.getList(objectType.AUTOSHIP).add(ship);
			this.updateAllUnits(ship);
		}
	}
	
	private void addUserShip(int quantity)
	{
		final int SHIP_SPEED = 3, SHIP_SIZE = 5;
		final int SHIP_ROTATION = 20, SHIP_CAPACITY = 15; // TODO: Set initial rotation based on spawn location.
		final float SHIP_RANGE = 100, SHIP_HEALTH = 30;
		
		for (int i=0; i<quantity; i++)
		{
			float[] shipSpawn = genShipSpawnLocation();
			
			Ship ship = new UserShip(shipSpawn, SHIP_SIZE, SHIP_SPEED, SHIP_ROTATION,
										   SHIP_RANGE, SHIP_CAPACITY, SHIP_HEALTH, gameInst, this);
			this.getList(objectType.USERSHIP).add(ship);
			this.updateAllUnits(ship);
		}
	}
	
	private void addBase()
	{
		// Add a base. All initial values could/should be tweaked.
		final float[] BASESPAWN = baseSpawnLocation();
		final float BASESIZE = 30;
		final Color BASECOLOR = Color.green;
		Base mainBase = new Base(BASESIZE, BASESPAWN, BASECOLOR, gameInst, this);
		this.getList(objectType.BASE).add(mainBase);
		this.updateAllUnits(mainBase);
	}
	
	private float[] genShipSpawnLocation() {
		float SPAWN_DIST = 70;
		
		// Get location of this team's base.
		float[] retVal = new float[2];
		Base thisBase = (Base) this.baseList.next.data;
		float[] baseLoc = thisBase.getLocation();
		
		// Generate random angle for spawn relative to base.
		float spawnAngle = 360f * (float) Math.random();

		// Set spawn location.
		retVal[0] = baseLoc[0] + SPAWN_DIST * Helper.cos(spawnAngle);
		retVal[1] = baseLoc[1] + SPAWN_DIST * Helper.sin(spawnAngle);
		
		return retVal;
	}
	
	private float[] baseSpawnLocation() {
		int teamNum = this.getTeam();
		float[] spawn = new float[2];
		float width  = this.gameInst.container.getWidth();
		float height = this.gameInst.container.getHeight();
		float dx =  width / 8f;
		float dy = height / 8f;
		
		switch(teamNum) {
			case 1:
				spawn[0] = dx;
				spawn[1] = dy;
				return spawn;
			case 2:
				spawn[0] =  width - dx;
				spawn[1] = height - dy;
				return spawn;
			case 3:
				spawn[0] = width - dx;
				spawn[1] = dy;
				return spawn;
			case 4:
				spawn[0] = dx;
				spawn[1] = height - dy;
				return spawn;
			default:
				return null;
		}
	}
}

