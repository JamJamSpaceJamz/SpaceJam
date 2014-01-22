package objects;

import java.util.ArrayList;

import game.Helper;
import game.List;
import game.SimpleTest;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

public class Ship extends Obj
{
	public float[] acceleration;
	private int rotateSpd, speed, size, credit;
	private boolean turnLeft, turnRight, accelerate, stop;
	private float rotation, range, damage;
	private int capacity, cargo, radarCoolDown, RADARDOWN;
	private ArrayList<Obj> closeObj;
	private SimpleTest gameInst;

	public Ship (int size, int speed, int rotateSpd, float range, int capacity, float health, SimpleTest inst, boolean team)
	{
		RADARDOWN = 5;
		location = new float[2];
		location[0] = 200;
		location[1] = 200;
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

		// visual representation of the range of the ship
		//Circle circle = new Circle(location[0], location[1], range);
		//g.draw(circle);
	}

	public void rotateLeft(boolean turn)
	{
		turnLeft = turn;    
	}

	public void rotateRight(boolean turn)
	{
		turnRight = turn;    
	}

	public void stop(boolean truth)
	{
		stop = truth;
	}
	
	// checks the objects around the ship and acts on them
	private void radar()
	{
		// only check if the cooldown is zero
				if (radarCoolDown == 0)
				{
					// resets cooldown
					radarCoolDown = RADARDOWN;
					// only check for credits if ship is not full and
					if (cargo != capacity)
					{
						ArrayList<Obj> inRange = objInRange();
						pullCredits(inRange);
					}
				}
				// decrease cooldown
				radarCoolDown -= 1;
	}
	
	// is called once a frame and updates the ship
	// with its new location
	public void update(int delta)
	{
		radar();
		float d = .01f*delta;
		if (turnRight)
			rotation += rotateSpd*.01f*delta;
		if (turnLeft)
			rotation -= rotateSpd*.01f*delta;

		if (!checkBorders() && accelerate )
		{
			acceleration[0] = (float) Helper.cos(rotation)*2; //speed;
			acceleration[1] = (float) Helper.sin(rotation)*2;//speed;

			velocity[0] += acceleration[0]*delta*.01f;
			velocity[1] += acceleration[1]*delta*.01f;
		}

		for(int i = 0; i < 2; i++)
		{           
			location[i] += velocity[i]*delta*.01f;
			if (stop)
			{
				velocity[i] -= velocity[i]/10 * d;
			}
		}
	}

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
	private boolean checkBorders()
	{
		boolean check = false;
		int width = gameInst.container.getWidth();
		int height = gameInst.container.getHeight();

		if (location[0] >= width)
		{
			check = true;
			if (velocity[0] > 0)
				velocity[0] *= -.5f;
		}
		if (location[0] <= 0)
		{
			check = true;
			if (velocity[0] < 0)
				velocity[0] *= -.5f;
		}

		if (location[1] >= height)
		{
			check = true;
			if (velocity[1] > 0)
				velocity[1] *= -.5f;
		}
		if (location[1] <= 0)
		{
			check = true;
			if (velocity[1] < 0)
				velocity[1] *= -.5f;
		}
		return check;
	}

	// creates a bullet and adds it to the game's bulletlist\
	// (future work: recoil?)
	public void fire()
	{

		int counter = 0;
		List<Obj> pointer = gameInst.bulletList;
		List<Obj> wrapper = new List<Obj>();
		Bullet shot = new Bullet(location, rotation, range, damage, wrapper);
		wrapper.data = shot; 
		wrapper.previous = pointer;
		wrapper.next = pointer.next;
		if (wrapper.next != null)
		{
			wrapper.next.previous = wrapper;
		}
		pointer.next = wrapper;
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

	// changes acceleration to false or true
	// (needed because of the way keypresses work)
	public void accelerate(boolean acc)
	{
		accelerate = acc;
	}

	public boolean collide(Obj hitter)
	{
		if (hitter instanceof Base)
		{
			Base a = (Base) hitter;
			a.credit(credit);
			credit = 0;
			cargo = 0;
			return false;
		}
		return true;
	}

	public boolean credit(int amount)
	{
		boolean full = capacity == cargo;
		if (!full)
		{
			credit += amount;
			cargo++;
		}
		System.out.println(!full);
		return !full;
	}
}