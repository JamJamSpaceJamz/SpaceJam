package ships;

import java.util.ArrayList;

import game.Helper;
import game.List;
import game.SimpleTest;
import game.Team;
import game.Team.objectType;
import game.Constants;

import objects.Base;
import objects.Bullet;
import objects.Credit;
import objects.Obj;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

public abstract class Ship extends Obj
{
	public float[] acceleration;
	protected int delta, rotateSpd, credit;
	protected boolean turnLeft, turnRight, accelerate, stop, isFullCredits;
	// NOTE: ROTATION IS IN DEGREES
	protected float rotation, range, damage, speed, size;
	protected int capacity, cargo, weaponCoolDown, radarCoolDown; 
	protected int COOLDOWN;
	final protected float MAX_SPEED = Constants.ship_max_speed;
	protected ArrayList<Obj> closeObj, objectsInRange;

	public Ship (float[] spawn, float size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, Team team)
	{
		COOLDOWN = Constants.ship_cooldown;
		
		this.location = new float[2];
		this.location[0] = spawn[0];
		this.location[1] = spawn[1];
		this.velocity = new float[2];
		this.acceleration = new float[2];
		this.speed = speed;
		this.size = size;
		this.rotateSpd = rotateSpd;
		this.range = range;
		this.capacity = capacity;
		this.mass = Constants.ship_mass;
		this.health = health;
		this.team = team;
		this.damage = Constants.fighter_damage;
		
		this.cargo = 0;
		this.gameInst = inst;  
		this.rotation = initRotation(location);
		makeShape();
	}

	private float initRotation(float[] loc)
	{
		float rotate = directionTo(gameInst.container.getWidth() / 2, gameInst.container.getHeight() / 2);
		System.out.println(rotate);
		return rotate;
	}
	
	public void draw(Graphics g)
	{
		makeShape();

		g.setColor(getColor());
		if (gameInst.toggle == 1 || gameInst.toggle == 2)
			drawCargo(g);
		if (gameInst.toggle == 2)
		{
			Circle circle = new Circle(location[0], location[1], range);
			g.draw(circle);
		}
		g.fill(shape);
	}
	
	private void makeShape()
	{
		float[] points = new float[6];
		points[0] = (size*Helper.cos(0 + rotation) + location[0]);
		points[1] = (size*Helper.sin(0 + rotation) + location[1]);


		points[2] = (size*Helper.cos(130 + rotation) + location[0]);
		points[3] = (size*Helper.sin(130 + rotation) + location[1]);


		points[4] = (size*Helper.cos(230 + rotation) + location[0]);
		points[5] = (size*Helper.sin(230 + rotation) + location[1]);

		shape = new Polygon(points);
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
		if (!turnTo(directionTo(a)))
			stop(true);
		else if (distanceTo(a) > 0)
			accelerate(true);
		else
			stop(true);
	}
	
	protected void navigateTo(float x, float y)
	{
		if (!turnTo(directionTo(x, y)))
			stop(true);
		else if (distanceTo(x, y) > 0)
			accelerate(true);
		else
			stop(true);
	}
	
	protected void returnToBase()
	{
		navigateTo(team.getList(objectType.BASE).next.data);
	}
	
	public boolean turnTo(float angle)
	{
		return turnTo(angle, Constants.ship_default_turn_accuracy);
	}
	
	protected boolean turnTo(float angle, float accuracy)
	{
		// angle in degrees
		float angularDistance = angle - rotation;

		if (angularDistance < -180f) 
			angularDistance += 360f;
		if (angularDistance > 180f)
			angularDistance -= 360f;
		
		if (angularDistance > accuracy)
		{
			this.rotateRight(true);
			return false;
		}
		if (angularDistance < -accuracy)
		{
			this.rotateLeft(true);
			return false;
		}
		else
		{
			return true;
		}
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
	protected void fire()
	{
		if (weaponCoolDown == 0)
		{
			// reset cooldown
			weaponCoolDown = COOLDOWN;

			Bullet shot = new Bullet(location, rotation, range, damage, this.team);
			team.addUnit(objectType.BULLET, shot);
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

	public void remove()
	{
		while (credit > 0)
		{
			System.out.println("Ship removed. Credit: " + credit);
			int worth = 40;
			if (credit < 40) worth = credit;
			credit -= worth;
			
			int direction = (int) (Math.random() *360);
			float[] location = new float[2];
			location[0] = this.location[0] + Helper.cos(direction)*size*5.03f;
			location[1] = this.location[1] + Helper.sin(direction)*size*5.03f;
			Credit crd = new Credit(location, direction, worth, gameInst);
			
		}
		objInst.remove();
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