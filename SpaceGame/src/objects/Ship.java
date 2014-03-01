package objects;

import java.util.ArrayList;

import game.Helper;
import game.List;
import game.SimpleTest;
import game.Team;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

public abstract class Ship extends Obj
{
	public float[] acceleration;
	protected int delta, rotateSpd, size, credit;
	protected boolean turnLeft, turnRight, accelerate, stop, isFullCredits;
	// NOTE ROTATION IS IN DEGREES
	protected float rotation, range, damage, speed;
	protected int capacity, cargo, weaponCoolDown, radarCoolDown; 
	final protected int COOLDOWN;
	final protected float MAX_SPEED = 12;
	final protected float[] baseLocation = new float[2];
	protected ArrayList<Obj> closeObj, objectsInRange;

	public Ship (float[] spawn, int size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, Team team)
	{
		COOLDOWN = 5;
		location = spawn;
		// baseLocation assumes that the ship spawns inside the base.
		baseLocation[0] = location[0];
		baseLocation[1] = location[1];
		this.velocity = new float[2];
		acceleration = new float[2];
		this.speed = speed;
		this.size = size;
		this.rotateSpd = rotateSpd;
		this.range = range;
		this.capacity = capacity;
		this.mass = 75;
		this.health = health;
		this.team = team;
		damage = 30;
		rotation = 0;
		cargo = 0;
		gameInst = inst;  
	}

	public void draw(Graphics g)
	{
		float[] points = new float[6];
		points[0] = (size*Helper.cos(0 + rotation) + location[0]);
		points[1] = (size*Helper.sin(0 + rotation) + location[1]);


		points[2] = (size*Helper.cos(130 + rotation) + location[0]);
		points[3] = (size*Helper.sin(130 + rotation) + location[1]);


		points[4] = (size*Helper.cos(230 + rotation) + location[0]);
		points[5] = (size*Helper.sin(230 + rotation) + location[1]);

		shape = new Polygon(points);

		g.setColor(Color.green);
		drawCargo(g);
		g.fill(shape);
	}

	abstract public void accelerate(boolean acc);
	
	abstract public void rotateLeft(boolean turn);

	abstract public void rotateRight(boolean turn);

	abstract public void stop(boolean truth);
	
	// checks the objects around the ship and acts on them
	protected void radar()
	{
		// only check if the cooldown is zero
		if (radarCoolDown == 0)
		{
			// resets cooldown
			radarCoolDown = COOLDOWN;
			objectsInRange = objInRange();
			// only check for credits if ship is not full and
			if (cargo != capacity)
			{
				pullCredits(objectsInRange);
			}
		}
		// decrease cooldown
		radarCoolDown -= 1;
	}
	
	// is called once a frame and updates the ship
	// with its new location
	abstract public void update(int dt);

	private void pullCredits(ArrayList<Obj> inRange)
	{
		for (Obj obj: inRange)
		{
			if (obj instanceof Credit)
			{
				Credit crd = (Credit) obj;
				float dir = crd.directionTo(this);
				crd.changeDir(dir);
			}
		}
	}
	
	protected void navigateTo(Obj a)
	{
		float angularDistance = directionTo(a) - this.rotation;
		if (angularDistance > 3)
		{
			this.rotateRight(true);
			this.stop(true);
		}
		else if (angularDistance < -3)
		{
			this.rotateLeft(true);
			this.stop(true);
		}
		else if (distanceTo(a) > 0)
			accelerate(true);
		else
			stop(true);
	}
	
	protected void navigateTo(float x, float y)
	{
		float angularDistance = directionTo(x, y) - this.rotation;
		if (angularDistance > 3)
		{
			this.rotateRight(true);
			this.stop(true);
		}
		else if (angularDistance < -3)
		{
			this.rotateLeft(true);
			this.stop(true);
		}
		else if (distanceTo(x, y) > 0)
			accelerate(true);
		else
			stop(true);
	}
	
	protected void returnToBase()
	{
		navigateTo(baseLocation[0], baseLocation[1]);
	}

	private ArrayList<Obj> objInRange()
	{
		ArrayList<Obj> inRange = new ArrayList<Obj>();

		List<List<Obj>> list1 = gameInst.gameList.next;
		while(list1 != null)
		{
			List<Obj> obj1 = list1.data.next;
			while(obj1 != null)
			{
				if (this.distanceTo(obj1.data) < range && obj1.data != this)
					inRange.add(obj1.data);
				obj1 = obj1.next;
			}
			list1 = list1.next;
			if (list1 != null)
				obj1 = list1.data.next;
		}
		return inRange;
	}

	// creates a bullet and adds it to the game's bulletlist\
	// (future work: recoil?)
	public void fire()
	{
		if (weaponCoolDown == 0)
		{
			// reset cooldown
			weaponCoolDown = COOLDOWN;
			
			List<Obj> pointer = gameInst.bulletList;
			List<Obj> wrapper = new List<Obj>();
			Bullet shot = new Bullet(location, rotation, range, damage, wrapper, this.team);
			wrapper.data = shot; 
			wrapper.previous = pointer;
			wrapper.next = pointer.next;
			if (wrapper.next != null)
			{
				wrapper.next.previous = wrapper;
			}
			pointer.next = wrapper;
		} else {
			--weaponCoolDown;
		}
	}

	private void drawCargo(Graphics g)
	{
		float cargoSize = 3;
		float ringSize = 3.5f;
		for (int i = 0; i < cargo; i++)
		{
			float dir = 360/cargo * i + 360/cargo;
			Circle crgo = new Circle(location[0] + size*ringSize*Helper.cos(dir),
					location[1] + size*ringSize*Helper.sin(dir), cargoSize);
			g.fill(crgo);
		}
	}

	public boolean collide(Obj hitter)
	{
		if (hitter instanceof Base)
		{
			Base a = (Base) hitter;
			a.credit(credit);
			credit = 0;
			cargo = 0;
			isFullCredits = false;
			return false;
		}
		return true;
	}

	public boolean credit(int amount)
	{
		boolean full = capacity == cargo;
		isFullCredits = full;
		if (!full)
		{
			credit += amount;
			cargo++;
			if (cargo == capacity) 
				isFullCredits = true;
		}
		return !full;
	}
}