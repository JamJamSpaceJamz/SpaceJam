package objects;


import game.Helper;
import game.SimpleTest;

import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Credit extends Obj
{
	public int amount;
	public float direction;
	private Color color;
	private int size;
	private float speed;
	final private float ACC;
	private boolean hasTarget;
	
	public Credit(float[] location, int direction, int amount, SimpleTest gameInst)
	{
		speed = (float) (Math.random());
		
		// hardcoded acceleration number
		ACC = 7;
		
//		System.out.println(amount);
		this.location = location;
		this.direction = direction;
		this.gameInst = gameInst;
		this.amount = amount;
		this.mass = 10;
		size = 2;
		shape = new Circle(location[0], location[1], size);
		velocity = new float[2];
		velocity[0] = (float) (speed * Math.cos(direction));
		velocity[1] = (float) (speed * Math.sin(direction));
		acceleration = new float[2];
		if (amount == 15)
			color = Color.lightGray;
		else if (amount == 30)
			color =  Color.blue;
		else if (amount == 45)
			color = Color.yellow;
		
	}

	public void changeDir(float dir)
	{
		this.direction = dir;
		acceleration[0] = (float) (ACC*Math.cos(dir));
		acceleration[1] = (float) (ACC*Math.sin(dir));
		hasTarget = true;
	}
	public void draw(Graphics gfloat) 
	{
		shape = new Circle(location[0], location[1], size);
		gfloat.setColor(color);
		gfloat.fill(shape);	
	}

	public void update(int delta)
	{
		checkBorders();
		velocity[0] += acceleration[0]*delta*.01f;
		velocity[1] += acceleration[1]*delta*.01f;
		location[0] += velocity[0]*delta*.01f;
		location[1] += velocity[1]*delta*.01f;
		if (!hasTarget)
		{
			acceleration[0] = -velocity[0];
			acceleration[1] = -velocity[1];
		}
		hasTarget = false;
	}
	
	private void checkBorders()
	{
		// the amount the asteroid is allowed out of bounds
		int outOfBounds = 3;
		int width = gameInst.container.getWidth() + outOfBounds;
		int height = gameInst.container.getHeight() + outOfBounds;
		
		if (location[0] > width)
			location[0] = -outOfBounds;
		if (location[0] < -outOfBounds)
			location[0] = width;
		
		if (location[1] > height)
			location[1] = -outOfBounds;
		if (location[1] < -outOfBounds)
			location[1] = height;
	}

	@Override
	public boolean collide(Obj hitter) 
	{
		if (hitter instanceof Ship)
		{
			Ship hit = (Ship) hitter;
			if (hit.credit(amount))
			{
//				System.out.println("removing");
				this.remove();
				
			}
			return false;
		}
		else if (hitter instanceof Base)
		{
			Base base = (Base) hitter;
			base.credit(amount);
			this.remove();
			return false;
		}
		else if (hitter instanceof Credit)
			return false;
		
		return true;
	}	
}
